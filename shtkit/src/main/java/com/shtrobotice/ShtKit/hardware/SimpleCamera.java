package com.shtrobotice.ShtKit.hardware;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.shtrobotice.ShtKit.hardware.pipeline.AprilTagDetectionPipeline;
import com.shtrobotice.ShtKit.hardware.pipeline.BlueFilterPipeline;
import com.shtrobotice.ShtKit.hardware.pipeline.GrayscalePipeline;
import com.shtrobotice.ShtKit.hardware.pipeline.InvertedColorPipeline;
import com.shtrobotice.ShtKit.hardware.pipeline.NormalViewPipeline;
import com.shtrobotice.ShtKit.hardware.pipeline.RedFilterPipeline;
import com.shtrobotice.ShtKit.hardware.pipeline.YellowFilterPipeline;

import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Config
public class SimpleCamera {
    public static Map<Integer, Consumer<AprilTagDetection>> tagActions = new HashMap<>();
    public static Map<Integer, Runnable> notTagActions = new HashMap<>();
    HardwareMap hm;
    Integer id;
    OpenCvCamera webcam;
    OpenCvPipeline pipeline = null;
    AprilTagDetectionPipeline april = null;
    public static double April_FX = 578.272;
    public static double April_FY = 578.272;
    public static double April_CX = 402.145;
    public static double April_CY = 221.506;
    public static double April_TagSize = 0.166;

    public enum Pipeline {
        NORMAL, INVERTED, FILTER_RED, FILTER_BLUE, FILTER_YELLOW, GRAY_SCALE, APRIL_TAG
    }

    public SimpleCamera(HardwareMap hardwareMap, String WebcamName) {
        this(hardwareMap, WebcamName, Pipeline.NORMAL, 640, 480);
    }

    public SimpleCamera(HardwareMap hardwareMap, String WebcamName, Pipeline pipeline) {
        this(hardwareMap, WebcamName, pipeline, 640, 480);
    }

    public SimpleCamera(HardwareMap hardwareMap, String WebcamName, Pipeline pipeline, Integer width, Integer height) {
        hm = hardwareMap;

        id = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance()
                .createWebcam(hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, WebcamName), id);

        switch (pipeline) {
            case NORMAL:        this.pipeline = new NormalViewPipeline();   break;
            case INVERTED:      this.pipeline = new InvertedColorPipeline();break;
            case FILTER_RED:    this.pipeline = new RedFilterPipeline();    break;
            case FILTER_BLUE:   this.pipeline = new BlueFilterPipeline();   break;
            case FILTER_YELLOW: this.pipeline = new YellowFilterPipeline(); break;
            case GRAY_SCALE:    this.pipeline = new GrayscalePipeline();    break;
            case APRIL_TAG: april = new AprilTagDetectionPipeline(April_TagSize, April_FX, April_FY, April_CX, April_CY ); break;
            default: break;
        }

        webcam.setPipeline((april == null) ? this.pipeline : april);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(width, height, OpenCvCameraRotation.UPRIGHT);

                FtcDashboard.getInstance().startCameraStream(webcam, 0);
            }

            @Override
            public void onError(int errorCode) {}
        });
    }
    public void addTaggingAction(int id, Consumer<AprilTagDetection> action) { tagActions.put(id, action); }
    public void addNotTaggingAction(int id, Runnable action) { notTagActions.put(id, action); }
    public double getYaw(MatrixF R) {
        double r10 = R.get(1,0);
        double r00 = R.get(0,0);
        return Math.toDegrees(Math.atan2(r10, r00));
    }
    public double getPitch(MatrixF R) {
        double r20 = R.get(2,0);
        double r21 = R.get(2,1);
        double r22 = R.get(2,2);
        return Math.toDegrees(Math.atan2(-r20, Math.sqrt(r21*r21 + r22*r22)));
    }
    public double getRoll(MatrixF R) {
        double r21 = R.get(2,1);
        double r22 = R.get(2,2);
        return Math.toDegrees(Math.atan2(r21, r22));
    }
    public void aprilUpDate() {
        ArrayList<AprilTagDetection> detections = april.getLatestDetections();
        Set<Integer> detectedIDs = new HashSet<>();

        if (!detections.isEmpty()) {
            for (AprilTagDetection tag : detections) {
                detectedIDs.add(tag.id);
                if (tagActions.get(tag.id) != null) Objects.requireNonNull(tagActions.get(tag.id)).accept(tag);
            }
        }

        for (Integer id : notTagActions.keySet()) {
            if (!detectedIDs.contains(id)) {
                if (notTagActions.get(id) != null) Objects.requireNonNull(notTagActions.get(id)).run();
            }
        }
    }
}
