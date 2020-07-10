package com.kastylenka.webcrawler.export;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.kastylenka.webcrawler.util.CsvUtils.writeLine;

@Component
public class CsvWriter {

    void write(List<String> elements, String path) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writeLine(writer, elements);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while writing data into file");
        }
    }
}
