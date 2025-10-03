package com.shtrobotice.ShtKit.hardware;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.function.Supplier;

public class DriveBase {
    HardwareMap hm;
    Gamepad gp;
    DcMotor lf;
    DcMotor lb;
    DcMotor rf;
    DcMotor rb;
    MotorBrake brake;
    Direction revered;
    Supplier<Float> pdx;
    Supplier<Float> pdy;
    Supplier<Float> pdr;
    GoBildaPinpointDriver pp;
    Direction ppRevered;

    public enum Direction {
        FORWARD, REVERSE
    }

    public enum MotorBrake {
        BRAKE, FLOAT
    }

    public DriveBase(HardwareMap hardwareMap, Gamepad gamepad, String leftFront, String leftBack, String rightFront, String rightBack) {
        this(hardwareMap, gamepad, leftFront, leftBack, rightFront, rightBack, MotorBrake.BRAKE, Direction.FORWARD, ()->gamepad.left_stick_x, ()->-gamepad.left_stick_y, ()->gamepad.right_stick_x);
    }

    public DriveBase(HardwareMap hardwareMap, Gamepad gamepad, String leftFront, String leftBack, String rightFront, String rightBack, MotorBrake brake) {
        this(hardwareMap, gamepad, leftFront, leftBack, rightFront, rightBack, brake, Direction.FORWARD, ()->gamepad.left_stick_x, ()->-gamepad.left_stick_y, ()->gamepad.right_stick_x);
    }

    public DriveBase(HardwareMap hardwareMap, Gamepad gamepad, String leftFront, String leftBack, String rightFront, String rightBack, MotorBrake brake, Direction revered) {
        this(hardwareMap, gamepad, leftFront, leftBack, rightFront, rightBack, brake, revered, ()->gamepad.left_stick_x, ()->-gamepad.left_stick_y, ()->gamepad.right_stick_x);
    }

    public DriveBase(HardwareMap hardwareMap, Gamepad gamepad, String leftFront, String leftBack, String rightFront, String rightBack, MotorBrake brake, Direction revered, Supplier<Float> stickX, Supplier<Float> stickY, Supplier<Float> stickR){
        hm = hardwareMap;
        gp = gamepad;

        lf = hm.get(DcMotor.class, leftFront);
        lb = hm.get(DcMotor.class, leftBack);
        rf = hm.get(DcMotor.class, rightFront);
        rb = hm.get(DcMotor.class, rightBack);

        pdx = stickX;
        pdy = stickY;
        pdr = stickR;

        setBreak(brake);

        setDirection(revered);

        pp = null;
    }

    public void setHeadless(String name, int xOffset, int yOffset, Direction revered) {
        pp = hm.get(GoBildaPinpointDriver.class, name);
        pp.setOffsets(xOffset,yOffset, DistanceUnit.MM);
        pp.resetPosAndIMU();
        ppRevered = revered;
    }

    public void setBreak(MotorBrake brake) {
        this.brake = brake;

        switch (this.brake) {
            case BRAKE:
                lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                break;
            case FLOAT:
                lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                break;
            default: break;
        }
    }

    public void setDirection(Direction revered) {
        this.revered = revered;

        switch (this.revered) {
            case FORWARD:
                rf.setDirection(DcMotorSimple.Direction.FORWARD);
                rb.setDirection(DcMotorSimple.Direction.FORWARD);
                lf.setDirection(DcMotorSimple.Direction.REVERSE);
                lb.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case REVERSE:
                rf.setDirection(DcMotorSimple.Direction.REVERSE);
                rb.setDirection(DcMotorSimple.Direction.REVERSE);
                lf.setDirection(DcMotorSimple.Direction.FORWARD);
                lb.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            default: break;
        }
    }

    public void update() {
        double iy = pdy.get();
        double ix = pdx.get();
        double is = pdr.get();

        if (pp != null) {
            pp.update();
            Pose2D pos = pp.getPosition();

            double heading = 0;

            switch (ppRevered) {
                case FORWARD: heading = pos.getHeading(AngleUnit.RADIANS); break;
                case REVERSE: heading = -pos.getHeading(AngleUnit.RADIANS); break;
                default: break;
            }

            ix = pdx.get() * Math.cos(heading) - pdy.get() * Math.sin(heading);
            iy = pdx.get() * Math.sin(heading) + pdy.get() * Math.cos(heading);
        }

        double lfp = iy + ix + is;
        double lbp = iy - ix + is;
        double rfp = iy - ix - is;
        double rbp = iy + ix - is;

        double max = Math.max(
                Math.max(Math.abs(lfp), Math.abs(lbp)),
                Math.max(Math.abs(rfp), Math.abs(rbp))
        );

        if (max > 1.0) {
            lfp /= max;
            lbp /= max;
            rfp /= max;
            rbp /= max;
        }

        lf.setPower(lfp);
        lb.setPower(lbp);
        rf.setPower(rfp);
        rb.setPower(rbp);
    }
}
