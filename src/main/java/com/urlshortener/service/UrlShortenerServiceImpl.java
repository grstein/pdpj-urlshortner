package com.urlshortener.service;

import com.urlshortener.model.ShortUrl;
import com.urlshortener.model.User;
import com.urlshortener.repository.ShortUrlRepository;
import com.urlshortener.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Override
    public ShortUrl shortenUrl(String longUrl) {
        User currentUser = getCurrentUser();
        String shortUrl = generateShortUrl(longUrl);
        ShortUrl shortUrlEntity = new ShortUrl();
        shortUrlEntity.setLongUrl(longUrl);
        shortUrlEntity.setShortUrl(shortUrl);
        shortUrlEntity.setCreatedAt(LocalDateTime.now());
        shortUrlEntity.setUsageCount(0);
        shortUrlEntity.setUser(currentUser);
        return shortUrlRepository.save(shortUrlEntity);
    }

    @Override
    public List<ShortUrl> getUserShortUrls() {
        User currentUser = getCurrentUser();
        return shortUrlRepository.findByUser(currentUser);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        ShortUrl shortUrlEntity = shortUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchElementException("Short URL not found"));
        incrementUsageCount(shortUrlEntity);
        return shortUrlEntity.getLongUrl();
    }

    @Override
    public void deleteShortUrl(Long shortUrlId) {
        shortUrlRepository.deleteById(shortUrlId);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    private String generateShortUrl(String longUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(longUrl.getBytes(StandardCharsets.UTF_8));
    
            StringBuilder shortUrlBuilder = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                shortUrlBuilder.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1, 3));
            }
            String shortUrl = shortUrlBuilder.toString();
    
            // Make sure the generated short URL does not already exist in the database
            while (shortUrlRepository.findByShortUrl(shortUrl).isPresent()) {
                longUrl = longUrl + System.currentTimeMillis();
                hash = digest.digest(longUrl.getBytes(StandardCharsets.UTF_8));
    
                shortUrlBuilder.setLength(0);
                for (int i = 0; i < 8; i++) {
                    shortUrlBuilder.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1, 3));
                }
                shortUrl = shortUrlBuilder.toString();
            }
    
            return shortUrl;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating short URL", e);
        }
    }

    private void incrementUsageCount(ShortUrl shortUrlEntity) {
        shortUrlEntity.setUsageCount(shortUrlEntity.getUsageCount() + 1);
        shortUrlRepository.save(shortUrlEntity);
    }
}
