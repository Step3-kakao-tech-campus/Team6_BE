package com.example.tripKo.domain.contents.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class TouristSpotResponse {

    private Long id;
    private String name;
    private Float averageScore;
    private String mainImage;
    private List<String> images;
    private String address;
    private String description;
    private String imagesPath;
    private Boolean isWished;

    public TouristSpotResponse(PlaceTouristSpot placeTouristSpot) {
        this.id = placeTouristSpot.getId();
        this.name = placeTouristSpot.getPlace().getName();
        this.averageScore = placeTouristSpot.getPlace().getAverageRating();
        this.mainImage = placeTouristSpot.getPlace().getFile().getName();
        this.images = placeTouristSpot.getPlace().getContents().stream()
                .flatMap(c -> c.getContentsHasFiles().stream())
                .map(c -> c.getFile().getName())
                .collect(Collectors.toList());
        this.address = addressToString(placeTouristSpot.getPlace().getAddress());
        this.description = placeTouristSpot.getPlace().getSummary();
        this.isWished = false;
    }

    public String addressToString(Address address) {
        String addressToString = address.getBuildingName() + " " + address.getRoadName();
        AddressCategory addressCategory = address.getAddressCategory();
        String addressCategoryToString = addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
        return addressToString + " " + addressCategoryToString;
    }

}
