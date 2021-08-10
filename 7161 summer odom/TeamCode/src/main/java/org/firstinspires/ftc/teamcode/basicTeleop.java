
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="teleop", group="Arcade")
//@Disabled
public class basicTeleop extends LinearOpMode {

    hwMap mDrive = new hwMap();





    @Override
    public void runOpMode()
    {

        mDrive.init(hardwareMap);


        waitForStart();

        while (opModeIsActive())
        {

            mDrive.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);


        }
    }


    }

