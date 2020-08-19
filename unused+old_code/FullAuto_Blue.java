package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.ColorSensor;


@Autonomous

public class FullAuto_Blue extends LinearOpMode {
    //sensors
    ColorSensor color_sensor;
    private DistanceSensor sensorRange;
    private DistanceSensor sensorRange2;
    //motors
    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;
        public DcMotor latchMotor = null;

    @Override
    public void runOpMode() {
        //sensors
        color_sensor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        sensorRange = hardwareMap.get(DistanceSensor.class, "sensor_range");
        sensorRange2 = hardwareMap.get(DistanceSensor.class, "sensor_range2");
        //motors
        lfMotor = hardwareMap.dcMotor.get("lf");
        rfMotor = hardwareMap.dcMotor.get("rf");
        lrMotor = hardwareMap.dcMotor.get("lr");
        rrMotor = hardwareMap.dcMotor.get("rr");
        latchMotor = hardwareMap.dcMotor.get("lm");
        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lrMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
          //start of auto go forward until the block is in range
            while(opModeIsActive() && sensorRange.getDistance(DistanceUnit.INCH) > 2.5){
              telemetry.addData("range 1", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));
              if(sensorRange.getDistance(DistanceUnit.INCH) > 10){
                lfMotor.setPower(-1);
                rfMotor.setPower(-1);
                lrMotor.setPower(-1);
                rrMotor.setPower(-1);
              }else if(sensorRange.getDistance(DistanceUnit.INCH) <= 10){
                lfMotor.setPower(-.35);
                rfMotor.setPower(-.35);
                lrMotor.setPower(-.35);
                rrMotor.setPower(-.35);
              }
            }

            //stop the robot and latch down on the block
            lfMotor.setPower(0);
            rfMotor.setPower(0);
            lrMotor.setPower(0);
            rrMotor.setPower(0);
            latchMotor.setPower(.5);

            sleep(1000);

            //after latching down go back to avoid hitting the other blocks
            latchMotor.setPower(0);
            lfMotor.setPower(1);
            rfMotor.setPower(1);
            lrMotor.setPower(1);
            rrMotor.setPower(1);

            sleep(1000);

            //go left fast
            lfMotor.setPower(1);
            rfMotor.setPower(-1);
            lrMotor.setPower(-1);
            rrMotor.setPower(1);

            sleep(2500);

            //slow down once close to the line
            while(color_sensor.red()< 1500&&opModeIsActive()){
              lfMotor.setPower(.35);
              rfMotor.setPower(-.35);
              lrMotor.setPower(-.35);
              rrMotor.setPower(.35);
            }

            //once at the line go fast left again
            lfMotor.setPower(1);
            rfMotor.setPower(-1);
            lrMotor.setPower(-1);
            rrMotor.setPower(1);

            sleep(800);

            //latch up to release the block and stop the robot
            latchMotor.setPower(-.5);
            lfMotor.setPower(.15);
            rfMotor.setPower(.15);
            lrMotor.setPower(.15);
            rrMotor.setPower(.15);

            sleep(500);

            latchMotor.setPower(0);

            //go fast left
            lfMotor.setPower(-1);
            rfMotor.setPower(1);
            lrMotor.setPower(1);
            rrMotor.setPower(-1);

            sleep(300);
            //slow once close to line
            while(color_sensor.red()< 1500&&opModeIsActive()){
              lfMotor.setPower(-.35);
              rfMotor.setPower(.35);
              lrMotor.setPower(.35);
              rrMotor.setPower(-.35);
            }

            //go fast left to get the second block
            lfMotor.setPower(-1);
            rfMotor.setPower(1);
            lrMotor.setPower(1);
            rrMotor.setPower(-1);

            sleep(1650);

            while(opModeIsActive() && sensorRange.getDistance(DistanceUnit.INCH) > 2.5){
            //go fast forward if far from block then slow if close
              if(sensorRange.getDistance(DistanceUnit.INCH) > 10){
                lfMotor.setPower(-1);
                rfMotor.setPower(-1);
                lrMotor.setPower(-1);
                rrMotor.setPower(-1);
              }else if(sensorRange.getDistance(DistanceUnit.INCH) <= 10){
                lfMotor.setPower(-.35);
                rfMotor.setPower(-.35);
                lrMotor.setPower(-.35);
                rrMotor.setPower(-.35);
              }
            }
            //latch down on block
            lfMotor.setPower(0);
            rfMotor.setPower(0);
            lrMotor.setPower(0);
            rrMotor.setPower(0);
            latchMotor.setPower(.5);

            sleep(1000);

            //go backwards to avoid the other blocks
            latchMotor.setPower(0);
            lfMotor.setPower(1);
            rfMotor.setPower(1);
            lrMotor.setPower(1);
            rrMotor.setPower(1);

            sleep(650);

            //go fast until close to line
            lfMotor.setPower(1);
            rfMotor.setPower(-1);
            lrMotor.setPower(-1);
            rrMotor.setPower(1);
            sleep(1250);

            //go left slow until line
            while(color_sensor.red()< 1500&&opModeIsActive()){
              lfMotor.setPower(.35);
              rfMotor.setPower(-.35);
              lrMotor.setPower(-.35);
              rrMotor.setPower(.35);
            }

            //go fast left once over line
            lfMotor.setPower(1);
            rfMotor.setPower(-1);
            lrMotor.setPower(-1);
            rrMotor.setPower(1);

            sleep(700);
            //latch up to release block
            latchMotor.setPower(-.5);
            lfMotor.setPower(.15);
            rfMotor.setPower(.15);
            lrMotor.setPower(.15);
            rrMotor.setPower(.15);

            sleep(1000);
            //latch motor off
            latchMotor.setPower(0);

            //go slow right until line then stop
            while(color_sensor.red()< 1500&&opModeIsActive()){
              lfMotor.setPower(-.35);
              rfMotor.setPower(.35);
              lrMotor.setPower(.35);
              rrMotor.setPower(-.35);
            }

            lfMotor.setPower(0);
            rfMotor.setPower(0);
            lrMotor.setPower(0);
            rrMotor.setPower(0);

            sleep(20000);

        }
          lfMotor.setPower(0);
          rfMotor.setPower(0);
          lrMotor.setPower(0);
          rrMotor.setPower(0);
    }
}
