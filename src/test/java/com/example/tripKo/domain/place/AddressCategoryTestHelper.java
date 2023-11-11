package com.example.tripKo.domain.place;

import com.example.tripKo.domain.place.dao.AddressCategoryRepository;
import com.example.tripKo.domain.place.entity.AddressCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressCategoryTestHelper {

  @Autowired
  AddressCategoryRepository addressCategoryRepository;

  public AddressCategory generate() {
    return this.builder().build();
  }

  public AddressCategoryBuilder builder() {
    return new AddressCategoryBuilder();
  }

  public final class AddressCategoryBuilder {

    private String emdName;
    private String siggName;
    private String sidoName;

    public AddressCategoryBuilder emdName(String emdName) {
      this.emdName = emdName;
      return this;
    }

    public AddressCategoryBuilder siggName(String siggName) {
      this.siggName = siggName;
      return this;
    }

    public AddressCategoryBuilder sidoName(String sidoName) {
      this.sidoName = sidoName;
      return this;
    }

    public AddressCategory build() {
      return addressCategoryRepository.save(AddressCategory.builder()
          .emdName(emdName != null ? emdName : "읍면동 이름")
          .siggName(siggName != null ? siggName : "시군구 이름")
          .sidoName(sidoName != null ? sidoName : "시도 이름")
          .build());
    }
  }

}

