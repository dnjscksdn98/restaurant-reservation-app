package com.alexcode.eatgo.interfaces;

import com.alexcode.eatgo.application.RestaurantService;
import com.alexcode.eatgo.domain.Restaurant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}