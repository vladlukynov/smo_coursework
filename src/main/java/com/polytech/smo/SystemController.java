package com.polytech.smo;

import com.polytech.smo.devices.BufferDevice;
import com.polytech.smo.devices.ProcessingDevice;
import com.polytech.smo.devices.SourceDevice;
import com.polytech.smo.events.Event;
import com.polytech.smo.events.EventTypes;
import com.polytech.smo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SystemController {
    private final List<SourceDevice> sourceDevices;
    private final List<BufferDevice> bufferDevices;
    private final List<ProcessingDevice> processingDevices;
    private final List<Event> events;

    public SystemController(int sourceDevicesCount, int bufferDevicesCount, int processingDevicesCount,
                            double lambda, double a, double b) {
        this.sourceDevices = new ArrayList<>();
        this.bufferDevices = new ArrayList<>();
        this.processingDevices = new ArrayList<>();
        this.events = new ArrayList<>();

        generateSourceDevices(sourceDevicesCount, lambda, events);
        generateBufferDevices(bufferDevicesCount, events);
        generateProcessingDevices(processingDevicesCount, a, b, events);

        generateFirstEvents();
    }

    public void userClickNextButton() {
        events.sort(Utils.eventComparator);
        Event event = popEvent();

        if (event.getEventType() == EventTypes.EndProcessing) {
            Optional<ProcessingDevice> processingDevice = processingDevices.stream()
                    .filter(device -> device.getProcessingEvent().equals(event)).findFirst();
            processingDevice.ifPresent(ProcessingDevice::free);

            Optional<BufferDevice> bufferDevice = bufferDevices.stream().filter(BufferDevice::isBuffered_)
                    .max(Utils.bufferedEventTimeComparator);
            if (bufferDevice.isPresent()) {
                Event bufferedEvent = bufferDevice.get().getEventAndFreeDevice();
                processingDevice.ifPresent(device -> device.setEventOnProcess(bufferedEvent));
                return;
            }

            return;
        }

        if (event.getEventType() == EventTypes.Generation) {
            generateEvent(event.getDeviceId());

            Optional<ProcessingDevice> processingDevice = processingDevices.stream().filter(ProcessingDevice::isFree)
                    .min(Utils.processingDevicePriorityComparator);

            if (processingDevice.isPresent()) {
                processingDevice.get().setEventOnProcess(event);
                return;
            }

            Optional<BufferDevice> bufferDevice = bufferDevices.stream().filter(BufferDevice::isFree)
                    .min(Utils.bufferDevicePriorityComparator);

            if (bufferDevice.isPresent()) {
                bufferDevice.get().bufferEvent(event);
                return;
            }

            BufferDevice oldestBufferDevice = findDeviceWithOldestBufferedEvent();
            oldestBufferDevice.freeAndBufferNewEvent(event);
        }
    }

    private void generateSourceDevices(int count, double lambda, List<Event> events) {
        for (int i = 0; i < count; i++) {
            sourceDevices.add(new SourceDevice(lambda, events, i + 1));
        }
    }

    private void generateBufferDevices(int count, List<Event> events) {
        for (int i = 0; i < count; i++) {
            bufferDevices.add(new BufferDevice(events, i + 1));
        }
    }

    private void generateProcessingDevices(int count, double a, double b, List<Event> events) {
        for (int i = 0; i < count; i++) {
            processingDevices.add(new ProcessingDevice(i + 1, a, b, events));
        }
    }

    private void generateFirstEvents() {
        for (SourceDevice sourceDevice : sourceDevices) {
            sourceDevice.generateEvent();
        }
    }

    private Event popEvent() {
        return events.remove(0);
    }

    private BufferDevice findDeviceWithOldestBufferedEvent() {
        Optional<BufferDevice> bufferDevice = bufferDevices.stream().min(Utils.bufferedEventTimeComparator);
        return bufferDevice.orElse(null);
    }

    private void generateEvent(int deviceId) {
        Optional<SourceDevice> sourceDevice = sourceDevices.stream().filter(device -> device.getDeviceId() == deviceId).findFirst();
        sourceDevice.ifPresent(SourceDevice::generateEvent);
    }
}
