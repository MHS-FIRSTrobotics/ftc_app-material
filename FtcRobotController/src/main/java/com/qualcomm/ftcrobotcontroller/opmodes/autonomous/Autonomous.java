package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.support.annotation.NonNull;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.util.RobotLog;


/**
 * This class controls how the RunLevels are ran and how the are ran.
 * It provides a means of running <code>runLevel</code>s that does not affect how
 * the standard OpMode is ran.
 *
 * @author David S. FTC5395
 * @version 0.1.1
 */
public class Autonomous implements Runnable {
    public RobotState robotState;
    private RunLevel[] levels;

    private int initLevel;
    private int stopLevel;
    private int safeLevel;

    private boolean done;

    /**
     * Builds a Automouous object with a specified amount of <code>Runlevels</code>
     * and, associated with a specific <code>RobotState</code> object.
     *
     * @param amount the number of RunLevels to generate for operation
     * @param state a <code>RobotState</code> object to manage operations
     * @throws IllegalArgumentException If amount < 3
     * @throws NullPointerException If state argument is null
     * @since 0.1.0
     */
    public Autonomous(int amount, RobotState state) throws IllegalArgumentException, NullPointerException {
        if (amount < 3) {
            throw new IllegalArgumentException("States be less than three");
        }
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }

        levels = new RunLevel[amount - 1];

        setInitState(0);
        setSafeState(amount - 1);
        setFinishState(amount - 1);

        robotState = state;
        done = false;
    }

    /**
     * Builds an Autonomous object based on an array of <code>runLevel</code>s and a <code>RobotState
     * </code> object.
     * @param states An array of <code>runLevel</code>s
     * @param state A <code>RobotState</code> used to manage autonomous operations
     * @throws NullPointerException If either states or state is null.
     */
    public Autonomous(RunLevel[] states, RobotState state) throws NullPointerException {
        if (states == null) {
            throw new NullPointerException("States cannot be equal" +
                    " to nothing");
        }
        if (state == null) {
            throw new NullPointerException("state cannot be null");
        }

        // Set globals to their new values
        levels = states;
        robotState = state;
        done = false;

        // Sets the default RunLevels
        setInitState(0);
        setSafeState(states.length - 1);
        setFinishState(states.length - 1);
    }

    /**
     * Returns an array of <code>runLevel</code>s that is associated with <code>Autonomous</code>
     *
     * @return an array of <code>runLevel</code>
     * @since 0.1.0
     */
    @NonNull
    public synchronized RunLevel[] getRunLevels() {
        return levels;
    }

    /**
     * Changes the array of <code>runLevel</code>s for use by <code>Autonomous</code>
     *
     * @param newLevels <code>runLevel</code> array to use
     * @throws IllegalStateException the init level is the same as the stopping level
     * @since 0.1.0
     */
    public synchronized void setRunLevels(@NonNull RunLevel[] newLevels) throws IllegalStateException{
        if (initLevel != stopLevel) {
            throw new IllegalStateException("initLevel and stopLevel are the same! This is bad.");
        }

        //Set the new important levels
        setInitState(initLevel);
        setFinishState(stopLevel);
        setFinishState(safeLevel);

        //Check final state
        isValidState(initLevel);
        isValidState(stopLevel);
    }

    /**
     * Changes the <code>runLevel</code> array that <code>Autonomous</code> uses. This also provides
     * any opportunity to change the special levels used.
     * @param newLevels The <code>runLevel</code> array to use (replaces the old)
     * @param newInit The index of the init runLevel
     * @param newFinish The index of the stop runLevel
     * @param newSafe The index of the safe runLevel
     * @throws IllegalArgumentException Gets thrown when any index you specifed does not exist in
     *  your array
     *  @since 0.1.0
     */
    public synchronized void setRunLevels(@NonNull RunLevel[] newLevels, int newInit, int newFinish, int newSafe) throws IllegalArgumentException {
        if (!(newInit < newLevels.length && newInit >= 0)) {
            throw new IllegalArgumentException("Parameter newInit is invalid.");
        }
        if (!(newFinish < newLevels.length && newFinish >= 0)) {
            throw new IllegalArgumentException("Parameter newFinish is invalid.");
        }
        if (!(newSafe < newLevels.length && newSafe >= 0)) {
            throw new IllegalArgumentException("Parameter newSafe is invalid.");
        }

        initLevel = newInit;
        stopLevel = newFinish;
        safeLevel = newSafe;

        //Pass to main setRunLevels
        setRunLevels(newLevels);
    }

    /**
     * Changes the init level to use
     *
     * @param level The index of the init state in the current <code>runLevel</code> array
     * @throws IllegalArgumentException Thrown when the index is not valid
     * @throws IllegalStateException Thrown when the <code>runLevel</code> array is null.
     * @since 0.1.0
     */
    public synchronized void setInitState(int level) throws IllegalArgumentException, IllegalStateException {
        if (levels != null) {
            throw new IllegalStateException("All states are null.");
        }
        if (isValidLevelNum(level)) {
            initLevel = level;
        } else {
            throw new IllegalArgumentException("Level must be between 0 and" + levels.length);
        }
    }

    /**
     * Changes the stop level to use
     *
     * @param level The index of the stop state in the current <code>runLevel</code> array
     * @throws IllegalArgumentException Thrown when the index is not valid
     * @throws IllegalStateException Thrown when the <code>runLevel</code> array is null.
     * @since 0.1.0
     */
    public synchronized void setFinishState(int level) throws IllegalArgumentException, IllegalStateException {
        assert (levels != null);
        if (!isValidLevelNum(level)) {
            throw new IllegalArgumentException();
        }

        stopLevel = level;
    }

    public synchronized void setSafeState(int level) {
        assert (levels != null);
        if (!isValidLevelNum(level)) {
            throw new IllegalArgumentException();
        }

        safeLevel = level;
    }

    public int runLevel(int level) {
        if (!isValidLevelNum(level)) {
            throw new IllegalArgumentException();
        }

        int execReturn;
        try {
            execReturn = levels[level].execute();
        } catch (Exception ex) {
            levels[level].safe();
            throw ex;
        }

        //Log result
        RobotLog.i("runLevel " + level + " returned a status code of " + execReturn);

        //Check if zero has been returned, if so, continue
        if (execReturn == 0) {
            RobotLog.i("runLevel " + level + " succeeded.");
            return 0;

            // Zero was not returned
        } else {
            if (execReturn > 0) {
                RobotLog.w("runLevel " + level + " returned a status code of " + execReturn);
                int safe = levels[level].safe(execReturn);
            } else {
                RobotLog.e("runLevel " + level + " returned a status code of " + execReturn);
                if (!levels[level].safe()) {
                    throw new RuntimeException("Not safe to continue! This was declared by runLevel: " + level);
                }
            }
        }

        return execReturn;
    }

    public int runInitLevel() {
        int result = 0;
        try {
                result = runLevel(initLevel);
        } catch (IllegalArgumentException ex) {
            DbgLog.error("InitLevel has an illegal argument:" + ex.getMessage());
        }

        return result;
        // TODO: handle errors with init level
    }

    public int runFinishLevel() {
        int result = 0;
        try {
            result = runLevel(stopLevel);
        } catch (IllegalArgumentException ex) {
            DbgLog.error("InitLevel has an illegal argument:" + ex.getMessage());
        }

        // TODO: handle errors with stop level
        return result;
    }

    // Handle main execution

    public void start() {
        runLevel(initLevel);
    }

    public void run() {
        //Run everything between initLevel and stopLevel
        for (int i = initLevel + 1; i < stopLevel; i++) {
            try {
                runLevel(i);
            } catch (RuntimeException ex) {
                RobotLog.logStacktrace(ex);
                break;
            }
        }

        done = true;
    }

    public void close() {
        if (!isValidLevelNum(stopLevel)) {
            throw new IllegalStateException();
        }

        runLevel(stopLevel);
    }

    // Check object states

    public synchronized boolean isDone() {
        return done;
    }

    public boolean isValidState() {
        return (levels != null) &&
                (levels[stopLevel] != null) &&
                (levels[initLevel] != null);
    }

    public boolean isValidState(int level) {
        return (isValidState() &&
                isValidLevelNum(level) &&
                levels[level] != null);
    }

    public boolean isValidLevelNum(int level) {
        return (level < levels.length && level > 0);
    }
}
