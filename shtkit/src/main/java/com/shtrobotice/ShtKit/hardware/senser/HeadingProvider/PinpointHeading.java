package com.shtrobotice.ShtKit.hardware.senser.HeadingProvider;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class PinpointHeading implements HeadingProvider{
    private GoBildaPinpointDriver pp;
    private boolean reversed;

    public PinpointHeading(GoBildaPinpointDriver pinpoint) {
        pp = pinpoint;
        reversed = false;

        init();
    }
    @Override
    public void init() {pp.resetPosAndIMU();}

    @Override
    public double getHeading(AngleUnit angleUnit) {
        pp.update();
        double angle = pp.getHeading(angleUnit);
        return (reversed) ? -angle : angle;
    }
}
