package com.kastylenka.webcrawler.export;

import com.kastylenka.webcrawler.models.Result;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CsvExporter implements FileExporter {

    private CsvWriter writer;
    private List<String> headers;

    public CsvExporter(List<String> headers, CsvWriter writer) {
        this.writer = writer;
        this.headers = headers;
    }

    @Override
    public void export(String path, List<Result> results) {
        createFile(path, headers);
        results.forEach(result -> {
            List<String> row = result.buildRow(headers);
            writer.write(row, path);
        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(String path, List<String> headers) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Error while creating file");
        }
        writer.write(headers, path);
    }
}
