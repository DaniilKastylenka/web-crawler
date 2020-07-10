package com.kastylenka.webcrawler.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InputData {

    private String seedUrl;
    private List<String> terms;
    private int maxPages;
    private int maxDepth;
    private int top;
}
