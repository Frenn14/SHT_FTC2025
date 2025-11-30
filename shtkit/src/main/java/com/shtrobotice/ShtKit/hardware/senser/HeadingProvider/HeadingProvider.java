package com.shtrobotice.ShtKit.hardware.senser.HeadingProvider;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public interface HeadingProvider {
    void init();
    double getHeading(AngleUnit angleUnit);
}
