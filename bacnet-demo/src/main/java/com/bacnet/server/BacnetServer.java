package com.bacnet.server;

import com.bacnet.util.BacnetUtils;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.RemoteObject;
import com.serotonin.bacnet4j.event.DeviceEventListener;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.obj.AnalogInputObject;
import com.serotonin.bacnet4j.obj.AnalogOutputObject;
import com.serotonin.bacnet4j.obj.AnalogValueObject;
import com.serotonin.bacnet4j.obj.BACnetObject;
import com.serotonin.bacnet4j.service.Service;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.type.constructed.*;
import com.serotonin.bacnet4j.type.enumerated.*;
import com.serotonin.bacnet4j.type.notificationParameters.NotificationParameters;
import com.serotonin.bacnet4j.type.primitive.*;

public class BacnetServer {

    public BacnetServer() throws Exception {
        IpNetwork network = BacnetUtils.getIpNetwork("192.168.1.90", "255.255.255.0", 24, 47808);
        int localDeviceID = 11111;
        DefaultTransport transport = new DefaultTransport(network);
        LocalDevice localDevice = new LocalDevice(localDeviceID, transport);
        System.out.println("Local device is running with device id " + localDeviceID);
        localDevice.writePropertyInternal(PropertyIdentifier.objectName, new CharacterString("Bacnet4j Slave Device01"));
        localDevice.getEventHandler().addListener(new DeviceEventListener() {

            @Override
            public void textMessageReceived(ObjectIdentifier textMessageSourceDevice, Choice messageClass, MessagePriority messagePriority, CharacterString message) {
            }

            @Override
            public void listenerException(Throwable e) {
                System.out.println("1");
            }

            @Override
            public void iAmReceived(RemoteDevice d) {
//                Encodable e = null;
//                try {
//                    e = RequestUtils.sendReadPropertyAllowNull(localDevice, d, d.getObjectIdentifier(),
//                            PropertyIdentifier.objectList);
//                } catch (BACnetException ex) {
//                    ex.printStackTrace();
//                }
//                System.out.println(e);
//                System.out.println("DiscoveryTest iAmReceived");
            }

            @Override
            public boolean allowPropertyWrite(Address from, BACnetObject obj, PropertyValue pv) {
                localDevice.writePropertyInternal(pv.getPropertyIdentifier(), pv.getValue());
                System.out.println(obj + "的" + pv.getPropertyIdentifier() + "值被更改为" + pv.getValue());
                return true;
            }

            @Override
            public void propertyWritten(Address from, BACnetObject obj, PropertyValue pv) {
                System.out.println("--");
            }

            @Override
            public void iHaveReceived(RemoteDevice d, RemoteObject o) {
                System.out.println("3");
            }

            @Override
            public void covNotificationReceived(UnsignedInteger subscriberProcessIdentifier, ObjectIdentifier initiatingDeviceIdentifier, ObjectIdentifier monitoredObjectIdentifier, UnsignedInteger timeRemaining, SequenceOf<PropertyValue> listOfValues) {
                System.out.println("-----------------");
                System.out.println(listOfValues.toString());
                System.out.println("-----------------");
            }

            @Override
            public void eventNotificationReceived(UnsignedInteger processIdentifier, ObjectIdentifier initiatingDeviceIdentifier, ObjectIdentifier eventObjectIdentifier, TimeStamp timeStamp, UnsignedInteger notificationClass, UnsignedInteger priority, EventType eventType, CharacterString messageText, NotifyType notifyType, com.serotonin.bacnet4j.type.primitive.Boolean ackRequired, EventState fromState, EventState toState, NotificationParameters eventValues) {
                System.out.println(processIdentifier);
            }

            @Override
            public void synchronizeTime(Address from, DateTime dateTime, boolean utc) {
                System.out.println(dateTime);
            }

            @Override
            public void requestReceived(Address from, Service service) {
                System.out.println(service.toString());
            }

        });
        new AnalogOutputObject(localDevice, 0, "outputTest0", 20, EngineeringUnits.noUnits, java.lang.Boolean.TRUE, 0);
        new AnalogOutputObject(localDevice, 1, "outputTest1", 30, EngineeringUnits.noUnits, java.lang.Boolean.TRUE, 0);
        new AnalogInputObject(localDevice, 0, "inputTest0", 10, EngineeringUnits.noUnits, java.lang.Boolean.TRUE);
        new AnalogValueObject(localDevice, 0, "valueTest0", 25, EngineeringUnits.noUnits, java.lang.Boolean.TRUE);

        localDevice.initialize();
        localDevice.startRemoteDeviceDiscovery();
        localDevice.sendGlobalBroadcast(localDevice.getIAm());
//        DeviceCommunicationControlRequest.EnableDisable state = localDevice.getCommunicationControlState();
//        System.out.println(state);
//        ObjectIdentifier oi = new ObjectIdentifier(ObjectType.analogOutput, 0);
    }

    public static void main(String[] args) throws Exception {
        new BacnetServer();
    }


}

