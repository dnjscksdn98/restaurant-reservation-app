package com.alexcode.eatgo.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Setter
  @NotEmpty
  private String email;

  @Setter
  @NotEmpty
  private String name;

  private String password;

  @Setter
  @NotNull
  private Long level;

  public boolean isAdmin() {
    return level >= 3;
  }

  public boolean isActive() {
    return level > 0;
  }

  public void deactivate() {
    level = 0L;
  }

  @JsonIgnore
  public String getAccessToken() {
    if(password == null) return "";
    return password.substring(0, 10);
  }
}