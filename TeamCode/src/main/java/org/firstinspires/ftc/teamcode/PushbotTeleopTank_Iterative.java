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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Pushbot: Teleop Tank", group="Pushbot")
//@Disabled
public class PushbotTeleopTank_Iterative extends OpMode {
    private SensorStartup sensors = new SensorStartup();
    /* Declare OpMode members. */
    HardwarePushbot_Nick robot       = new HardwarePushbot_Nick(); // use the class created to define a Pushbot's hardware



    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        sensors.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "You got this my brofessor");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        double leftFrontSpeed;
        double rightFrontSpeed;
        double leftRearSpeed;
        double rightRearSpeed;
        double liftSpeed = 0;

        if (gamepad2.dpad_up)
            liftSpeed = 0.5f;

        if (gamepad2.dpad_down)
            liftSpeed = -0.5f;

        if ((gamepad2.dpad_up && sensors.liftLimitTop.getState()==true)|| (gamepad2.dpad_down && sensors.liftLimitBottom.getState()==false) )
        {
            liftSpeed = 0;
        }
        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)

        leftFrontSpeed = -gamepad1.left_stick_y - gamepad1.left_trigger + gamepad1.right_trigger;
        rightFrontSpeed = -gamepad1.left_stick_y + gamepad1.left_trigger - gamepad1.right_trigger;
        leftRearSpeed = -gamepad1.left_stick_y - gamepad1.left_trigger + gamepad1.right_trigger;
        rightRearSpeed = -gamepad1.left_stick_y + gamepad1.left_trigger - gamepad1.right_trigger;

        robot.leftFrontMotor.setPower(leftFrontSpeed);
        robot.rightFrontMotor.setPower(rightFrontSpeed);
        robot.leftRearMotor.setPower(leftRearSpeed);
        robot.rightRearMotor.setPower(rightRearSpeed);
        robot.liftMotor.setPower(liftSpeed);
//Servo position 1 is left and servo position right is 0 and servo straight is 0.5
        //if (gamepad1.x){
          //  robot.leftRearServo.setPosition(0);
        //}   else if (gamepad1.y) {
          //  robot.leftRearServo.setPosition(0.1);

        //}
        //else {


        robot.leftRearServo.setPosition(-.5*gamepad1.right_stick_x+.5);
        robot.rightRearServo.setPosition(-.5*gamepad1.right_stick_x+.5);
        robot.leftFrontServo.setPosition(-.5*gamepad1.right_stick_x+.5);
        robot.rightFrontServo.setPosition(-.5*gamepad1.right_stick_x+.5);

//}

        // Send telemetry message to signify robot running;
        telemetry.addData("Servo",  "Offset = %.2f",robot.steeringstriaght);
        //telemetry.addData("left",  "%.2f", left);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
