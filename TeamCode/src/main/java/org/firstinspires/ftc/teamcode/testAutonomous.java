package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Test Auto.")
public class testAutonomous extends MetaAutomation {

    @Override
    public void runOpMode() {
        setupHardware();
        waitForStart();

        /*
        //Testing angles
        transform.left();
        sleep(2000);
        transform.right();
        sleep(2000);
        transform.straight();
        sleep(2000);
        */

        //Testing driving
        transform.straight();
        transform.eDriveDistance(speed_NORMAL, 12, 3);
        sleep(1000);
        transform.eDriveDistance(speed_SLOW, 12, 3);

    }
}
