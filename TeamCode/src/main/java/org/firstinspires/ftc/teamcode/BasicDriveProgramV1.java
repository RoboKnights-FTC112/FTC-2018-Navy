import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class BasicDriveProgramV1 extends LinearOpMode {

    public DcMotor leftMotor = null; // motor controller 1
    public DcMotor rightMotor = null;
    public DcMotor rightarmMotor = null;
    public DcMotor leftarmMotor = null;

    public DcMotor liftMotor = null; // motor controller 2
    public DcMotor midarmMotor = null;
    public Dcmotor sweepMotor = null;
    public Dcmotor extraMotor = null;

    @Override
    public void runOpMode() {

        leftMotor = hardwareMap.dcMotor.get("ld");
        rightMotor = hardwareMap.dcMotor.get("rd");
        rightarmMotor = hardwareMap.dcMotor.get("ra");
        leftarmMotor = hardwareMap.dcMotor.get("la");

        liftMotor = hardwareMap.dcMotor.get("ld2");
        midarmMotor = hardwareMap.dcMotor.get("rd2")
        sweepMotor = hardwareMap.dcMotor.get("ra2")
        extraMotor = hardwareMap.dcMotot.get("la2")



        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftarmMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "BasicDriveProgram");
            telemetry.update();

            rightMotor.setPower(-gamepad1.right_stick_y);
            leftMotor.setPower(-gamepad1.left_stick_y);

            liftMotor.setPower(-gamepad2.left_stick_y);

            rightarmMotor.setPower(-gamepad2.right_stick_y);
            leftarmMotor.setPower(-gamepad2.right_stick_y);

            

        }
    }
}
