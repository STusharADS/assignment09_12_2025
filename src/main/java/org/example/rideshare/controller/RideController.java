package org.example.rideshare.controller;

import org.example.rideshare.model.Ride;
import org.example.rideshare.service.RideService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rides")
@Tag(name = "Core Ride APIs", description = "Core ride management endpoints")
public class RideController {

    private final RideService service;

    public RideController(RideService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new ride request",
               description = "Passenger creates a ride request with pickup and drop locations. Ride status starts as REQUESTED.")
    public Ride createRide(@AuthenticationPrincipal UserDetails user, @RequestBody Ride ride) {
        return service.createRide(user.getUsername(), ride);
    }

    @PostMapping("/accept/{id}")
    @Operation(summary = "Accept a ride",
               description = "Driver accepts an existing ride request. Updates status to ACCEPTED.")
    public Ride accept(@AuthenticationPrincipal UserDetails driver, @PathVariable String id) {
        return service.acceptRide(id, driver.getUsername());
    }

    @PostMapping("/complete/{id}")
    @Operation(summary = "Complete a ride",
               description = "Mark a ride as completed. Updates status to COMPLETED.")
    public Ride complete(@PathVariable String id) {
        return service.completeRide(id);
    }
}
