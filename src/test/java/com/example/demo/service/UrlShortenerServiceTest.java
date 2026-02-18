package com.example.demo.service;

import com.example.demo.model.ShortUrl;
import com.example.demo.repository.ShortUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlShortenerServiceTest {
    @Mock
    private ShortUrlRepository repository;
    @InjectMocks
    private UrlShortenerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShortenUrl() {
        String originalUrl = "https://example.com";
        when(repository.findByShortCode(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(ShortUrl.class))).thenAnswer(i -> i.getArgument(0));
        ShortUrl shortUrl = service.shortenUrl(originalUrl);
        assertEquals(originalUrl, shortUrl.getOriginalUrl());
        assertNotNull(shortUrl.getShortCode());
    }

    @Test
    void testGetOriginalUrl() {
        String code = "abc1234";
        ShortUrl shortUrl = new ShortUrl("https://example.com", code);
        when(repository.findByShortCode(code)).thenReturn(Optional.of(shortUrl));
        Optional<ShortUrl> result = service.getOriginalUrl(code);
        assertTrue(result.isPresent());
        assertEquals(code, result.get().getShortCode());
    }
}
