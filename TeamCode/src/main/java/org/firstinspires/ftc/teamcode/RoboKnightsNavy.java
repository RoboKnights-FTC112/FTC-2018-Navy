package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


 boolean success = false;
 try {
     runOpMode();
     success = true;
 } catch (runOpMode) {
     success = false;
 }
 if (success) {
     runOpMode();
 }

 try{
     runOpMode();
     success = true;
 }catch () {
     success = false;
 }
 if(success) {
     runOpMode();
 }

 try {
   runOpMode();
   success = true;
 }catch (Exception runOpMode) {
   success = false;
 }
 if (sucess) {
   runOpMode();
 }


@Autonomous(name = "VuforiaRelic", group = "Vuforia")
public class RoboKnightsNavy extends LinearOpMode{
    OpenGLMatrix lastLocation = null; // wARNING VERY INACCURATE, USE ONLY TO ADJUST TO FIND IMAGE AGAIN! DO NOT BASE MAJOR MOVEMENTS OFF OF THIS!!
    double tX; // X value extracted from our the offset of the traget relative to the robot.
    double tZ; // Same as above but for Z
    double tY; // Same as above but for Y
    // -----------------------------------
    double rX; // X value extracted from the rotational components of the tartget relitive to the robot
    double rY; // Same as above but for Y
    double rZ; // Same as above but for Z

    DcMotor right; // Random Motor
    DcMotor left; // Random Motor

    VuforiaLocalizer vuforia;

     public void runOpMode()
     {
         right = hardwareMap.dcMotor.get("rd"); // Random Motor
         left = hardwareMap.dcMotor.get("ld"); // Random Motor
         int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
         VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
         parameters.vuforiaLicenseKey = "AfNu6or/////AAABmbz3RBzAq0E9lLg95aebMBM2Er4MR6IvcC26uRVkA8M7SqU+48qaf2KciR2LRYOkQv1L1KWo3EWY+ALYOBdHyP5oro61RsCW24FOXYhooU2QdYVk28MQfU8dUvG1NDMJ8zN/5RnM3fMPfMqEtZ+4hluqGNCjTdwzrecN9ZpAtDpTpIQ4V5eN9M1YYyNfT9QJRswL7PdyZ4jDC70w+KNtx5U6iRJNKLira1dzBvoGZjGDBRoU9w+m1RVFzgUHVBDGQ85BQKlXNUDUcJKW1e9/2B6YkAYr38kSTQw1FK3ywpJ9FwtKOUw8ByBf96n7ScWnC72l2keQQ6tsh6xsZioxaXO8WOBO4ukoFWuevJOzFLn+";
         parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT; // Use FRONT Camera (Change to BACK if you want to use that one)
         parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; // Display Axes

         this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
         VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("sphere");
         VuforiaTrackable relicTemplate = relicTrackables.get(0);
         waitForStart();

         relicTrackables.activate(); // Activate Vuforia

         while (opModeIsActive())

             RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
             if (vuMark != RelicRecoveryVuMark.UNKNOWN) { // Test to see if image is visable
                 OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose(); // Get Positional value to use later
                 telemetry.addData("Pose", format(pose));
                 if (pose != null)
                 {
                     VectorF trans = pose.getTranslation();
                     Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                     // Extract the X, Y, and Z components of the offset of the target relative to the robot
                     tX = trans.get(0);
                     tY = trans.get(1);
                     tZ = trans.get(2);

                     // Extract the rotational components of the target relative to the robot. NOT: VERY IMPORTANT IF BASING MOVEMENT OFF OF THE IMAGE!!!!
                     rX = rot.firstAngle;
                     rY = rot.secondAngle;
                     rZ = rot.thirdAngle;
                 }
                 if (vuMark == RelicRecoveryVuMark.LEFT)
                 { // Test to see if Image is the "LEFT" image and display value.
                     telemetry.addData("VuMark is", "Left");
                     telemetry.addData("X =", tX);
                     telemetry.addData("Y =", tY);
                     telemetry.addData("Z =", tZ);
                 } else if (vuMark == RelicRecoveryVuMark.RIGHT)
                 { // Test to see if Image is the "RIGHT" image and display values.
                     telemetry.addData("VuMark is", "Right");
                     telemetry.addData("X =", tX);
                     telemetry.addData("Y =", tY);
                     telemetry.addData("Z =", tZ);
                 } else if (vuMark == RelicRecoveryVuMark.CENTER)
                 { // Test to see if Image is the "CENTER" image and display values.
                     telemetry.addData("VuMark is", "Center");
                     telemetry.addData("X =", tX);
                     telemetry.addData("Y =", tY);
                     telemetry.addData("Z =", tZ);

                 }
             } else
                 {
                 telemetry.addData("VuMark", "not visible");
             }
             telemetry.update();
         }


     }
    String format(OpenGLMatrix transformationMatrix)
    {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}
