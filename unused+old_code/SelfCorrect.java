package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.File;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;
@Autonomous
public class SelfCorrect extends LinearOpMode {
      BNO055IMU imu;
    ColorSensor color_sensor;

    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;
    public DcMotor rlatch = null;
    public DcMotor llatch = null;
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 4.0 ;
    static final double     DRIVE_GEAR_REDUCTION    = 72.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 3.14 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {

            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
       parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
       parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
       parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
       parameters.loggingEnabled      = true;
       parameters.loggingTag          = "IMU";
       parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

       // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
       // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
       // and named "imu".
       imu = hardwareMap.get(BNO055IMU.class, "imu");

      lfMotor = hardwareMap.dcMotor.get("lf");
      rfMotor = hardwareMap.dcMotor.get("rf");
      lrMotor = hardwareMap.dcMotor.get("lr");
      Orientation angles;
      rrMotor = hardwareMap.dcMotor.get("rr");
      rlatch = hardwareMap.dcMotor.get("lm");
      llatch = hardwareMap.dcMotor.get("lm2");
      color_sensor = hardwareMap.get(ColorSensor.class, "colorr");


      rfMotor.setDirection(DcMotor.Direction.REVERSE);
      rrMotor.setDirection(DcMotor.Direction.REVERSE);

      telemetry.addData("Status", "Resetting Encoders");
      telemetry.update();

      lfMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rfMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      lrMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rrMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      lfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      rfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      lrMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      rrMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

      rfMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      lfMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      rrMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      lrMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

      imu.initialize(parameters);
              while(!imu.isGyroCalibrated()&&!isStopRequested()){

          sleep(50);
          idle();
      }

    //   telemetry.addData(lfMotor.getCurrentPosition() + "LEFT FRONT" +
    //                     rfMotor.getCurrentPosition() + "right FRONT" +
    //                     lrMotor.getCurrentPosition() + "LEFT rear" +
    //                     rrMotor.getCurrentPosition() + "right rear");

      telemetry.update();

      waitForStart();


      // LF || RF || LR || RR

      encoderDrive(1.0,  50,  -50, -50,  50);


      telemetry.addData("Path", "Complete");
      telemetry.update();
    }
      public void encoderDrive(double speed, double lfInches, double rfInches, double lrInches, double rrInches) {
          int newLFTarget;
          int newRFTarget;
          int newLRTarget;
          int newRRTarget;

          int theta = 0;

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



              while (opModeIsActive() && (lfMotor.isBusy()|| rrMotor.isBusy()) {
                  angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                  theta = angles.firstAngle;

                    if(lfInches > 0 && theta < 0){
                      lfMotor.setPower(Math.abs(speed));
                      rrMotor.setPower(Math.abs(speed)*.75);
                      lrMotor.setPower(Math.abs(speed));
                      rfMotor.setPower(Math.abs(speed)*.75);
                    }else if(lfInches < 0 && theta > 0){
                      lfMotor.setPower(Math.abs(speed));
                      rrMotor.setPower(Math.abs(speed)*.75);
                      lfMotor.setPower(Math.abs(speed));
                      rlrMotor.setPower(Math.abs(speed)*.75);
                    }else if(lfInches > 0 && theta > 0){
                      lrMotor.setPower(Math.abs(speed));
                      rfMotor.setPower(Math.abs(speed)*.88);
                      rrMotor.setPower(Math.abs(speed));
                      lfMotor.setPower(Math.abs(speed)*.75);
                    }else if(lfInches < 0 && theta < 0){
                      lrMotor.setPower(Math.abs(speed));
                      rfMotor.setPower(Math.abs(speed)*.75);
                      rrMotor.setPower(Math.abs(speed));
                      lfMotor.setPower(Math.abs(speed)*.75);
                    }else{
                      lfMotor.setPower(Math.abs(speed));
                      rfMotor.setPower(Math.abs(speed));
                      lrMotor.setPower(Math.abs(speed));
                      rrMotor.setPower(Math.abs(speed));
                    }


                  }

                  lfMotor.setPower(0);
                  rfMotor.setPower(0);
                  lrMotor.setPower(0);
                  rrMotor.setPower(0);

                  rfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                  lfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                  rrMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                  lrMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);






              }
            }
          }
