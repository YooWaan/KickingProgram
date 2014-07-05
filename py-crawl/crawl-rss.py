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

class CrawlRss:

    def __init__(self, depth):
        """ Init CrawlRss
        """
        self._depth = depth

    def run(self,url,port = 80):
        conn = httplib.HTTPConnection(url, port)
        conn.request("GET", "/")
        res = conn.getresponse()
        html = res.read()
        conn.close()

        urls = re.findall(r'href=[\'"]?([^\'" >]+)', html)
        print ', '.join(urls)


crawler = CrawlRss(3)


crawler.run("www.google.co.jp")

