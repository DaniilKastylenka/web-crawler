package com.kastylenka.webcrawler.export;

import com.kastylenka.webcrawler.models.Result;

import java.util.List;

public interface FileExporter {

    void export(String path, List<Result> results);
}
