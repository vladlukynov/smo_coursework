package com.polytech.smo.devices;

import com.polytech.smo.events.Event;

import java.util.List;

public class BufferDevice {
    private final int deviceId;
    private final List<Event> events;
    private Event bufferedEvent;
    private boolean isBuffered;

    public BufferDevice(List<Event> events, int deviceId) {
        this.events = events;
        this.deviceId = deviceId;
        this.bufferedEvent = null;
        this.isBuffered = false;
    }

    public void bufferEvent(Event event) {
        this.isBuffered = true;
        this.bufferedEvent = event;
        System.out.println("Заявка " + event.getDeviceId() + "." + event.getCount() + " отправлена в буфер " + deviceId);
    }

    public void freeAndBufferNewEvent(Event event) {
        System.out.println("Заявка " + bufferedEvent.getDeviceId() + "." + bufferedEvent.getCount() + " ушла в отказ, время генерации: " + bufferedEvent.getEventTime());
        bufferEvent(event);
    }

    public Event getEventAndFreeDevice() {
        this.isBuffered = false;
        Event event = this.bufferedEvent;
        this.bufferedEvent = null;
        return event;
    }

    public Event getBufferedEvent() {
        return bufferedEvent;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public boolean isFree() {
        return !isBuffered;
    }

    public boolean isBuffered_() {
        return isBuffered;
    }
}
