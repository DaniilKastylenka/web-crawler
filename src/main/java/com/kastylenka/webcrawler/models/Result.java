package com.kastylenka.webcrawler.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kastylenka.webcrawler.util.CommonUtils.HEADER_TOTAL;
import static java.util.Objects.nonNull;

@Getter
@Setter
public class Result {

    private String link;
    private Map<String, Integer> terms = new HashMap<>();

    public void addTotal() {
        int total = terms.entrySet().stream()
                .mapToInt(Map.Entry::getValue)
                .sum();
        terms.put(HEADER_TOTAL, total);
    }

    public List<String> buildRow(List<String> headers) {
        List<String> row = new ArrayList<>();
        row.add(link);
        headers.forEach(header -> {
            Integer count = this.terms.get(header);
            if (nonNull(count)) {
                row.add(String.valueOf(count));
            }
        });
        return row;
    }
}
