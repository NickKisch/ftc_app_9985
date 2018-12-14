package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

@Autonomous(name="TensorBox")
public class TensorBoxAutonomous extends MetaAutomation {


    @Override
    public void runOpMode(){
        setupHardware();
        initVuforia();
        initTfod();
        waitForStart();


    }
}
