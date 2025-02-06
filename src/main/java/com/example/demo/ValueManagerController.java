package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/valuemanager")
public class ValueManagerController {

    private final Map<String, String> valueMap = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<Void> writeValue(@RequestParam String key, @RequestParam String value) {
        valueMap.put(key, value);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/{key}/{value}")
    public ResponseEntity<Void> writeValue2(@PathVariable String key, @PathVariable String value) {
        valueMap.put(key, value);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> getValue(@PathVariable String key) {
        String value = valueMap.getOrDefault(key, null);
        if (value == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(value);
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getAllValues() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.copyOf(valueMap));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteValue(@PathVariable String key) {
        if (valueMap.remove(key) != null) {
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}