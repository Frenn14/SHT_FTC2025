package com.shtrobotice.ShtKit.samples;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.shtrobotice.ShtKit.hardware.DriveBase;
import com.shtrobotice.ShtKit.hardware.drive.MecannumBase;
import com.shtrobotice.ShtKit.hardware.senser.HeadingProvider.PinpointHeading;

@TeleOp(name = "BasicDriveBase", group = "ShtKit")
@Disabled
public class BasicDriveBase extends LinearOpMode {

    /*
     * 드라이브 베이스 제어를 위한 DriveBase class 예제 코드
     */

    @Override
    public void runOpMode() {
        MecannumBase mecannumBase   = new MecannumBase(
                hardwareMap,
                "LeftFrontMotor",
                "LeftBackMotor",
                "RightFrontMotor",
                "RightBackMotor"
        );
        DriveBase driveBase         = new DriveBase(new MecannumBase(hardwareMap), gamepad1);

        driveBase.setInX(()->gamepad1.left_stick_y);
        driveBase.setInY(()->gamepad1.right_stick_x);
        driveBase.setInRot(()->gamepad1.left_stick_y);

        driveBase.setHeadless( new PinpointHeading(hardwareMap, "pinpoint") );

        driveBase.setMecannumBase( new MecannumBase(hardwareMap) );

        driveBase.initInput(gamepad1);


        waitForStart();

        while (opModeIsActive()) {
            /*
             * 설정된 값을 기준으로 모터 제동
             */
            driveBase.update();
        }
    }
}
