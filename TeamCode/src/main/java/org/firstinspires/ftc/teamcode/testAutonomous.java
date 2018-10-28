package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Test Auto.")
public class testAutonomous extends MetaAutomation {

    @Override
    public void runOpMode() {
        setupHardware();
        waitForStart();


        //Testing angles
        transform.left();
        sleep(2000);
        transform.right();
        sleep(2000);
        transform.straight();
        sleep(2000);

        //Testing driving
        transform.eDriveDistance(speed_NORMAL, 12 , 3);

    }
}
