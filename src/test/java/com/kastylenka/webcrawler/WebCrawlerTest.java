package com.kastylenka.webcrawler;

import com.kastylenka.webcrawler.converter.DocumentToWebPageConverter;
import com.kastylenka.webcrawler.models.InputData;
import com.kastylenka.webcrawler.models.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(PER_CLASS)
public class WebCrawlerTest {

    private static InputData inputData;

    private Map<Integer, List<String>> linksMap = new HashMap<>();

    private WebCrawler webCrawler = new WebCrawlerImpl(mockLinksContainer(), mockConverter());

    @Before
    public void initInputData() {
        inputData = createInputData();
    }

    @Test
    public void testThatCountOfCrawledPagesEqualsMaxPages() {
        inputData.setMaxPages(10);
        List<Result> results = webCrawler.start(inputData);
        Assert.assertEquals(results.size(), inputData.getMaxPages());
    }

    @Test
    public void testThatLinksOfNextDepthsAreNotCrawling() {
        inputData.setMaxDepth(3);
        webCrawler.start(inputData);
        Assert.assertNull(linksMap.get(inputData.getMaxDepth() + 1));
    }

    @Test
    public void testThatResultIsNullIfUrlIsInvalid() {
        inputData.setSeedUrl("invalid url");
        List<Result> results = webCrawler.start(inputData);
        Assert.assertNull(results);
    }

    private LinksContainer mockLinksContainer() {
        LinksContainer linksContainer = Mockito.mock(LinksContainer.class);
        when(linksContainer.add(anyList(), anyInt())).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            return addToLinksMap((List<String>) arguments[0], (int) arguments[1]);
        });
        when(linksContainer.getLinks(anyInt())).then(invocation -> linksMap.get(invocation.getArgument(0)));
        return linksContainer;
    }

    @SuppressWarnings("Duplicates")
    private List<String> addToLinksMap(List<String> links, int depth) {
        List<String> existingLinks = linksMap.get(depth);
        if (isNull(existingLinks)) {
            linksMap.put(depth, new ArrayList<>());
            existingLinks = linksMap.get(depth);
        }
        existingLinks.addAll(links);
        return linksMap.put(depth, existingLinks);
    }

    private DocumentToWebPageConverter mockConverter() {
        DocumentToWebPageConverter converter = Mockito.mock(DocumentToWebPageConverter.class);
        when(converter.convert(any())).thenCallRealMethod();
        return converter;
    }

    private static InputData createInputData() {
        InputData inputData = new InputData();
        inputData.setMaxPages(100);
        inputData.setMaxDepth(8);
        inputData.setSeedUrl("http://localhost:63342/webcrawler/test_html_pages/seedPage.html");
        inputData.setTerms(List.of("a", "b", "c"));
        return inputData;
    }
}
