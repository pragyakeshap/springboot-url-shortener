package com.example.demo.controller;

import com.example.demo.model.ShortUrl;
import com.example.demo.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class UrlShortenerController {
    private final UrlShortenerService service;

    @Autowired
    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        ShortUrl shortUrl = service.shortenUrl(originalUrl);
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl.getShortCode()));
    }

    @GetMapping("/{shortURL}")
    public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable String shortURL) {
        return service.getOriginalUrl(shortURL)
                .map(shortUrl -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(shortUrl.getOriginalUrl()))
                        .build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable Long id) {
        service.deleteShortUrl(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/expiry")
    public ResponseEntity<ShortUrl> updateExpiry(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long validitySeconds = request.get("expiry");
        ShortUrl updated = service.updateShortUrl(id, null, validitySeconds);
        return ResponseEntity.ok(updated);
    }
}
