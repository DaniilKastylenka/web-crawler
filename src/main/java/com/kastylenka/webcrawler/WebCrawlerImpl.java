package com.kastylenka.webcrawler;

import com.kastylenka.webcrawler.converter.DocumentToWebPageConverter;
import com.kastylenka.webcrawler.models.InputData;
import com.kastylenka.webcrawler.models.Result;
import com.kastylenka.webcrawler.models.WebPage;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

@Component
public class WebCrawlerImpl implements WebCrawler {

    private LinkConnector linkConnector;
    private LinksContainer linksContainer;
    private DocumentToWebPageConverter converter;

    private List<Result> results = new ArrayList<>();
    private Set<String> visitedPages = new HashSet<>();

    public WebCrawlerImpl(LinkConnector linkConnector, LinksContainer linksContainer, DocumentToWebPageConverter converter) {
        this.linkConnector = linkConnector;
        this.linksContainer = linksContainer;
        this.converter = converter;
    }

    @Override
    public List<Result> start(InputData inputData) {
        String seedUrl = inputData.getSeedUrl();
        List<String> terms = inputData.getTerms();
        int maxPages = inputData.getMaxPages();
        int maxDepth = inputData.getMaxDepth();

        Document seedPage = linkConnector.connect(seedUrl);
        if (isNull(seedPage)) {
            return null;
        }
        visitedPages.add(seedUrl);
        WebPage webPage = converter.convert(seedPage);

        Result result = crawl(webPage, terms);
        results.add(result);

        linksContainer.add(webPage.getLinks(), 2);

        for (int depth = 2, count = 1; depth <= maxDepth && count < maxPages; depth++) {
            List<String> links = linksContainer.getLinks(depth);
            if (isNull(links)) {
                break;
            }
            for (int i = 0; i < links.size() && count < maxPages; i++, count++) {
                String currentLink = links.get(i);
                if (visitedPages.contains(currentLink)) {
                    count--;
                    continue;
                }
                Document document = linkConnector.connect(currentLink);
                if (isNull(document)) {
                    count--;
                    continue;
                }
                visitedPages.add(currentLink);
                WebPage currentPage = converter.convert(document);
                Result curResult = crawl(currentPage, terms);
                results.add(curResult);
                if (depth != maxDepth) {
                    linksContainer.add(currentPage.getLinks(), depth + 1);
                }
                if (count % 30 == 0) {
                    System.out.println(count + " pages crawled");
                }
            }
        }
        return results;
    }

    private Result crawl(WebPage webPage, List<String> terms) {
        Result result = new Result();
        terms.forEach(term -> {
            Pattern pattern = Pattern.compile(term, CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(webPage.getText());
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            result.getTerms().put(term, count);
        });
        result.setLink(webPage.getUrl());
        result.addTotal();
        return result;
    }
}
