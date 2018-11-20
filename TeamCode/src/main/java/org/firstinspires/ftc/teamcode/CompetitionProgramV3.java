import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class CompetitionProgramV3 extends LinearOpMode {

  public DcMotor leftMotor = null; // motor controller 1
  public DcMotor rightMotor = null;
  public DcMotor rightarmMotor = null;
  public DcMotor leftarmMotor = null;

  public DcMotor liftMotor = null; // motor controller 2
  public DcMotor midarmMotor = null;
  public DcMotor sweepMotor = null;
  public DcMotor extraMotor = null;

    @Override
    public void runOpMode() {

      leftMotor = hardwareMap.dcMotor.get("ld");
      rightMotor = hardwareMap.dcMotor.get("rd");
      rightarmMotor = hardwareMap.dcMotor.get("ra");
      leftarmMotor = hardwareMap.dcMotor.get("la");

      liftMotor = hardwareMap.dcMotor.get("ld2");
      midarmMotor = hardwareMap.dcMotor.get("rd2");
      sweepMotor = hardwareMap.dcMotor.get("ra2");
      extraMotor = hardwareMap.dcMotor.get("la2");


      leftMotor.setDirection(DcMotor.Direction.REVERSE);
      leftarmMotor.setDirection(DcMotor.Direction.REVERSE);

      rightarmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      leftarmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      midarmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      rightarmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      leftarmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      midarmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


      leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      sweepMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      extraMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      sweepMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      extraMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double leftMotorposition = leftMotor.getCurrentPosition();
            double rightMotorposition = rightMotor.getCurrentPosition();
            double rightarmMotorposition = rightarmMotor.getCurrentPosition();
            double leftarmMotorposition = leftarmMotor.getCurrentPosition();
            double liftMotorposition = liftMotor.getCurrentPosition();
            double midarmMotorposition = midarmMotor.getCurrentPosition();
            double sweepMotorposition = sweepMotor.getCurrentPosition();
            double extraMotorposition = extraMotor.getCurrentPosition();

            double powerMultiple = gamepad1.right_trigger;

            if (gamepad1.left_trigger != 0 || gamepad1.right_trigger != 0) {
              telemetry.addData("Status","VideogameDriveMode");
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
              rightMotor.setPower(-gamepad1.right_stick_y);
              leftMotor.setPower(-gamepad1.left_stick_y);
              telemetry.addData("Status", "TankDriveMode");
            }

            if(gamepad1.left_bumper){
              liftMotor.setPower(.99);
            }else{
              liftMotor.setPower(0);
            }

            if(gamepad1.right_bumper){
              liftMotor.setPower(-.99);
            }else{
              liftMotor.setPower(0);
            }

            if (leftarmMotorposition > 380 ) {
              rightarmMotor.setTargetPosition(360);
              rightarmMotor.setPower(1);
              leftarmMotor.setTargetPosition(360);
              leftarmMotor.setPower(1);
            }else{
              if (gamepad2.right_stick_y > 0) {
                rightarmMotor.setPower(-gamepad2.right_stick_y/3);
                leftarmMotor.setPower(-gamepad2.right_stick_y/3);
              }else{
                rightarmMotor.setPower(-gamepad2.right_stick_y);
                leftarmMotor.setPower(-gamepad2.right_stick_y);
              }
            }

            if (gamepad2.left_stick_y < 0) {
              midarmMotor.setPower(gamepad2.left_stick_y/3);
            }else{
              midarmMotor.setPower(gamepad2.left_stick_y);
            }

            sweepMotor.setPower(gamepad2.right_trigger);
            sweepMotor.setPower(-gamepad2.left_trigger);

            telemetry.addData("Encoder rightarmMotorposition", rightarmMotorposition);
            telemetry.addData("Encoder leftarmMotorposition", leftarmMotorposition);
            telemetry.addData("Encoder midarmMotorposition", midarmMotorposition);

            // telemetry.addData("Encoder leftMotorposition", leftMotorposition);
            // telemetry.addData("Encoder rightMotorposition", rightMotorposition);
            // telemetry.addData("Encoder liftMotorposition", liftMotorposition);
            // telemetry.addData("Encoder sweepMotorposition", sweepMotorposition);
            // telemetry.addData("Encoder extraMotorposition", extraMotorposition);

            telemetry.update();
        }
    }
}
