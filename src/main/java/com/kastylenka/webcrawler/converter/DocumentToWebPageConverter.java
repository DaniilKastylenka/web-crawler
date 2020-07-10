package com.kastylenka.webcrawler.converter;

import com.kastylenka.webcrawler.models.WebPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class DocumentToWebPageConverter {

    private static final String A_TAG_PROPERTY = "a";
    private static final String HREF_ATTRIBUTE_PROPERTY = "abs:href";

    public WebPage convert(Document document) {
        WebPage webPage = new WebPage();
        Element documentBody = document.body();
        webPage.setUrl(document.location());
        webPage.setLinks(documentBody.getElementsByTag(A_TAG_PROPERTY).eachAttr(HREF_ATTRIBUTE_PROPERTY));
        webPage.setText(documentBody.text());
        return webPage;
    }
}
