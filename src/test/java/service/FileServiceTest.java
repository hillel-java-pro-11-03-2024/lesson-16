package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.SneakyThrows;
import model.PetView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileServiceTest {

  private static final String TEST_ROOT_PATH = "src/test/resources/";
  private static final String DATAFILE = "data.json";
  private static final ObjectMapper mapper = new ObjectMapper(new JsonFactory());

  private final FileService fileService = new FileService(TEST_ROOT_PATH, DATAFILE);

  @BeforeEach
  @SneakyThrows
  public void setUp() {
    Files.deleteIfExists(Paths.get(TEST_ROOT_PATH + DATAFILE));
  }

  @Test
  void testLoadData_FileDoesNotExist() {
    List<PetView> pets = fileService.loadData();

    assertTrue(pets.isEmpty());
  }

  @Test
  @SneakyThrows
  void testLoadData_FileExists() {
    saveTestPet();
    PetView savedPet = getTestPet();

    List<PetView> loadedPets = fileService.loadData();

    assertEquals(1, loadedPets.size());
    assertEquals(savedPet.getName(), loadedPets.get(0).getName());
    assertEquals(savedPet.getBreed(), loadedPets.get(0).getBreed());
    assertEquals(savedPet.getAge(), loadedPets.get(0).getAge());
  }

  @Test
  @SneakyThrows
  void testSaveData_FileDoesNotExist() {
    PetView pet = getTestPet();
    List<PetView> petList = List.of(pet);

    fileService.saveData(petList);

    Path filePath = Paths.get(TEST_ROOT_PATH + DATAFILE);
    assertTrue(Files.exists(filePath));

    String content = new String(Files.readAllBytes(filePath));
    List<PetView> savedPets = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, PetView.class));

    assertEquals(1, savedPets.size());
    assertEquals(pet.getName(), savedPets.get(0).getName());
    assertEquals(pet.getBreed(), savedPets.get(0).getBreed());
    assertEquals(pet.getAge(), savedPets.get(0).getAge());
  }

  @SneakyThrows
  private void saveTestPet() {
    PetView testPet = getTestPet();
    List<PetView> petList = List.of(testPet);
    String content = mapper.writeValueAsString(petList);
    Files.write(Paths.get(TEST_ROOT_PATH + DATAFILE), content.getBytes());
  }

  private PetView getTestPet() {
    return new PetView()
        .withName("name")
        .withBreed("breed")
        .withAge(3);
  }
}
