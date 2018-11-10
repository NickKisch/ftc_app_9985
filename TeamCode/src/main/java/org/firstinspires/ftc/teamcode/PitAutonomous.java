package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="Pit")
public class PitAutonomous extends MetaAutomation {

    //Declare variables exclusily used for this autnomous mode only!

    @Override
    public void runOpMode() throws InterruptedException{
        //Init the autonomous
        setupHardware();
        waitForStart();

        //Add autonomous code here

        LiftDown(10);
        transform.straight();
        transform.eDriveDistance(speed_NORMAL, 2, 2);

        transform.right();
        transform.eDriveDistance(speed_NORMAL, 25, 5);

        transform.setAngleAll(turnLeft);

        transform.driveDetectBallStop(19, speed_SLOW, 0);
        transform.setAngleAll(-75);
        int i = 0;
        boolean gold = false;

        while ((i < 3) && !gold) {
            if (i != 0) {
                transform.straight();
                transform.eDriveDistance(-speed_SLOW, -4 , 5);
                transform.driveDetectBallStop(19, -speed_SLOW, 0);
                transform.right();
            }
            i++;
            transform.driveDetectBallStop(5 , speed_SLOW, 5);
            sleep(750);
            gold = colorSensor.isObjectGold();
            if (gold) {
                break;
            }



            transform.eDriveDistance(-speed_SLOW, -4, 5);
        }

        transform.right();
        transform.eDriveDistance(speed_NORMAL, 10, 5);


    }

}
