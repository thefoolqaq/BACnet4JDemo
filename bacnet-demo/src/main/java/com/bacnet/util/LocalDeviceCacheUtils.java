//package com.bacnet.util;
//
//import cn.hutool.core.text.CharSequenceUtil;
//import cn.hutool.core.util.ObjectUtil;
//import com.bacnet.domain.BacnetLocalDeviceBean;
//import com.serotonin.bacnet4j.LocalDevice;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * LocalDevice缓存工具类
// * @author hwt
// * @date 2023/11/30
// */
//public class LocalDeviceCacheUtils {
//    private static final Map<String, LocalDevice> localDeviceMap = new ConcurrentHashMap<>();
//    private static final Logger logger = LoggerFactory.getLogger(LocalDeviceCacheUtils.class);
//
//    private static String getKey(String broadcastAddress, Integer networkPrefixLength, String localBindAddress) {
//        if (CharSequenceUtil.isBlank(broadcastAddress)) {
//            broadcastAddress = "?";
//        }
//        if (ObjectUtil.isEmpty(networkPrefixLength)) {
//            networkPrefixLength = 0;
//        }
//        if (CharSequenceUtil.isBlank(localBindAddress)) {
//            localBindAddress = "?";
//        }
//        return CharSequenceUtil.format("{}_{}_{}", broadcastAddress, networkPrefixLength, localBindAddress);
//    }
//
//    private static String getKey(BacnetLocalDeviceBean bacnetLocalDeviceBean) {
//        String broadcastAddress = bacnetLocalDeviceBean.getBroadcastAddress();
//        Integer networkPrefixLength = bacnetLocalDeviceBean.getNetworkPrefixLength();
//        String localBindAddress = bacnetLocalDeviceBean.getLocalBindAddress();
//        return getKey(broadcastAddress, networkPrefixLength, localBindAddress);
//    }
//
//    private static LocalDevice getLocalDeviceInCache(BacnetLocalDeviceBean bacnetLocalDeviceBean) {
//        String key = getKey(bacnetLocalDeviceBean);
//        String localBindAddress = bacnetLocalDeviceBean.getLocalBindAddress();
//        localDeviceMap.keySet().forEach(t -> {
//            if (!key.equalsIgnoreCase(t) && t.contains(localBindAddress)) {
//                LocalDevice localDevice = localDeviceMap.get(t);
//                if (ObjectUtil.isNotNull(localDevice)) {
//                    try {
//                        localDevice.terminate();
//                    } catch (Exception exception) {
//                        logger.warn(exception.getMessage());
//                    }
//                }
//            }
//        });
//        return localDeviceMap.get(key);
//    }
//
//    public static LocalDevice get(BacnetLocalDeviceBean bacnetLocalDeviceBean) {
//        return getLocalDeviceInCache(bacnetLocalDeviceBean);
//    }
//
//    public static Boolean put(BacnetLocalDeviceBean bacnetLocalDeviceBean, LocalDevice localDevice) {
//        String key = getKey(bacnetLocalDeviceBean);
//        if (localDevice.isInitialized()) {
//            localDeviceMap.put(key, localDevice);
//            return true;
//        }
//        return false;
//    }
//}
