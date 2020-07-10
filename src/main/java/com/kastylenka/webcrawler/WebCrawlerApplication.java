package com.kastylenka.webcrawler;

import com.kastylenka.webcrawler.export.CsvExporter;
import com.kastylenka.webcrawler.export.CsvWriter;
import com.kastylenka.webcrawler.export.FileExporter;
import com.kastylenka.webcrawler.models.InputData;
import com.kastylenka.webcrawler.models.Result;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

import static com.kastylenka.webcrawler.util.CommonUtils.*;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@SpringBootApplication
public class WebCrawlerApplication implements CommandLineRunner {

    private WebCrawler webCrawler;
    private CsvWriter writer;

    public static void main(String[] args) {
        SpringApplication.run(WebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        InputData inputData = createInputData();

        System.out.println("Seed URL: " + inputData.getSeedUrl() + "\n"
                + "Terms: " + inputData.getTerms() + "\n"
                + "Max pages: " + inputData.getMaxPages() + "\n"
                + "Max depth: " + inputData.getMaxDepth() + "\n"
        );
        System.out.println("Start web crawling...");

        List<Result> results = webCrawler.start(inputData);

        if (isNull(results)) {
            System.err.println("Invalid url");
            return;
        }

        List<Result> topPages = results.stream()
                .sorted((result, t1) -> t1.getTerms().get(TOTAL_HEADER) - result.getTerms().get(TOTAL_HEADER))
                .limit(inputData.getTop())
                .collect(toList());

        List<String> headers = new ArrayList<>();
        headers.add(LINK_HEADER);
        headers.addAll(inputData.getTerms());
        headers.add(TOTAL_HEADER);

        FileExporter exporter = new CsvExporter(headers, writer);
        exporter.export(EXPORT_PATH + ALL_PAGES_CSV, results);
        exporter.export(EXPORT_PATH + TOP_PAGES_CSV, topPages);

        topPages.forEach(page -> {
            List<String> list = page.buildRow(headers);
            System.out.println(list);
        });
        System.out.println("Web crawling completed");
        System.out.println("Exported to " + EXPORT_PATH);
    }

    private InputData createInputData() {
        InputData inputData = new InputData();
        inputData.setSeedUrl(INPUT_DATA_SEED_URL);
        inputData.setTerms(INPUT_DATA_TERMS);
        inputData.setMaxPages(INPUT_DATA_MAX_PAGES);
        inputData.setMaxDepth(INPUT_DATA_MAX_DEPTH);
        inputData.setTop(INPUT_DATA_TOP);
        return inputData;
    }
}
