package service;

import java.util.List;
import java.util.Scanner;
import model.PetView;

public class TerminalService {

  private static final Scanner scanner = new Scanner(System.in);
  private final List<PetView> availablePets;
  private String input = "";

  private final FileService fileService;

  public TerminalService() {
    fileService = new FileService("src/main/resources/", "data.json");
    availablePets = fileService.loadData();
  }

  private static void clearConsole() {
    for (int i = 0; i < 20; i++) {
      System.out.println();
    }
  }

  private static void pause() {
    System.out.println("Press Enter to return to the menu...");
    scanner.nextLine();
  }

  public void start() {
    while (!"0".equals(input)) {
      showMenu();
      input = scanner.nextLine();

      switch (input) {
        case "1" -> addPetToShelter();
        case "2" -> showAllPets();
        case "3" -> takePetFromShelter();
      }

    }
    fileService.saveData(availablePets);
  }

  private void addPetToShelter() {
    clearConsole();
    System.out.println("Add new pet to shelter...");
    System.out.println("Please enter a name, breed and age using comma as delimiter");
    System.out.println("Example: Jack, labrador, 23");
    System.out.print("Please enter: ");
    input = scanner.nextLine();
    String[] parts = input.split(",");
    if (parts.length != 3) {
      System.out.println("Invalid input. Please enter name, breed, and age separated by commas.");
      pause();
    } else {

      String name = parts[0].trim();
      String breed = parts[1].trim();
      int age;
      try {
        age = Integer.parseInt(parts[2].trim());
      } catch (NumberFormatException e) {
        System.out.println("Invalid age. Please enter a valid number for age.");
        pause();
        return;
      }

      availablePets.add(new PetView()
          .withName(name)
          .withBreed(breed)
          .withAge(age));
      System.out.println("New pet added successfully!");
      pause();
    }
  }

  private void showAllPets() {
    clearConsole();
    System.out.println("List of all pets in shelter:");
    for (PetView pet : availablePets) {
      System.out.println(pet);
    }
    pause();
  }

  private void takePetFromShelter() {
    clearConsole();
    System.out.println("Take a pet from shelter...");
    System.out.print("Please enter the name of the pet you want to take: ");
    input = scanner.nextLine();

    availablePets.stream()
        .filter(pet -> pet.getName().equalsIgnoreCase(input.trim()))
        .findFirst()
        .ifPresentOrElse(
            pet -> {
              availablePets.remove(pet);
              System.out.println("Pet " + pet.getName() + " has been taken from the shelter.");
            },
            () -> System.out.println("No pet found with the name: " + input)
        );
    pause();
  }

  private void showMenu() {
    clearConsole();
    System.out.println("Good day");
    System.out.println("Total amount of pert in shelter: " + availablePets.size());
    System.out.println("1. Add new pet to shelter");
    System.out.println("2. Show all pets");
    System.out.println("3. Take a pet from shelter");
    System.out.println("0. Exit from program");
  }
}
