package com.automation.ryder.controller.configuration;

import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class DockerController {

    private ReportController reportController;
    public DockerController(ReportController reportController) {
        this.reportController=reportController;
    }
    public void startDocker() {
        if (!isAlive()) {
            reportController.write(LogLevel.INFO, "Trying to start Docker container " + getContainerId());
            runCommand("docker start " + getContainerId());

            long startTime = System.currentTimeMillis() / 1000;
            while (!isAlive() && (startTime + 15 > System.currentTimeMillis() / 1000)) {
                reportController.write(LogLevel.INFO, "Waiting for Docker container " + getContainerId() + " to start");
            }
        } else {
            reportController.write(LogLevel.INFO, "Docker container " + getContainerId() + " is already started");
        }
    }

    public void stopDocker() {
        if (isAlive()) {
            reportController.write(LogLevel.INFO, "Trying to stop Docker container " + getContainerId());
            runCommand("docker stop " + getContainerId());

            long startTime = System.currentTimeMillis() / 1000;
            while (isAlive() && (startTime + 15 > System.currentTimeMillis() / 1000)) {
                reportController.write(LogLevel.INFO, "Waiting for Docker container " + getContainerId() + " to stop");
            }
        } else {
            reportController.write(LogLevel.INFO, "Docker container " + getContainerId() + " is already stopped");
        }
    }

    public boolean isAlive() {
        return readCommandResult(runCommand("docker inspect -f {{.State.Running}} " + getContainerId()));
    }

    private static Process runCommand(String command) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    private boolean readCommandResult(Process proc) {
        String s;
        boolean result = false;
        try {
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))
                    result = Boolean.parseBoolean(s);
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                reportController.write(LogLevel.INFO, s);
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getContainerId() {
        return "selenium-" + EnvironmentFactory.getBrowsingDevice().toLowerCase();
    }
}
