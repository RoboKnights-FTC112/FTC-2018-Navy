import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class VideogameDriveV1 extends LinearOpMode {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;


    @Override
    public void runOpMode() {

        leftMotor = hardwareMap.dcMotor.get("ld");
        rightMotor = hardwareMap.dcMotor.get("rd");
        //armMotor = hardwareMap.dcMotor.get("arm");
        //sweeperMotor = hardwareMap.dcMotor.get("sweep");
        //sweeperMotor.setPower(-gamepad2.right_stick_y);
        //armMotor.setPower(-gamepad2.left_stick_y);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "VideogameDrive");
            telemetry.update();
            double powerMultiple = 0;
            if (gamepad1.left_trigger != 0) {
              powerMultiple = -gamepad1.left_trigger;
            }else{
              powerMultiple = gamepad1.right_trigger;
            }if (gamepad1.left_stick_x > 0) {
              rightMotor.setPower(powerMultiple);
              leftMotor.setPower(powerMultiple*(1-gamepad1.left_stick_x));
            }else if (gamepad1.left_stick_x < 0) {
              leftMotor.setPower(powerMultiple);
              rightMotor.setPower(powerMultiple*(1+gamepad1.left_stick_x));
            }else {
              rightMotor.setPower(powerMultiple);
              leftMotor.setPower(powerMultiple);
            }
        }
    }
}
