package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class hwMap {
    public DcMotor fl; //Motor 0 - frontLeft
    public DcMotor fr; //Motor 1 - backLeft
    public DcMotor br; //Motor 2 - backRight
    public DcMotor bl; //Motor 3 - frontRight


    public BNO055IMU imu; //bus 0


    // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
    // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
    // and named "imu".

    HardwareMap hardwareMap;

    public hwMap() {
        fl = null;
        fr = null;
        br = null;
        bl = null;
    }

    public void init(HardwareMap h) {
        hardwareMap = h;

        DcMotor fl = hardwareMap.dcMotor.get("frontLeft");
        DcMotor fr = hardwareMap.dcMotor.get("frontRight");
        DcMotor bl = hardwareMap.dcMotor.get("backLeft");
        DcMotor br = hardwareMap.dcMotor.get("backRight");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;


        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);


        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        freeze();

        fl.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();


    }


    public void freeze() {
        fl.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        fr.setPower(0);

    }


    public void resetEncoders() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    public double getEncoderAvg() {
        return ((Math.abs(fr.getCurrentPosition()) + Math.abs(fl.getCurrentPosition())) / 2.0);
    }


}

//TODO: if problem, look at proxy
