package com.shtrobotice.ShtKit.hardware.pipeline;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class RedFilterPipeline extends OpenCvPipeline {
    private final Mat hsv = new Mat();
    private final Mat mask1 = new Mat();
    private final Mat mask2 = new Mat();
    private final Mat mask = new Mat();

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        Core.inRange(hsv, new Scalar(0, 100, 100), new Scalar(10, 255, 255), mask1);
        Core.inRange(hsv, new Scalar(170, 100, 100), new Scalar(180, 255, 255), mask2);

        Core.addWeighted(mask1, 1.0, mask2, 1.0, 0.0, mask);
        return mask;
    }
}
