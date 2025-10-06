package com.shtrobotice.ShtKit.hardware.pipeline;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

public class InvertedColorPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        Mat output = new Mat();
        Core.bitwise_not(input, output); // 색 반전
        return output;
    }
}
