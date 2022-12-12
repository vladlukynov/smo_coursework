package com.polytech.smo.devices;

import com.polytech.smo.events.Event;
import com.polytech.smo.events.EventTypes;
import com.polytech.smo.utils.Utils;

import java.util.List;

public class ProcessingDevice {
    private final int deviceId;
    private Event processingEvent;
    private final List<Event> events;
    private boolean isProcessing;
    private final double a;
    private final double b;

    public ProcessingDevice(int deviceId, double a, double b, List<Event> events) {
        this.deviceId = deviceId;
        this.isProcessing = false;
        this.events = events;
        this.a = a;
        this.b = b;
        this.processingEvent = null;
    }

    public void setEventOnProcess(Event event) {
        isProcessing = true;
        processingEvent = event;
        double endProcessingTime = event.getEventTime() + Utils.getNormalDistributionTime(a, b);
        events.add(new Event(EventTypes.EndProcessing, endProcessingTime, event.getDeviceId(), event.getCount()));
        System.out.println("Заявка " + event.getDeviceId() + "." + event.getCount() + " установлена на прибор " + deviceId
                + ", время окончания: " + endProcessingTime);
    }

    public void free() {
        System.out.println("Освобождение прибора " + deviceId);
        isProcessing = false;
        processingEvent = null;
    }

    public boolean isFree() {
        return !isProcessing;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public Event getProcessingEvent() {
        return processingEvent;
    }
}
