package jmu.booze.energysave.model;

import java.util.ArrayList;
import java.util.Iterator;

import jmu.booze.energysave.DeviceListeners;

public class DeviceCollection extends ArrayList<Device> {

    private static DeviceCollection collection = new DeviceCollection();

    private DeviceCollection() {
        super();
    }

    public static DeviceCollection getInstance() {
        return collection;
    }

    public void addListenerToAllDevices( DeviceListeners listener ) {
        Iterator<Device> i = this.iterator();
        while (i.hasNext()) {
            i.next().addListener(listener);
        }
    }
}
