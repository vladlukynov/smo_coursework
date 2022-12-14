package com.polytech.smo.devices;

import com.polytech.smo.events.Event;
import com.polytech.smo.table.TableBuffer;
import com.polytech.smo.table.TableEvent;
import com.polytech.smo.view.ModelingController;

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
        ModelingController.tableEvents.add(new TableEvent(event.getEventTime(),
                "Заявка " + event.getDeviceId() + "." + event.getCount() + " отправлена в буфер " + deviceId));
        ModelingController.tableBuffers.removeIf(device -> device.getDeviceId() == deviceId);
        ModelingController.tableBuffers.add(new TableBuffer(deviceId, "Ожидание заявки " +
                event.getDeviceId() + "." + event.getCount()));
    }

    public void freeAndBufferNewEvent(Event event) {
        ModelingController.tableBuffers.removeIf(device -> device.getDeviceId() == deviceId);
        ModelingController.tableBuffers.add(new TableBuffer(deviceId, "Свободен"));
        ModelingController.tableEvents.add(new TableEvent(event.getEventTime(),
                "Заявка " + bufferedEvent.getDeviceId() + "." + bufferedEvent.getCount() + " ушла в отказ"));
        bufferEvent(event);
    }

    public Event getEventAndFreeDevice(double currentTime) {
        ModelingController.tableBuffers.removeIf(device -> device.getDeviceId() == deviceId);
        ModelingController.tableBuffers.add(new TableBuffer(deviceId, "Свободен"));
        ModelingController.tableEvents.add(new TableEvent(currentTime, "Освобождение буфера " + deviceId));
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
