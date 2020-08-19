import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.File;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;
import com.qualcomm.robotcore.util.ReadWriteFile;


import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@TeleOp
public class OneControllerMatrixDrift extends LinearOpMode {
    BNO055IMU imu;
    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;

    public DcMotor lift1Motor = null;
    public DcMotor lift2Motor = null;

    public DcMotor spoolMotor = null;

    public Servo arm = null;
    // public Servo lock = null;


    @Override
    public void runOpMode() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        lfMotor = hardwareMap.dcMotor.get("lf");
        rfMotor = hardwareMap.dcMotor.get("rf");
        lrMotor = hardwareMap.dcMotor.get("lr");
        rrMotor = hardwareMap.dcMotor.get("rr");
        Orientation angles;

        spoolMotor = hardwareMap.dcMotor.get("sp");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lrMotor.setDirection(DcMotor.Direction.REVERSE);
        lift2Motor.setDirection(DcMotor.Direction.REVERSE);

        lift1Motor = hardwareMap.dcMotor.get("l1");
        lift2Motor = hardwareMap.dcMotor.get("l2");
        arm = hardwareMap.servo.get("s1");
        // lock = hardwareMap.servo.get("s2");
        double rootxy = 0.0
        double x = 0.0;
        double y = 0.0;
        double m = 0.0;
        double s = 0.0;
        double sm = 0.0;
        double tan = 0.0;
        double theta = 0.0;
        boolean matrix = true;
        boolean normal = false;



        imu.initialize(parameters);
        while (!imu.isGyroCalibrated() && !isStopRequested()) {
            sleep(50);
            idle();
        }
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {



            if (gamepad1.right_stick_button) {
                matrix = true;
                normal = false;
            }

            if (gamepad1.left_stick_button) {
                matrix = false;
                normal = true;
            }
            if (normal) {
                telemetry.addData("Status", "NormalDrive");
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                telemetry.addLine((y) + "  Y");
                telemetry.addLine((x) + "  X");
                telemetry.update();
            }
            if (matrix) {
                telemetry.addData("Status", "AngleDrive");
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                theta = angles.firstAngle;
                if (theta < 0) {
                    theta += 360;
                }
                double gy = gamepad1.left_stick_y;
                double gx = gamepad1.left_stick_x;
                x = ((gx * (Math.sin((theta + 90) * Math.PI / 180))) + ((gy * (Math.cos((theta + 90) * Math.PI / 180)))));
                y = ((gx * (Math.cos((theta + 90) * Math.PI / 180))) - ((gy * (Math.sin((theta + 90) * Math.PI / 180)))));
                telemetry.addLine((y) + "  Y");
                telemetry.addLine((x) + "  X");
                telemetry.addLine(theta + "  angle from your POV");
                telemetry.update();
            }

            double rx = gamepad1.right_stick_x;




            if (gamepad1.dpad_right) {

                BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
                imu.initialize(parameters);

                String filename = "BNO055IMUCalibration.json";
                File file = AppUtil.getInstance().getSettingsFile(filename);
                ReadWriteFile.writeFile(file, calibrationData.serialize());

                while (gamepad1.dpad_right) {
                    telemetry.update();
                    idle();
                }
            }

            if (gamepad1.y) {
                arm.setPosition(0.15);
            }
            if (gamepad1.x) {
                arm.setPosition(0.6);
            }

            // if (gamepad1.a) {
            //     lock.setPosition(-.5);
            // }
            // if (gamepad1.b) {
            //     lock.setPosition(.6);
            // }

            if (gamepad1.right_trigger > 0) {
                lift1Motor.setPower(-gamepad1.right_trigger);
                lift2Motor.setPower(-gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > 0) {
                lift1Motor.setPower(gamepad1.left_trigger);
                lift2Motor.setPower(gamepad1.left_trigger) ;
            } else {
                lift1Motor.setPower(0);
                lift2Motor.setPower(0);
                lift1Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                lift2Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            }

            if (gamepad1.right_bumper) {
                spoolMotor.setPower(-1);
            } else if (gamepad1.left_bumper) {
                spoolMotor.setPower(1);
            } else {
                spoolMotor.setPower(0);
                spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }





            // begin of drive
      double rootxy = java.lang.Math.sqrt((y * y) + (x * x));

      if (x != 0) {
          tan = java.lang.Math.atan(y / x) * 180 / 3.141592;
      }


      if (x >= 0 && y >= 0) {
          m = rootxy;

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          lfMotor.setPower(s);
          rrMotor.setPower(sm);
      } else if (x <= 0 && y <= 0) {
          m = (rootxy);

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          lfMotor.setPower(-s);
          rrMotor.setPower(-sm);
      } else if (x >= 0 && y <= 0) {
          m = (rootxy) * ((tan + 45) / 45);

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          lfMotor.setPower(s);
          rrMotor.setPower(sm);
      }else if (x <= 0 && y >= 0) {
          m = (rootxy) * ((-tan - 45) / 45);

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          lfMotor.setPower(-s);
          rrMotor.setPower(-sm);
      } else if (x == 0 && y==0 && rx != 0){
        lfMotor.setPower(rx);
        rfMotor.setPower(-rx);
        lrMotor.setPower(rx);
        rrMotor.setPower(-rx);
      }else {
          lfMotor.setPower(0);
          rfMotor.setPower(0);
          lrMotor.setPower(0);
          rrMotor.setPower(0);
      }


      if (x <= 0 && y >= 0) {
          m = rootxy;

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          rfMotor.setPower(sm);
          lrMotor.setPower(s);
      } else if (x >= 0 && y <= 0) {
          m = rootxy;

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          rfMotor.setPower(-sm);
          lrMotor.setPower(-s);

      } else if (x >= 0 && y >= 0) {
          m = (rootxy) * ((tan - 45) / 45);

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          rfMotor.setPower(-sm);
          lrMotor.setPower(-s);

      } else if (x <= 0 && y <= 0) {
          m = (rootxy) * ((45 - tan) / 45);

          if (rx > 0 && rx < m) {
              sm = (m * m) / ((m * m) + (rx * rx));
              s = m;
          }else if (rx == 0){
            s = m;
            sm = m;
          }else if (rx < 0 && Math.abs(rx) < m){
              s = (m * m) / ((m * m) + (rx * rx));
              sm = m;
          }else if (Math.abs(rx) >0 && m == 0){
            sm = -rx;
            s = rx;
          }else if (rx < 0 && rx > m){
            s = (rx * rx) / ((rx * rx) + (m * m));
            sm = -rx;
          }else if (rx > 0 && rx > m){
            s = rx;
            sm = -(rx * rx) / ((rx * rx) + (m * m));
          }


          rfMotor.setPower(sm);
          lrMotor.setPower(s);

      } else {
          lfMotor.setPower(0);
          rfMotor.setPower(0);
          lrMotor.setPower(0);
          rrMotor.setPower(0);
      }





        }
    }
}
