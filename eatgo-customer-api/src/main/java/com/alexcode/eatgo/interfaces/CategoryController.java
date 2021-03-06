package com.alexcode.eatgo.interfaces;

import com.alexcode.eatgo.application.CategoryService;
import com.alexcode.eatgo.network.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "customer/api/v1/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('category:read')")
  public ResponseEntity<SuccessResponse> list() {
    SuccessResponse body = categoryService.getCategories();
    return new ResponseEntity<>(body, HttpStatus.OK);
  }

}
