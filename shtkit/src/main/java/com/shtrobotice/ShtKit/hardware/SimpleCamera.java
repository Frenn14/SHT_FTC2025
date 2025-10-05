package com.shtrobotice.ShtKit.hardware;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class SimpleCamera {

    HardwareMap hm;
    FtcDashboard db;
    Integer cMV;
    OpenCvCamera webcam;

    public SimpleCamera(HardwareMap hardwareMap, String WebcamName) {
        this(hardwareMap, WebcamName, 640, 480);
    }
    public SimpleCamera(HardwareMap hardwareMap, String WebcamName, Integer width, Integer height) {
        hm = hardwareMap;

        cMV =  hm.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(hm.get(WebcamName.class, WebcamName), cMV);

        db = FtcDashboard.getInstance();
        db.startCameraStream(webcam, 0);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });
    }
}
