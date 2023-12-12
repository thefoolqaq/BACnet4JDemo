//package com.bacnet.demo;
//
//
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.wlinker.driver.common.DriverFactory;
//import com.bacnet.BacnetDriver;
//import com.bacnet.domain.BacnetBaseBean;
//import com.bacnet.domain.BacnetLocalDeviceBean;
//import com.bacnet.domain.BacnetObject;
//import com.serotonin.bacnet4j.util.sero.IpAddressUtils;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Map;
//
//
///**
// * @author hwt
// * @date 2023/11/30
// */
//public class BacnetTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(BacnetTest.class);
//
//    static BacnetDriver bacnetDriver;
//
//    static {
//        BacnetLocalDeviceBean bacnetLocalDeviceBean = new BacnetLocalDeviceBean();
//        bacnetLocalDeviceBean.setLocalBindAddress("192.168.174.1");
////        bacnetLocalDeviceBean.setLocalBindAddress("192.168.1.90");
//        bacnetLocalDeviceBean.setBroadcastAddress("255.255.255.255");
//        bacnetLocalDeviceBean.setNetworkPrefixLength(24);
//        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(bacnetLocalDeviceBean);
//        try {
//            bacnetDriver = DriverFactory.getDriverByConnInfo(BacnetDriver.class,stringObjectMap);
//            bacnetDriver.open();
//            Thread.sleep(3000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void read() throws InterruptedException {
//        Boolean isOpen = bacnetDriver.isOpen();
//        if (!isOpen){
//            System.out.println("驱动已关闭");
//        }
//        BacnetBaseBean bacnetBaseBean = new BacnetBaseBean();
//        bacnetBaseBean.setRemoteId("16705");
//        bacnetBaseBean.setObjectId("analogOutput 0");
//        while (true) {
//            BacnetObject read = bacnetDriver.read(bacnetBaseBean);
//            logger.error("analoginput0-presentValue：{},", read.getPresentValue());
//            Thread.sleep(3000);
//        }
////        List<BacnetObject> bacnetObjects = bacnetDriver.discovery();
////        for (BacnetObject object :bacnetObjects){
////            System.out.println(object.getObjectIdentifier()+"---"+object.getPresentValue());
////        }
////        logger.error("结果：{}",bacnetObjects);
//    }
//
//
//
//
//}
