package com.kastylenka.webcrawler.models;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WebPage {

    @Include
    private String url;
    private String text;
    private List<String> links;
}
