//package com.bacnet;
//
//import cn.wlinker.driver.common.IDriverType;
//
///**
// * @author hwt
// * @date 2023/11/30
// */
//public class BacnetDriverType implements IDriverType<BacnetDriver> {
//
//    public static final BacnetDriverType INSTANCE = new BacnetDriverType();
//
//    private BacnetDriverType() {
//    }
//
//    @Override
//    public String getType() {
//        return "bacnet";
//    }
//
//    @Override
//    public String getName() {
//        return "楼宇自控协议";
//    }
//
//    @Override
//    public Class<BacnetDriver> getDriver() {
//        return BacnetDriver.class;
//    }
//}