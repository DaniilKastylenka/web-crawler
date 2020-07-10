package com.kastylenka.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class LinkConnector {

    Document connect(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            return null;
        }
    }
}
