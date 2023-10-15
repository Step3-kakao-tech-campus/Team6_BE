package com.example.tripKo.domain.food;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodCategory {

    KOREAN("Korean", "한식"),
    WESTERN("Western", "양식"),
    CHINESE("Chinese", "중식"),
    JAPANESE("Japanese", "일식");

    private final String key;
    private final String title;

}
