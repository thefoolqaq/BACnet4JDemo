//package com.bacnet.util;
//
//import cn.hutool.core.collection.CollUtil;
//import com.bacnet.domain.BacnetObject;
//import com.serotonin.bacnet4j.RemoteDevice;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// *
// *  bacnet缓存工具类
// * @author hwt
// * @date 2023/11/30
// *
// */
//public class BacnetCacheUtils {
//    /**
//     * key:localBindAddress
//     */
//    private static final ConcurrentHashMap<String, Set<RemoteDevice>> remoteDeviceCache = new ConcurrentHashMap<>();
//
//    /**
//     * key:remoteId
//     */
//    private static final ConcurrentHashMap<String, Set<BacnetObject>> bacnetObjectCache = new ConcurrentHashMap<>();
//
//    public static Set<RemoteDevice> getRemoteDeviceCache(String localBindAddress){
//        return remoteDeviceCache.get(localBindAddress);
//    }
//
//
//    public static void putRemoteDeviceCache(String localBindAddress, RemoteDevice remoteDevice) {
//        Set<RemoteDevice> remoteDevices = remoteDeviceCache.get(localBindAddress);
//        if (CollUtil.isEmpty(remoteDevices)) {
//            remoteDevices = new HashSet<>();
//        }
//        remoteDevices.add(remoteDevice);
//        remoteDeviceCache.put(localBindAddress, remoteDevices);
//    }
//
//    public static void removeRemoteDeviceCache(String localBindAddress, RemoteDevice remoteDevice) {
//        Set<RemoteDevice> remoteDevices = remoteDeviceCache.get(localBindAddress);
//        if (CollUtil.isEmpty(remoteDevices)) {
//            return;
//        }
//        remoteDevices.remove(remoteDevice);
//    }
//
//
//    public static void putBacnetObjectCache(String remoteId, BacnetObject bacnetObject) {
//        Set<BacnetObject> bacnetObjects = bacnetObjectCache.get(remoteId);
//        if (CollUtil.isEmpty(bacnetObjects)) {
//            bacnetObjects = new HashSet<>();
//        }
//        bacnetObjects.add(bacnetObject);
//        bacnetObjectCache.put(remoteId, bacnetObjects);
//    }
//
//    public static void putBacnetObjectCache(String remoteId, Set<BacnetObject> bacnetObject) {
//        Set<BacnetObject> bacnetObjects = bacnetObjectCache.get(remoteId);
//        if (CollUtil.isEmpty(bacnetObjects)) {
//            bacnetObjects = new HashSet<>();
//        }
//        bacnetObjects.addAll(bacnetObject);
//        bacnetObjectCache.put(remoteId, bacnetObjects);
//    }
//
//    public static void removeBacnetObjectCache(String remoteId, BacnetObject bacnetObject) {
//        Set<BacnetObject> bacnetObjects = bacnetObjectCache.get(remoteId);
//        if (CollUtil.isEmpty(bacnetObjects)) {
//            return;
//        }
//        bacnetObjects.remove(bacnetObject);
//    }
//}
