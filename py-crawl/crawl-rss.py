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
import httplib
import re
import fileinput
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
        self._ignore_preifx = pfx

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
        self._ignore_suffix = ['png', 'jpg', 'ico', 'xls', 'xlsx', 'css'] if opt is None else opt.ignore_suffix

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

    def run(self,url):
        crawled_urls = []
        rss_lst = []
        self.crawl(url, url, crawled_urls, rss_lst, 0)
        print url + "," + rss_lst

    def crawl(self,starturl, url, crawled_urls, rss_list, depth):
        self.verbose("cawled?: " + str(url in crawled_urls) + ", DEPTH: " + str(depth))
        if self._depth == depth or url in crawled_urls:
            return
        if self.is_ignore_suffix(url) or self.is_ignore_prefix(url):
            self.verbose("ignore prefix or suffix" + url)
            return
        crawled_urls.append(url)
        try:
            self.verbose("CHECK: " + url )
            body, sts, isrss = self.communicate(url)
            if sts != 200:
                return
            # self.verbose(body)
            if isrss:
                self.verbose('RSS ----->' + url)
                rss_list.push(url)
                return
            links = re.findall(r'href=[\'"]?([^\'" >]+)', body)
            for href in links:
                if href.startswith('#'):
                    continue
                if href.startswith("http") == False:
                    if href.startswith('/') and url.endswith('/'):
                        href = href[1:]
                    href = url + href
                if href.startswith(starturl) == False:
                    continue
                self.crawl(starturl, href, crawled_urls, rss_list, depth+1)
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
        conn.request("GET", urlresult.path);
        res = conn.getresponse()
        body = res.read()
        sts = res.status
        if sts != 200:
            return None, None, None
        contenttype = res.getheader('Content-Type')
        conn.close()
        return body, sts, contenttype.find("xml") != -1


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
        print "[" + line + "]" + str(fileinput.isstdin())
else:
    exec_crawl(opts, check_url)

