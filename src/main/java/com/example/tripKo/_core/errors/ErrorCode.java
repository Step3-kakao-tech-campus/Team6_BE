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
  PLACE_NOT_MATCH("요청한 URL의 Place 타입과 요청한 id의 Place 타입이 다릅니다.", HttpStatus.BAD_REQUEST),

  /*** Place ***/
  LOCATION_CANNOT_FOUND("해당 지역의 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LOCATION_RESTAURANT_CANNOT_FOUND("해당 지역의 식당 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LOCATION_FESTIVAL_CANNOT_FOUND("해당 지역의 축제 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LOCATION_TOURISTSPOT_CANNOT_FOUND("해당 지역의 관광지 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  /*** Review ***/
  REVIEW_CANNOT_FOUND("해당하는 리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  REVIEW_NOT_MINE("본인의 작성한 리뷰가 아닙니다.", HttpStatus.BAD_REQUEST),
  REVIEW_BEFORE_RESERVATION("리뷰는 예약 완료 후에 작성이 가능합니다.", HttpStatus.BAD_REQUEST),
  REVIEW_BEFORE_RESERVATION_TIME("리뷰는 예약 시간 이후에 작성 가능합니다.", HttpStatus.BAD_REQUEST),
  REVIEW_ALREADY_DONE("이미 작성한 리뷰가 존재합니다.", HttpStatus.BAD_REQUEST),
  REVIEW_PAGE_CANNOT_FOUND("더이상 리뷰가 없습니다.", HttpStatus.NOT_FOUND),


  /*** Member ***/
  EMAIL_ALREADY_EXIST("This email is already being used by someone else", HttpStatus.BAD_REQUEST),
  MEMBERID_ALREADY_EXIST("This ID is already being used by someone else", HttpStatus.BAD_REQUEST),
  RESERVATION_NOT_COMPLETE("예약이 완료되지 않았습니다.", HttpStatus.BAD_REQUEST),

  /*** Wishlist ***/
  WISHLIST_ALREADY_EXIST("이미 추가된 위시리스트입니다.", HttpStatus.BAD_REQUEST),
  WISHLIST_ALREADY_DELETE("이미 삭제된 위시리스트입니다.", HttpStatus.BAD_REQUEST),

  /*** Food ***/
  FOOD_CANNOT_FOUND("해당하는 음식을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  CONTENTS_CANNOT_FOUND("해당하는 컨텐츠를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  /*** File ***/
  IMAGE_CANNOT_DELETE("이미지를 삭제하는 중 문제가 발생하였습니다.", HttpStatus.NOT_MODIFIED),
  IMAGE_CANNOT_FOUND_AND_DELETE("삭제할 이미지가 없습니다.", HttpStatus.NOT_FOUND),
  IMAGE_CANNOT_FOUND("이미지를 찾는 중 문제가 발생하였습니다.", HttpStatus.NOT_FOUND),
  IMAGE_TYPE_NOT_CORRECT("올바르지 않은 파일 확장자 형식입니다.", HttpStatus.BAD_REQUEST),
  IMAGE_CANNOT_SAVE("이미지를 저장하는 중 오류가 발생하였습니다.", HttpStatus.NOT_MODIFIED)
  ;

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
