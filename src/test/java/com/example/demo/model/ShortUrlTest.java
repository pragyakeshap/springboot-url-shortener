package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortUrlTest {
    @Test
    void testGettersAndSetters() {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(1L);
        shortUrl.setOriginalUrl("https://example.com");
        shortUrl.setShortCode("abc1234");
        shortUrl.setExpiry(3600L);

        assertEquals(1L, shortUrl.getId());
        assertEquals("https://example.com", shortUrl.getOriginalUrl());
        assertEquals("abc1234", shortUrl.getShortCode());
        assertEquals(3600L, shortUrl.getExpiry());
    }

    @Test
    void testConstructor() {
        ShortUrl shortUrl = new ShortUrl("https://example.com", "abc1234");
        assertEquals("https://example.com", shortUrl.getOriginalUrl());
        assertEquals("abc1234", shortUrl.getShortCode());
    }
}
