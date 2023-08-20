package ru.job4j.concurrent;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlValidator {
    public void validateUrl(String url) {
        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
