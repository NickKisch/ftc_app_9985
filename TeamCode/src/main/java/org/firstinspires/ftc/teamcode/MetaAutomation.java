package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

abstract public class MetaAutomation extends LinearOpMode {
    //Constants for autonomous

    //Driving Constants
    public static final double speed_FULL   = 1;
    public static final double speed_NORMAL = 0.5;
    public static final double speed_SLOW   = 0.1;

    //Servo Turn Constants
    public static final double turnLeft     = 1;
    public static final double turnStraight = 0.5;
    public static final double turnRight    = 0;

    //Motor Turn Constants
    public static final double speedTurn_FULL   = 0;
    public static final double speedTurn_NORMAL = 0;
    public static final double speedTurn_SLOW   = 0;

    //Timeout Constants
    public static final double tout_SHORT  = 3;
    public static final double tout_MEDIUM = 5;
    public static final double tout_LONG   = 10;

    //Vars for later

    //Time counting variable for timeout
    private ElapsedTime runtime = new ElapsedTime();

    //Declare motors and sensors
    HardwarePushbot_Nick robot = new HardwarePushbot_Nick();
    SensorStartup sensors = new SensorStartup();

    //Declare inner classes
    transform transform = new transform();
    rotate rotate = new rotate();
    /*
    *  Functions for autnomouns
    *  this is the stuff that makes autonomous work
    *
    */

    public void setupHardware(){
        //Start autonomous
        robot.init(hardwareMap);
        sensors.init(hardwareMap);
    }

    public void LiftDown(double sTimeOut) {
        double reverseTime = 250; //Set a time interval to reverse the motor to stop the momentum
        runtime.reset(); //Reset time counter
        robot.liftMotor.setPower(speed_FULL); // Set lift motor to full speed
        while(sensors.liftLimitTop.getState()==false || (runtime.milliseconds() <= (sTimeOut * 1000))) {
            //while the touchsensor is not presssedn and have not reach the timout
            // do nothing
            idle();
        }
        /*
        runtime.reset(); // Reset the time counter
        while(runtime.milliseconds() <= reverseTime) {
            robot.liftMotor.setPower(-speed_FULL);
        }
        robot.liftMotor.setPower(0);
        */
    }

    public class transform {
        public static final double forward = 0.5;
        public static final double left = 0;
        public static final double left45 = 0.25;
        public static final double right = 1;
        public static final double right45 = 0.75;

        public void dMove(double speed, double angle, double distance, double sTimeout) {
            if (speed < 0.0) {
                speed = -speed;
                distance = -distance;
            }
        }

        public void setServo(double angle) {
            angle = Range.clip(angle, -90, 90);

        }

        public void eDrive (
                double power,
                double leftFrontEncoderTicks,
                double rightRearEncoderTicks,
                double sTimeout) {
            //Reset the encoders on the motors that have them
            robot.leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //Make targets for encoders
            int newLeftFrontTarget;
            int newRightRearTarget;

            if (opModeIsActive()){
                idle();

                newLeftFrontTarget = robot.leftFrontMotor.getCurrentPosition() + (int) (leftFrontEncoderTicks);
                newRightRearTarget = robot.rightRearMotor.getCurrentPosition() + (int) (rightRearEncoderTicks);

                //Set the targets for the encoders
                robot.leftFrontMotor.setTargetPosition(newLeftFrontTarget);
                robot.rightRearMotor.setTargetPosition(newRightRearTarget);

                //Set the mode on encoders to run to position
                robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                //Reset the timeout and start motion
                runtime.reset();
                power = Range.clip(Math.abs(power), 0.0, 1.0);
                robot.leftFrontMotor.setPower(power);
                robot.rightFrontMotor.setPower(power);
                robot.leftRearMotor.setPower(power);
                robot.rightRearMotor.setPower(power);

                idle();

                while (opModeIsActive() || (runtime.seconds() < sTimeout) || robot.leftFrontMotor.isBusy() || robot.rightRearMotor.isBusy()) {
                    //View old autonomous if this does not work

                    //Display telemetry
                    telemetry.addData("Status", "eDrive");
                    telemetry.addData("Running to", "%7d :%7d", newLeftFrontTarget, newRightRearTarget);
                    telemetry.addData("Running at", "%7d :7d", robot.leftFrontMotor.getCurrentPosition(), robot.rightRearMotor.getCurrentPosition());
                    telemetry.update();
                    idle();
                }

                //Stop all motion
                robot.leftFrontMotor.setPower(0);
                robot.rightFrontMotor.setPower(0);
                robot.leftRearMotor.setPower(0);
                robot.rightRearMotor.setPower(0);
                idle();

                //Turn off run to position mode on encoders
                // Turn off RUN_TO_POSITION
                robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                idle();
            }
        }
    }

    public class rotate {

    }
}
