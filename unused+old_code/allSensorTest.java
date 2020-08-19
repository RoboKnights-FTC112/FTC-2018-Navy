
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp

public class allSensorTest extends LinearOpMode {
    ColorSensor color_sensor;
    private DistanceSensor sensorRange;
    //private DistanceSensor sensorRange2;
    ColorSensor color_sensor2;

    @Override
    public void runOpMode() {
        //color_sensor = hardwareMap.colorSensor.get("sensor_color_distance");
        //color_sensor2 = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        color_sensor2 = hardwareMap.get(ColorSensor.class, "colorf");
        color_sensor = hardwareMap.get(ColorSensor.class, "colorr");
        //sensorRange2 = hardwareMap.get(DistanceSensor.class, "sensor_range2");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("Alpha", color_sensor2.alpha());
            telemetry.addData("Red  ", color_sensor2.red());
            telemetry.addData("Green", color_sensor2.green());
            telemetry.addData("Blue ", color_sensor2.blue());
            //telemetry.addData("range 1", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));
            //telemetry.addData("range2", String.format("%.01f in", sensorRange2.getDistance(DistanceUnit.INCH)));
            telemetry.update();
            // color_sensor.red();   // Red channel value
            // color_sensor.green(); // Green channel value
            // color_sensor.blue();  // Blue channel value
 
            // color_sensor.alpha(); // Total luminosity
            // color_sensor.argb();  // Combined color value
            
            
            // if(sensorRange.getDistance(DistanceUnit.INCH)>/*certian distance*/){
                
            // }
        }
    }
}
