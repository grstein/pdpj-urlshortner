package com.urlshortener.service;

import com.urlshortener.model.ShortUrl;

import java.util.List;

public interface UrlShortenerService {

    ShortUrl shortenUrl(String longUrl);

    List<ShortUrl> getUserShortUrls();

    String getLongUrl(String shortUrl);

    void deleteShortUrl(Long shortUrlId);

}
