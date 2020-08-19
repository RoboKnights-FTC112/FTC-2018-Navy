import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;

@TeleOp
public class MecanumDrive extends LinearOpMode {
    public DcMotor lfMotor = null;
    public DcMotor rfMotor = null;
    public DcMotor lrMotor = null;
    public DcMotor rrMotor = null;
    @Override
    public void runOpMode() {
        lfMotor = hardwareMap.dcMotor.get("lf");
        rfMotor = hardwareMap.dcMotor.get("rf");
        lrMotor = hardwareMap.dcMotor.get("lr");
        rrMotor = hardwareMap.dcMotor.get("rr");
        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lrMotor.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Status","MecanumDrive");
            telemetry.update();

            double x = -gamepad1.left_stick_x;

            double y = gamepad1.left_stick_y;

            double rx = gamepad1.right_stick_y;

            if (x >= 0 && y >= 0) {
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
            }

            if (x <= 0 && y >= 0) {
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
            }

            if (ry =0){
              lfMotor.setPower(rx);
              rfMotor.setPower(-rx);
              lrMotor.setPower(rx);
              rrMotor.setPower(-rx);
            }

        }
    }
}
