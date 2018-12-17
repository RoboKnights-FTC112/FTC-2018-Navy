package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous

public class AutonomousScan extends LinearOpMode {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public DcMotor liftMotor = null;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.dcMotor.get("rd");
        rightMotor = hardwareMap.dcMotor.get("ld");
        liftMotor = hardwareMap.dcMotor.get("ld2");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

          //down from latch
            telemetry.addData("Status", "Running");
            telemetry.update();
            liftMotor.setPower(-100);
            telemetry.addData("Status", "Down");
            telemetry.update();
            sleep(11000);

          //stop when gets to bottom
            liftMotor.setPower(0);
            telemetry.addData("Status", "Stop");
            telemetry.update();

          //go back after gets on the ground
            rightMotor.setPower(-100);
            leftMotor.setPower(-100);
            telemetry.addData("Status", "Back");
            telemetry.update();
            sleep(320);

          //turn left
            rightMotor.setPower(100);
            leftMotor.setPower(-100);
            telemetry.addData("Status", "Left");
            telemetry.update();
            sleep(250);

          //go foward
            rightMotor.setPower(100);
            leftMotor.setPower(100);
            telemetry.addData("Status", "Foward");
            telemetry.update();
            sleep(750);

          //turn right
            rightMotor.setPower(-100);
            leftMotor.setPower(100);
            telemetry.addData("Status", "Right");
            telemetry.update();
            sleep(750);

          //go forward
            rightMotor.setPower(100);
            leftMotor.setPower(100);
            telemetry.addData("Status", "Forward");
            telemetry.update();
            sleep(400);

          //go back
            rightMotor.setPower(-100);
            leftMotor.setPower(-100);
            telemetry.addData("Status", "Back");
            telemetry.update();
            sleep(3000)

          //stop
            rightMotor.setPower(0);
            leftMotor.setPower(0);
            telemetry.addData("Status","Stop")
            telemetry.update();

            break;

        }
    }
}
