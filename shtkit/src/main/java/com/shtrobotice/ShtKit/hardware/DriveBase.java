package com.shtrobotice.ShtKit.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.shtrobotice.ShtKit.hardware.drive.MecannumBase;
import com.shtrobotice.ShtKit.hardware.senser.HeadingProvider.HeadingProvider;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.function.Supplier;

public class DriveBase {
    private MecannumBase mecannumBase;
    private Supplier<Float> inX, inY, inRot;
    private HeadingProvider heading;

    public DriveBase(MecannumBase mecannumBase) { this.mecannumBase = mecannumBase; }
    public DriveBase(MecannumBase mecannumBase, Gamepad gamepad) {
        this.mecannumBase = mecannumBase;
        initInput(gamepad);
    }
    public void initInput(Gamepad gamepad) {
        inX     = ()->gamepad.left_stick_x;
        inY     = ()->gamepad.left_stick_y;
        inRot   = ()->gamepad.right_stick_x;
    }
    public void setMecannumBase(MecannumBase mecannumBase) { this.mecannumBase = mecannumBase; }
    public void setInX(Supplier<Float> key) { inX = key; }
    public void setInY(Supplier<Float> key) { inY = key; }
    public void setInRot(Supplier<Float> key) { inRot = key; }
    public void setHeadless(HeadingProvider heading) { this.heading = heading; }

    public void update() {
        double x = inX.get();
        double y = inY.get();
        double r = inRot.get();

        if(heading != null) {
            double h = heading.getHeading(AngleUnit.RADIANS);
            x = x * Math.cos(h) - y * Math.sin(h);
            y = x * Math.sin(h) + y * Math.cos(h);
        }

        mecannumBase.setPower(x,y,r);
    }
}
