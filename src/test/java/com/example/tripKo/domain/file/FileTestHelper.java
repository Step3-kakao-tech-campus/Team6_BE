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
    private String url;

    public FileBuilder type(String type) {
      this.type = type;
      return this;
    }

    public FileBuilder name(String name) {
      this.name = name;
      return this;
    }

    public FileBuilder url(String url) {
      this.url = url;
      return this;
    }

    public File build() {
      return fileRepository.save(File.builder()
          .type(type != null ? type : "타입")
          .name(name != null ? name : "/image.jpg")
          .url(url != null ? url : "https://tripko-be6.s3.ap-northeast-2.amazonaws.com/%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C.jpg")
          .build());
    }
  }

}

