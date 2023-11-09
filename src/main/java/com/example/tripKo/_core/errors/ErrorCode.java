package com.example.tripKo._core.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  /*** Contents ***/
  PLACE_ID_CANNOT_FOUND("해당 플레이스를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
  RESTAURANT_ID_CANNOT_FOUND("해당 식당을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
  FESTIVAL_ID_CANNOT_FOUND("해당 축제를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
  TOURISTSPOT_ID_CANNOT_FOUND("해당 관광지를 찾지 못했습니다.", HttpStatus.NOT_FOUND),

  /*** Place ***/
  LOCATION_CANNOT_FOUND("해당 지역의 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LOCATION_RESTAURANT_CANNOT_FOUND("해당 지역의 식당 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LOCATION_FESTIVAL_CANNOT_FOUND("해당 지역의 축제 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LOCATION_TOURISTSPOT_CANNOT_FOUND("해당 지역의 관광지 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  /*** Review ***/
  REVIEW_CANNOT_FOUND("해당하는 리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  REVIEW_NOT_MINE("본인의 작성한 리뷰가 아닙니다.", HttpStatus.BAD_REQUEST),

  /*** Member ***/
  EMAIL_ALREADY_EXIST("이미 다른 사람이 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
  MEMBERID_ALREADY_EXIST("이미 다른 사람이 사용 중인 아이디입니다.", HttpStatus.BAD_REQUEST),
  RESERVATION_NOT_COMPLETE("예약이 완료되지 않았습니다.", HttpStatus.BAD_REQUEST),

  /*** Wishlist ***/
  WISHLIST_ALREADY_EXIST("이미 추가된 위시리스트입니다.", HttpStatus.BAD_REQUEST),
  WISHLIST_ALREADY_DELETE("이미 삭제된 위시리스트입니다.", HttpStatus.BAD_REQUEST),

  /*** Food ***/
  FOOD_CANNOT_FOUND("해당하는 음식을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  CONTENTS_CANNOT_FOUND("해당하는 컨텐츠를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  ;

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
