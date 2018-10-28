package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by FIRSTUser on 9/30/2018.
 */

public class SensorStartup {
public DigitalChannel liftLimitTop = null;
public DigitalChannel liftLimitBottom = null;
public DigitalChannel liftLimitBottomArm = null;
public DigitalChannel liftLimitTopArm = null;
HardwareMap hwMap = null;
public void init(HardwareMap ahwMap)
{
    hwMap = ahwMap;
    liftLimitTop = hwMap.get (DigitalChannel.class,"LiftLimit_Top");
    liftLimitBottom = hwMap.get (DigitalChannel.class,"LiftLimit_Bottom");
    liftLimitBottomArm = hwMap.get(DigitalChannel.class, "LiftLimit_BottomArm");
    liftLimitTopArm = hwMap.get(DigitalChannel.class, "liftLimit_TopArm");

}
}
