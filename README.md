#Web-crawler
This is project that can crawl web pages. All data that needed to start - is a seed url.
Then application starts reading text from web page, and also scan all ulr's on the page.
And while number of pages or depth of the url are lower than max values, application goes to
these url's and scan new pages. 
Result automatically export to csv files - first with all data that crawled, and second -
with top 10 total results.

Steps to start application: 
1. Build project to get .jar 
2. By the command: java -jar <name of .jar>.jar

In the CommonUtils class you can change default values of InputData.