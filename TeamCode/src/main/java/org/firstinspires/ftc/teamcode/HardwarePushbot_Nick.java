package org.firstinspires.ftc.teamcode;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 */
public class HardwarePushbot_Nick
{
    /* Public OpMode members. */
    public DcMotor leftFrontMotor  = null;
    public DcMotor leftRearMotor   = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor rightRearMotor  = null;
    public DcMotor liftMotor       = null;
    public DcMotor armMotor        = null;
    public Servo leftFrontServo    = null;
    public Servo leftRearServo     = null;
    public Servo rightFrontServo   = null;
    public Servo rightRearServo    = null;
    public Servo grabberHorizServo = null;
    public Servo grabberVertServo  = null;
    public Servo latchServo       = null;
    public static final double steeringstriaght =  0.5;
    public static final double steeringright    =    0;
    public static final double steeringleft     =    1;
    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();
    /* Constructor */
    public HardwarePushbot_Nick(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFrontMotor  = hwMap.get(DcMotor.class, "FrontLeft_Motor");
        leftRearMotor   = hwMap.get(DcMotor.class, "RearLeft_Motor");
        rightFrontMotor = hwMap.get(DcMotor.class, "FrontRight_Motor");
        rightRearMotor  = hwMap.get(DcMotor.class, "RearRight_Motor");
        liftMotor       = hwMap.get(DcMotor.class, "lift_Motor");
        armMotor        = hwMap.get(DcMotor.class, "arm_Motor");
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        //Set spefic motors to hault immedeitly.
        //liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Set all motors to zero power
        leftFrontMotor.setPower(0);
        leftRearMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightRearMotor.setPower(0);
        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Define and initialize ALL installed servos.
        leftFrontServo    = hwMap.get(Servo.class, "FrontLeft_Servo");
        rightFrontServo   = hwMap.get(Servo.class, "FrontRight_Servo");
        rightRearServo    = hwMap.get(Servo.class, "RearRight_Servo");
        leftRearServo     = hwMap.get(Servo.class, "RearLeft_Servo");
        grabberHorizServo = hwMap.get (Servo.class, "GrabberHoriz_Servo");
        grabberVertServo  = hwMap.get (Servo.class, "GrabberVert_Servo");
        //latchServo        = hwMap.get(Servo.class, "latch_Servo");

        rightRearServo.setPosition(MetaAutomation.turnRight);
        rightFrontServo.setPosition(MetaAutomation.turnRight);
        leftRearServo.setPosition(MetaAutomation.turnLeft);
        leftFrontServo.setPosition(MetaAutomation.turnLeft);
        //grabberVertServo.setPosition(steeringstriaght);
        //grabberHorizServo.setPosition(steeringstriaght);
        //latchServo.setPosition(MetaAutomation.latch);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

 }


