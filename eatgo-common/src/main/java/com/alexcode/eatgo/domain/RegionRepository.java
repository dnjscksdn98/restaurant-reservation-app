package com.alexcode.eatgo.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<Region, Long> {

  List<Region> findAll();

  Region save(Region region);
}