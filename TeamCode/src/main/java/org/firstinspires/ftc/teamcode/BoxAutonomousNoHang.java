package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="Box NO HANG")
public class BoxAutonomousNoHang extends MetaAutomation {

    //Declare variables exclusily used for this autnomous mode only!

    @Override
    public void runOpMode() throws InterruptedException{
        //Init the autonomous
        setupHardware();
        waitForStart();

        //Add autonomous code here

        transform.right();
        transform.eDriveDistance(speed_NORMAL, 2,2);
        transform.straight();
        transform.eDriveDistance(-speed_NORMAL, -16.5, 10);

        transform.rightNoHit();
        transform.driveDetectBallStop(18,speed_SLOW,0);
        transform.straight();
        transform.driveDetectBallStop(7,-speed_SLOW,0);
        boolean gold = false;
        sleep(300);
        gold = colorSensor.isObjectGold();
        int exit = topExit;

        if (!gold) {
            transform.leftNoHit();
            transform.eDriveDistance(speed_NORMAL, 3, 5);
            transform.driveDetectBallStop(20, speed_SLOW, 10);
            transform.straight();
            transform.driveDetectBallStop(7, -speed_SLOW, 10);
            sleep(300);
            gold = colorSensor.isObjectGold();
            exit = middleExit;

            if (!gold) {
                transform.leftNoHit();
                transform.eDriveDistance(speed_NORMAL, 3, 10);
                transform.driveDetectBallStop(20, speed_SLOW, 10);
                transform.straight();
                transform.driveDetectBallStop(7, -speed_SLOW, 10);
                exit = bottomExit;
            }
        }

        transform.straight();
        transform.eDriveDistance(-speed_NORMAL, -10, 5);

        switch(exit) {
            case topExit:
                transform.eDriveDistance(-speed_NORMAL, -20, 2);
                transform.setAngleAll(turn_HalfLeft);
                transform.eDriveDistance(-speed_NORMAL, -6, 2);
                break;

            case middleExit:
                transform.eDriveDistance(-speed_NORMAL, -24, 5);
                transform.right();
                transform.eDriveDistance(speed_NORMAL, 6, 5);
                break;

            case bottomExit:
                transform.eDriveDistance(-speed_NORMAL, -10, 2);
                transform.right();
                transform.eDriveDistance(speed_NORMAL, 18, 2);
                break;
        }

        releaseToken(500);
        transform.straight();


    }

}
