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
    public DcMotor strafeMotor = null;  // motor controller 2
    public DcMotor strafe2Motor = null;


    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.dcMotor.get("rd");
        rightMotor = hardwareMap.dcMotor.get("ld");
        liftMotor = hardwareMap.dcMotor.get("ld2");
        strafeMotor = hardwareMap.dcMotor.get("sd");
        strafe2Motor = hardwareMap.dcMotor.get("sd2");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        strafe2Motor.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

          //down from latch
            liftMotor.setPower(-100);
            sleep(10000);
            liftMotor.setPower(0);

          //make space between latch and hook
            strafeMotor.setPower(50);
            strafe2Motor.setPower(50);
            sleep(20);
            strafeMotor.setPower(0);
            strafe2Motor.setPower(0);

          //drive out of the latch
            rightMotor.setPower(20);
            leftMotor.setPower(20);
            sleep(120);
            rightMotor.setPower(0);
            leftMotor.setPower(0);

          //strafe foward to begin sampling process
            strafeMotor.setPower(100);
            strafe2Motor.setPower(100);
            sleep(750);
            strafeMotor.setPower(0);
            strafe2Motor.setPower(0);

          //drive foward to sample far right block
            rightMotor.setPower(30);
            leftMotor.setPower(30);
            sleep(500);
            rightMotor.setPower(0);
            leftMotor.setPower(0);
          //drive backward to sample middle and the far left_trigger
            rightMotor.setPower(-10);
            leftMotor.setPower(-10);
            sleep(2000);
            rightMotor.setPower(0);
            leftMotor.setPower(0);


            break;

        }
    }
}
