# SHT_FTC

[![Java](https://img.shields.io/badge/Java-18-ED8B00.svg?logo=openjdk)](https://www.azul.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.20-585DEF.svg?logo=kotlin)](http://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-8.7.3-02303A.svg?logo=gradle)](https://gradle.org)
[![FTCRobotController](https://img.shields.io/badge/FtcRobotController-11.0-ED3F27.svg)](https://github.com/FIRST-Tech-Challenge/FtcRobotController)
[![FTCDashboard](https://img.shields.io/badge/FTCDashboard-0.5.0-6E8CFB.svg)]([https://acmerobotics.github.io/ftc-dashboard/)
[![LearnRoadRunner](https://img.shields.io/badge/LearnRoadRunner-1.0.1-3C467B.svg)](https://learnroadrunner.com/)

## FTC Software Working Kit

---

* #### Features
  * 사용 기능 통합화
  * 기능별 사용성 개선

* #### Class
  * DriveBase
  * SimpleCamera

---
## Releases v2.2 (2025-10-07)
#### 코드 간소화 및 사용성 개선 업데이트

### DriveBase
#### 주행 기반 코드 통합 클래스

* 필수 인자 제외 모든 인자에 기본값 사용
  * 하드웨어 이름 정의 기본값 지원
  * 세부 설정 정의 기본값 지원
* 예제 코드 설명 주석 가독성 개선

### SimpleCamera
#### 일반 카메라 사용 및 April Tag 인식 클레스

* 필수 인자 제외 모든 인자에 기본값 사용
  * 하드웨어 이름 정의 기본값 지원
* 예제 코드 설명 주석 가독성 개선

---
## Releases v2.1 (2025-10-06)
#### 지원 기능 추가 업데이트

### DriveBase
#### 주행 기반 코드 통합 클래스

* Headless 드라이브 Control Hub 내장 imu 지원
  * Control Hub 내장 imu `BNO055IMU` 추가
* 예제 코드 설명 주석 가독성 개선
---
## Releases v2.0 (2025-10-06)
#### 새 클레스 추가 업데이트

### SimpleCamera
#### 일반 카메라 사용 및 April Tag 인식 클레스

* 기존 카메라 OpenCV 코드 리팩토링
  * 가독성 개선 및 중복 제거
* 카메라 클래스 추가
  * `SimpleCamera` 사용 가능
* April Tag 인식 지원
  * `SimpleCamera.Pipeline.APRIL_TAG` Pipeline 사용
  * `addTaggingAction()` 및 `addNotTaggingAction()` 사용
* 각 목적 별 Pipeline 지원
  * 종류
    * NORMAL        : 기본 카메라 화면을 출력
    * INVERTED      : 색 반전 카메라 화면을 출력
    * FILTER_RED    : 빨강색 필터 카메라 화면을 출력
    * FILTER_BLUE   : 파란색 필터 카메라 화면을 출력
    * FILTER_YELLOW : 노란색 필터 카메라 화면을 출력
    * GRAY_SCALE    : 흑백 카메라 화면을 출력
    * APRIL_TAG     : April Tag 인식용 모드 (기본 카메라 화면)

### 예제 코드
* DriveBase & Headless : /shtkit/src/main/java/com/shtrobotice/ShtKit/samples/BasicSimpleCamera.java
---
## Releases v1.1 (2025-10-05)
#### 기능 함수 추가 업데이트

### DriveBase
#### 주행 기반 코드 통합 클래스

* 모터 제동 개별 방향 설정 함수 추가 
  * `setDirection()` 사용
* 모터 제동 속도 제어 키 설정 함수 추가
  * `setGovernor()` 사용 
  * 가변 키 사용을 통해 속도 세부 가변 조정 사용 가능
* 예제 코드 설명 주석 가독성 개선

---
## Releases v1.0 (2025-10-04)
#### 새 클레스 추가 업데이트

### DriveBase
#### 주행 기반 코드 통합 클래스

* 기존 모터 Drive 코드 리팩토링
  * 가독성 개선 및 중복 제거
* 로봇 베이스 드라이브 클래스 추가
    * `DriveBase` 사용 가능
* Headless 드라이브 지원
    * 필드 기준 이동 가능
    * `GoBildaPinpoint` 사용
  
### 예제 코드
* DriveBase & Headless : /shtkit/src/main/java/com/shtrobotice/ShtKit/samples/BasicDriveBase.java
---
