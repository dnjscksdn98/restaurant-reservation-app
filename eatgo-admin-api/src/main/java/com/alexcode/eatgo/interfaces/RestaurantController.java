package com.alexcode.eatgo.interfaces;

import com.alexcode.eatgo.application.RestaurantService;
import com.alexcode.eatgo.domain.Restaurant;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @GetMapping("/restaurants")
  public List<Restaurant> list() {
    return restaurantService.getRestaurants();
  }

  @GetMapping("/restaurants/{id}")
  public Restaurant detail(@PathVariable("id") Long id) {
    return restaurantService.getRestaurantById(id);
  }

  @PostMapping("/restaurants")
  public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource)
      throws URISyntaxException {

    Restaurant restaurant = restaurantService.addRestaurant(
        Restaurant.builder()
            .name(resource.getName())
            .address(resource.getAddress())
            .build()
    );

    URI location = new URI("/restaurants/" + restaurant.getId());
    return ResponseEntity.created(location).body("{}");
  }

  @PatchMapping("/restaurants/{id}")
  public String update(@PathVariable("id") Long id,
                       @Valid @RequestBody Restaurant resource) {
    String name = resource.getName();
    String address = resource.getAddress();
    restaurantService.updateRestaurant(id, name, address);
    return "{}";
  }
}