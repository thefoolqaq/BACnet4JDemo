//package com.bacnet.util;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.lang.Assert;
//import cn.hutool.core.util.ObjectUtil;
//import cn.wlinker.driver.common.exception.DiscoveryException;
//import cn.wlinker.driver.common.exception.ReadException;
//import com.bacnet.domain.BacnetBaseBean;
//import com.bacnet.domain.BacnetLocalDeviceBean;
//import com.bacnet.domain.BacnetObject;
//import com.serotonin.bacnet4j.LocalDevice;
//import com.serotonin.bacnet4j.RemoteDevice;
//import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * bacnet读取处理器
// * @author hwt
// * @date 2023/11/30
// */
//@Slf4j
//public class BacnetReadHandler {
//
//
//    /**
//     * 发现设备列表
//     *
//     * @param bacnetLocalDeviceBean 本地设备信息
//     * @return 设备列表
//     */
//    public static List<BacnetObject> discoveryDeviceForList(BacnetLocalDeviceBean bacnetLocalDeviceBean) {
//        List<BacnetObject> bacnetObjects = new LinkedList<>();
//        LocalDevice localDevice = null;
//        try {
//            localDevice = BacnetLocalDeviceHelper.initLocalDevice(bacnetLocalDeviceBean);
//            List<RemoteDevice> remoteDevices = localDevice.getRemoteDevices();
//            Integer minId = bacnetLocalDeviceBean.getMinId();
//            Integer maxId = bacnetLocalDeviceBean.getMaxId();
//            LocalDevice finalLocalDevice = localDevice;
//            remoteDevices.stream()
//                    .filter(remoteDevice -> {
//                        int instanceNumber = remoteDevice.getInstanceNumber();
//                        return (ObjectUtil.isEmpty(minId) || instanceNumber >= minId)
//                                && (ObjectUtil.isEmpty(maxId) || instanceNumber <= maxId);
//                    })
//                    .map(remoteDevice -> BacnetObjectUtils.findObjects(
//                            finalLocalDevice, bacnetLocalDeviceBean.getLocalBindAddress(), remoteDevice))
//                    .forEach(bacnetObjects::addAll);
//            return bacnetObjects;
//        } catch (Exception e) {
//            if (localDevice != null) {
//                localDevice.terminate();
//            }
//            e.printStackTrace();
//            throw new DiscoveryException(e);
//        }
//    }
//
//    /**
//     * 读取设备
//     *
//     * @param bacnetLocalDeviceBean 本地设备信息
//     * @param dto                   读取参数信息
//     * @return 设备
//     */
//    public static Optional<BacnetObject> read(BacnetLocalDeviceBean bacnetLocalDeviceBean, BacnetBaseBean dto) {
//        LocalDevice localDevice = null;
//        try {
//            String remoteId = dto.getRemoteId();
//            String objectId = dto.getObjectId();
//            Integer deviceId = Convert.toInt(remoteId);
//            bacnetLocalDeviceBean.setMinId(deviceId);
//            bacnetLocalDeviceBean.setMaxId(deviceId);
//            localDevice = BacnetLocalDeviceHelper.initLocalDevice(bacnetLocalDeviceBean);
//            Assert.isTrue(ObjectUtil.isNotNull(localDevice) && CollUtil.isNotEmpty(localDevice.getRemoteDevices()), "未找到远程设备！");
//            RemoteDevice remoteDevice = localDevice.getRemoteDevice(Convert.toInt(remoteId)).get();
//            Assert.notNull(remoteDevice, "设备{}不存在", deviceId);
//            ObjectIdentifier objectIdentifier = ObjectIdentifierUtils.getReadObjectIdentifierById(objectId);
//            Assert.notNull(remoteDevice, "objectIdentifier 不合法");
//            return BacnetObjectUtils.findObject(localDevice, bacnetLocalDeviceBean.getLocalBindAddress(), remoteDevice, objectIdentifier);
//        } catch (Exception e) {
//            if (localDevice != null) {
//                localDevice.terminate();
//            }
//            throw new ReadException(e);
//        }
//    }
//
//}
//
