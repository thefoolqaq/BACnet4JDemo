//package com.bacnet.util;
//
//import cn.hutool.core.util.StrUtil;
//import com.bacnet.domain.BacnetObject;
//import com.serotonin.bacnet4j.LocalDevice;
//import com.serotonin.bacnet4j.RemoteDevice;
//import com.serotonin.bacnet4j.type.constructed.SequenceOf;
//import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
//import com.serotonin.bacnet4j.util.PropertyReferences;
//import com.serotonin.bacnet4j.util.PropertyValues;
//import com.serotonin.bacnet4j.util.RequestUtils;
//import lombok.SneakyThrows;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Optional;
//import java.util.Set;
//
///**
// * bacnet点位对象操作工具类
// * @author hwt
// * @date 2023/11/30
// */
//public class BacnetObjectUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(BacnetObjectUtils.class);
//
//    @SneakyThrows
//    public static Set<BacnetObject> findObjects(LocalDevice localDevice, String localBindAddress, RemoteDevice remoteDevice){
//        BacnetCacheUtils.putRemoteDeviceCache(localBindAddress,remoteDevice);
//        SequenceOf<ObjectIdentifier> ids = RequestUtils.getObjectList(localDevice, remoteDevice);
//        PropertyReferences refs = new PropertyReferences();
//        for (ObjectIdentifier oid : ids) {
//            PropertiesUtils.addPropertyReferences(refs, oid);
//        }
//        return getBacnetObjects(localDevice,remoteDevice,refs);
//    }
//
//    public static Optional<BacnetObject> findObject(LocalDevice localDevice, String localBindAddress, RemoteDevice remoteDevice, ObjectIdentifier objectIdentifier){
//        BacnetCacheUtils.putRemoteDeviceCache(localBindAddress,remoteDevice);
//        PropertyReferences refs = new PropertyReferences();
//        PropertiesUtils.addPropertyReferences(refs, objectIdentifier);
//        Set<BacnetObject> bacnetObjects = getBacnetObjects(localDevice, remoteDevice, refs);
//        return bacnetObjects.stream().findFirst() ;
//    }
//
//
//    @SneakyThrows
//    private static Set<BacnetObject>  getBacnetObjects(LocalDevice localDevice, RemoteDevice d, PropertyReferences refs){
//        PropertyValues pvs = RequestUtils.readProperties(localDevice, d, refs, false, (v, i, objectIdentifier, propertyIdentifier, unsignedInteger, encodable) -> {
////            logger.error("{},{},{},{},{},{}",v,i,objectIdentifier,propertyIdentifier,unsignedInteger,encodable);
//            logger.error("{},{},{},{}",i,objectIdentifier,propertyIdentifier,encodable);
//            return false;
//        });
//        int instanceNumber = d.getInstanceNumber();
////        System.out.println("-------------------------------------------------------------");
////        System.out.println("------------------------设备号"+d.getInstanceNumber()+"的原始数据:"+pvs+"\n");
////        System.out.println("-------------------------------------------------------------");
//        String remoteId = StrUtil.toString(instanceNumber);
//        Set<BacnetObject> bacnetObjects = PropertiesUtils.convert2Set(remoteId, pvs);
//        BacnetCacheUtils.putBacnetObjectCache(remoteId,bacnetObjects);
//        return PropertiesUtils.convert2Set(remoteId, pvs);
//    }
//}
