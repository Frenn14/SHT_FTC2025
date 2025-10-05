package com.shtrobotice.ShtKit.samples;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.shtrobotice.ShtKit.hardware.SimpleCamera;

@TeleOp(name = "BasicSimpleCamera")
public class BasicSimpleCamera extends LinearOpMode {
    @Override public void runOpMode() {
        SimpleCamera sc = new SimpleCamera(hardwareMap, "Webcam 1");

        waitForStart();
        while (opModeIsActive()) {
            // OpMode loop
        }
    }
}
