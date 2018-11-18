package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="Box")
public class BoxAutonomous extends MetaAutomation {

    //Declare variables exclusily used for this autnomous mode only!

    @Override
    public void runOpMode() throws InterruptedException{
        //Init the autonomous
        setupHardware();
        waitForStart();

        //Add autonomous code here


        LiftDown(10);
        releaseLatch();

        boolean gold = false;
        transform.driveDetectBallStop(10, speed_NORMAL, 10);
        sleep(750);
        gold = colorSensor.isObjectGold();
        int exit = middleExit;

        if (!gold) {
            transform.right();
            transform.eDriveDistance(speed_SLOW, 2, 5);
            transform.driveDetectBallStop(10, speed_SLOW, 10);
            sleep(750);
            gold = colorSensor.isObjectGold();
            exit = topExit;

            if (!gold) {
                transform.left();
                transform.eDriveDistance(speed_SLOW, 2, 10);
                transform.driveDetectBallStop(10, speed_SLOW, 10);
                transform.eDriveDistance(speed_SLOW, 2, 10);
                transform.driveDetectBallStop(10, speed_SLOW, 10);
                exit = bottomExit;
            }
        }

        transform.straight();
        transform.eDriveDistance(-speed_NORMAL, -10, 5);

        switch(exit) {
            case topExit:
                transform.eDriveDistance(-speed_NORMAL, -2, 2);
                transform.left();
                transform.eDriveDistance(speed_NORMAL, 20, 2);
                break;

            case middleExit:
                transform.eDriveDistance(speed_NORMAL, 27, 5);
                break;

            case bottomExit:
                transform.eDriveDistance(-speed_NORMAL, -2, 2);
                transform.right();
                transform.eDriveDistance(speed_NORMAL, 20, 2);
                break;
        }

        releaseToken(300);
        latch();

    }

}
