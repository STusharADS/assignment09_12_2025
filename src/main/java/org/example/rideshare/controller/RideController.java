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

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService service;

    public RideController(RideService service) {
        this.service = service;
    }

    @PostMapping
    public Ride createRide(@AuthenticationPrincipal UserDetails user, @RequestBody Ride ride) {
        return service.createRide(user.getUsername(), ride);
    }

    @PostMapping("/accept/{id}")
    public Ride accept(@AuthenticationPrincipal UserDetails driver, @PathVariable String id) {
        return service.acceptRide(id, driver.getUsername());
    }

    @PostMapping("/complete/{id}")
    public Ride complete(@PathVariable String id) {
        return service.completeRide(id);
    }
}
