한국이 <span style="color:#FF8A00">**처음**</span>이라도, 한국어를 <span style="color:#FF8A00">**몰라도**</span>

<span style="color:#FF8A00">**제약없이**</span> 즐기는 한국 로컬여행

# **Trip<span style="color:#FF8A00">K</span>o**

한국 로컬 관광정보 플랫폼

## 서비스 소개

#### 1️⃣다양한 관광 정보 제공

- 현지인들에게 인기 있는 장소를 구석구석 수록
- 한국어와 문화를 몰라도 만족스러운 여행 경험 선사할 수 있는 정확하고 자세한 정보를 영어로 제공

#### 2️⃣한국 음식 검색 기능

- 오번역을 포함한 검색이 가능 
- 음식의 재료 및 제조 과정 등 정확하고 자세한 정보 제공
- 검색한 음식을 이용할 수 있는 관련 식당정보 제공

#### 3️⃣예약하기

- 예약 대행 서비스
- 의사소통의 어려움 없이 간편하게 식당, 축제 예약

#### 4️⃣사용자 편의 기능

- 찜하기 기능
- 리뷰 작성 및 관리
- 프로필 수정

## 개발 환경

### 💡BackEnd

- #### Language & Framework

    <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=OpenJDK&logoColor=white" height="29"/> 
    <img src="https://img.shields.io/badge/springBoot-6DB33F?style=for-the-badge&logo=springBoot&logoColor=white" height="29"/> 

- #### DataBase
    <img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white" height="29"/> 
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" height="29"/> 

- #### Image Storage
    <img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat&logo=amazons3&logoColor=white" height="29"/>

### 💡FrontEnd

- #### Language & Framework
    <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white" height="29"/>
    <img src="https://img.shields.io/badge/React-61DAFB?style=flat&logo=react&logoColor=white" height="29"/>
    <img src="https://img.shields.io/badge/Tailwind CSS-06B6D4?style=flat&logo=tailwindcss&logoColor=white" height="29"/>
    <img src="https://img.shields.io/badge/Redux Toolkit-764ABC?style=flat&logo=redux&logoColor=white" height="29"/>

## 구성도

![구성도](https://github.com/Step3-kakao-tech-campus/Team6_BE/assets/99969990/8ed965fa-2ca2-467d-bea5-3bbca7c6ef02)

## 최종 ERD

- #### 전체 구조

  ![image](https://github.com/Step3-kakao-tech-campus/Team6_BE/assets/99969990/7156401e-64f8-4fdc-a98a-03de36f6d549)


- #### 플레이스 관련

  ![image-5](https://github.com/Step3-kakao-tech-campus/Team6_BE/assets/99969990/da7255fc-f886-4f25-bfd0-74b24a8a9ebf)

- #### 음식 관련

  ![image-4](https://github.com/Step3-kakao-tech-campus/Team6_BE/assets/99969990/88a21957-ee29-4d4d-bfbe-3462bda7c836)

- #### 주소 관련

  ![image-2](https://github.com/Step3-kakao-tech-campus/Team6_BE/assets/99969990/0856e9ef-0ba0-49bb-a432-8aede813d090)


- #### 회원 관련

  ![image-3](https://github.com/Step3-kakao-tech-campus/Team6_BE/assets/99969990/560fa62e-7ac6-4e14-9645-0d2dc68de253)

- #### 파일 관련

  ![image-6](https://github.com/Step3-kakao-tech-campus/Team6_BE/assets/99969990/b2c4d420-0480-46e8-af6e-c9f9c37968de)

## 주안점을 두고 개발한 기능

#### 1️⃣Redis를 이용한 Refresh-Token 구현

RefreshTokenFilter, RedisConfig, RedisUtil, JwtProvider

- JWT와 Spring Security를 이용해서 Access-Token을 발급하는 단순한 인증 프로세스를 넘어서 Access-Token 만료 시 재 발급 자동화를 위한 Refresh-Token 도입
- Refresh-Token은 엑세스가 빈번한 데이터이므로 다른 DB들보다 빠르고 가벼운 인메모리 DB인 Redis를 이용해 저장
- Refresh-Token과 관련된 필터를 개별적으로 구현하여 단일 책임 원칙(SRP)을 지향 
- Refresh-Token의 도입으로 Access Token의 유효기간을 단축해 정보 유출 위험을 줄여 보안성 강화
- 빈번한 로그인 만료로 인해 사용자의 서비스 이용 흐름을 방해하지 않아 사용자 편의성 증대

#### 2️⃣S3를 이용한 이미지 관리 (멘토님 권장사항 반영)

- 본 프로젝트는 관광정보 플랫폼이므로 컨텐츠, 리뷰 등 이미지 처리가 잦기 때문에 이미지들을 프로젝트 내부에 저장할 경우 용량과 관리에 대한 이슈 발생 가능성이 매우 높음
- S3를 이용한 이미지 저장/삭제 로직을 구현하여 리뷰 작성/수정, 프로필 이미지 수정 등의 API에 적용


#### 3️⃣예외 및 오류 처리를 통한 서비스 개선

- 리뷰 작성 시 작성하려는 장소 유형에 따라 예약 상태가 완료여야 하거나, 리뷰 작성 날짜가 예약 날짜보다 뒤여야 하는 등의 다양한 조건 처리
- 이러한 과정에서 발생하는 유저의 요청에 대한 예외 및 오류 처리를 진행
- 이를 통헤 프로그램 실행 중 오작동이나 비정상적 종료를 방지하는 효과를 기대


#### 4️⃣5️⃣6️⃣