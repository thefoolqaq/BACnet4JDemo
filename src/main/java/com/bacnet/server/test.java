package com.bacnet.server;

import com.bacnet.util.BacnetUtils;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.RemoteObject;
import com.serotonin.bacnet4j.event.DeviceEventListener;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.exception.BACnetServiceException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.obj.AnalogInputObject;
import com.serotonin.bacnet4j.obj.AnalogOutputObject;
import com.serotonin.bacnet4j.obj.BACnetObject;
import com.serotonin.bacnet4j.service.Service;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.constructed.*;
import com.serotonin.bacnet4j.type.enumerated.*;
import com.serotonin.bacnet4j.type.notificationParameters.NotificationParameters;
import com.serotonin.bacnet4j.type.primitive.CharacterString;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.UnsignedInteger;
import com.serotonin.bacnet4j.util.RequestUtils;
import org.junit.Test;


/**
 * @author hwt
 * @date 2023/12/1
 */
public class test {

    public static void main(String[] args) {
        try {
            IpNetwork network = BacnetUtils.getIpNetwork("192.168.174.1", "255.255.255.0", 24, 47808);
            int localDeviceID = 10000 + (int) (Math.random() * 10000);
            network.enableBBMD();
            DefaultTransport transport = new DefaultTransport(network);
            LocalDevice localDevice = new LocalDevice(localDeviceID, transport);
            System.out.println("Local device is running with device id " + localDeviceID);
            new AnalogOutputObject(localDevice, 0, "outputValue", 20, EngineeringUnits.noUnits, Boolean.TRUE, 0);
            new AnalogInputObject(localDevice, 0, "inputValue", 10, EngineeringUnits.noUnits, Boolean.TRUE);
            localDevice.initialize();
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
//                    Encodable e = null;
//                    try {
//                        e = RequestUtils.sendReadPropertyAllowNull(localDevice, d, d.getObjectIdentifier(),
//                                PropertyIdentifier.objectList);
//                    } catch (BACnetException ex) {
//                        ex.printStackTrace();
//                    }
//                    System.out.println(e);
//
//                    System.out.println("DiscoveryTest iAmReceived");
                }

                @Override
                public boolean allowPropertyWrite(Address from, BACnetObject obj, PropertyValue pv) {
                    localDevice.writePropertyInternal(pv.getPropertyIdentifier(),pv.getValue());
                    System.out.println(obj+"的"+pv.getPropertyIdentifier()+"值被更改为"+pv.getValue());
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
                    System.out.println("5");
                }

                @Override
                public void synchronizeTime(Address from, DateTime dateTime, boolean utc) {
                    System.out.println("7");
                }

                @Override
                public void requestReceived(Address from, Service service) {
                    System.out.println(service.toString());
                }


            });
//            localDevice.withAPDUSegmentTimeout()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 创建设备-》 完成初始化 -》客户端操作设备-》服务端处理请求后响应


//    @Test
//    public void Test() throws Exception {
//        IpNetwork network = BacnetUtils.getIpNetwork("192.168.174.1", "255.255.255.0", 24, 47809);
//        int localDeviceID = 10000 + (int) (Math.random() * 10000);
//        network.enableBBMD();
//        DefaultTransport transport = new DefaultTransport(network);
//        LocalDevice localDevice1 = new LocalDevice(localDeviceID, transport);
//        System.out.println("Local device is running with device id " + localDeviceID);
//        new AnalogOutputObject(localDevice1, 0, "outputValue", 20, EngineeringUnits.noUnits, Boolean.TRUE, 0);
//        new AnalogInputObject(localDevice1, 0, "inputValue", 10, EngineeringUnits.noUnits, Boolean.TRUE);
//        localDevice1.initialize();Thread.sleep(60000);
//    }

}
