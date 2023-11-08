package com.example.tripKo.domain.file;

import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.file.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileTestHelper {
  @Autowired
  FileRepository fileRepository;

  public File generate() {
    return this.builder().build();
  }

  public FileBuilder builder() {
    return new FileBuilder();
  }

  public final class FileBuilder {

    private String type;
    private String name;

    public FileBuilder type(String type) {
      this.type = type;
      return this;
    }

    public FileBuilder name(String name) {
      this.name = name;
      return this;
    }

    public File build() {
      return fileRepository.save(File.builder()
          .type(type != null ? type : "타입")
          .name(name != null ? name : "/image.jpg")
          .build());
    }
  }

}

