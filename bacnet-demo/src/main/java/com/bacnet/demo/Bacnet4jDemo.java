package com.bacnet.demo;


import com.bacnet.util.BacnetUtils;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.ServiceFuture;
import com.serotonin.bacnet4j.event.ReinitializeDeviceHandler;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.exception.PropertyValueException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.acknowledgement.AcknowledgementService;
import com.serotonin.bacnet4j.service.acknowledgement.ReadPropertyAck;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyRequest;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.Boolean;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.RequestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hwt
 * @date 2023/11/30
 */
public class Bacnet4jDemo {

    private static final String BIND_ADDRESS = "192.168.174.1";
    private static final String SUB_NET_ADDRESS = "255.255.255.0";
    private static final Integer NEWWORK_PREFIX_LENGTH = 24;
    private static final Integer PORT = 47808;
    static LocalDevice localDevice;

    IpNetwork ipNetwork = BacnetUtils.getIpNetwork(BIND_ADDRESS, SUB_NET_ADDRESS, NEWWORK_PREFIX_LENGTH, PORT);


    public Bacnet4jDemo() {
        localDevice = new LocalDevice(12345, new DefaultTransport(ipNetwork));
    }

    // 初始化
    public void init() throws Exception {
        localDevice.initialize();
    }


    //搜索远程设备
    public RemoteDevice funRemoteDevice(Integer instanceNumber) throws BACnetException {
        localDevice.startRemoteDeviceDiscovery();
        return localDevice.getRemoteDeviceBlocking(instanceNumber);
    }

    //搜索所有远程设备
    public List<RemoteDevice> funAllRemoteDevice()  {
        localDevice.startRemoteDeviceDiscovery();
        return localDevice.getRemoteDevices();
    }

    //获取设备所具有的所有标识符
    public void getAllObject(LocalDevice localDevice, RemoteDevice remoteDevice) throws BACnetException {
        List<ObjectIdentifier> objectList = RequestUtils.getObjectList(localDevice, remoteDevice).getValues();
        for (ObjectIdentifier oi : objectList) {
            System.out.println(oi.getObjectType().toString() + "," + oi.getInstanceNumber());
        }
    }

    // 获取设备某种类型的标识符数据并输出
    public void getObjcetType(LocalDevice localDevice, RemoteDevice remoteDevice, ObjectType type) throws BACnetException, PropertyValueException {
        List<ObjectIdentifier> objectList = RequestUtils.getObjectList(localDevice, remoteDevice).getValues();
        List<ObjectIdentifier> list = new ArrayList<>();
        for (ObjectIdentifier oi : objectList) {
            if (oi.getObjectType().equals(type)) {
                list.add(new ObjectIdentifier(type, oi.getInstanceNumber()));
            }
        }
        PropertyValues pvAiObjectName = BacnetUtils.readOiPropertiesValue(localDevice, remoteDevice, list, null, PropertyIdentifier.objectName);
        PropertyValues pvAiPresentValue = BacnetUtils.readOiPropertiesValue(localDevice, remoteDevice, list, null, PropertyIdentifier.presentValue);
        PropertyValues pvAiDescription = BacnetUtils.readOiPropertiesValue(localDevice, remoteDevice, list, null, PropertyIdentifier.description);
        System.out.println();
        System.out.println(objectList.get(0).getObjectType().toString() + ":" + objectList.get(0).getInstanceNumber());
        for (ObjectIdentifier oi : list) {
            System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " Name: " + pvAiObjectName.get(oi, PropertyIdentifier.objectName).toString());
            System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " PresentValue: " + pvAiPresentValue.get(oi, PropertyIdentifier.presentValue).toString());
            System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " Description: " + pvAiDescription.get(oi, PropertyIdentifier.description).toString());
        }
    }

    //读取设备数据
    public void read(List<RemoteDevice> remoteDevices,ObjectType type) throws BACnetException, PropertyValueException {
        for (RemoteDevice remoteDevice : remoteDevices) {
            System.out.println("<=============================>");
            //Object所有标识符
            System.out.println("设备所有标识符:");
           // getAllObject(localDevice, remoteDevice);
            getObjcetType(localDevice, remoteDevice, type);
        }
    }

    //修改设备数据
    public void write(RemoteDevice remoteDevice,ObjectType type,int ObjectTypeNum,int value) throws BACnetException {
        ObjectIdentifier oi = new ObjectIdentifier(type, ObjectTypeNum);
        RequestUtils.writeProperty(localDevice, remoteDevice, oi, PropertyIdentifier.outOfService, Boolean.FALSE);
        RequestUtils.writePresentValue(localDevice,remoteDevice,oi,new Real(value));
        System.out.println("修改成功！");
    }



    public static void main (String[]args){
        try {
            Bacnet4jDemo demo = new Bacnet4jDemo();
            demo.init();
            /*读操作*/
//            while(true) {
//                demo.read(demo.funAllRemoteDevice(), ObjectType.analogOutput);
//                Thread.sleep(1000);
//            }
            /*写操作*/
            RemoteDevice remoteDevice = demo.funRemoteDevice(15271);
            demo.write(remoteDevice,ObjectType.analogOutput,0,88);



            //        // 创建网络对象，设置本地绑定地址、子网掩码、端口等参数
//        IpNetwork network = BacnetUtils.getIpNetwork(BIND_ADDRESS, SUB_NET_ADDRESS, NETWORK_PREFIX_LENGTH, PORT);
//        // 创建虚拟的本地设备，deviceNumber不能重复
//        localDevice = new LocalDevice(10000 + (int) (Math.random() * 10000),new DefaultTransport(network));
//        try {
//
//            localDevice.initialize();
//            localDevice.startRemoteDeviceDiscovery();
//            remoteDevice = localDevice.getRemoteDeviceBlocking(12050);
//            ObjectIdentifier oi = new ObjectIdentifier(ObjectType.analogOutput, 0);
//            ReadPropertyRequest request = new ReadPropertyRequest(oi, PropertyIdentifier.presentValue);
//           AcknowledgementService acknowledgementService =
//            localDevice.send(remoteDevice.getAddress(), request).get();
//            System.out.println(acknowledgementService);
//            RequestUtils.writeProperty(localDevice, remoteDevice, oi, PropertyIdentifier.outOfService, Boolean.FALSE);
//            RequestUtils.writePresentValue(localDevice,remoteDevice,oi,new Real(35));
//            Thread.sleep(200000);
//        }
//        finally {
//            if (remoteDevice != null){
//            localDevice.send(remoteDevice, new SubscribeCOVRequest(new UnsignedInteger(1), aoid, null, null));
//            localDevice.terminate();}
//        }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            localDevice.terminate();
        }
    }






}
