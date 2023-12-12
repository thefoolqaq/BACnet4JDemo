package com.bacnet.demo;

import com.bacnet.util.BacnetUtils;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.exception.PropertyValueException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.acknowledgement.ReadPropertyMultipleAck;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyMultipleRequest;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.type.constructed.*;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.Boolean;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * @author hwt
 * @date 2023/12/6
 */
public class SimpleClient {

    final Logger logger = LoggerFactory.getLogger(SimpleClient.class);

    static LocalDevice localDevice = null;
    private static final String BIND_ADDRESS = "192.168.1.90";
    private static final String SUB_NET_ADDRESS = "255.255.255.0";
    private static final Integer NETWORK_PREFIX_LENGTH = 24;
    private static final Integer PORT = 47808;


    //搜索远程设备
    public RemoteDevice funRemoteDevice(Integer instanceNumber) throws BACnetException {
        localDevice.startRemoteDeviceDiscovery();
        return localDevice.getRemoteDeviceBlocking(instanceNumber);
    }

    //搜索所有远程设备
    public List<RemoteDevice> funAllRemoteDevice() {
        localDevice.startRemoteDeviceDiscovery();
        List<RemoteDevice> remoteDevices = localDevice.getRemoteDevices();
        System.out.println(remoteDevices.size());
        return remoteDevices;
    }

    //获取设备所具有的所有标识符
    public Set<ObjectType> getAllObject(LocalDevice localDevice, RemoteDevice remoteDevice) throws BACnetException {
        List<ObjectIdentifier> objectList = RequestUtils.getObjectList(localDevice, remoteDevice).getValues();
        Set<ObjectType> objectTypeList = new HashSet<>();
        for (ObjectIdentifier oi : objectList) {
            objectTypeList.add(oi.getObjectType());
        }
        return objectTypeList;
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
        for (ObjectIdentifier oi : list) {
            System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " Name: " + pvAiObjectName.get(oi, PropertyIdentifier.objectName).toString());
            System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " PresentValue: " + pvAiPresentValue.get(oi, PropertyIdentifier.presentValue).toString());
            //System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " Description: " + pvAiDescription.get(oi, PropertyIdentifier.description).toString());
        }
    }

    //读取设备数据
    public void read(RemoteDevice remoteDevice, ObjectType type) throws BACnetException, PropertyValueException {
        if (type == null) {
            System.out.println("<=============================>");
            Set<ObjectType> types = getAllObject(localDevice, remoteDevice);
            for (ObjectType o : types) {
                getObjcetType(localDevice, remoteDevice, o);
            }
            System.out.println("<=============================>");
        } else {
            System.out.println("<=============================>");
            getObjcetType(localDevice, remoteDevice, type);
            System.out.println("<=============================>");
        }
    }


    //修改设备数据
    public void write(RemoteDevice remoteDevice, ObjectType type, int ObjectTypeNum, int value) throws BACnetException {
        ObjectIdentifier oi = new ObjectIdentifier(type, ObjectTypeNum);
        RequestUtils.writeProperty(localDevice, remoteDevice, oi, PropertyIdentifier.outOfService, Boolean.TRUE);
        RequestUtils.writePresentValue(localDevice, remoteDevice, oi, new Real(value));
        System.out.println("修改成功！");
    }


    public static void main(String[] args) throws Exception {

        SimpleClient client = new SimpleClient();
        //创建网络对象
        IpNetwork network = BacnetUtils.getIpNetwork(BIND_ADDRESS, SUB_NET_ADDRESS, NETWORK_PREFIX_LENGTH, PORT);
        // 创建虚拟的本地设备，deviceNumber不能重复
        localDevice = new LocalDevice(10000 + (int) (Math.random() * 10000), new DefaultTransport(network));
        //初始化本地设备
        localDevice.initialize();
        // 获取远程设备
        RemoteDevice remoteDevice = client.funRemoteDevice(11111);

        //获取所有远程设备
         // List<RemoteDevice> remoteDevices = client.funAllRemoteDevice();
//        List<RemoteDevice> r = new ArrayList<>();
//        r.add(remoteDevices);


        /*读取对象的名称以及实时值*/
//           while(true) {
//              client.read(remoteDevice, ObjectType.analogOutput);
//               Thread.sleep(5000);
//           }



        /*写操作*/
//        client.read(remoteDevice, ObjectType.analogInput);
//        client.write(remoteDevice, ObjectType.analogInput, 0, 20);
////        client.read(r, ObjectType.analogOutput);
////        client.write(remoteDevice, ObjectType.analogOutput, 0, 88);
//        client.read(remoteDevice, ObjectType.analogInput);


        // 读取设备所有对象属性
         client.readMultiple(localDevice, remoteDevice);


        // 动态创建对象失败，BACnet设备可能被配置为只接受预先定义的对象创建，而不支持动态创建。删除同理
//        CreateObjectRequest request = new CreateObjectRequest(ObjectType.binaryInput, new SequenceOf<>(
//                new PropertyValue(PropertyIdentifier.objectName, new CharacterString("新建的对象名称")),
//                new PropertyValue(PropertyIdentifier.objectIdentifier, new CharacterString("binary")),
//                new PropertyValue(PropertyIdentifier.objectType, new CharacterString("type")),
//                new PropertyValue(PropertyIdentifier.description, new CharacterString("这是我创建的新对象")),
//                new PropertyValue(PropertyIdentifier.presentValue, BinaryPV.forId(44))));
//        localDevice.send(remoteDevice,request).get();

    }


    // 读取对象的多个属性
    public void readMultiple(LocalDevice localDevice, RemoteDevice remoteDevice) throws BACnetException {
        List<ObjectIdentifier> objectList = RequestUtils.getObjectList(localDevice, remoteDevice).getValues();
        for (int i = 0; i < objectList.size(); i++) {
            List<PropertyReference> refs = new ArrayList<>();
            refs.add(new PropertyReference(PropertyIdentifier.all));
            List<ReadAccessSpecification> specs = new ArrayList<>();
            specs.add(new ReadAccessSpecification(objectList.get(i), new SequenceOf<>(refs)));
            ReadPropertyMultipleRequest request = new ReadPropertyMultipleRequest(new SequenceOf<>(specs));
            ReadPropertyMultipleAck ack = localDevice.send(remoteDevice, request).get();
            SequenceOf<ReadAccessResult> listOfReadAccessResults = ack.getListOfReadAccessResults();
            ReadAccessResult base1 = listOfReadAccessResults.getBase1(1);
            System.out.println("设备属性：" + base1.getObjectIdentifier());
            SequenceOf<ReadAccessResult.Result> listOfResults = base1.getListOfResults();
            List<ReadAccessResult.Result> results = listOfResults.getValues();
            for (ReadAccessResult.Result r : results) {
                System.out.println(r.getPropertyIdentifier() + ":" + r.getReadResult());
            }
            System.out.println("----------------------------------------------");
        }

    }


}



