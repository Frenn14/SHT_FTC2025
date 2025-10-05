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
    Supplier<Float> pdx;
    Supplier<Float> pdy;
    Supplier<Float> pdr;
    Supplier<Float> gKey;
    Boolean vRevered;
    Double minValue;
    Double maxValue;
    GoBildaPinpointDriver pp;
    Direction ppRevered;

    public enum Direction {
        FORWARD, REVERSE
    }

    public enum MotorDirection {
        LEFTFRONT, LEFTBACK, RIGHTFRONT, RIGHTBACK
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

    public DriveBase(HardwareMap hardwareMap, Gamepad gamepad, String leftFront, String leftBack, String rightFront, String rightBack, MotorBrake brake, Direction motorDirection) {
        this(hardwareMap, gamepad, leftFront, leftBack, rightFront, rightBack, brake, motorDirection, ()->gamepad.left_stick_x, ()->-gamepad.left_stick_y, ()->gamepad.right_stick_x);
    }

    public DriveBase(HardwareMap hardwareMap, Gamepad gamepad, String leftFront, String leftBack, String rightFront, String rightBack, MotorBrake brake, Direction motorDirection, Supplier<Float> stickX, Supplier<Float> stickY, Supplier<Float> stickR){
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

        Direction _r = (motorDirection == Direction.FORWARD) ? Direction.REVERSE : Direction.FORWARD;

        setDirection(MotorDirection.LEFTFRONT, _r);
        setDirection(MotorDirection.LEFTBACK, _r);
        setDirection(MotorDirection.RIGHTFRONT, motorDirection);
        setDirection(MotorDirection.RIGHTBACK, motorDirection);

        pp = null;
        gKey = null;
        vRevered = null;
    }

    public void setHeadless(String name, Integer xOffset, Integer yOffset, Direction revered) {
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

    public void setDirection(MotorDirection motorDirection ,Direction direction) {
        DcMotorSimple.Direction _direction = (direction == Direction.FORWARD) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;

        switch (motorDirection) {
            case LEFTFRONT: lf.setDirection(_direction); break;
            case LEFTBACK: lb.setDirection(_direction); break;
            case RIGHTFRONT: rf.setDirection(_direction); break;
            case RIGHTBACK: rb.setDirection(_direction); break;
            default: break;
        }
    }

    public void setGovernor(Supplier<Float> governorKey, Double minValue, Double maxValue) {
        gKey = governorKey;
        this.minValue = minValue;
        this.maxValue = maxValue;
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

        double _p = 1.0;
        if (gKey != null) _p = (gKey.get() - minValue) / (maxValue - minValue);

        lf.setPower(lfp * _p);
        lb.setPower(lbp * _p);
        rf.setPower(rfp * _p);
        rb.setPower(rbp * _p);
    }
}
