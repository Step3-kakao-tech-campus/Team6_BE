package com.example.tripKo.domain.food.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.food.dao.FoodRepository;
import com.example.tripKo.domain.food.dto.response.FoodResponse;
import com.example.tripKo.domain.food.entity.Food;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SortComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FoodService {
    private final FoodRepository foodRepository;

    @Transactional
    public List<FoodResponse> findByKeyword(String query, String sort) {
        //사용자의 검색 키워드 쪼개기 six/times
        String[] keywords = query.toUpperCase().split(" ");

        //키워드 별로 해당 키워드를 포함하는 음식 찾기
        List<Food> foods = new ArrayList<>();
        for (String keyword : keywords) {
            foods.addAll(foodRepository.findAllByKeyword(keyword));
        }

        if (foods.isEmpty()) {
            throw new Exception404("해당하는 음식을 찾을 수 없습니다. query : " + query);
        }

        List<Food> foodSet = new ArrayList<>(new HashSet<>(foods));

        switch (sort) {
            // 추천순의 경우 사용자 검색 키워드가 많이 포함된 음식부터 출력
            case ("recommended"):
                Collections.sort(foodSet, new Comparator<Food>() {
                    @Override
                    public int compare(Food f1, Food f2) {
                        String f1Keyword = f1.getKeyword().toUpperCase();
                        String f2Keyword = f2.getKeyword().toUpperCase();
                        int count1 = 0;
                        int count2 = 0;
                        // 각 키워드 별로 반복문을 돌며 포함 개수를 센다.
                        for (String keyword : keywords) {
                            for (int i = 0; i < f1Keyword.length()-keyword.length()+1; i++) {
                                if (f1Keyword.substring(i, i+keyword.length()).equals(keyword)) {
                                    count1++;
                                }
                            }
                            for (int i = 0; i < f2Keyword.length()-keyword.length()+1; i++) {
                                if (f2Keyword.substring(i, i+keyword.length()).equals(keyword)) {
                                    count2++;
                                }
                            }
                        }
                        //다양한 키워드가 포함된 음식일수록 순위가 높게 하기 위해서 추가
                        //ex) bibim bap 검색 시 bibimbap은 2개, bibimnoodle은 1개, gimbap은 1개가 곱해짐
                        //-> bibim만, bap만 따로 검색된 bibimnoodle과 gimbap보다 bibimbap이 높은 순위를 차지하게 됨
                        count1 *= Collections.frequency(foods, f1);
                        count2 *= Collections.frequency(foods, f2);
                        if (count1 > count2)
                            return -1;
                        else if (count1 < count2)
                            return 1;
                        else {
                            return f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase());
                        }
                    }
                });
                break;
            // 오름차순(대소문자 무시) 정렬
            case ("ascending"):
                foodSet.sort(Comparator.comparing(Food::getName, String.CASE_INSENSITIVE_ORDER));
                break;
            // 내림차순(대소문자 무시) 정렬
            case ("descending"):
                foodSet.sort(Comparator.comparing(Food::getName, String.CASE_INSENSITIVE_ORDER).reversed());
                break;
            // 조회순 정렬
            case ("views"):
                foodSet.sort(Comparator.comparing(Food::getView).reversed());
                break;
            default:
                throw new Exception404("잘못된 정렬 기준입니다. sort : " + sort);
        }

        List<FoodResponse> foodResponses = foodSet.stream().map(f->FoodResponse.builder().food(f).build()).collect(Collectors.toList());

        return foodResponses;
    }
}
