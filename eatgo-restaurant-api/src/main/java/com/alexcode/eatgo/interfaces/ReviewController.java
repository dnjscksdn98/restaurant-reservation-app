package com.alexcode.eatgo.interfaces;

import com.alexcode.eatgo.application.ReviewService;
import com.alexcode.eatgo.network.SuccessResponse;
import com.alexcode.eatgo.security.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "owner/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('review:read')")
    public ResponseEntity<SuccessResponse> list(Authentication authentication) {
        ApplicationUser applicationUser = (ApplicationUser) authentication.getPrincipal();
        Long restaurantId = applicationUser.getRestaurantId();

        SuccessResponse body = reviewService.list(restaurantId);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
