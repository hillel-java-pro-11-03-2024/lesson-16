package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PetView {

  private String name;
  private String breed;
  private int age;

  @Override
  public String toString() {
    return " -> " + name + " | " + breed + " | " + age;
  }
}
