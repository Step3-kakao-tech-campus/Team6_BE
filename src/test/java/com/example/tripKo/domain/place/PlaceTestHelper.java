package com.example.tripKo.domain.place;


import com.example.tripKo.domain.file.FileTestHelper;
import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceTestHelper {

  @Autowired
  PlaceRepository placeRepository;
  @Autowired
  FileTestHelper fileTestHelper;
  @Autowired
  AddressTestHelper addressTestHelper;

  public Place generate() {
    return this.builder().build();
  }

  public PlaceBuilder builder() {
    return new PlaceBuilder();
  }

  public final class PlaceBuilder {

    private String name;
    private String summary;
    private int count;
    private float averageRating;
    private File file;
    private Address address;
    private PlaceType placeType;

    public PlaceBuilder name(String name) {
      this.name = name;
      return this;
    }

    public PlaceBuilder summary(String summary) {
      this.summary = summary;
      return this;
    }

    public PlaceBuilder count(int count) {
      this.count = count;
      return this;
    }

    public PlaceBuilder averageRating(float averageRating) {
      this.averageRating = averageRating;
      return this;
    }

    public PlaceBuilder File(File file) {
      this.file = file;
      return this;
    }

    public PlaceBuilder address(Address address) {
      this.address = address;
      return this;
    }

    public PlaceBuilder placeType(PlaceType placeType) {
      this.placeType = placeType;
      return this;
    }

    public Place build() {
      return placeRepository.save(Place.builder()
          .name(name != null ? name : "이름")
          .summary(summary != null ? summary : "요약")
          .count(1)
          .averageRating(1.0F)
          .file(file != null ? file : fileTestHelper.generate())
          .address(address != null ? address : addressTestHelper.generate())
          .placeType(placeType != null ? placeType : PlaceType.RESTAURANT)
          .build());
    }
  }

}




