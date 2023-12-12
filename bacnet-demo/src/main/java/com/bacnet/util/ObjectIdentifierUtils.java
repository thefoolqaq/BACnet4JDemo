//package com.bacnet.util;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.convert.Convert;
//import com.serotonin.bacnet4j.type.enumerated.ObjectType;
//import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
//
//import java.util.List;
//
///**
// * ObjectIdentifier工具类
// * @author hwt
// * @date 2023/11/30
// */
//public class ObjectIdentifierUtils {
//    /**
//     * 写入对象类型
//     */
//    private static final List<ObjectType> WRITE_OBJECT_TYPES = CollUtil.newArrayList(
//            ObjectType.analogOutput, ObjectType.analogValue,
//            ObjectType.binaryOutput, ObjectType.binaryValue,
//            ObjectType.multiStateOutput, ObjectType.multiStateValue);
//
//    /**
//     * 读取对象类型
//     */
//    private static final List<ObjectType> READ_OBJECT_TYPES = CollUtil.newArrayList(
//            ObjectType.analogOutput, ObjectType.analogValue, ObjectType.analogInput,
//            ObjectType.binaryOutput, ObjectType.binaryValue, ObjectType.binaryInput,
//            ObjectType.multiStateOutput, ObjectType.multiStateValue, ObjectType.multiStateInput);
//
//    /**
//     * 根据对象id获取写入对象标识
//     *
//     * @param objectId 对象id
//     * @return 对象标识
//     */
//    static ObjectIdentifier getWriteObjectIdentifierById(String objectId) {
//        return getObjectIdentifier(objectId, WRITE_OBJECT_TYPES);
//    }
//
//    /**
//     * 根据对象id获取读取对象标识
//     *
//     * @param objectId 对象id
//     * @return 对象标识
//     */
//    static ObjectIdentifier getReadObjectIdentifierById(String objectId) {
//        return getObjectIdentifier(objectId, READ_OBJECT_TYPES);
//    }
//
//    /**
//     * 根据对象id和类型获取对象标识
//     *
//     * @param objectId         对象id
//     * @param readObjectTypes 读取对象类型
//     * @return 对象标识
//     */
//    static ObjectIdentifier getObjectIdentifier(String objectId, List<ObjectType> readObjectTypes) {
//        for (ObjectType objectType : readObjectTypes) {
//            String objectTypeName = objectType.toString().replace(" ", "").replace("-", "").toLowerCase();
//            String objectIdLowerCase = objectId.toLowerCase();
//            if (objectIdLowerCase.contains(objectTypeName)) {
//                String instanceNumberStr = objectIdLowerCase.replace(objectTypeName, "");
//                Integer instanceNumber = Convert.toInt(instanceNumberStr);
//                return new ObjectIdentifier(objectType, instanceNumber);
//            }
//        }
//        return null;
//    }
//}
