package com.shtrobotice.ShtKit.samples;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.shtrobotice.ShtKit.hardware.SimpleCamera;

@TeleOp(name = "BasicSimpleAprilCamera", group = "ShtKit")
@Disabled
public class BasicSimpleAprilCamera extends LinearOpMode {

    /*
     * April 인식 사용를 위한 SimpleCamera class 예제 코드
     *
     * SimpleCamera 사용을 위한 선언은 필수입니다.
     * 선언 이외 기능 사용을 위한 함수는 선택적으로 사용 가능합니다.
     *
     *
     */

    @Override
    public void runOpMode() {
        /*
         * ================================
         * 카메라 설정
         * ================================
         *
         * 필수 인자:
         * - hardwareMap : 하드웨어 맵
         *
         * 1. 카메라 정의 (필수)
         * - 카메라 이름 정의
         * - hardwareMap Configure 이름으로 등록 필수
         *
         * 2. 카메라 파이프라인(용도) 정의 (선택)
         * - 역할: 카메라 출력 형태 또는 카메라 사용 용도 정의
         * - 기본값: SimpleCamera.Pipeline.NORMAL (April Tag 인식 불가)
         * - 값:
         *   - NORMAL       : 기본 카메라 화면을 출력
         *   - INVERTED     : 색 반전 카메라 화면을 출력
         *   - FILTER_RED   : 빨강색 필터 카메라 화면을 출력
         *   - FILTER_BLUE  : 파란색 필터 카메라 화면을 출력
         *   - FILTER_YELLOW: 노란색 필터 카메라 화면을 출력
         *   - GRAY_SCALE   : 흑백 카메라 화면을 출력
         *   - APRIL_TAG    : April Tag 인식용 모드 (기본 카메라 화면)
         *
         * 3. 화면 해상도 width/height 설정 (선택)
         * - 기본값 : 640, 480 (추천)
         *
         * ================================
         */
        SimpleCamera simpleCamera = new SimpleCamera(
                hardwareMap,
                "Webcam 1",
                SimpleCamera.Pipeline.APRIL_TAG,
                640,
                480
        );

        /*
         * ================================
         * April Tag 인식 액션 설정
         * ================================
         *
         * 설명:
         * - 설정된 ID의 April Tag 인식 시 실행할 코드를 정의합니다.
         * - April 파이프 라인 이외는 사용하지 않음
         *
         * 1. ID 정의 (필수)
         * - 값 : 정수 형태의 ID를 입력 (FTC 기준 0~8 사용)
         *
         * 2. 실행 익명 함수 정의 (필수)
         * - 문법 : tag->({ Code }); (람다 표현식)
         * - Code 위치에 예제와 같이 실행시 코드를 입력
         * - tag 역할 : 인식된 April 태그의 정보를 코드에 제공
         * - Tag 정보:
         *   - tag.id               : 인식된 April Tag ID
         *   - tag.hamming          : 인식 시 오류 보정 정도
         *   - tag.pose.x           : 카메라 기준 April Tag X 위치 (미터 단위)
         *   - tag.pose.y           : 카메라 기준 April Tag Y 위치 (미터 단위)
         *   - tag.pose.z           : 카메라 기준 April Tag Z 위치 (미터 단위)
         *   - tag.pose.R           : 3x3 회전 행렬
         *     - getYaw()           : 행렬 값을 통해 카메라 기준 수평 회전 반환 함수
         *     - getPitch()         : 행렬 값을 통해 위/아래 기울기 반환 함수
         *     - getRoll()          : 행렬 값을 통해 좌우 기울기 반환 함수
         *   - tag.center.x         : 중심 X 좌표 (픽셀 단위)
         *   - tag.center.y         : 중심 Y 좌표 (픽셀 단위)
         *   - tag.corners          : 태그 모서리 좌표 배열 (PointF[4])
         *   - tag.decisionMargin   : 태그 인식 신뢰도 (높을수록 확실)
         *
         * ================================
         */
        simpleCamera.addTaggingAction(0, tag-> {
            telemetry.addLine("Tag 0 :");
            telemetry.addData("Center", "(%.2f, %.2f)", tag.center.x, tag.center.y);
            telemetry.addData("pose", "(%.2f, %.2f, %.2f)", tag.pose.x, tag.pose.y, tag.pose.z);
            telemetry.update();
        });

        /*
         * ================================
         * April Tag 인식 액션 설정
         * ================================
         *
         * 설명:
         * - 설정된 ID의 April Tag 인식 되지 않을시 실행할 코드를 정의합니다.
         * - April 파이프 라인 이외는 사용하지 않음
         *
         * 1. ID 정의 (필수)
         * - 값 : 정수 형태의 ID를 입력 (FTC 기준 0~8 사용)
         *
         * 2. 실행 익명 함수 정의 (필수)
         * - 문법 : ()->({ Code }); (람다 표현식)
         *
         * ================================
         */
        simpleCamera.addNotTaggingAction(0, ()-> {
            telemetry.addLine("Not Tagging 0");
        });

        waitForStart();
        while (opModeIsActive()) {
           /*
            * 설정된 값을 기준으로 April Tag 탐색
            * - April 파이프 라인 이외는 사용하지 않음
            */
            simpleCamera.aprilUpDate();

            telemetry.update();
        }
    }
}
