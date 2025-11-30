package com.shtrobotice.ShtKit.hardware.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecannumBase {
    private DcMotor lf, lb, rf, rb;

    public MecannumBase(HardwareMap hardwareMap) {
        this(hardwareMap, "leftFront", "leftBack", "rightFront", "rightBack");
    }
    public MecannumBase(HardwareMap hardwareMap, String leftFront, String leftBack, String rightFront, String rightBack) {
        lf = hardwareMap.get(DcMotor.class, leftFront);
        lb = hardwareMap.get(DcMotor.class, leftBack);
        rf = hardwareMap.get(DcMotor.class, rightFront);
        rb = hardwareMap.get(DcMotor.class, rightBack);

        setBreak(DcMotor.ZeroPowerBehavior.BRAKE);

        setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void setBreak(DcMotor.ZeroPowerBehavior brake) {
        rf.setZeroPowerBehavior(brake);
        lb.setZeroPowerBehavior(brake);
        lf.setZeroPowerBehavior(brake);
        rb.setZeroPowerBehavior(brake);
    }
    public void setDirection(DcMotorSimple.Direction direction) {
        DcMotorSimple.Direction _r = (direction == DcMotorSimple.Direction.FORWARD) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
        setDirection(MotorDirection.RIGHTFRONT, direction);
        setDirection(MotorDirection.RIGHTBACK, direction);
        setDirection(MotorDirection.LEFTFRONT, _r);
        setDirection(MotorDirection.LEFTBACK, _r);
    }
    public void setDirection(MotorDirection motorDirection , DcMotorSimple.Direction direction) {
        switch (motorDirection) {
            case RIGHTFRONT:    rf.setDirection(direction); break;
            case LEFTBACK:      lb.setDirection(direction); break;
            case LEFTFRONT:     lf.setDirection(direction); break;
            case RIGHTBACK:     rb.setDirection(direction); break;
            default: break;
        }
    }
    public void setPower(double x, double y, double r) {
        double lfp = y + x + r;
        double lbp = y - x + r;
        double rfp = y - x - r;
        double rbp = y + x - r;

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