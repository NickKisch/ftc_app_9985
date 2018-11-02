package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by FIRSTUser on 9/30/2018.
 */

public class SensorStartup {

    //Touch sensors
    public DigitalChannel liftLimitTop = null;
    public DigitalChannel liftLimitBottom = null;
    public DigitalChannel liftLimitBottomArm = null;
    public DigitalChannel liftLimitTopArm = null;

    //Color & Distance Sensor
    public ColorSensor                  colorSensor    = null;
    public ModernRoboticsI2cRangeSensor distanceSensor = null;

    HardwareMap hwMap = null;
    public void init(HardwareMap ahwMap)
    {
        hwMap = ahwMap;

        //Touch Sensors
        liftLimitTop = hwMap.get (DigitalChannel.class,"LiftLimit_Top");
        liftLimitBottom = hwMap.get (DigitalChannel.class,"LiftLimit_Bottom");
        liftLimitBottomArm = hwMap.get(DigitalChannel.class, "LiftLimit_BottomArm");
        liftLimitTopArm = hwMap.get(DigitalChannel.class, "liftLimit_TopArm");

        //Color & Distance sensor
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color_distance");
        distanceSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

    }
}
