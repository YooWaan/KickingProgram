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

import sys
import httplib
import re
from urlparse import urlparse

class CrawlRss:

    def __init__(self, depth, verbose_flag = False):
        """ Init CrawlRss
        """
        self._depth = depth
        self._verbose = verbose_flag
        self._ignore_prefix = ['mail']
        self._ignore_suffix = ['png', 'jpg', 'ico', 'xls', 'xlsx', 'css']

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
        self.crawl(url, url, crawled_urls, 0)

    def crawl(self,starturl, url, crawled_urls, depth):
        self.verbose("cawled?" + str(url in crawled_urls) + ", DEPTH: " + str(depth))
        if self._depth == depth or url in crawled_urls:
            return
        if self.is_ignore_suffix(url) or self.is_ignore_prefix(url):
            return
        crawled_urls.append(url)
        try:
            self.verbose("CHECK: " + url )
            body, sts, isrss = self.communicate(url)
            if sts != 200:
                return
            if isrss:
                print 'RSS---------'
                print url
                return
            links = re.findall(r'href=[\'"]?([^\'" >]+)', body)
            for href in links:
                if href.startswith('#'):
                    continue
                if href.startswith("http") == False:
                    if href.startswith('/') and url.endswith('/'):
                        href = href[1:]
                    href = url + href
                if starturl == href:
                    continue
                self.crawl(starturl, href, crawled_urls, depth+1)
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



crawler = CrawlRss(3, True)

# crawler.run("http://www.google.co.jp/")
crawler.run("http://www.brainpad.co.jp/")

