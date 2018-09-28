 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class FactorProgramV1 extends LinearOpMode {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;


    @Override
    public void runOpMode() {

        leftMotor = hardwareMap.dcMotor.get("ld");
        rightMotor = hardwareMap.dcMotor.get("rd");
        armMotor = hardwareMap.dcMotor.get("arm");
        sweeperMotor = hardwareMap.dcMotor.get("sweep");



        leftMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "FactorProgram");
            telemetry.update();

            double factor = null;
            double factor2 = null;

            rightMotor.setPower(-gamepad1.right_stick_y*factor);
            leftMotor.setPower(-gamepad1.left_stick_y*factor);

            sweeperMotor.setPower(-gamepad2.right_stick_y);
            armMotor.setPower(-gamepad2.left_stick_y);

            if (gamepad1.right_trigger !=0) { //makes the robot drive at speed 1/4 if the right trigger is pressed on gamepad1
              telemetry.addData("Status", "QuarterSpeed");
              factor = .25;
            }else if (gamepad1.left_bumper) { // maks the robot controlled by only the right stick when the left bumper is pressed on gamepad1
              telemetry.addData("Status", "StraightDrive");
              rightMotor.setPower(-gamepad1.right_stick_y);
              leftMotor.setPower(-gamepad1.right_stick_y);
            }else if (gamepad1.right_bumper) { //makes the robot drive at speed 1/2 if the right bumper is pressed on gamepad1
              telemetry.addData("Status", "HalfSpeedDrive");
              factor = .5;
            }else if (gamepad2.right_trigger !=0) { // makes the sweeper speed 1/4 when the right trigger is pressed on gamepad2
              telemetry.addData("Status", "QuarterSpeedSwepper");
              factor2 = .25;
              //sweeperMotor.setPower(-gamepad2.right_stick_y/4);
            }else if (gamepad2.right_bumper) { // makes the sweeper speed 1/2 when the right bumper is pressed on gamepad2
              telemetry.addData("Status", "HalfSpeedSweeper");
              factor2 = .25;
              //sweeperMotor.setPower(-gamepad2.right_stick_y/2);
            }else if (gamepad2.left_trigger != 0) {  // makes the arm speed 1/4 when the left trigger is pressed on gamepad2
              telemetry.addData("Status", "QuarterSpeedArm");
              factor2 = .5;
              //armMotor.setPower(-gamepad2.left_stick_y/4);
            }else if (gamepad2.left_bumper) {  // makes the arm speed 1/2 when the left stick is pressed on gamepad2
              telemetry.addData("Status", "HalfSpeedArm");
              factor2 = .5;
              //armMotor.setPower(-gamepad2.left_stick_y/2);
            }else{ // makes everything full power
              telemetry.addData("Status", "AllFullPower");
              sweeperMotor.setPower(-gamepad2.right_stick_y);
              armMotor.setPower(-gamepad2.left_stick_y);
              rightMotor.setPower(-gamepad1.right_stick_y);
              leftMotor.setPower(-gamepad1.left_stick_y);
            }
        }
    }
}
