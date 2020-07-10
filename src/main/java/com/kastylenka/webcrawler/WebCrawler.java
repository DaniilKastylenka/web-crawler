package com.kastylenka.webcrawler;

import com.kastylenka.webcrawler.models.InputData;
import com.kastylenka.webcrawler.models.Result;

import java.util.List;

public interface WebCrawler {

    List<Result> start(InputData inputData);
}
