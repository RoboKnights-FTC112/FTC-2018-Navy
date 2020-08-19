
package org.firstinspires.ftc.robotcontroller.external.samples;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


@Autonomous

public class Auto extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";



    private static final String VUFORIA_KEY =
            "AT8pGt3/////AAABmQ9LKBWthkikgQSErtn4C1GN+U/k35mErGuydnhrXtBLs2+wEnRYzMx2qJC0Q+4bHLUaWRZ18gRQcTZOoaKDYfG7yIcNfsexI4G5IdAgwfAZnSbrWco7IW2mdaHZrQ5mw/u0mh1RHbcPdK3JAheEknMP53n73JNNBFbEcB+IN2qPSI4AUrWqK3TuAl7XCnEBQrHKB7kU62rXWs+4r4/RcNB0g/yMZ3S5Yv7vfHYGMEA3/Wj+4PC/6v/pO9StgMjxKaVZMjTYiHvUN6yi6CgVfQlKlmkEMU0IR60PcUgA9hKq9CPXVNPN1tXCTGFGdd+WbhFEGdkbZxY3scU85G4kDQy2oNbFRaClpdHYINBOV1U1";


    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;

    BNO055IMU imu;

    public DcMotor lift1Motor = null;
    public DcMotor lift2Motor = null;

    public DcMotor spoolMotor = null;

    public DcMotor tapeMeasure = null;

    public Servo arm = null;

    public double skyPos = 0.0;


    static final double     COUNTS_PER_MOTOR_REV    = 7.0 ;//or 28
    static final double     DRIVE_GEAR_REDUCTION    = 5.2 ;
    static final double     WHEEL_DIAMETER_INCHES   = 3.937 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        lfMotor = hardwareMap.dcMotor.get("lf");
        rfMotor = hardwareMap.dcMotor.get("rf");
        lrMotor = hardwareMap.dcMotor.get("lr");
        rrMotor = hardwareMap.dcMotor.get("rr");
        tapeMeasure = hardwareMap.dcMotor.get("tm");
        Orientation angles;

        spoolMotor = hardwareMap.dcMotor.get("sp");

        rfMotor.setDirection(DcMotor.Direction.REVERSE);
        rrMotor.setDirection(DcMotor.Direction.REVERSE);

        rfMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rrMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lrMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lfMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift1Motor = hardwareMap.dcMotor.get("l1");
        lift2Motor = hardwareMap.dcMotor.get("l2");
        tapeMeasure = hardwareMap.dcMotor.get("tm");
        arm = hardwareMap.servo.get("s1");
        lift2Motor.setDirection(DcMotor.Direction.REVERSE);
        l1Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        l2Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu.initialize(parameters);
        while (!imu.isGyroCalibrated() && !isStopRequested()) {
            sleep(50);
            idle();
        }


        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }


        if (tfod != null) {
            tfod.activate();
        }


        if (!opModeIsActive()) {
            while (!opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      // step through the list of recognitions and display boundary info.
                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        if(recognition.getLabel().equals("Skystone")){
                            skyPos = recognition.getRight();
                        }
                      }
                      telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (skyPos > 100){
            telemetry.addData(">","right");
            telemetry.update();
        }
        // LF || RF || LR || RR

        encoderDrive(1.0,  10,  10, 10,  10);


    }





    public void encoderDrive(double speed, double lfInches, double rfInches, double lrInches, double rrInches) {
        int newLFTarget;
        int newRFTarget;
        int newLRTarget;
        int newRRTarget;

        if (opModeIsActive()) {
            newRRTarget = rrMotor.getCurrentPosition() + (int)(rrInches * COUNTS_PER_INCH);
            newRFTarget = rfMotor.getCurrentPosition() + (int)(rfInches * COUNTS_PER_INCH);
            newLFTarget = lfMotor.getCurrentPosition() + (int)(lfInches * COUNTS_PER_INCH);
            newLRTarget = lrMotor.getCurrentPosition() + (int)(lrInches * COUNTS_PER_INCH);

            rrMotor.setTargetPosition(newRRTarget);
            rfMotor.setTargetPosition(newRFTarget);
            lfMotor.setTargetPosition(newLFTarget);
            lrMotor.setTargetPosition(newLRTarget);

            lfMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rfMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lrMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rrMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            lfMotor.setPower(Math.abs(speed));
            rfMotor.setPower(Math.abs(speed));
            lrMotor.setPower(Math.abs(speed));
            rrMotor.setPower(Math.abs(speed));

            while (opModeIsActive() && (lfMotor.isBusy() && rfMotor.isBusy() && rrMotor.isBusy() && rfMotor.isBusy()) ) {

                  //   telemetry.addData(lfMotor.getCurrentPosition() + "LEFT FRONT to" + newLFTarget + rfMotor.getCurrentPosition() + "right FRONT to" + newRFTarget +  lrMotor.getCurrentPosition() + "LEFT rear to" + newLRTarget +   rrMotor.getCurrentPosition() + "right rear to" + newRRTarget);
                  //   telemetry.update();
                }

                lfMotor.setPower(-.01);
                rfMotor.setPower(-.01);
                lrMotor.setPower(-.01);
                rrMotor.setPower(-.01);

                rfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rrMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lrMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            }
          }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minimumConfidence = 0.8;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
