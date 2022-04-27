# project

---

## 주요 기술 스택

Kotlin 1.6

Spring boot 2.6.7

Mysql 8.0

Redis 6.2.5


Cloud SQL

CLoud Build

Cloud Run

---

![](https://velog.velcdn.com/images/roo333/post/63bd42bc-afcd-44be-9ae9-d3d843ad9b2e/image.png)


---

## 데이터베이스 구조


![image](https://user-images.githubusercontent.com/38750489/165446820-635b6f86-af3e-410a-99f7-9f5c3bb38715.png)

유저와 권한만 있는 단순한 테이블 구조입니다. 유저랑 어드민이랑 서로 권한을 다르게 하기 위해 1:N이 아닌 N:M으로 관계를 설정했습니다.

---

## [파일 구조](https://github.com/Cozak92/project/blob/main/src/main/resources/tree.text)




---

## 아키텍쳐

![hexagonal-architecture_hu6764515d7030d45af6f7f498c79e292b_50897_956x0_resize_box_3](https://user-images.githubusercontent.com/38750489/165505245-778ba3c6-2d52-4713-b49f-b53d7ddde752.png)

유연한 소프트웨어 구조를 갖기 위해 모놀리 헥사고날 아키텍처를 기반으로 개발했습니다.

---

# API

https://ably-project-ifvfniimwq-du.a.run.app/api/v1/swagger-ui/index.html

해당 주소에서 API 깔끔하게 볼 수 있습니다. 실제 요청은 header가 없어서 불가능 합니다.😥
API에 대한 설명도 작성 되어있습니다.

https://www.getpostman.com/collections/143f3c21ab1c4ee4c710

Postman Collection입니다.

첫 호출시 Cloud Run 콜드스타트 때문에 응답이 느릴 수 있습니다!

### 계정 정보

* user 
  * email - user1@user.com
  * passwrod - user1

* admin
  * email - admin1@admin.com
  * password - admin1



## 전화 인증

[twilio](https://www.twilio.com/) 외부 서비스를 사용했습니다. 인증 코드를 요청하고 인증코드를 통해 twilio에서 발급된 SID를 제공합니다. SID는 Redis에 저장되며 5분이 지나면 자동으로 삭제됩니다.


* **POST** /api/v1/verification/sms - 인증코드 발급
* **POST** /api/v1/verification/sms/{verificationCode} - 인증코드 검증

## 회원 가입

필요한 정보를 받아 회원 가입을 진행합니다. 헤더에 SID가 없으면 전화 인증이 안된걸로 간주하고 회원가입이 진행되지 않습니다. Redis에 요청한 SID가 없을 경우 마찬가지로 가입이 진행되지 않습니다. 

이메일이나 휴대폰 번호 중복시 가입이 불가능합니다.

* **POST** /api/v1/user - 회원가입

## 로그인 & JWT 발급

이메일과 비밀번호를 통해 DB에서 유저정보를 확인하고 JWT토큰을 발급합니다.

* **POST** /api/v1/authorize

## 회원 정보 확인

회원 정보를 확인하는 API입니다.

* **GET** /api/v1/user - JWT 토큰을 기반으로 현재 요청한 회원의 정보를 확인합니다.
* **GET** /api/v1/user/{userId} - ADMIN 권한을 가능 유저만 요청을 할 수 있으며 userId에 해당하는 회원의 정보를 확인 할 수 있습니다.

## 회원 정보 수정


* **PUT** /api/v1/user/{userId} - 회원 정보를 받아 회원을 수정합니다.
* **PATCH** /api/v1/user/{userId}/passwd - 유저 비밀번호를 수정합니다. 휴대폰 인증에서 받은 SID가 헤더에 들어있어야 합니다.

## 회원 탈퇴

* **Delete** /api/v1/user/{userId} - 해당 userId로 탈퇴를 진행합니다.




