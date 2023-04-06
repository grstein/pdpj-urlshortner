package com.urlshortener.controller;

import com.urlshortener.model.ShortUrl;
import com.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrl> shortenUrl(@RequestParam("longUrl") String longUrl) {
        ShortUrl shortUrl = urlShortenerService.shortenUrl(longUrl);
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping("/shorturls")
    public ResponseEntity<List<ShortUrl>> getUserShortUrls() {
        List<ShortUrl> shortUrls = urlShortenerService.getUserShortUrls();
        return new ResponseEntity<>(shortUrls, HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToLongUrl(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
        String longUrl = urlShortenerService.getLongUrl(shortUrl);
        response.sendRedirect(longUrl);
    }

    @DeleteMapping("/shorturls/{shortUrlId}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable("shortUrlId") Long shortUrlId) {
        urlShortenerService.deleteShortUrl(shortUrlId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
