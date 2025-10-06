package com.shtrobotice.ShtKit.hardware.pipeline;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class BlueFilterPipeline extends OpenCvPipeline {
    private final Mat hsv = new Mat();
    private final Mat mask = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        Core.inRange(hsv, new Scalar(100, 150, 50), new Scalar(140, 255, 255), mask);

        return mask;
    }
}

