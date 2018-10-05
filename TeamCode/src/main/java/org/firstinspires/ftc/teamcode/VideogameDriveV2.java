import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class VideogameDriveV2 extends LinearOpMode {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    //public DcMotor sweeperMotor = null;
    //public DcMotor armMotor = null;
    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.dcMotor.get("ld"); //slot 0 on the motor controller
        rightMotor = hardwareMap.dcMotor.get("rd"); //slot 1 on the motor controller
        //armMotor = hardwareMap.dcMotor.get("arm"); //slot 2 or 3 on the motor controller
        //sweeperMotor = hardwareMap.dcMotor.get("sweep");//slot 2 or 3 on the motor controller
        //sweeperMotor.setPower(-gamepad2.right_stick_y);
        //armMotor.setPower(-gamepad2.left_stick_y);
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Status", "VideogameDriveV2");
            telemetry.update();
            double powerMultiple = gamepad1.right_trigger;

            if (gamepad1.left_trigger != 0 || gamepad1.right_trigger != 0) {
              telemetry.addData("VideogameDrive Mode");
              if (gamepad1.left_trigger != 0) {
                powerMultiple = -gamepad1.left_trigger;
              }
              if (gamepad1.left_stick_x < 0) {
                rightMotor.setPower(powerMultiple);
                leftMotor.setPower(powerMultiple*(1+gamepad1.left_stick_x));
              }else if (gamepad1.left_stick_x > 0) {
                leftMotor.setPower(powerMultiple);
                rightMotor.setPower(powerMultiple*(1-gamepad1.left_stick_x));
              }else{
                rightMotor.setPower(powerMultiple);
                leftMotor.setPower(powerMultiple);
              }
            }else{
              telemetry.addData("TankDrive Mode");
              rightMotor.setPower(-gamepad1.right_stick_y);
              leftMotor.setPower(-gamepad1.left_stick_y);
            }
        }
    }
}
