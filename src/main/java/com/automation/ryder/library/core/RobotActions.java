package com.automation.ryder.library.core;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * @author nauman.shahid

 * simulates hardware actions
 */

public final class RobotActions {
    Robot robot;

    public RobotActions() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Simulates hardware mouse actions according to required x-axis and y-axis
     */
    public void mouseClick(int x, int y) throws AWTException {
        int mask = InputEvent.BUTTON1_DOWN_MASK;
        robot.mouseMove(x, y);
        robot.mousePress(mask);
        robot.mouseRelease(mask);
    }

    /**
     * Simulates key board Esc
     */
    public void escape() throws AWTException {
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    /**
     * Simulates key board Enter
     */
    public void enter() throws AWTException {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * Simulates key board Control key press
     */
    public void ctrl_Press() throws AWTException {
        robot.keyPress(KeyEvent.VK_CONTROL);
    }

    /**
     * Simulates key board Control key release
     */
    public void ctrl_Release() throws AWTException {
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    /**
     * Simulates key board Control key release
     */
    public void ctrl_Save() throws AWTException {
        ctrl_Press();
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        ctrl_Release();
    }

    /**
     * Simulates key board Control key release
     */
    public void copy() throws AWTException {
        ctrl_Press();
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        ctrl_Release();
    }

    /**
     * Simulates key board Control key rel
     */
    public void paste() throws AWTException {
        ctrl_Press();
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        ctrl_Release();
    }

    public Robot getRobot() {
        return robot;
    }
}
