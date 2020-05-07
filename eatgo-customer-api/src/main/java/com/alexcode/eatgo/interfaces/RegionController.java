package com.alexcode.eatgo.interfaces;

import com.alexcode.eatgo.application.RegionService;
import com.alexcode.eatgo.domain.Region;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RegionController {

  @Autowired
  private RegionService regionService;

  @GetMapping("/regions")
  public List<Region> list() {
    List<Region> regions = regionService.getRegions();

    return regions;
  }

}