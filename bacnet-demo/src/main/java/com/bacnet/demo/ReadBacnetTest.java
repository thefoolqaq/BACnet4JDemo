package com.bacnet.demo;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkBuilder;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyReferences;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.ReadListener;
import com.serotonin.bacnet4j.util.RequestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hwt
 * @date 2023/12/7
 */
public class ReadBacnetTest {

    static LocalDevice localDevice;

    private static final Integer REMOTE_DEVICE_ID = 16999;

    public static void main(String[] args) throws Exception {
        try {
            //创建网络对象
            IpNetwork ipNetwork = new IpNetworkBuilder()
                    //本机的ip
                    .withLocalBindAddress("192.168.1.90")
                    //掩码和长度，如果不知道本机的掩码和长度的话，可以使用代码的工具类IpNetworkUtils获取
                    .withSubnet("255.255.255.0", 24)
                    //默认的UDP端口
                    .withPort(47808)
                    .withReuseAddress(true)
                    .build();
            //创建虚拟的本地设备，deviceNumber随意
            localDevice = new LocalDevice(123, new DefaultTransport(ipNetwork));
            //初始化本地设备
            localDevice.initialize();
            //搜寻网段内远程设备
            localDevice.startRemoteDeviceDiscovery();

            //获取远程设备，instanceNumber是远程设备ID
            RemoteDevice remoteDevice = localDevice.getRemoteDeviceBlocking(REMOTE_DEVICE_ID);

            //获取远程设备的标识符对象
            List<ObjectIdentifier> objectList =  RequestUtils.getObjectList(localDevice, remoteDevice).getValues();

            List<ObjectIdentifier> aiList = new ArrayList<>();
            List<ObjectIdentifier> avList = new ArrayList<>();

            System.out.println("<===================对象标识符的对象类型，实例数(下标)===================>");

            //Object所有标识符
            for(ObjectIdentifier oi : objectList){

                System.out.println(oi.getObjectType().toString() + "," + oi.getInstanceNumber());

                /**
                 * ObjectIdentifier对象的不同类型的数据，进行过滤
                 * 此例子只列举了测试软件中存在的部分类型
                 * 具体业务时，根据需求，取对应类型的对象即可
                 * 对象类型(ObjectType)：
                 *      analog-input、analog-value、binary-value、character-string-value、multi-state-value、...
                 * 对象属性标识符类型(PropertyIdentifier)：
                 *      objectName、presentValue、description、eventState、units、...
                 */

                //analog-input
                if(oi.getObjectType().equals(ObjectType.analogInput)){
                    aiList.add(new ObjectIdentifier(ObjectType.analogInput, oi.getInstanceNumber()));
                }
                //analog-value
                if(oi.getObjectType().equals(ObjectType.analogValue)){
                    avList.add(new ObjectIdentifier(ObjectType.analogValue, oi.getInstanceNumber()));
                }
                //....
            }
            System.out.println("<==================================================================>");

            System.out.println("取值开始！！！================>");
            //根据对象属性标识符的类型进行取值操作 [测试工具模拟的设备点位的属性有objectName、description、present-value等等]
            //analog-input
            PropertyValues pvAiObjectName = readValueByPropertyIdentifier(localDevice, remoteDevice, aiList, null, PropertyIdentifier.objectName);
            PropertyValues pvAiPresentValue = readValueByPropertyIdentifier(localDevice, remoteDevice, aiList, null, PropertyIdentifier.presentValue);
            PropertyValues pvAiDescription = readValueByPropertyIdentifier(localDevice, remoteDevice, aiList, null, PropertyIdentifier.description);
            for (ObjectIdentifier oi : aiList){
                //取出点位对象不同类型分别对应的值
                System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " Name: " + pvAiObjectName.get(oi, PropertyIdentifier.objectName).toString());
                System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " PresentValue: " + pvAiPresentValue.get(oi, PropertyIdentifier.presentValue).toString());
                System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " Description: " + pvAiDescription.get(oi, PropertyIdentifier.description).toString());
            }

            //analog-value 同上，只是对象属性标识符列表不同List
            PropertyValues pvAvPresentValue = readValueByPropertyIdentifier(localDevice, remoteDevice, avList, null, PropertyIdentifier.presentValue);
            PropertyValues pvAvDescription = readValueByPropertyIdentifier(localDevice, remoteDevice, avList, null, PropertyIdentifier.description);
            for (ObjectIdentifier oi : avList){
                System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " PresentValue: " + pvAvPresentValue.get(oi, PropertyIdentifier.presentValue).toString());
                System.out.println(oi.getObjectType().toString() + " " + oi.getInstanceNumber() + " Description: " + pvAvDescription.get(oi, PropertyIdentifier.description).toString());
            }
            System.out.println("================>取值结束！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != localDevice){
                //shutdown
                localDevice.terminate();
            }
        }
    }

    /**
     * 读取远程设备的属性值 根据属性标识符
     * @param localDevice 本地设备
     * @param d 远程设备
     * @param ois 对象标识符列表
     * @param callback 回调
     * @param propertyIdentifier 属性标识符
     * @return 某一类属性的值
     * @throws BACnetException Exception
     */
    public static PropertyValues readValueByPropertyIdentifier(final LocalDevice localDevice, final RemoteDevice d,
                                                               final List<ObjectIdentifier> ois, final ReadListener callback, PropertyIdentifier propertyIdentifier) throws BACnetException, BACnetException {
        if (ois.size() == 0) {
            return new PropertyValues();
        }

        final PropertyReferences refs = new PropertyReferences();
        for (final ObjectIdentifier oid : ois) {
            refs.add(oid, propertyIdentifier);
        }

        return RequestUtils.readProperties(localDevice, d, refs, false, callback);
    }
}
