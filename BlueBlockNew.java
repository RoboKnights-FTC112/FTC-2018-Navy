package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.hardware.bosch.BNO055IMU;
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
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Locale;

@Autonomous
public class BlueBlockNew extends LinearOpMode {
    ColorSensor color_sensor;
    ColorSensor color_sensor2;
    public Servo lock = null;
    final double ARM_SPEED = 0.15;
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

    BNO055IMU imu;

    Orientation angles;
    @Override
    public void runOpMode() {

      lfMotor = hardwareMap.dcMotor.get("lf");
      rfMotor = hardwareMap.dcMotor.get("rf");
      lrMotor = hardwareMap.dcMotor.get("lr");
      rrMotor = hardwareMap.dcMotor.get("rr");
      rlatch = hardwareMap.dcMotor.get("lm");
      llatch = hardwareMap.dcMotor.get("lm2");
      color_sensor = hardwareMap.get(ColorSensor.class, "colorr");
      color_sensor2 = hardwareMap.get(ColorSensor.class, "colorf");
        lock = hardwareMap.servo.get("s2");
        lock.setPosition(-1);
      double dist = 0.0;
      double back = 11.0;
      double bridge = 43.0;

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
       BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        double theta = 0.0;

//      Set up our telemetry dashboard

//       telemetry.addData(lfMotor.getCurrentPosition() + "LEFT FRONT" +
//                     rfMotor.getCurrentPosition() + "right FRONT" +
//                     lrMotor.getCurrentPosition() + "LEFT rear" +
//                     rrMotor.getCurrentPosition() + "right rear");
    while(!imu.isGyroCalibrated()&&!isStopRequested()){

    sleep(50);
    idle();
 }

      telemetry.update();

      waitForStart();

      // angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

      // while(angles.firstAngle > -165 && isStopRequested()){
      //   lfMotor.setPower(-1);
      //   rfMotor.setPower(1);
      //   lrMotor.setPower(-1);
      //   rrMotor.setPower(1);
      //   angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
      // }
      // while(angles.firstAngle < -175 && isStopRequested()){
      //   lfMotor.setPower(1);
      //   rfMotor.setPower(-1);
      //   lrMotor.setPower(1);
      //   rrMotor.setPower(-1);
      //   angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
      // }

      // LF || RF || LR || RR
      //       angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

      // theta = angles.firstAngle;
      //             telemetry.addLine(theta+"  angle from your POV");
      //       telemetry.update();

      encoderDrive(1.0,  29,  29, 29,  29);//move close to blocks

      lfMotor.setPower(-.01);
      rfMotor.setPower(-.01);
      lrMotor.setPower(-.01);
      rrMotor.setPower(-.01);

      sleep(600);

      //go set distance to first block

      encoderDrive(1.0,  7,  -7, -7,  7);
      //correct();
        encoderDrive(1.0,  2,  2, 2,  2);
        sleep(200);
      if(color_sensor2.red() - color_sensor2.blue()<400){

        dist = 0.0;

        // telemetry.addData(color_sensor2.red()+"");
        // telemetry.addData(color_sensor2.blue()+"");
        //   telemetry.update();
      }else if(color_sensor.red() - color_sensor.blue()<400){
            encoderDrive(1.0,  -2,  -2, -2,  -2);
            dist += 17.0;
            encoderDrive(1.0,  17.0,  -17.0, -17.0,  17.0);
            encoderDrive(1.0,  2,  2, 2,  2);
        }else{
        encoderDrive(1.0,  -2,  -2, -2,  -2);
        //go set distance to second block
        dist = 8.5;
        encoderDrive(1.0,  8.5,  -8.5, -8.5,  8.5);
        encoderDrive(1.0,  2,  2, 2,  2);
        // if(color_sensor.red() - color_sensor.blue()<400){

        //   dist = 8.5;

        // }else{
        //   //go set distance to thrid block
        //   encoderDrive(1.0,  -2,  -2, -2,  -2);
        //   encoderDrive(1.0,  -8.5,  8.5, 8.5,  -8.5);
        //   encoderDrive(1.0,  2,  2, 2,  2);
        //   //correct();
        //   dist += 17.0;
        // }
      }

      //drive dist

      lfMotor.setPower(-.01);
      rfMotor.setPower(-.01);
      lrMotor.setPower(-.01);
      rrMotor.setPower(-.01);

      //encoderDrive(1.0,  2.0,  2.0, 2.0,  2.0);

      rlatch.setPower(.5);
            lock.setPosition(.6);//open latch
            sleep(600);
      rlatch.setPower(0);

      encoderDrive(1.0, -back-2, -back-2, -back-2, -back-2);

      //go dist + dist to bridge
      //correct();

      encoderDrive(1.0, -71.0-dist,  71.0+dist, 71.0+dist,-71.0-dist);
      //correct();
      rlatch.setPower(-.5);
      sleep(600);
      rlatch.setPower(0);

      encoderDrive(1.0,  bridge+dist,  -bridge-dist, -bridge-dist, bridge+dist);

      encoderDrive(1.0, back+3.5 , back+3.5 , back+3.5 , back+3.5 );

      rlatch.setPower(.5);
      sleep(600);
      rlatch.setPower(0);

      encoderDrive(1.0, -back-5.2, -back-5.2, -back-5.2, -back-5.2);

      encoderDrive(1.0,  -bridge-dist-2, +bridge+dist+2,+bridge+dist+2, -bridge-dist-2);

      rlatch.setPower(-.5);
      sleep(600);
      rlatch.setPower(0);

      encoderDrive(1.0,  16.0,  -16.0, -16.0, 16.0);

      encoderDrive(1.0,  5.0, 5.0,5.0, 5.0);

      sleep(1000);

      telemetry.addData("Path", "Complete");
      telemetry.update();
    }

    public void correct(){
                        lfMotor.setPower(0);
                rfMotor.setPower(0);
                lrMotor.setPower(0);
                rrMotor.setPower(0);
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        while(angles.firstAngle > 2.2 && !isStopRequested()){
            if(angles.firstAngle > 10){
                lfMotor.setPower(.35);
                rfMotor.setPower(-.35);
                lrMotor.setPower(.35);
                rrMotor.setPower(-.35);
                angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            }else if(angles.firstAngle < 0){
                lfMotor.setPower(0);
                rfMotor.setPower(0);
                lrMotor.setPower(0);
                rrMotor.setPower(0);
                                angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            }else{
                lfMotor.setPower(.15);
                rfMotor.setPower(-.15);
                lrMotor.setPower(.15);
                rrMotor.setPower(-.15);
                angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            }
                            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        }

        //angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
       while(angles.firstAngle < -2.2 && !isStopRequested()){
        if(angles.firstAngle < -10){
        lfMotor.setPower(-.35);
        rfMotor.setPower(.35);
        lrMotor.setPower(-.35);
        rrMotor.setPower(.35);
                        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        }else if(angles.firstAngle > 0){
                          lfMotor.setPower(0);
        rfMotor.setPower(0);
        lrMotor.setPower(0);
        rrMotor.setPower(0);
                        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        }else{
                 lfMotor.setPower(-.15);
        rfMotor.setPower(.15);
        lrMotor.setPower(-.15);
        rrMotor.setPower(.15);
                        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        }
                        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

       }
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
          }
