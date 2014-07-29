#!/usr/bin/python
# -*- coding: utf-8 -*-
#
# Copyright 2014 YooWaan. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""description


  install libraries

  pip install urllib
  pip install urllib5
"""

__author__ = 'YooWaan'

import os, stat, sys
import random
import httplib
import re
import fileinput
import time
from urlparse import urlparse

class CmdOptions:

    def __init__(self, depth = 3, csvfile = None, verbose = False, igpfx = ['mail'], igsfx = ['png', 'jpg', 'ico', 'xls', 'xlsx', 'css']):
        """ Init CmdOptions
        """
        self._csvfile = csvfile
        self._verbose = verbose
        self._ignore_prefix = igpfx
        self._ignore_suffix = igsfx
        self._depth = depth

    def dump(self):
        return str(self._csvfile) + ", " + str(self._verbose) + ", " + str(self._ignore_prefix) + ", " + str(self._ignore_suffix)

    @property
    def depth(self):
        return self._depth

    @depth.setter
    def depth(self, dth):
        self._depth = dth

    @property
    def csv(self):
        return self._csvfile

    @csv.setter
    def csv(self, cf):
        self._csvfile = cf;

    @property
    def verbose(self):
        return self._verbose

    @verbose.setter
    def verbose(self, flg):
        self._verbose = flg

    @property
    def ignore_prefix(self):
        return self._ignore_prefix

    @ignore_prefix.setter
    def ignore_prefix(self, pfx):
        self._ignore_prefix = pfx

    @property
    def ignore_suffix(self):
        return self._ignore_suffix

    @ignore_suffix.setter
    def ignore_suffix(self, sfx):
        self._ignore_suffix = sfx


class CrawlRss:

    def __init__(self, opt):
        """ Init CrawlRss
        """
        self._depth = 3 if opt is None  else opt.depth
        self._verbose = False if opt is None else opt.verbose
        self._ignore_prefix = ['mail'] if opt is None else opt.ignore_prefix
        self._ignore_suffix = ['png', 'jpg', 'ico', 'xls', 'xlsx', 'css', 'pdf'] if opt is None else opt.ignore_suffix
        self._csv_file = opt.csv

        self._user_agents = ["Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:9.0.1) Gecko/20100101 Firefox/9.0.1", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; ARM; Trident/6.0)"]

        self._max_check_pages = 2000

    def is_ignore_prefix(self, url):
        for pfx in self._ignore_prefix:
            if url.startswith(pfx):
                return True
        return False

    def is_ignore_suffix(self, url):
        for sfx in self._ignore_suffix:
            if url.endswith(sfx):
                return True
        return False

    def verbose(self,msg):
        if self._verbose:
            print msg

    def rss_str(self, rss_lst):
        s = ""
        for rss in rss_lst:
            if (len(s) > 0):
                s = s + "|"
            s = s + rss
        return s

    def run(self,url):
        crawled_urls = []
        rss_lst = []
        self.crawl(self.get_base_url(url), url, crawled_urls, rss_lst, 0)
        txt = url + "," + self.rss_str(rss_lst) +"," + str(len(crawled_urls))
        if self._csv_file == None:
            print txt
        else:
            f = open(self._csv_file, "a")
            f.write(txt + os.linesep)
            f.close()

    def crawl(self,starturl, url, crawled_urls, rss_lst, depth):
        self.verbose("urls=" + str(len(crawled_urls)) + ", rss=" + str(len(rss_lst)) + ",cawled?: " + str(url in crawled_urls) + ", DEPTH: " + str(depth) + ", url=" + str(url))
        if (len(crawled_urls) == self._max_check_pages):
            return
        if self._depth == depth or url in crawled_urls:
            return
        if self.is_ignore_suffix(url) or self.is_ignore_prefix(url):
            self.verbose("ignore prefix or suffix" + url)
            return
        crawled_urls.append(url)
        try:
            self.verbose("CHECK: " + url )
            body, sts, isrss = self.communicate(url)
            if sts == 301 and depth == 0:
                self.crawl(self.get_base_url(body), body, crawled_urls, rss_lst, depth)
            if sts != 200:
                return
            # self.verbose(body)
            if isrss:
                self.verbose('RSS ----->' + url)
                rss_lst.append(url)
                return
            links = re.findall(r'href=[\'"]?([^\'" >]+)', body)
            #self.verbose("links=" + str(links))
            for href in links:
                if href.startswith('#') or href.startswith('javascript:'):
                    continue

                href = self.concat_href(starturl, url, href)
                self.verbose("href=" + href)

                if href.startswith(starturl) == False:
                    continue
                #time.sleep(0.3)
                self.crawl(starturl, href, crawled_urls, rss_lst, depth+1)
        except:
            self.verbose("err:" + str(sys.exc_info()[0]))
            return


    def communicate(self, url):
        urlresult = urlparse(url)
        secure = urlresult.scheme.startswith('https')
        if urlresult.port is None:
            port = 443 if secure else 80
        else:
            port = urlresult.port
        conn = httplib.HTTPSConnection(urlresult.netloc,port) if secure else httplib.HTTPConnection(urlresult.netloc,port)
        # TODO: check suffix
        conn.request("GET", urlresult.path, headers = { 'User-Agent' : self._user_agents[ random.randint(0, len(self._user_agents)-1) ] });
        res = conn.getresponse()
        sts = res.status
        if (sts == 301):
            return res.getheader('location'), sts, False
        body = res.read()
        #self.verbose("headers=" + str(res.getheaders()))
        contenttype = res.getheader('Content-Type')
        #self.verbose("sts=" + str(res.status) + ", content type=" + contenttype + "body=¥n" + body)
        conn.close()
        return body, sts, contenttype.find("xml") != -1

    def get_base_url(self, url):
        result = urlparse(url)
        return result.scheme + "://" + result.netloc

    def concat_href(self, starturl, url, href):
        if href.startswith("http"):
            return href

        if href.startswith('/'):
            return starturl + href

        if (url.endswith("/") == False):
            url =  url[0: url.rindex('/')+1]

        return url + href


def exec_crawl(opts, url):
    """
    """
    crawler = CrawlRss(opts)
    crawler.run(url)

def parse_argv(opts):
    url = None
    for i,arg in enumerate(sys.argv):
        if "-url" == arg:
            url = sys.argv[i+1]
        if "-v" == arg:
            opts.verbose = True
        if "-csv" == arg:
            opts.csv = sys.argv[i+1]
        if "-depth" == arg:
            opts.depth = int(sys.argv[i+1])
        if "-pfx" == arg:
            opts.ignore_prefix = sys.argv[i+1].split(",")
        if "-sfx" == arg:
            opts.ignore_suffix = sys.argv[i+1].split(",")
        if "-h" == arg:
            return True, None
    return False, url

#
# Main
#


# arguments
opts = CmdOptions()
help , check_url = parse_argv(opts)
if help:
    print """
Usage
  -v      verbose
  -csv    specify csv output file name
  -depth  
  -pfx    ignore prefix seprated by ,
  -sfx    ignore suffix seprated by ,
    """
    sys.exit(1)

# execute
mode = os.fstat(0).st_mode
if stat.S_ISFIFO(mode) or stat.S_ISREG(mode):
    for line in fileinput.input():
        line = line.rstrip()
        if (line.startswith("#") == False):
            exec_crawl(opts, line)

else:
    exec_crawl(opts, check_url)

