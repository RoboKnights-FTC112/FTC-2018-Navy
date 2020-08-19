import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;

@TeleOp
public class Usethis extends LinearOpMode {
    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;

    public DcMotor lift1Motor = null;
    // public DcMotor lift2Motor = null;

    public DcMotor spoolMotor = null;

    public Servo arm = null;

    final double ARM_SPEED = 0.15;

    @Override
    public void runOpMode() {
        lfMotor = hardwareMap.dcMotor.get("lf");
        rfMotor = hardwareMap.dcMotor.get("rf");
        lrMotor = hardwareMap.dcMotor.get("lr");
        rrMotor = hardwareMap.dcMotor.get("rr");

        spoolMotor = hardwareMap.dcMotor.get("sp");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lrMotor.setDirection(DcMotor.Direction.REVERSE);

        lift1Motor = hardwareMap.dcMotor.get("l1");
        // lift2Motor = hardwareMap.dcMotor.get("l2");
        arm = hardwareMap.servo.get("s1");

        // lift2Motor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Status","MecanumDrive");
            telemetry.update();

            double x = gamepad1.left_stick_x;

            double y = -gamepad1.left_stick_y;

            double rx = gamepad1.right_stick_x;


          if(gamepad1.a){
              arm.setPosition(0.15);
              }
          if(gamepad1.b){
              arm.setPosition(0.6);
              }

          if(gamepad1.left_trigger > 0){
            lift1Motor.setPower(-gamepad1.left_trigger);
            // lift2Motor.setPower(-gamepad1.left_trigger);
          }else if(gamepad1.right_trigger > 0){
            lift1Motor.setPower(gamepad1.right_trigger);
            // lift2Motor.setPower(gamepad1.right_trigger) ;
          }else{
            lift1Motor.setPower(0);
            // lift2Motor.setPower(0);
          }
        if(gamepad1.right_bumper){
            spoolMotor.setPower(-1);
          }else if(gamepad1.left_bumper){
            spoolMotor.setPower(1);
          }else{
            spoolMotor.setPower(0);
          }





// begin of drive
            if (rx != 0 ){
              lfMotor.setPower(rx);
              rfMotor.setPower(-rx);
              lrMotor.setPower(rx);
              rrMotor.setPower(-rx);
            }else if (x >= 0 && y >= 0) {
              lfMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
              rrMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
            } else if (x <= 0 && y <= 0){
              lfMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
              rrMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
            } else if (x >= 0 && y <= 0){
              lfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)+360) - 315)/45));
              rrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)+360) - 315)/45));
            } else if (x <= 0 && y >= 0){
              lfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((315 - ((java.lang.Math.atan(y/x)*180/3.141592)+360))/45));
              rrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((315 - ((java.lang.Math.atan(y/x)*180/3.141592)+360))/45));
            }else{
              lfMotor.setPower(0);
              rfMotor.setPower(0);
              lrMotor.setPower(0);
              rrMotor.setPower(0);
            }


            if (rx !=0){
              lfMotor.setPower(rx);
              rfMotor.setPower(-rx);
              lrMotor.setPower(rx);
              rrMotor.setPower(-rx);
            }else if (x <= 0 && y >= 0) {
              rfMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
              lrMotor.setPower(java.lang.Math.sqrt((y*y)+(x*x)));
            } else if (x >= 0 && y <= 0){
              rfMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
              lrMotor.setPower(-(java.lang.Math.sqrt((y*y)+(x*x))));
            } else if (x >= 0 && y >= 0){
              rfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)) - 45)/45));
              lrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((((java.lang.Math.atan(y/x)*180/3.141592)) - 45)/45));
            } else if (x <= 0 && y <= 0){
              rfMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((45 - ((java.lang.Math.atan(y/x)*180/3.141592)))/45));
              lrMotor.setPower((java.lang.Math.sqrt((y*y)+(x*x))) * ((45 - ((java.lang.Math.atan(y/x)*180/3.141592)))/45));
            }else{
              lfMotor.setPower(0);
              rfMotor.setPower(0);
              lrMotor.setPower(0);
              rrMotor.setPower(0);
            }

            // if (gamepad1.right_trigger>0){
            //   armMotor.setPower(gamepad1.right_trigger);
            // }else if(gamepad1.left_trigger>0){
            //   armMotor.setPower(-gamepad1.left_trigger);
            // }else{
            //   armMotor.setPower(0);
            // }



        }
    }
}
