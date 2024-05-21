package service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.PetView;

public class FileService {

  private final String ROOT_PATH;
  private final String DATAFILE;
  private static final ObjectMapper mapper = new ObjectMapper(new JsonFactory());

  public FileService(String rootPath, String filename) {
    ROOT_PATH = rootPath;
    DATAFILE = filename;
  }

  public List<PetView> loadData() {
    File file = new File(String.format("%s%s", ROOT_PATH, DATAFILE));
    if (!file.exists()) {
      return new ArrayList<>();
    }
    try {
      String content = Files.readString(file.toPath());
      return mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, PetView.class));
    } catch (IOException e) {
      System.err.println("Error loading data from file: " + DATAFILE);
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public void saveData(List<PetView> petViews) {
    try {
      Path filePath = Paths.get(String.format("%s%s", ROOT_PATH, DATAFILE));
      if (!Files.exists(filePath)) {
        Files.createFile(filePath);
      }
      String content = mapper.writeValueAsString(petViews);
      Files.write(filePath, content.getBytes());
    } catch (IOException e) {
      System.err.println("Error saving data to file: " + DATAFILE);
      e.printStackTrace();
    }
  }

}
