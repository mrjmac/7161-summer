package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="test teleop", group="Arcade")
//@Disabled
public class basicTeleop extends OpMode {

//    hwMap mDrive = new hwMap();

    public DcMotor fl; //Motor 0 - frontLeft
    public DcMotor fr; //Motor 1 - backLeft
    public DcMotor br; //Motor 2 - backRight
    public DcMotor bl;
    DcMotor verticalLeft;
  //  DcMotor verticalRight;
    DcMotor horizontal;

    @Override
    public void init() {
         fl = hardwareMap.dcMotor.get("frontLeft");
         fr = hardwareMap.dcMotor.get("frontRight");
         bl = hardwareMap.dcMotor.get("backLeft");
         br = hardwareMap.dcMotor.get("backRight");

        verticalLeft = hardwareMap.dcMotor.get("backLeft");
    //    verticalRight = hardwareMap.dcMotor.get("frontLeft");
        horizontal = hardwareMap.dcMotor.get("backRight");


        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //define motor settings
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      //  verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
     //   verticalRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_x) > .1 || Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.right_stick_x) > .1) {      //check for inputs
            double FLP = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;          //calculate power for each motor
            double FRP = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
            double BLP = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
            double BRP = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

            double max = Math.max(Math.max(BLP, BRP), Math.max(FLP, FRP)); //find max value

            if (max > 1) {    //if max value over 1, divide everything by max to get correct range (0-1 power)
                FLP /= max;
                FRP /= max;
                BLP /= max;
                BRP /= max;
            }

            if (gamepad1.right_trigger > .1) {    //slow mode for accurate movements
                fl.setPower(FLP * .35);
                fr.setPower(FRP * .35);
                bl.setPower(BLP * .35);
                br.setPower(BRP * .35);
                telemetry.addData("FLP:", FLP * .35);
                telemetry.addData("FRP:", FRP * .35);
                telemetry.addData("BLP:", BLP * .35);
                telemetry.addData("BRP:", BRP * .35);
            } else {
                fl.setPower(FLP);
                fr.setPower(FRP);
                bl.setPower(BLP);
                br.setPower(BRP);
                telemetry.addData("FLP:", FLP);
                telemetry.addData("FRP:", FRP);
                telemetry.addData("BLP:", BLP);
                telemetry.addData("BRP:", BRP);            }

        } else {
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }
        telemetry.addData("horizontal:", horizontal.getCurrentPosition());
        telemetry.addData("verticalLeft:", verticalLeft.getCurrentPosition());
    //    telemetry.addData("verticalRight:", verticalRight.getCurrentPosition());

        telemetry.update();
    }

    @Override
    public void stop() {

    }
}

