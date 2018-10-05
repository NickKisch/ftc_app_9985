package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by FIRSTUser on 9/30/2018.
 */

public class SensorStartup {
public DigitalChannel liftLimitTop = null;
public DigitalChannel liftLimitBottom = null;
HardwareMap hwMap = null;
public void init(HardwareMap ahwMap)
{
    hwMap = ahwMap;
    liftLimitTop = hwMap.get (DigitalChannel.class,"LiftLimit_Top");
    liftLimitBottom = hwMap.get (DigitalChannel.class,"LiftLimit_Bottom");

}
}
