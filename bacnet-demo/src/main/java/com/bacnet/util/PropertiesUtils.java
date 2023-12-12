//package com.bacnet.util;
//
//
//import cn.hutool.core.text.CharSequenceUtil;
//import cn.hutool.core.text.StrPool;
//import cn.hutool.core.util.ObjectUtil;
//import com.bacnet.domain.BacnetObject;
//import com.serotonin.bacnet4j.exception.PropertyValueException;
//import com.serotonin.bacnet4j.type.Encodable;
//import com.serotonin.bacnet4j.type.constructed.ObjectPropertyReference;
//import com.serotonin.bacnet4j.type.enumerated.ObjectType;
//import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
//import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
//import com.serotonin.bacnet4j.util.PropertyReferences;
//import com.serotonin.bacnet4j.util.PropertyValues;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Properties工具类
// *
// * @author hwt
// * @date 2023/11/30
// */
//public class PropertiesUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
//
//    /**
//     * 完善属性引用
//     *
//     * @param refs 属性引用
//     * @param oid  点位标识
//     */
//    public static void addPropertyReferences(PropertyReferences refs, ObjectIdentifier oid) {
//        refs.add(oid, PropertyIdentifier.objectName);
//        refs.add(oid, PropertyIdentifier.description);
//
//        ObjectType type = oid.getObjectType();
//        if (ObjectType.accumulator.equals(type)) {
//            refs.add(oid, PropertyIdentifier.units);
//        } else if (ObjectType.analogInput.equals(type) || ObjectType.analogOutput.equals(type)
//                || ObjectType.analogValue.equals(type) || ObjectType.pulseConverter.equals(type)) {
//            refs.add(oid, PropertyIdentifier.units);
//        } else if (ObjectType.binaryInput.equals(type) || ObjectType.binaryOutput.equals(type)
//                || ObjectType.binaryValue.equals(type)) {
//            refs.add(oid, PropertyIdentifier.inactiveText);
//            refs.add(oid, PropertyIdentifier.activeText);
//        } else if (ObjectType.lifeSafetyPoint.equals(type)) {
//            refs.add(oid, PropertyIdentifier.units);
//        } else if (ObjectType.loop.equals(type)) {
//            refs.add(oid, PropertyIdentifier.outputUnits);
//        } else if (ObjectType.multiStateInput.equals(type) || ObjectType.multiStateOutput.equals(type)
//                || ObjectType.multiStateValue.equals(type)) {
//            refs.add(oid, PropertyIdentifier.stateText);
//        } else {
//            return;
//        }
//        refs.add(oid, PropertyIdentifier.presentValue);
//    }
//
//    /**
//     * 获取点位的封装过后的Id
//     *
//     * @param remoteId         设备id
//     * @param objectIdentifier 点位标识
//     * @return 点位封装过后的Id
//     */
//    public static String getOriginalId(String remoteId, String objectIdentifier) {
//        StringBuilder newObjectIdentifier = new StringBuilder();
//        if (objectIdentifier.contains(StrPool.DASHED)) {
//            String[] split = objectIdentifier.split(StrPool.DASHED);
//            for (String s : split) {
//                newObjectIdentifier.append(CharSequenceUtil.upperFirst(s));
//            }
//        }
//        if (StringUtils.isNotEmpty(remoteId) && StringUtils.isNotEmpty(newObjectIdentifier)) {
//            return CharSequenceUtil.format("bacnet_{}_{}", remoteId, newObjectIdentifier.toString());
//        }
//        return "";
//    }
//
//    /**
//     * 获取点位字符串标识
//     *
//     * @param objectIdentifier 点位标识
//     * @return 点位字符串标识
//     */
//    public static String getObjectIdentifierStr(ObjectIdentifier objectIdentifier) {
//        return objectIdentifier.getObjectType().toString() + objectIdentifier.getInstanceNumber();
//    }
//
//    /***
//     * 按一个模块代表一个设备组装监控项
//     * 转化和过滤成需要的对象
//     * @return 响应对象集合
//     */
//    public static Set<BacnetObject> convert2Set(String remoteId, PropertyValues propertyValues) {
//        Map<String, BacnetObject> map = new ConcurrentHashMap<>(8);
//        for (ObjectPropertyReference propertyValue : propertyValues) {
//            ObjectIdentifier objectIdentifier = propertyValue.getObjectIdentifier();
//            String objectIdentifierStr = getObjectIdentifierStr(objectIdentifier);
//            String originalId = getOriginalId(remoteId, objectIdentifierStr);
//            Encodable encodable;
//            try {
//                encodable = propertyValues.get(propertyValue);
//            } catch (PropertyValueException e) {
//                logger.warn("{}的属性{}值获取异常", objectIdentifierStr, propertyValue.getPropertyIdentifier());
//                e.printStackTrace();
//                continue;
//            }
//            PropertyIdentifier propertyIdentifier = propertyValue.getPropertyIdentifier();
//            BacnetObject bacnetObject = map.get(originalId);
//            if (ObjectUtil.isEmpty(bacnetObject)) {
//                bacnetObject = new BacnetObject();
//                bacnetObject.setObjectIdentifier(objectIdentifierStr);
//                bacnetObject.setRemoteId(remoteId);
//                bacnetObject.setOriginalId(originalId);
//                map.put(originalId, bacnetObject);
//            }
//            if (propertyIdentifier.equals(PropertyIdentifier.units)) {
//                bacnetObject.setUnits(encodable.toString());
//            } else if (propertyIdentifier.equals(PropertyIdentifier.objectName)) {
//                bacnetObject.setObjectName(encodable.toString());
//            } else if (propertyIdentifier.equals(PropertyIdentifier.description)) {
//                bacnetObject.setDescription(encodable.toString());
//            } else if (propertyIdentifier.equals(PropertyIdentifier.presentValue)) {
//                bacnetObject.setPresentValue(encodable.toString());
//            } else if (propertyIdentifier.equals(PropertyIdentifier.activeText)) {
//                bacnetObject.setActiveText(encodable.toString());
//            } else if (propertyIdentifier.equals(PropertyIdentifier.inactiveText)) {
//                bacnetObject.setInactiveText(encodable.toString());
//            }
//        }
//        return new HashSet<>(map.values());
//    }
//
//}
