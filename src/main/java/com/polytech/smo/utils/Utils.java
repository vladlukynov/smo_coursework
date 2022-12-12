package com.polytech.smo.utils;

import com.polytech.smo.devices.BufferDevice;
import com.polytech.smo.devices.ProcessingDevice;
import com.polytech.smo.events.Event;

import java.util.Comparator;

public class Utils {
    public static double getPoissonDistributionTime(double lambda) {
        return (-1 / lambda) * Math.log(Math.random());
    }

    public static double getNormalDistributionTime(double a, double b) {
        return Math.random() * (b - a) + a;
    }

    public static Comparator<Event> eventComparator = (o1, o2) -> {
        if (o1.getEventTime() > o2.getEventTime()) {
            return 1;
        } else if (o1.getEventTime() < o2.getEventTime()) {
            return -1;
        }
        return 0;
    };

    public static Comparator<ProcessingDevice> processingDevicePriorityComparator = (o1, o2) -> {
        if (o1.getDeviceId() > o2.getDeviceId()) {
            return 1;
        } else if (o1.getDeviceId() < o2.getDeviceId()) {
            return -1;
        }
        return 0;
    };

    public static Comparator<BufferDevice> bufferDevicePriorityComparator = (o1, o2) -> {
        if (o1.getDeviceId() > o2.getDeviceId()) {
            return 1;
        } else if (o1.getDeviceId() < o2.getDeviceId()) {
            return -1;
        }
        return 0;
    };

    public static Comparator<BufferDevice> bufferedEventTimeComparator = (o1, o2) -> {
        if (o1.getBufferedEvent().getEventTime() > o2.getBufferedEvent().getEventTime()) {
            return 1;
        } else if (o1.getBufferedEvent().getEventTime() < o2.getBufferedEvent().getEventTime()) {
            return -1;
        }
        return 0;
    };
}
