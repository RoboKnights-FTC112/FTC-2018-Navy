import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class CombinationProgramV2 extends LinearOpMode {
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
            telemetry.addData("Status", "CombinationProgramV2");
            telemetry.update();
            double powerMultiple = 0;
            double powerLeft = -gamepad1.left_stick_y;
            double powerRight = -gamepad1.right_stick_y;
            double powerLeft2 = -gamepad2.left_stick_y;
            double powerRight2 = -gamepad2.right_stick_y;
            if (gamepad1.left_bumper) { //makes the robot drive at speed 1/4 if the left trigger is pressed on gamepad1
              telemetry.addData("Status", "QuarterSpeedDrive");
              rightMotor.setPower(powerRight/4);
              leftMotor.setPower(powerLeft/4);
            }else if (gamepad1.right_bumper) { //makes the robot drive at speed 1/8 if the right bumper is pressed on gamepad1
              telemetry.addData("Status", "EighthSpeedDrive");
              rightMotor.setPower(powerRight/8);
              leftMotor.setPower(powerLeft/8);
            }else if (gamepad2.right_trigger !=0) { // makes the sweeper speed 1/4 when the right trigger is pressed on gamepad2
              telemetry.addData("Status", "QuarterSpeedSwepper");
              sweeperMotor.setPower(powerRight2/4);
            }else if (gamepad2.right_bumper) { // makes the sweeper speed 1/2 when the right bumper is pressed on gamepad2
              telemetry.addData("Status", "EighthSpeedSweeper");
              sweeperMotor.setPower(powerRight2/8);
            }else if (gamepad2.left_trigger != 0) {  // makes the arm speed 1/4 when the left trigger is pressed on gamepad2
              telemetry.addData("Status", "QuarterSpeedArm");
              armMotor.setPower(powerLeft2/4);
            }else if (gamepad2.left_bumper) {  // makes the arm speed 1/2 when the left stick is pressed on gamepad2
              telemetry.addData("Status", "EighthSpeedArm");
              armMotor.setPower(powerLeft2/8);
            }else{
              if (gamepad1.left_trigger != 0) {
                powerMultiple = -gamepad1.left_trigger;
              }else{
                powerMultiple = gamepad1.right_trigger;
              }if (gamepad1.left_stick_x < 0) {
                rightMotor.setPower(powerMultiple);
                leftMotor.setPower(powerMultiple*(1+gamepad1.left_stick_x));
              }else if (gamepad1.left_stick_x > 0) {
                leftMotor.setPower(powerMultiple);
                rightMotor.setPower(powerMultiple*(1-gamepad1.left_stick_x));
              }else {
                rightMotor.setPower(powerMultiple);
                leftMotor.setPower(powerMultiple);
               }
            }
        }
    }
}
