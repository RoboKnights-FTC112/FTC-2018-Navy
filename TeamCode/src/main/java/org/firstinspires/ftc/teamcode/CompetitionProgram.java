import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class CompetitionProgram extends LinearOpMode {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public DcMotor armMotor = null;
    public DcMotor sweeperMotor = null;
    @Override
    public void runOpMode() {

        leftMotor = hardwareMap.dcMotor.get("ld");
        rightMotor = hardwareMap.dcMotor.get("rd");
        armMotor = hardwareMap.dcMotor.get("arm");
        sweeperMotor = hardwareMap.dcMotor.get("sweep");
        //sweeperMotor.setPower(-gamepad2.right_stick_y);
        //armMotor.setPower(-gamepad2.left_stick_y);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "CompetitionProgram");
            telemetry.update();
            if (gamepad1.left_trigger !=0) { //makes the robot drive at speed 1/4 if the left trigger is pressed on gamepad1
              telemetry.addData("Status", "QuarterSpeed");
              rightMotor.setPower(-gamepad1.right_stick_y/4);
              leftMotor.setPower(-gamepad1.left_stick_y/4);
            }else if (gamepad1.left_bumper) { // maks the robot controlled by only the right stick when the left bumper is pressed on gamepad1
              telemetry.addData("Status", "StraightDrive");
              rightMotor.setPower(-gamepad1.right_stick_y);
              leftMotor.setPower(-gamepad1.right_stick_y);
            }else if (gamepad1.right_bumper) { //makes the robot drive at speed 1/2 if the right bumper is pressed on gamepad1
              telemetry.addData("Status", "HalfSpeedDrive");
              rightMotor.setPower(-gamepad1.right_stick_y/2);
              leftMotor.setPower(-gamepad1.left_stick_y/2);
            }else if (gamepad2.right_trigger !=0) { // makes the sweeper speed 1/4 when the right trigger is pressed on gamepad2
              telemetry.addData("Status", "QuarterSpeedSwepper");
              sweeperMotor.setPower(-gamepad2.right_stick_y/4);
            }else if (gamepad2.right_bumper) { // makes the sweeper speed 1/2 when the right bumper is pressed on gamepad2
              telemetry.addData("Status", "HalfSpeedSweeper");
              sweeperMotor.setPower(-gamepad2.right_stick_y/2);
            }else if (gamepad2.left_trigger != 0) {  // makes the arm speed 1/4 when the left trigger is pressed on gamepad2
              telemetry.addData("Status", "QuarterSpeedArm");
              armMotor.setPower(-gamepad2.left_stick_y/4);
            }else if (gamepad2.left_bumper) {  // makes the arm speed 1/2 when the left stick is pressed on gamepad2
              telemetry.addData("Status", "HalfSpeedArm");
              armMotor.setPower(-gamepad2.left_stick_y/2);
            }else{
              telemetry.addData("Status", "AllFullPower");
              rightMotor.setPower(-gamepad1.right_stick_y);
              leftMotor.setPower(-gamepad1.left_stick_y);
              sweeperMotor.setPower(-gamepad2.right_stick_y);
              armMotor.setPower(-gamepad2.left_stick_y);
            }
        }
    }
}
