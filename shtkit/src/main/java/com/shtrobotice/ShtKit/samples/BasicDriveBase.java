package com.shtrobotice.ShtKit.samples;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.shtrobotice.ShtKit.hardware.DriveBase;

@TeleOp(name = "BasicDriveBase", group = "ShtKit")
@Disabled
public class BasicDriveBase extends LinearOpMode {

    @Override
    public void runOpMode() {
        /*
         * ================================
         * 로봇 드라이브 베이스 설정
         * ================================
         *
         * 필수 인자:
         * - hardwareMap : 하드웨어 맵
         * - gamepad      : 조작기 입력
         *
         * 1. 모터 정의 (필수)
         * - 순서: 왼쪽 위, 왼쪽 아래, 오른쪽 위, 오른쪽 아래
         * - hardwareMap Configure 이름으로 등록 필수
         *
         * 2. 모터 브레이크 설정 (선택)
         * - 역할: 로봇 제동 후 Power 0에 도달했을 때 브레이킹
         * - 값: DriveBase.MotorBrake.BRAKE 또는 DriveBase.MotorBrake.FLOAT
         *
         * 3. 모터 방향 설정 (선택)
         * - 역할: 제동 시 모터 정/역방향 회전
         * - 값: DriveBase.Direction.FORWARD 또는 DriveBase.Direction.REVERSE
         *
         * 4. 조작 스틱 정의 (선택)
         * - 역할: 로봇 제동 시 기준이 되는 스틱 입력
         * - 문법: () -> gamepad1.left_stick_x (람다 표현식)
         *   - stickX : 좌우 움직임
         *   - stickY : 상하 움직임
         *   - stickR : 회전 움직임
         *
         * ================================
         */
        DriveBase drivebase = new DriveBase(
                hardwareMap, gamepad1,
                "0",
                "1",
                "2",
                "3"
        );

        /*
         * 로봇 제동 후 Power 0에 도달했을 때 브레이킹 여부를 변경하는 함수입니다.
         */
        drivebase.setBreak(DriveBase.MotorBrake.BRAKE);

       /*
        * 제동 시 모터 정/역방향 회전를 변경하는 함수입니다.
        */
        drivebase.setDirection(DriveBase.Direction.FORWARD);

       /*
        * ================================
        * 로봇 드라이브 Headless 설정
        * ================================
        *
        * 설명:
        * - 로봇이 어떤 각도로 있어도 필드 기준으로 이동 가능
        *
        * 1. Pinpoint 정의 (필수)
        * - Configure 이름으로 등록 필수
        * - 사용 라이브러리: GoBildaPinpoint
        *
        * 2. 위치 정의 (필수)
        * - 로봇 중앙 기준 Pinpoint 위치 지정
        * - 단위: mm (로봇 중앙 → Pinpoint 중앙 거리)
        *
        * 3. 모터 방향 설정 (필수)
        * - 역할: 제동 시 Headless 좌/우 반전
        * - 값: DriveBase.Direction.FORWARD 또는 DriveBase.Direction.REVERSE
        *
        * ================================
        */
        drivebase.setHeadless("pin", 0,0, DriveBase.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            /*
             * 설정된 값을 기준으로 모터 제동
             */
            drivebase.update();
        }
    }
}
