package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous

public class AutonomousDrive extends LinearOpMode {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.dcMotor.get("ld");
        rightMotor = hardwareMap.dcMotor.get("rd");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            rightMotor.setPower(100);
            leftMotor.setPower(100);
            telemetry.addData("Status", "Forward");
            telemetry.update();
            sleep(2500);
            rightMotor.setPower(0);
            leftMotor.setPower(0);
            telemetry.addData("Status", "Stop");
            telemetry.update();
            sleep(100);
            rightMotor.setPower(-100);
            leftMotor.setPower(-100);
            telemetry.addData("Status", "back");
            telemetry.update();
            sleep(1200);
            rightMotor.setPower(-100);
            leftMotor.setPower(0);
            telemetry.addData("Status", "right");
            telemetry.update();
            sleep(1200);
            rightMotor.setPower(100);
            leftMotor.setPower(100);
            telemetry.addData("Status", "Forward");
            telemetry.update();
            sleep(6400);
            rightMotor.setPower(0);
            leftMotor.setPower(0);
            telemetry.addData("Status", "Stop");
            telemetry.update();
            break;

        }
    }
}
