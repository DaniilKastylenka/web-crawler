package com.kastylenka.webcrawler.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtils {

    public static final String EXPORT_PATH = "/home/roman/Documents/exportFolder/";
    public static final String ALL_PAGES_CSV = "allPages.csv";
    public static final String TOP_PAGES_CSV = "topPages.csv";

    public static final String TOTAL_HEADER = "Total";
    public static final String LINK_HEADER = "Link";

    public static final String INPUT_DATA_SEED_URL = "https://en.wikipedia.org/wiki/Softeq";
    public static final List<String> INPUT_DATA_TERMS = List.of("developer", "programming", "it", "softeq");
    public static final int INPUT_DATA_MAX_PAGES = 150;
    public static final int INPUT_DATA_MAX_DEPTH = 8;
    public static final int INPUT_DATA_TOP = 10;

}