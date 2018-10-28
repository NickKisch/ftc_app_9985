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

    //Encoder Constants
    public static final double COUNTS_PER_INCHES = 90;

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

    public void waitForStart() {
        while (!isStarted() && !isStopRequested()){
            idle();
        }
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

        public void left() {
            double server = turnLeft;

            setAngleInd(server, server, server, server);
        }

        public void right() {
            double server = turnRight;

            setAngleInd(server, server, server, server);

        }

        public void straight() {
            double server = turnStraight;

            setAngleInd(server, server, server, server);

        }

        public void setAngleAll(double angle) {
            angle = Range.clip(angle, -90, 90);
            //Equation for sever position from angle is (1/180) * (angle + 90) where the 1/180 is the slope
            double serverStep = ((1./180.)*(angle+90.));

            setAngleInd(serverStep, serverStep, serverStep, serverStep);

        }

        public void setAngleInd(double frontLeft, double frontRight, double rearLeft, double rearRight){
            robot.leftFrontServo.setPosition(frontLeft);
            robot.rightFrontServo.setPosition(frontRight);
            robot.leftRearServo.setPosition(rearLeft);
            robot.rightRearServo.setPosition(rearRight);
        }

        public void eDriveDistance(double speed, double distanceInches, double stimeout){
            if (speed < 0.0){
                speed = -speed;
                distanceInches = -distanceInches;
            }

            double encoderDistance = distanceInches * COUNTS_PER_INCHES;
            eDrive(speed, encoderDistance, encoderDistance, encoderDistance, encoderDistance, stimeout);

        }

        public void eDrive (
                double power,
                double leftFrontEncoderTicks,
                double rightFrontEncoderTicks,
                double leftRearEncoderTicks,
                double rightRearEncoderTicks,
                double sTimeout) {
            //Reset the encoders on the motors that have them
            robot.leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.leftRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //Make targets for encoders
            int newLeftFrontTarget;
            int newRightFrontTarget;
            int newLeftRearTarget;
            int newRightRearTarget;


            if (opModeIsActive()){
                idle();

                newLeftFrontTarget = robot.leftFrontMotor.getCurrentPosition() + (int) (leftFrontEncoderTicks);
                newRightFrontTarget = robot.rightFrontMotor.getCurrentPosition() + (int) (rightFrontEncoderTicks);
                newLeftRearTarget = robot.leftRearMotor.getCurrentPosition() + (int) (leftRearEncoderTicks);
                newRightRearTarget = robot.rightRearMotor.getCurrentPosition() + (int) (rightRearEncoderTicks);

                //Set the targets for the encoders
                robot.leftFrontMotor.setTargetPosition(newLeftFrontTarget);
                robot.rightFrontMotor.setTargetPosition(newRightFrontTarget);
                robot.leftRearMotor.setTargetPosition(newLeftRearTarget);
                robot.rightRearMotor.setTargetPosition(newRightRearTarget);

                //Set the mode on encoders to run to position
                robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.leftRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                //Reset the timeout and start motion
                runtime.reset();
                power = Range.clip(Math.abs(power), 0.0, 1.0);
                robot.leftFrontMotor.setPower(power);
                robot.rightFrontMotor.setPower(power);
                robot.leftRearMotor.setPower(power);
                robot.rightRearMotor.setPower(power);

                idle();

                while (opModeIsActive() || (runtime.seconds() < sTimeout) || robot.leftFrontMotor.isBusy() || robot.rightRearMotor.isBusy() || robot.rightFrontMotor.isBusy() || robot.leftRearMotor.isBusy()) {
                    //View old autonomous if this does not work

                    //Display telemetry
                    telemetry.addData("Status", "eDrive");
                    telemetry.addData("Running to", "%7d :%7d :%7d :%7d", newLeftFrontTarget, newRightFrontTarget, newLeftRearTarget, newRightRearTarget );
                    telemetry.addData("Running at", "%7d :%7d :%7d :%7d", robot.leftFrontMotor.getCurrentPosition(), robot.rightFrontMotor.getCurrentPosition(), robot.leftRearMotor.getCurrentPosition(), robot.rightRearMotor.getCurrentPosition());
                    telemetry.addData("Runing Time Max", "%7d", runtime.milliseconds());
                    telemetry.addData("Running Time At", "%7d", (sTimeout * 1000.));
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
                robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                idle();
            }
        }
    }

    public class rotate {


    }
}

