package org.example.rideshare.controller;

import org.example.rideshare.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Core Analytics APIs", description = "Core analytics endpoints")
public class AnalyticsController {

    private final AnalyticsService analytics;

    public AnalyticsController(AnalyticsService analytics) {
        this.analytics = analytics;
    }

    @GetMapping("/driver/{driver}/earnings")
    @Operation(summary = "Get driver earnings",
               description = "Calculate total earnings for a driver from all completed rides.")
    public Double earnings(@PathVariable String driver) {
        return analytics.totalEarnings(driver);
    }
}
