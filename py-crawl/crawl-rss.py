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
"""

__author__ = 'YooWaan'

import httplib
import re
# from urllib.parse import urlparse
from urlparse import urlparse

class CrawlRss:

    def __init__(self, depth):
        """ Init CrawlRss
        """
        self._depth = depth

    def run(self,url):
        self.trackrss(url, 0)

    def trackrss(self,url, depth):
        if depth == 3:
            return

        print url
        html, sts, isrss = self.gethtml(url)
        if sts != 200:
            return
        if isrss:
            print 'RSS---------'
            print url
        links = re.findall(r'href=[\'"]?([^\'" >]+)', html)

        for href in links:
            if href.startswith("mail"):
                continue
            if href.startswith("http") == False:
                if href.startswith('/') != False:
                    href = '' + href
                href = url + href
            self.trackrss(href, depth+1)

    def gethtml(self, url):
        urlresult = urlparse(url)
        port = 80 if urlresult.port is None else urlresult.port
        conn = httplib.HTTPSConnection(urlresult.netloc,port) if urlresult.scheme.startswith('https') else httplib.HTTPConnection(urlresult.netloc,port)
        conn.request("GET", urlresult.path);
        res = conn.getresponse()
        html = res.read()
        sts = res.status
        if sts != 200:
            return None, None, None
        contenttype = res.getheader('Content-Type')
        conn.close()
        return html, sts, contenttype.find("xml") != -1



crawler = CrawlRss(3)

# crawler.run("http://www.google.co.jp/")
crawler.run("http://www.brainpad.co.jp/")

