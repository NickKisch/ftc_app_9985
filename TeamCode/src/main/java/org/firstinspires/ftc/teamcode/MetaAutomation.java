package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

abstract public class MetaAutomation extends LinearOpMode {
    //Constants for autonomous

    //Driving Constants
    public static final double speed_FULL   = 1;
    public static final double speed_NORMAL = 0.5;
    public static final double speed_SLOW   = 0.3;

    //Servo Angle Constants
    public static final double turn_HalfLeft  = -45;
    public static final double turn_HalfRight = 45;

    //Servo Turn Constants
    public static final double turnLeft     = 1;
    public static final double turnStraight = 0.5;
    public static final double turnRight    = 0;

    //Other Servo Constants
    public static final double latch = 0.5;
    public static final double releaseLatch = 0.25;

    //Motor Turn Constants
    public static final double speedTurn_FULL   = 0;
    public static final double speedTurn_NORMAL = 0;
    public static final double speedTurn_SLOW   = 0;

    //Timeout Constants
    public static final double tout_SHORT  = 3;
    public static final double tout_MEDIUM = 5;
    public static final double tout_LONG   = 10;

    //Encoder Constants
    public static final double COUNTS_PER_INCHES = 77.143;

    //Other Constants
    public static final int topExit = 1;
    public static final int middleExit = 0;
    public static final int bottomExit = -1;

    //Tesnor Flow & related items
    protected static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    protected static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    protected static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    public static final String VUFORIA_KEY = "AUYFfMb/////AAAAGeK9R/Mswk3ko4WgwY69fsB3D/KziaC/ZBui6bKvAUjjnhKoPyiDs0+TfVP3vMkYQ4Q0Amo4yosMAH9Xs0k+HX5MHGkhFbGLrDYj5zUN8NinByqcruRQZJuuISEHn1TfD5Fpa9psUmylGexAIwVB6WMfYTL2eKg4EE5mAaRsPgRKZnk/SjMzitYtthDxFusHftOK0N8vywIVSX79mBGmdy6+XUqLLa72zYXUvCrs9lov+xGuC06dUrmpFHl7uwt75QBVb5qyvbsruC4Bfnezzz1S747xiTHQz7Q86q1ZCix2V3AmxQxUuqhlYXDiC6uBseB3npuzuRtNxyCpn6+p3L1qv+Y1axec01BAOUATpSvy";

    public static final int leftPosition = 0;
    public static final int centerPosition = 1;
    public static final int rightPosition = 2;

    protected VuforiaLocalizer vuforia;
    protected TFObjectDetector tfod;

    //Vars for later

    //Time counting variable for timeout
    private ElapsedTime runtime = new ElapsedTime();

    //Declare motors and sensors
    HardwarePushbot_Nick robot = new HardwarePushbot_Nick();
    SensorStartup sensors = new SensorStartup();

    //Declare inner classes
    transform transform = new transform();
    rotate rotate = new rotate();
    colorSensor colorSensor = new colorSensor();

    enum GoldPosition {
        left, center, right, unknown
    }


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

    protected void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    protected void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public GoldPosition detectMineralPosition(double waitMiliseconds) {
        int grainSize = 250;
        int left = 0;
        int center = 0;
        int right = 0;

        tfod.activate();
        runtime.reset();
        while ((runtime.milliseconds() < waitMiliseconds) && opModeIsActive()) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() == 3) {
                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                            left += 1;
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                            right += 1;
                        } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                            center += 1;
                        }
                    }
                }
                telemetry.update();
            }
            sleep(grainSize);
        }
        tfod.deactivate();
        if (left == 0 && center == 0 && right == 0) {
            return GoldPosition.unknown;
        } else
            if (left > center) {
                if (left > right) {
                    return GoldPosition.left;
                } else {
                    return GoldPosition.right;
                }
            } else {
                if (center > right) {
                    return GoldPosition.center;
                } else {
                    return GoldPosition.right;
                }
            }
    }

    public void latch() {
        robot.latchServo.setPosition(latch);
        sleep(500);
    }

    public void releaseLatch() {
        robot.latchServo.setPosition(releaseLatch);
        sleep(500);
    }

    public void LiftDown(double sTimeOut) {
        double reverseTime = 250; //Set a time interval to reverse the motor to stop the momentum
        runtime.reset(); //Reset time counter
        robot.liftMotor.setPower(-speed_FULL); // Set lift motor to full speed
        while(sensors.liftLimitTop.getState() && (runtime.seconds() <= sTimeOut) && opModeIsActive()) {
            //while the touch sensor is not presssed and have not reach the timeout
            // do nothing
            idle();
        }

        runtime.reset(); // Reset the time counter
        robot.liftMotor.setPower(0);

    }

    public void releaseToken(double msTimeOut) {
        runtime.reset();
        robot.armMotor.setPower(speed_FULL);
        while ((runtime.milliseconds() <= msTimeOut) && sensors.liftLimitTopArm.getState() && opModeIsActive()) {
            idle();
        }
        runtime.reset(); // Reset the time counter
        robot.armMotor.setPower(0);
    }

    private boolean isBetween(double startValue, double endValue, double initValue){
        return endValue > startValue ? initValue > startValue && initValue < endValue : initValue > endValue && initValue < startValue;
    }

    public class transform {
        private int delay = 750;
        private boolean forward = true;
        private boolean reverse = false;
        boolean leftFrontOrientation = true;
        boolean rightFrontOrientation = true;
        boolean leftRearOrientation = true;
        boolean rightRearOrientation = true;


        public void serverOrientation(boolean leftFront, boolean rightFront, boolean leftRear, boolean rightRear){
            leftFrontOrientation = leftFront;
            rightFrontOrientation = rightFront;
            leftRearOrientation = leftRear;
            rightRearOrientation = rightRear;
        }


        public void left() {
            double server = turnLeft;

            serverOrientation(forward, forward, forward, forward);
            setAngleInd(server, server, server, server);
        }

        public void right() {
            double server = turnRight;

            serverOrientation(forward, forward, forward, forward);
            setAngleInd(server, server, server, server);

        }

        public void straight() {
            double server = turnStraight;

            serverOrientation(forward, forward, forward, forward);
            setAngleInd(server, server, server, server);

        }

        public void leftNoHit(){

            serverOrientation(forward, forward, reverse, forward);
            setAngleInd(turnLeft, turnLeft, turnLeft ,turnRight);
        }

        public void rightNoHit() {

            serverOrientation(forward, forward, forward, reverse);
            setAngleInd(turnRight, turnRight, turnLeft, turnRight);
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
            sleep(delay);
        }

        private void setMotorPower(double power){
            if (leftFrontOrientation)
                robot.leftFrontMotor.setPower(power);
            else
                robot.leftFrontMotor.setPower(-power);

            if (rightFrontOrientation)
                robot.rightFrontMotor.setPower(power);
            else
                robot.rightFrontMotor.setPower(-power);

            if (leftRearOrientation)
                robot.leftRearMotor.setPower(power);
            else
                robot.leftRearMotor.setPower(-power);

            if (rightRearOrientation)
                robot.rightRearMotor.setPower(power);
            else
                robot.rightRearMotor.setPower(-power);


        }

        public void eDriveDistance(double speed, double distanceInches, double stimeout){

            double encoderDistance = distanceInches * COUNTS_PER_INCHES;
            eDrive(speed, encoderDistance, encoderDistance, encoderDistance, encoderDistance, stimeout);

        }

        public void driveDetectBallStop(double sensorCM, double power, int sTimeout) {
            double sensorDistance = 0;

            robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            runtime.reset();

            setMotorPower(power);

            sensorDistance = sensors.distanceSensor.getDistance(DistanceUnit.CM);
            while (opModeIsActive() && (!isBetween(0, sensorCM, sensorDistance))) {
                sensorDistance = sensors.distanceSensor.getDistance(DistanceUnit.CM);
                telemetry.addData("Target Max Distance", sensorCM);
                telemetry.addData("Current Distance", sensorDistance);
                telemetry.update();
                idle();
            }

            robot.leftFrontMotor.setPower(0);
            robot.rightFrontMotor.setPower(0);
            robot.leftRearMotor.setPower(0);
            robot.rightRearMotor.setPower(0);
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
            //robot.rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.leftRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.rightRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //Make targets for encoders
            int newLeftFrontTarget;
            int newRightFrontTarget;
            int newLeftRearTarget;
            int newRightRearTarget;


            if (opModeIsActive()){
                idle();

                newLeftFrontTarget = robot.leftFrontMotor.getCurrentPosition() + (int) (leftFrontEncoderTicks);
                //newRightFrontTarget = robot.rightFrontMotor.getCurrentPosition() + (int) (rightFrontEncoderTicks);
                //newLeftRearTarget = robot.leftRearMotor.getCurrentPosition() + (int) (leftRearEncoderTicks);
                //newRightRearTarget = robot.rightRearMotor.getCurrentPosition() + (int) (rightRearEncoderTicks);

                //Set the targets for the encoders
                robot.leftFrontMotor.setTargetPosition(newLeftFrontTarget);
                //robot.rightFrontMotor.setTargetPosition(newRightFrontTarget);
                //robot.leftRearMotor.setTargetPosition(newLeftRearTarget);
                //robot.rightRearMotor.setTargetPosition(newRightRearTarget);

                //Set the mode on encoders to run to position
                robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //robot.leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                //Reset the timeout and start motion
                runtime.reset();
                //power = Range.clip(Math.abs(power), 0.0, 1.0);
                robot.leftFrontMotor.setPower(Range.clip(Math.abs(power), 0.0, 1.0));
                robot.rightFrontMotor.setPower(power *0.8);
                robot.leftRearMotor.setPower(power *0.8);
                robot.rightRearMotor.setPower(power *0.8);

                idle();

                while ((opModeIsActive() && (runtime.seconds() < sTimeout)) && (robot.leftFrontMotor.isBusy()/* && robot.rightRearMotor.isBusy()  && robot.rightFrontMotor.isBusy() && robot.leftRearMotor.isBusy() */)) {
                    //View old autonomous if this does not work

                    //Display telemetry
                    telemetry.addData("Status", "eDrive");
                    telemetry.addData("Running to", "%7d", newLeftFrontTarget);
                    telemetry.addData("Running at", "%7d", robot.leftFrontMotor.getCurrentPosition());
                    /*
                    telemetry.addData("Running to", "%7d :%7d :%7d :%7d", newLeftFrontTarget, newRightFrontTarget, newLeftRearTarget, newRightRearTarget );
                    telemetry.addData("Running at", "%7d :%7d :%7d :%7d", robot.leftFrontMotor.getCurrentPosition(), robot.rightFrontMotor.getCurrentPosition(), robot.leftRearMotor.getCurrentPosition(), robot.rightRearMotor.getCurrentPosition());
                    */
                    //telemetry.addData("Runing Time Max", "%7d", runtime.milliseconds());
                    //telemetry.addData("Running Time At", "%7d", (sTimeout * 1000.));
                    telemetry.update();
                    idle();
                }

                //Turn off run to position mode on encoders
                // Turn off RUN_TO_POSITION
                robot.leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                idle();

                //Stop all motion
                robot.leftFrontMotor.setPower(0);
                robot.rightFrontMotor.setPower(0);
                robot.leftRearMotor.setPower(0);
                robot.rightRearMotor.setPower(0);

                robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                idle();


                robot.leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
        }
    }

    public class rotate {

    }

    public class colorSensor {
        public boolean isObjectGold() {
            float hsvValues[] = {0F, 0F, 0F};

            final float values[] = hsvValues;

            final double SCALE_FACTOR = 255;

            Color.RGBToHSV((int) (sensors.colorSensor.red() * SCALE_FACTOR),
                    (int) (sensors.colorSensor.green() * SCALE_FACTOR),
                    (int) (sensors.colorSensor.blue() * SCALE_FACTOR),
                    hsvValues);

            if (hsvValues[0]<100) {
                return Boolean.TRUE;
            }else {
                return Boolean.FALSE;
            }
        }
    }
}

