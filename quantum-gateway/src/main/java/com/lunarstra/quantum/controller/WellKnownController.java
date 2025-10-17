package com.lunarstra.quantum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class WellKnownController {
    @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
    public ResponseEntity<Map<String, Object>> handleDevToolsRequest() {
        Map<String, Object> workspace = new HashMap<>();
        workspace.put("root", System.getProperty("user.dir"));
        workspace.put("uuid", UUID.randomUUID().toString()); // Replace with a valid UUID
        Map<String, Object> response = new HashMap<>();
        response.put("workspace", workspace);
        return ResponseEntity.ok(response);
    }
}