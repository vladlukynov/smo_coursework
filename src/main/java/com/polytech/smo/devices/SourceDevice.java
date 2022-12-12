package com.polytech.smo.devices;

import com.polytech.smo.events.Event;
import com.polytech.smo.events.EventTypes;
import com.polytech.smo.utils.Utils;

import java.util.List;

public class SourceDevice {
    private final int deviceId;
    private double currentTime;
    private double nextGenerationTime;
    private final List<Event> events;
    private final double lambda;
    private int count;

    public SourceDevice(double lambda, List<Event> events, int deviceId) {
        currentTime = Utils.getPoissonDistributionTime(lambda);
        this.lambda = lambda;
        nextGenerationTime = currentTime + Utils.getPoissonDistributionTime(lambda);
        this.events = events;
        this.deviceId = deviceId;
        this.count = 0;
    }

    public void generateEvent() {
        count++;
        events.add(new Event(EventTypes.Generation, currentTime, deviceId, count));
        System.out.println("Сгенерирована заявка " + deviceId + "." + count + ", время: " + currentTime);
        currentTime = nextGenerationTime;
        nextGenerationTime += Utils.getPoissonDistributionTime(lambda);
    }

    public int getDeviceId() {
        return deviceId;
    }
}
