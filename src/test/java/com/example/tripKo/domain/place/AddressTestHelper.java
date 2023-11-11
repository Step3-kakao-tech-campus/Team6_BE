package com.example.tripKo.domain.place;

import com.example.tripKo.domain.place.AddressCategoryTestHelper;
import com.example.tripKo.domain.place.dao.AddressRepository;
import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.AddressCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressTestHelper {

  @Autowired
  AddressRepository addressRepository;
  @Autowired
  AddressCategoryTestHelper addressCategoryTestHelper;

  public Address generate() {
    return this.builder().build();
  }

  public AddressBuilder builder() {
    return new AddressBuilder();
  }

  public final class AddressBuilder {

    private String buildingName;
    private String roadName;
    private String zipCode;
    private AddressCategory addressCategory;

    public AddressBuilder buildingName(String buildingName) {
      this.buildingName = buildingName;
      return this;
    }

    public AddressBuilder roadName(String roadName) {
      this.roadName = roadName;
      return this;
    }

    public AddressBuilder zipCode(String zipCode) {
      this.zipCode = zipCode;
      return this;
    }

    public AddressBuilder addressCategory(AddressCategory addressCategory) {
      this.addressCategory = addressCategory;
      return this;
    }

    public Address build() {
      return addressRepository.save(Address.builder()
          .buildingName(buildingName != null ? buildingName : "빌딩이름")
          .roadName(roadName != null ? roadName : "로드이름")
          .zipCode(zipCode != null ? zipCode : "우편 번호")
          .addressCategory(addressCategory != null ? addressCategory : addressCategoryTestHelper.generate())
          .build());
    }

  }


}

