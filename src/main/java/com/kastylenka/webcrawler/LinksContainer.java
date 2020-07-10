package com.kastylenka.webcrawler;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
public class LinksContainer {

    @Setter
    private Map<Integer, List<String>> linksMap = new HashMap<>();

    List<String> add(List<String> links, int depth) {
        List<String> existingLinks = linksMap.get(depth);
        if (isNull(existingLinks)) {
            linksMap.put(depth, new ArrayList<>());
            existingLinks = linksMap.get(depth);
        }
        existingLinks.addAll(links);
        return linksMap.put(depth, existingLinks);
    }

    List<String> getLinks(int depth) {
        return linksMap.get(depth);
    }

}
