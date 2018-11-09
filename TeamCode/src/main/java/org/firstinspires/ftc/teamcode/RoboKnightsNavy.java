package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Windows on 2016-09-19.
 */
public class RoboKnightsNavy extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfNu6or/////AAABmbz3RBzAq0E9lLg95aebMBM2Er4MR6IvcC26uRVkA8M7SqU+48qaf2KciR2LRYOkQv1L1KWo3EWY+ALYOBdHyP5oro61RsCW24FOXYhooU2QdYVk28MQfU8dUvG1NDMJ8zN/5RnM3fMPfMqEtZ+4hluqGNCjTdwzrecN9ZpAtDpTpIQ4V5eN9M1YYyNfT9QJRswL7PdyZ4jDC70w+KNtx5U6iRJNKLira1dzBvoGZjGDBRoU9w+m1RVFzgUHVBDGQ85BQKlXNUDUcJKW1e9/2B6YkAYr38kSTQw1FK3ywpJ9FwtKOUw8ByBf96n7ScWnC72l2keQQ6tsh6xsZioxaXO8WOBO4ukoFWuevJOzFLn+";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("sphere");
        beacons.get(0).setName("sphere");

        waitForStart();

        beacons.activate();

        while (opModeIsActive()) {
            for (VuforiaTrackable beac : beacons) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

                if (pose != null) {
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(beac.getName() + "-Translation", translation);

                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                    telemetry.addData(beac.getName() + "-Degrees", degreesToTurn);
                }
            }
            telemetry.update();
        }
    }
}


