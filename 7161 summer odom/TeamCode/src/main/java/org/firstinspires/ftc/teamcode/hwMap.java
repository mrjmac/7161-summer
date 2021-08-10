package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class hwMap {
    public DcMotor FL; //Motor 0 - frontLeft
    public DcMotor BL; //Motor 1 - backLeft
    public DcMotor BR; //Motor 2 - backRight
    public DcMotor FR; //Motor 3 - frontRight


    public BNO055IMU imu; //bus 0


    // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
    // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
    // and named "imu".

    HardwareMap hardwareMap;

    public hwMap() {
        FL = null;
        BL = null;
        BR = null;
        FR = null;
    }

    public void init(HardwareMap h) {
        hardwareMap = h;

        BR = hardwareMap.get(DcMotor.class, "backRight");
        FR = hardwareMap.get(DcMotor.class, "frontRight");
        FL = hardwareMap.get(DcMotor.class, "frontLeft");
        BL = hardwareMap.get(DcMotor.class, "backLeft");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;


        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);


        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        freeze();

        FL.setDirection(DcMotor.Direction.FORWARD);
        BL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();


    }


    public void freeze() {
        FL.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
        FR.setPower(0);

    }


    public void resetEncoders() {
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    public double getEncoderAvg() {
        return ((Math.abs(FR.getCurrentPosition()) + Math.abs(FL.getCurrentPosition())) / 2.0);
    }

    public void drive(double x, double y, double t)
    {

        double magnitude = Math.sqrt(x * x + y * y);
        double distance = Math.atan2(y, x);
        double turn =  2 * t / 3.0;


        //calculate power with angle and magnitude

        double backLeft = magnitude * Math.sin(distance - Math.PI / 4) + turn;
        double backRight = magnitude * Math.sin(distance + Math.PI / 4) - turn;
        double frontLeft = magnitude * Math.sin(distance + Math.PI / 4) + turn;
        double frontRight = magnitude * Math.sin(distance - Math.PI / 4) - turn;

        /*in case the power to the motors gets over 1(as 1 is the maximum motor value, and in order
        to strafe diagonally, wheels have to move at different speeds), we divide them all by the
        highest value. This keeps them under 1, but in respect with each other*/

        if (magnitude != 0) {
            double divisor = 0;
            divisor = Math.max(Math.abs(backLeft), Math.abs(backRight));
            divisor = Math.max(divisor, Math.abs(frontLeft));
            divisor = Math.max(divisor, Math.abs(frontRight));

            telemetry.addData("divisor: ", divisor);

            backLeft = magnitude * (backLeft / divisor);
            backRight = magnitude * (backRight / divisor);
            frontLeft = magnitude * (frontLeft / divisor);
            frontRight = magnitude * (frontRight / divisor);
        }


        BL.setPower(backRight);
        BR.setPower(backLeft);
        FL.setPower(frontRight);
        FR.setPower(frontLeft);


        telemetry.addData("encoder tick FL", FL.getCurrentPosition());
        telemetry.addData("encoder tick FR", FR.getCurrentPosition());
        telemetry.addData("encoder tick BL", BL.getCurrentPosition());
        telemetry.addData("encoder tick BR", BR.getCurrentPosition());



        telemetry.update();

    }


}