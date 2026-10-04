package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.navigate.AprilTagWebcam;

@TeleOp(name = "Apriltag_reader_stu", group = "TeleOp")
public class Motor_Mover_Apriler_Tag_Stu extends OpMode {

    private static final double JOYSTICK_DEADBAND = 0.08;

    private DcMotor leftFront;
    public  AprilTagWebcam RedTagReaderstu;



    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setPower(0.0);
        RedTagReaderstu=new AprilTagWebcam();
        RedTagReaderstu.init(hardwareMap, telemetry);
        telemetry.addData("webcam flag", RedTagReaderstu.tagDetected);


        telemetry.addData("Status", "Initialized");
        telemetry.addData("Motor", "leftFront ready");
        telemetry.addData("Deadband", JOYSTICK_DEADBAND);
        telemetry.update();

    }

    @Override
    public void loop() {
        double leftStickY = -gamepad1.left_stick_y*0.5;
        double x_value;
        double motorPower=0;
        double joystick_deadband=0.08;
        double DEADBAND_THRESHOLD_APRILTAG=10;
        RedTagReaderstu.updateDetections();
        leftFront.setPower(motorPower);
            if (RedTagReaderstu.tagDetected)
        {
            x_value = RedTagReaderstu.x_value;
            motorPower = applyDeadband(x_value, DEADBAND_THRESHOLD_APRILTAG);
            motorPower = Range.clip(motorPower, -1, 1);
            leftFront.setPower(motorPower);

        } else {
            motorPower = applyDeadband(leftStickY, joystick_deadband);
            motorPower = Range.clip(motorPower, -1, 1);
            leftFront.setPower(motorPower);
        }

        telemetry.addData("webcam flag", RedTagReaderstu.tagDetected);
        telemetry.addData("Status", "Running");
        telemetry.addData("Deadband", JOYSTICK_DEADBAND);
        telemetry.addData("Left Stick Y (inverted)", "%.3f", leftStickY);
        telemetry.addData("Motor Power", "%.3f", motorPower);

        //telemetry.addata//

        telemetry.update();
    }

    private double applyDeadband(double value, double deadband) {
        if (Math.abs(value) <= deadband) {
            return 0.0;
        }

        return value;
    }


    }

   /* @Override
    public void stop() {
        if (leftFront != null) {
            leftFront.setPower(0.0);*/



