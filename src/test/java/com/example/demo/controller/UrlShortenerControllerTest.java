package com.example.demo.controller;

import com.example.demo.model.ShortUrl;
import com.example.demo.service.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlShortenerControllerTest {
    @Mock
    private UrlShortenerService service;
    @InjectMocks
    private UrlShortenerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShortenUrl() {
        ShortUrl shortUrl = new ShortUrl("https://example.com", "abc1234");
        when(service.shortenUrl("https://example.com")).thenReturn(shortUrl);
        ResponseEntity<Map<String, String>> response = controller.shortenUrl(Map.of("url", "https://example.com"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("abc1234", response.getBody().get("shortUrl"));
    }

    @Test
    void testRedirectToOriginalUrlFound() {
        ShortUrl shortUrl = new ShortUrl("https://example.com", "abc1234");
        when(service.getOriginalUrl("abc1234")).thenReturn(Optional.of(shortUrl));
        ResponseEntity<Object> response = controller.redirectToOriginalUrl("abc1234");
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    void testRedirectToOriginalUrlNotFound() {
        when(service.getOriginalUrl("notfound")).thenReturn(Optional.empty());
        ResponseEntity<Object> response = controller.redirectToOriginalUrl("notfound");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
