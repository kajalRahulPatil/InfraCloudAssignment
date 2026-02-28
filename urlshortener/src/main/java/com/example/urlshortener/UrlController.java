package com.example.urlshortener;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    //Shorten API
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortUrl = urlService.shortenUrl(originalUrl);
        return ResponseEntity.ok(shortUrl);
    }

    //Redirect API
    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortKey) {
        String originalUrl = urlService.getOriginalUrl(shortKey);
        if (originalUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalUrl))
                    .build();
        }
        return ResponseEntity.notFound().build();
    }

    //Metrics API
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Integer>> getTopDomains() {
        return ResponseEntity.ok(urlService.getTopDomains());
    }

}
