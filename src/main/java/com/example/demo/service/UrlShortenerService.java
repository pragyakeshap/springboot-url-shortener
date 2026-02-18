package com.example.demo.service;

import com.example.demo.model.ShortUrl;
import com.example.demo.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {
    private final ShortUrlRepository repository;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private final Random random = new Random();

    @Autowired
    public UrlShortenerService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public ShortUrl shortenUrl(String originalUrl) {
        return shortenUrl(originalUrl, null);
    }

    public ShortUrl shortenUrl(String originalUrl, Long validitySeconds) {
        String code;
        do {
            code = generateCode();
        } while (repository.findByShortCode(code).isPresent());
        ShortUrl shortUrl = new ShortUrl(originalUrl, code);
        if (validitySeconds != null) {
            shortUrl.setExpiry(validitySeconds);
        }
        return repository.save(shortUrl);
    }

    public Optional<ShortUrl> getOriginalUrl(String code) {
        return repository.findByShortCode(code);
    }

    // CRUD operations
    public Optional<ShortUrl> getShortUrl(Long id) {
        return repository.findById(id);
    }

    public ShortUrl updateShortUrl(Long id, String newOriginalUrl, Long validitySeconds) {
        ShortUrl shortUrl = repository.findById(id).orElseThrow();
        shortUrl.setOriginalUrl(newOriginalUrl);
        if (validitySeconds != null) {
            shortUrl.setExpiry(validitySeconds);
        }
        return repository.save(shortUrl);
    }

    public void deleteShortUrl(Long id) {
        repository.deleteById(id);
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
