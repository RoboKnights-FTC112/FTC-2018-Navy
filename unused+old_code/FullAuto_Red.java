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

public class FullAuto_Red extends LinearOpMode {
    //sensors
    ColorSensor color_sensor;
    private DistanceSensor sensorRange;
    private DistanceSensor sensorRange2;
    //motors
    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;

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
        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lrMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
          //  /*
            while(sensorRange.getDistance(DistanceUnit.INCH) > 1){
              //goforward
              lfMotor.setPower(-1);
              rfMotor.setPower(-1);
              lrMotor.setPower(-1);
              rrMotor.setPower(-1);

            }

            //latchdown

            while(sensorRange2.getDistance(DistanceUnit.INCH) > 1){
              //goback
              lfMotor.setPower(1);
              rfMotor.setPower(1);
              lrMotor.setPower(1);
              rrMotor.setPower(1);
            }

            //latchup

            // while(!(trackable.getName().equals("Stone Target"))){
            //   //goleft
            //   lfMotor.setPower(1);
            //   rfMotor.setPower(-1);
            //   lrMotor.setPower(-1);
            //   rrMotor.setPower(1);
            // }

            lfMotor.setPower(1);
              rfMotor.setPower(-1);
              lrMotor.setPower(-1);
              rrMotor.setPower(1);

              sleep(3000);

            while(sensorRange.getDistance(DistanceUnit.INCH) > 1){
              //goforward
              lfMotor.setPower(-1);
              rfMotor.setPower(-1);
              lrMotor.setPower(-1);
              rrMotor.setPower(-1);
            }

            //latchdown

            while(sensorRange2.getDistance(DistanceUnit.INCH) > 1){
              //goback
              lfMotor.setPower(1);
              rfMotor.setPower(1);
              lrMotor.setPower(1);
              rrMotor.setPower(1);
            }

            while(color_sensor.red()< 2000){
              //goright
              lfMotor.setPower(-1);
              rfMotor.setPower(1);
              lrMotor.setPower(1);
              rrMotor.setPower(-1);
            }

            lfMotor.setPower(-1);//goright wait time (ms)
            rfMotor.setPower(1);
            lrMotor.setPower(1);
            rrMotor.setPower(-1);

            sleep(750);

            //latch up

            lfMotor.setPower(1);//goleft wait time (ms)
            rfMotor.setPower(-1);
            lrMotor.setPower(-1);
            rrMotor.setPower(1);

            sleep(750);

            lfMotor.setPower(0);
            rfMotor.setPower(0);
            lrMotor.setPower(0);
            rrMotor.setPower(0);



            // telemetry.addData("Status", "Running");
            // telemetry.addData("Red  ", color_sensor.red());
            // telemetry.addData("range 1", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));
            // telemetry.addData("range2", String.format("%.01f in", sensorRange2.getDistance(DistanceUnit.INCH)));
            // telemetry.update();

            break;
        }
    }
}
