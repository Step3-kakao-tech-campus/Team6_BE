package com.example.tripKo.domain.contents.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
import com.example.tripKo.domain.contents.entity.Contents;
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
    private List<Content> contents;
    private String address;
    private Boolean isWished;

    @Builder
    @Getter
    public static class Content {
        private Long page;
        private String description;
        private List<String> image;
    }


    public TouristSpotResponse(PlaceTouristSpot placeTouristSpot) {
        this.id = placeTouristSpot.getId();
        this.name = placeTouristSpot.getPlace().getName();
        this.averageScore = placeTouristSpot.getPlace().getAverageRating();
        this.mainImage = placeTouristSpot.getPlace().getFile().getName();
        this.contents = placeTouristSpot.getPlace().getContents().stream()
            .map(this::mapContent)
            .collect(Collectors.toList());
        this.address = placeTouristSpot.getPlace().addressToString(placeTouristSpot.getPlace().getAddress());
        this.isWished = false;
    }

    private Content mapContent(Contents contents) {
        return Content.builder()
            .page(contents.getPage())
            .description(contents.getDescription())
            .image(contents.getContentsHasFiles().stream()
                .map(c->c.getFile().getName())
                .collect(Collectors.toList()))
            .build();
    }

}
