package com.organization.controller;

import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DbStatsController {

    private static final double FREE_TIER_LIMIT_MB = 512.0;

    private final MongoTemplate mongoTemplate;

    public DbStatsController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        Document command = new Document("dbStats", 1);
        Document stats = mongoTemplate.executeCommand(command);

        double storageSizeBytes = getNumericValue(stats, "storageSize");
        double dataSizeBytes = getNumericValue(stats, "dataSize");
        double indexSizeBytes = getNumericValue(stats, "indexSize");

        double storageSizeMb = bytesToMegabytes(storageSizeBytes);
        double dataSizeMb = bytesToMegabytes(dataSizeBytes);
        double indexSizeMb = bytesToMegabytes(indexSizeBytes);
        double usedMb = Math.max(storageSizeMb, dataSizeMb + indexSizeMb);
        double remainingMb = Math.max(FREE_TIER_LIMIT_MB - usedMb, 0);

        Map<String, Object> response = new HashMap<>();
        response.put("database", stats.getString("db"));
        response.put("collections", stats.get("collections"));
        response.put("objects", stats.get("objects"));
        response.put("dataSize", formatMb(dataSizeMb));
        response.put("indexSize", formatMb(indexSizeMb));
        response.put("storageSize", formatMb(storageSizeMb));
        response.put("estimatedUsed", formatMb(usedMb));
        response.put("freeTierLimit", formatMb(FREE_TIER_LIMIT_MB));
        response.put("estimatedRemaining", formatMb(remainingMb));

        return ResponseEntity.ok(response);
    }

    private double getNumericValue(Document stats, String key) {
        Object value = stats.get(key);
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return 0D;
    }

    private double bytesToMegabytes(double bytes) {
        return bytes / (1024D * 1024D);
    }

    private String formatMb(double value) {
        return String.format("%.2f MB", Math.max(value, 0));
    }
}
