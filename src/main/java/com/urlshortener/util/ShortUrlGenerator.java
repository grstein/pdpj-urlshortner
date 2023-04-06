package com.urlshortener.util;

import com.urlshortener.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class ShortUrlGenerator {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public String generateShortUrl(String longUrl) {
        // Implement your desired URL shortening algorithm here
        // For example, use a hash function or generate a random string
        String shortUrl;
        do {
            shortUrl = generateSha1ShortUrl(longUrl);
        } while (shortUrlRepository.findByShortUrl(shortUrl).isPresent());
        return shortUrl;
    }

    private String generateSha1ShortUrl(String longUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(longUrl.getBytes(StandardCharsets.UTF_8));

            StringBuilder shortUrlBuilder = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                shortUrlBuilder.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return shortUrlBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating short URL", e);
        }
    }
}
