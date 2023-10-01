package com.example.tripKo._core.utils;

import com.example.tripKo.domain.address.dao.AddressCategoryRepository;
import com.example.tripKo.domain.address.dao.AddressRepository;
import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
import com.example.tripKo.domain.contents.dao.ContentsMenuRepository;
import com.example.tripKo.domain.contents.dao.ContentsRepository;
import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.contents.entity.ContentsMenu;
import com.example.tripKo.domain.file.dao.ContentsFileRepository;
import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.file.entity.ContentsFile;
import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.place.dao.PlaceFestivalJPARepository;
import com.example.tripKo.domain.place.dao.PlaceJPARepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantJPARepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotJPARepository;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("test")
@RequiredArgsConstructor
public class TestData implements CommandLineRunner {
    private final PlaceJPARepository placeJPARepository;
    private final PlaceRestaurantJPARepository placeRestaurantJPARepository;
    private final PlaceFestivalJPARepository placeFestivalJPARepository;
    private final PlaceTouristSpotJPARepository placeTouristSpotJPARepository;
    private final ContentsFileRepository contentsFileRepository;
    private final FileRepository fileRepository;
    private final ContentsRepository contentsRepository;
    private final ContentsMenuRepository contentsMenuRepository;
    private final AddressCategoryRepository addressCategoryRepository;
    private final AddressRepository addressRepository;


    @Override
    public void run(String... args) throws Exception {
        //file table 데이터 입력
        List<File> files = Arrays.asList(
                File.builder().name("/image/restaurant/1").type("image/png").build(),
                File.builder().name("/image/restaurant/2").type("image/png").build(),
                File.builder().name("/image/restaurant/3").type("image/png").build(),
                File.builder().name("/image/festival/1").type("image/png").build(),
                File.builder().name("/image/festival/2").type("image/png").build(),
                File.builder().name("/image/festival/3").type("image/png").build(),
                File.builder().name("/image/touristSpot/1").type("image/png").build(),
                File.builder().name("/image/touristSpot/2").type("image/png").build(),
                File.builder().name("/image/touristSpot/3").type("image/png").build(),
                File.builder().name("/image/menu/1").type("image/png").build(),
                File.builder().name("/image/menu/2").type("image/png").build(),
                File.builder().name("/image/menu/3").type("image/png").build(),
                File.builder().name("/image/menu/4").type("image/png").build()

        );
        fileRepository.saveAll(files);

        //address_category 테이블 데이터 입력
        List<AddressCategory> addressCategories = Arrays.asList(
                AddressCategory.builder().sidoName("Busan").emdName("GeumjeongGu").siggName("JangjeonDong").build(),
                AddressCategory.builder().sidoName("Busan").emdName("HaeundaeGu").siggName("UDong").build(),
                AddressCategory.builder().sidoName("Busan").emdName("BusanjinGu").siggName("JeonpoDong").build()
        );
        addressCategoryRepository.saveAll(addressCategories);

        List<Address> addresses = Arrays.asList(
                Address.builder().zipCode("11111").roadName("RoadName1").buildingName("BuildingName1").addressCategory(addressCategories.get(0)).build(),
                Address.builder().zipCode("22222").roadName("RoadName2").buildingName("BuildingName2").addressCategory(addressCategories.get(0)).build(),
                Address.builder().zipCode("33333").roadName("RoadName3").buildingName("BuildingName3").addressCategory(addressCategories.get(1)).build(),
                Address.builder().zipCode("44444").roadName("RoadName4").buildingName("BuildingName4").addressCategory(addressCategories.get(0)).build(),
                Address.builder().zipCode("55555").roadName("RoadName5").buildingName("BuildingName5").addressCategory(addressCategories.get(1)).build(),
                Address.builder().zipCode("66666").roadName("RoadName6").buildingName("BuildingName6").addressCategory(addressCategories.get(2)).build()
        );
        addressRepository.saveAll(addresses);

        List<Place> places = Arrays.asList(
                Place.builder().name("정문토스트").summary("정문토스트는..").count(100).averageRating((float)4.4).address(addresses.get(0)).file(files.get(0)).build(),
                Place.builder().name("명물토스트").summary("명물토스트는..").count(200).averageRating((float)4.2).address(addresses.get(1)).file(files.get(1)).build(),
                Place.builder().name("2023 부산 국제 락 페스티벌").summary("부락페는..").count(300).averageRating((float)4.3).address(addresses.get(2)).file(files.get(3)).build(),
                Place.builder().name("2023 대동제").summary("대동제는..").count(200).averageRating((float)4.0).address(addresses.get(0)).file(files.get(3)).build(),
                Place.builder().name("Signiel Busan").summary("부산 시그니엘은..").count(100).averageRating((float)4.5).address(addresses.get(4)).file(files.get(6)).build(),
                Place.builder().name("전포 카페 거리").summary("전포 카페 거리는..").count(200).averageRating((float)4.3).address(addresses.get(5)).file(files.get(7)).build()
        );
        placeJPARepository.saveAll(places);

        List<PlaceRestaurant> placeRestaurants = Arrays.asList(
                new PlaceRestaurant(null, "051-111-2222", "11:00", "22:00", "15:00", "17:00", "Monday", places.get(0)),
                new PlaceRestaurant(null, "051-222-3333", "12:00", "22:00", "14:00", "17:00", null, places.get(1))
        );
        placeRestaurantJPARepository.saveAll(placeRestaurants);

        List<PlaceFestival> placeFestivals = Arrays.asList(
                new PlaceFestival("16:00", "21:00", places.get(2), false),
                new PlaceFestival("18:00", "23:00", places.get(3), false)
        );
        placeFestivalJPARepository.saveAll(placeFestivals);

        List<PlaceTouristSpot> placeTouristSpots = Arrays.asList(
                new PlaceTouristSpot(places.get(4)),
                new PlaceTouristSpot(places.get(5))
        );
        placeTouristSpotJPARepository.saveAll(placeTouristSpots);

        List<Contents> contents = new ArrayList<>();
        List<ContentsMenu> contentsMenus = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            contents.add(Contents.builder().place(places.get(i)).page(0L).description("컨텐츠 설명" + (i+1)).build());
            contentsMenus.add(ContentsMenu.builder().contents(contents.get(i)).name(places.get(i).getName() + " 메뉴1").price(10000L).characteristic("메뉴 특징1").file(files.get(9 + i)).description("메뉴 설명1").build());
            contentsMenus.add(ContentsMenu.builder().contents(contents.get(i)).name(places.get(i).getName() + " 메뉴2").price(10000L).characteristic("메뉴 특징2").file(files.get(10 + i)).description("메뉴 설명2").build());
            contents.get(i).addContentsMenus(contentsMenus.get(i));
        }
        contentsRepository.saveAll(contents);
        contentsMenuRepository.saveAll(contentsMenus);

        List<ContentsFile> contentsFiles = Arrays.asList(
                ContentsFile.builder().contents(contents.get(0)).file(files.get(0)).build(),
                ContentsFile.builder().contents(contents.get(0)).file(files.get(1)).build(),
                ContentsFile.builder().contents(contents.get(1)).file(files.get(2)).build()
        );
        contentsFileRepository.saveAll(contentsFiles);
    }
}
