package com.alexcode.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

  @Id
  @Setter
  @GeneratedValue
  private Long id;

  @NotEmpty
  private String name;

  @NotEmpty
  private String address;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private List<MenuItem> menuItems;

  public void updateInformation(String name, String address) {
    this.name = name;
    this.address = address;
  }

  public String getInformation() {
    return name + " in " + address;
  }

  public void setMenuItems(List<MenuItem> menuItems) {
    this.menuItems = new ArrayList<>(menuItems);
  }
}
