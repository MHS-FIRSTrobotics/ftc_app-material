package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by David on 7/9/2015.
 */
public interface RunLevel {
    int execute();

    boolean safe();

    int safe(int errorlevel);

    void close();
}
