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

        LiftDown(6);
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
                transform.eDriveDistance(speed_NORMAL, 2 , 5);
                transform.driveDetectBallStop(19, -speed_SLOW, 0);
                transform.setAngleAll(-75);
            }
            transform.driveDetectBallStop(5 , speed_SLOW, 5);
            sleep(1000);
            gold = colorSensor.isObjectGold();
            transform.setAngleAll(75);
            transform.eDriveDistance(speed_NORMAL, 6, 5);
            i++;
        }





        sleep(10000);


    }

}
