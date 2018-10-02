package jmu.booze.energysave.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

import jmu.booze.energysave.DeviceListeners;

public class Device {

    private long serialNumber;
    private String name;
    private Double usage;
    private Random random;
    private double min;
    private double max;
    private Thread thread;
    private boolean deviceOn;
    private List<DeviceListeners> listeners;
    private double totalUsage;

    public Device( String name, long serialNumber, double min, double max) {
        this.name = name;
        random = new Random();
        totalUsage = 0;
        this.max = max;
        this.min = min;
        generateNewRandomUsage();
        thread = new Thread() {
            @Override
            public void run() {
                while ( true ) {
                    while (deviceOn) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                        if ( deviceOn ) {
                            System.out.println("Changed!");
                            totalUsage += generateNewRandomUsage();
                            notifyListeners();
                        }
                    }
                }
            }
        };
        deviceOn = true;
        this.serialNumber = serialNumber;
        listeners = new ArrayList<>();
        startThread();
    }

    /**
     * Never call more than once.
     */
    private void startThread() {
        thread.start();
    }

    private void resumeThread() {
        deviceOn = true;
    }

    private double generateNewRandomUsage() {
        usage = min + (max - min) * random.nextDouble();
        return usage;
    }

    public double getTotalUsage() {
        return totalUsage;
    }

    public Double getUsage() {
        return usage;
    }


    public boolean removeListener( DeviceListeners listeners ) {
        return this.listeners.remove(listeners);
    }

    public String getName() {
        return name;
    }

    private void notifyListeners() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            listeners.get(i).changeStatus();
        }
    }

    public void addListener( DeviceListeners deviceListener) {
        listeners.add(deviceListener);
    }

    public void toggle() {
        if ( deviceOn ) {
            deviceOn = false;
            usage = new Double(0.0);
            notifyListeners();
        } else {
            resumeThread();
        }
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public boolean isDeviceOn() {
        return deviceOn;
    }
}
