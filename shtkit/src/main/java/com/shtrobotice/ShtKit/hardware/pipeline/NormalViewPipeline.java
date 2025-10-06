package com.shtrobotice.ShtKit.hardware.pipeline;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Mat;

public class NormalViewPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        return input;
    }
}
