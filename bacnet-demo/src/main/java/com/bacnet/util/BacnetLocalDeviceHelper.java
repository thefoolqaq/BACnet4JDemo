//package com.bacnet.util;
//
//import cn.hutool.core.text.CharSequenceUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.wlinker.driver.common.constant.Constant;
//import cn.wlinker.driver.common.exception.DriverInitException;
//import com.bacnet.domain.BacnetLocalDeviceBean;
//import com.serotonin.bacnet4j.LocalDevice;
//import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
//import com.serotonin.bacnet4j.npdu.ip.IpNetworkBuilder;
//import com.serotonin.bacnet4j.transport.DefaultTransport;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Optional;
//import java.util.Random;
//
///**
// * 虚拟bacnet设备
// * @author hwt
// * @date 2023/11/30
// */
//public class BacnetLocalDeviceHelper {
//    public static final int LOCAL_DEVICE_ID = 99999;
//    public static final int BASE_MIN_ID = 250000;
//    public static final Random RANDOM = new Random();
//    private static Logger logger = LoggerFactory.getLogger(BacnetLocalDeviceHelper.class);
//
//    public static synchronized LocalDevice initLocalDevice(BacnetLocalDeviceBean bacnetLocalDeviceBean) throws Exception {
//        LocalDevice localDevice = LocalDeviceCacheUtils.get(bacnetLocalDeviceBean);
//        if (ObjectUtil.isNotNull(localDevice)) {
//            if (!localDevice.isInitialized()) {
//                localDevice.terminate();
//            } else {
//                localDevice.startRemoteDeviceDiscovery();
//                return localDevice;
//            }
//        }
//        try {
//            String localBindAddress = bacnetLocalDeviceBean.getLocalBindAddress();
//            IpNetworkBuilder ipNetworkBuilder = new IpNetworkBuilder()
//                    .withLocalBindAddress(localBindAddress)
//                    .withSubnet("255.255.255.0", 24)
//                    .withPort(47808)
//                    .withReuseAddress(true);
//            String broadcastAddress = bacnetLocalDeviceBean.getBroadcastAddress();
//            Integer networkPrefixLength = bacnetLocalDeviceBean.getNetworkPrefixLength();
//            if (CharSequenceUtil.isNotBlank(broadcastAddress) && ObjectUtil.isNotEmpty(networkPrefixLength)) {
//                ipNetworkBuilder.withBroadcast(broadcastAddress, networkPrefixLength);
//            }
//            IpNetwork network = ipNetworkBuilder.build();
//            int localDeviceId = RANDOM.nextInt(LOCAL_DEVICE_ID) + BASE_MIN_ID;
//            logger.error("localDeviceId:{}", localDeviceId);
//            DefaultTransport defaultTransport = new DefaultTransport(network);
//            localDevice = new LocalDevice(localDeviceId, defaultTransport);
//            MyListener myListener = new MyListener();
//            localDevice.getEventHandler().addListener(myListener);
//            localDevice.initialize();
//            localDevice.startRemoteDeviceDiscovery();
//            myListener.setLocalDevice(localDevice);
//            // Wait a bit for responses to come in.
//            for (int i = 1; i <= Constant.RETRIES; i++) {
//                Boolean put = LocalDeviceCacheUtils.put(bacnetLocalDeviceBean, localDevice);
//                if (Boolean.TRUE.equals(put)) {
//                    break;
//                }
//                Thread.sleep(Constant.TIMEOUT_MS);
//            }
//        } catch (InterruptedException e) {
//            if (ObjectUtil.isNotNull(localDevice)) {
//                localDevice.terminate();
//            }
//            throw new DriverInitException(e);
//        }
//        return localDevice;
//    }
//
//    public static Boolean connect(BacnetLocalDeviceBean bacnetLocalDeviceBean) throws Exception {
//        LocalDevice localDevice = initLocalDevice(bacnetLocalDeviceBean);
//        return localDevice.isInitialized();
//    }
//
//    public static Boolean isOpen(BacnetLocalDeviceBean bacnetLocalDeviceBean) {
//        LocalDevice localDevice = LocalDeviceCacheUtils.get(bacnetLocalDeviceBean);
//        return Optional.ofNullable(localDevice).isPresent() && localDevice.isInitialized();
//    }
//
//    public static Boolean close(BacnetLocalDeviceBean bacnetLocalDeviceBean) {
//        LocalDevice localDevice = LocalDeviceCacheUtils.get(bacnetLocalDeviceBean);
//        if (localDevice != null) {
//            localDevice.terminate();
//        }
//        return true;
//    }
//
//}
