import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class BasicDriveProgram extends LinearOpMode {
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
            telemetry.addData("Status", "BasicDriveProgram");
            telemetry.update();

            rightMotor.setPower(-gamepad1.right_stick_y);
            leftMotor.setPower(-gamepad1.left_stick_y);
            sweeperMotor.setPower(-gamepad2.right_stick_y);
            armMotor.setPower(-gamepad2.left_stick_y);

        }
    }
}
