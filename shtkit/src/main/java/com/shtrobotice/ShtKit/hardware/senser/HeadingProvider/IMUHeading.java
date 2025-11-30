package com.shtrobotice.ShtKit.hardware.senser.HeadingProvider;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMUHeading implements HeadingProvider{
    private BNO055IMU imu;
    private IMUAxis axis;
    private boolean reversed;

    public IMUHeading(HardwareMap hardwareMap, String name) {
        this(hardwareMap.get(BNO055IMU.class, name));
    }

    public IMUHeading(HardwareMap hardwareMap, String name, IMUDirection usbDirection, IMUDirection logoDirection) {
        this(hardwareMap.get(BNO055IMU.class, name), usbDirection, logoDirection);
    }

    public IMUHeading(BNO055IMU imu) {
        this.imu        = imu;

        init();

        AutoOrientation();
    }

    public IMUHeading(BNO055IMU imu, IMUDirection usbDirection, IMUDirection logoDirection) {
        this.imu        = imu;

        init();

        setOrientation(usbDirection, logoDirection);
    }


    @Override
    public void init() {
        BNO055IMU.Parameters para = new BNO055IMU.Parameters();
        para.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(para);
    }

    public void AutoOrientation() {
        Acceleration g = imu.getGravity();

        double gx = Math.abs(g.xAccel);
        double gy = Math.abs(g.yAccel);
        double gz = Math.abs(g.zAccel);

        // 일단 로봇 보고 검사 후 반영
    }

    public void setOrientation(IMUDirection usbDirection, IMUDirection logoDirection) {
        // 일단 로봇 보고 검사 후 반영
    }

    @Override
    public double getHeading(AngleUnit angleUnit) {
        Orientation o = imu.getAngularOrientation();
        double angular = 0;

        switch (axis) {
            case xAxis: angular = o.firstAngle; break;
            case yAxis: angular = o.secondAngle; break;
            case zAxis: angular = o.thirdAngle; break;
            default: break;
        }
        double angle = (reversed) ? -angular : angular;

        return (angleUnit == AngleUnit.RADIANS) ? angle : Math.toDegrees(angle);
    }
}
