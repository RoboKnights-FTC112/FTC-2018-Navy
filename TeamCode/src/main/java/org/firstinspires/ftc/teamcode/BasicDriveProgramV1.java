import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class BasicDriveProgramV1 extends LinearOpMode {

    public DcMotor leftMotor = null; // motor controller 1
    public DcMotor rightMotor = null;
    public DcMotor midarmMotor = null;
    public DcMotor liftMotor = null;

    public Dcmotor strafeMotor = null;  // motor controller 2
    public Dcmotor strafe2Motor = null;

    @Override
    public void runOpMode() {

        leftMotor = hardwareMap.dcMotor.get("ld");
        rightMotor = hardwareMap.dcMotor.get("rd");
        liftMotor = hardwareMap.dcMotor.get("ld2");
        midarmMotor = hardwareMap.dcMotor.get("rd2")

        strafeMotor = hardwareMap.dcMotor.get("sd")
        strafe2Motor = hardwareMap.dcMotot.get("sd2")



        leftMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "BasicDriveProgram");
            telemetry.update();

            rightMotor.setPower(-gamepad1.right_stick_y);
            leftMotor.setPower(-gamepad1.left_stick_y);

            liftMotor.setPower(-gamepad2.left_stick_y);

            midarmMotor.setPower(-gamepad2.right_stick_y);

            if (gamepad1.right_trigger) {
              strafeMotor.setPower(gamepad1.right_trigger)
              strafe2Motor.setpower(gamepad1.right_trigger)
            }
            if (gamepad1.left_trigger) {
              strafeMotor.setpower(-gamepad1.left_trigger)
              strafe2Motor.setPower(-gamepad1.left_trigger)
            }
            
        }
    }
}
