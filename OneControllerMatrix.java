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

@TeleOp
public class OneControllerMatrix extends LinearOpMode {
    BNO055IMU imu;
    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;

    public DcMotor lift1Motor = null;
    public DcMotor latchMotor = null;
    public DcMotor latchMotor2 = null;

    public DcMotor spoolMotor = null;

    public Servo arm = null;
    public Servo lock = null;
    final double ARM_SPEED = 0.15;

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
        rrMotor = hardwareMap.dcMotor.get("rr");
        Orientation angles;
        latchMotor = hardwareMap.dcMotor.get("lm");
        latchMotor2 = hardwareMap.dcMotor.get("lm2");

        spoolMotor = hardwareMap.dcMotor.get("sp");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lrMotor.setDirection(DcMotor.Direction.REVERSE);

        lift1Motor = hardwareMap.dcMotor.get("l1");
        // lift2Motor = hardwareMap.dcMotor.get("l2");
        arm = hardwareMap.servo.get("s1");
        lock = hardwareMap.servo.get("s2");
         double x = 0 ;
         double y= 0 ;
        double theta = 0;
        boolean matrix = true;
        boolean normal = false;


        // lift2Motor.setDirection(DcMotor.Direction.REVERSE);
        imu.initialize(parameters);
                while(!imu.isGyroCalibrated()&&!isStopRequested()){

            sleep(50);
            idle();
        }
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {



            if(gamepad1.right_stick_button){
              matrix = true;
              normal = false;
            }

            if(gamepad1.left_stick_button){
              matrix = false;
              normal = true;
            }
            if (normal){
              telemetry.addData("Status","NormalDrive");
            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
                        telemetry.addLine((y)+"  Y");
            telemetry.addLine((x)+"  X");
            telemetry.update();
            }
            if (matrix){
              telemetry.addData("Status","AngleDrive");
              angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
              theta = angles.firstAngle;
              if (theta < 0){
                theta += 360;
              }
            double gy = gamepad1.left_stick_y;
            double gx = gamepad1.left_stick_x;
            x = ((gx*(Math.sin((theta+90)*Math.PI/180)))+((gy*(Math.cos((theta+90)*Math.PI/180)))));
            y = ((gx*(Math.cos((theta+90)*Math.PI/180)))-((gy*(Math.sin((theta+90)*Math.PI/180)))));
                          telemetry.addLine((y)+"  Y");
            telemetry.addLine((x)+"  X");
            telemetry.addLine(theta+"  angle from your POV");
            telemetry.update();
            }

            double rx = gamepad1.right_stick_x;




          if (gamepad1.dpad_right) {

                // Get the calibration data
                BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
                imu.initialize(parameters);
                // Save the calibration data to a file. You can choose whatever file
                // name you wish here, but you'll want to indicate the same file name
                // when you initialize the IMU in an opmode in which it is used. If you
                // have more than one IMU on your robot, you'll of course want to use
                // different configuration file names for each.
                String filename = "BNO055IMUCalibration.json";
                File file = AppUtil.getInstance().getSettingsFile(filename);
                ReadWriteFile.writeFile(file, calibrationData.serialize());
                //telemetry.log().add("saved to '%s'", filename);

                // Wait for the button to be released
                while (gamepad1.dpad_right) {
                    telemetry.update();
                    idle();
                }
            }

          if(gamepad1.y){
              arm.setPosition(0.15);
              }
          if(gamepad1.x){
              arm.setPosition(0.6);
              }
           if(gamepad1.dpad_up){
              latchMotor.setPower(-.5);
              latchMotor2.setPower(-.5);
              }
          if(gamepad1.dpad_down){
              latchMotor.setPower(.5);
              latchMotor2.setPower(.5);
              }
          if(gamepad1.dpad_left){
              latchMotor.setPower(0);
              latchMotor2.setPower(0);
              }
          if(gamepad1.a){
              lock.setPosition(-.5);
              }
          if(gamepad1.b){
              lock.setPosition(.6);
              }

          if(gamepad1.right_trigger > 0){
            lift1Motor.setPower(-gamepad1.right_trigger);
            // lift2Motor.setPower(-gamepad1.left_trigger);
          }else if(gamepad1.left_trigger > 0){
            lift1Motor.setPower(gamepad1.left_trigger);
            // lift2Motor.setPower(gamepad1.right_trigger) ;
          }else{
            lift1Motor.setPower(0);
            // lift2Motor.setPower(0);
          }
        if(gamepad1.right_bumper){
            spoolMotor.setPower(-1);
          }else if(gamepad1.left_bumper){
            spoolMotor.setPower(1);
          }else{
            spoolMotor.setPower(0);
            spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
          }





// begin of drive
            if (rx != 0 ){
              lfMotor.setPower(rx);
              rfMotor.setPower(-rx);
              lrMotor.setPower(rx);
              rrMotor.setPower(-rx);
            }else if (x >= 0 && y >= 0) {
              lfMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
              rrMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
            } else if (x <= 0 && y <= 0){
              lfMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
              rrMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
            } else if (x >= 0 && y <= 0){
              lfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)+360) - 315)/45));
              rrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)+360) - 315)/45));
            } else if (x <= 0 && y >= 0){
              lfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((315 - ((java.lang.Math.atan(y/x)*180/3.141592)+360))/45));
              rrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((315 - ((java.lang.Math.atan(y/x)*180/3.141592)+360))/45));
            }else{
              lfMotor.setPower(0);
              rfMotor.setPower(0);
              lrMotor.setPower(0);
              rrMotor.setPower(0);
            }


            if (rx !=0){
              lfMotor.setPower(rx);
              rfMotor.setPower(-rx);
              lrMotor.setPower(rx);
              rrMotor.setPower(-rx);
            }else if (x <= 0 && y >= 0) {
              rfMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
              lrMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
            } else if (x >= 0 && y <= 0){
              rfMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
              lrMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
            } else if (x >= 0 && y >= 0){
              rfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)) - 45)/45));
              lrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)) - 45)/45));
            } else if (x <= 0 && y <= 0){
              rfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((45 - ((java.lang.Math.atan(y/x)*180/3.141592)))/45));
              lrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((45 - ((java.lang.Math.atan(y/x)*180/3.141592)))/45));
            }else{
              lfMotor.setPower(0);
              rfMotor.setPower(0);
              lrMotor.setPower(0);
              rrMotor.setPower(0);
            }

            // if (gamepad1.right_trigger>0){
            //   armMotor.setPower(gamepad1.right_trigger);
            // }else if(gamepad1.left_trigger>0){
            //   armMotor.setPower(-gamepad1.left_trigger);
            // }else{
            //   armMotor.setPower(0);
            // }



        }
    }
}
