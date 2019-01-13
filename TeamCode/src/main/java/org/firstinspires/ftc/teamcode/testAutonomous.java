package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous(name="Test Auto.")
public class testAutonomous extends MetaAutomation {

    enum GoldView {
        ONE, TWO, OUT
    }

    @Override
    public void runOpMode() {
        initVuforia();
        initTfod();
        tfod.activate();
        while (!isStarted() && !isStopRequested()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    if (updatedRecognitions.size() == 2) {

                        int mineral1 = 0;
                        int mineral2 = 0;
                        int mineral3 = 0;

                        for (Recognition recognition : updatedRecognitions) {
                            int imageWidth = recognition.getImageWidth();
                            int sectorLine1 = (imageWidth/3);
                            int halfLine = (imageWidth/2);
                            int sectorLine2 = ((imageWidth/3)*2);
                            int mineralPosition = (int) recognition.getLeft();
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {

                                if (mineralPosition < sectorLine1 ) {
                                    mineral1 = 1;
                                    telemetry.addData("Gold Mineral Detected", "Left");
                                } else if (mineralPosition < sectorLine2) {
                                    mineral2 = 1;
                                    telemetry.addData("Gold Mineral Detected", "Center");
                                } else {
                                    mineral3 = 1;
                                    telemetry.addData("Gold Mineral Detected", "Right");
                                }
                            } else {
                                if (mineralPosition < sectorLine1) {
                                    mineral1 = -1;
                                    telemetry.addData("Silver Mineral Detected", "Left");
                                } else if (mineralPosition < sectorLine2) {
                                    mineral2 = -1;
                                    telemetry.addData("Silver Mineral Detected", "Center");
                                } else {
                                    mineral3 = -1;
                                    telemetry.addData("Silver Mineral Detected", "Right");
                                }
                            }
                        }
                        if (mineral1 > mineral2) {
                            if (mineral1 > mineral3) {
                                telemetry.addData("Gold Mineral Position", "Left");
                            } else {
                                telemetry.addData("Gold Mineral Position", "Right");
                            }
                        } else if (mineral2 > mineral3) {
                            telemetry.addData("Gold Mineral Position", "Center");
                        } else {
                            telemetry.addData("Gold Mineral Position", "Right");
                        }
                    }
                    telemetry.update();
                }
            }
        }
        tfod.deactivate();
        tfod.shutdown();

    }
}
