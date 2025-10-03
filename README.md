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

---

## Releases v1.0 (2025-10-04)

### DriveBase
#### 주행 기반 코드 통합 클래스

* 로봇 베이스 드라이브 클래스 추가
    * `DriveBase` 사용 가능
* Headless 드라이브 지원
    * 필드 기준 이동 가능
    * `GoBildaPinpoint` 사용
* 기존 모터 Drive 코드 리팩토링
    * 가독성 개선 및 중복 제거
  
### 예제 코드 (로컬)
* DriveBase & Headless : [/shtkit/src/main/java/com/shtrobotice/ShtKit/samples/BasicDriveBase.java](file://shtkit/src/main/java/com/shtrobotice/ShtKit/samples/BasicDriveBase.java)
---
