//package com.bacnet.util;
//
//import com.serotonin.bacnet4j.LocalDevice;
//import com.serotonin.bacnet4j.RemoteDevice;
//import com.serotonin.bacnet4j.event.DeviceEventAdapter;
//import lombok.Setter;
//import lombok.SneakyThrows;
//
///**
// * 自定义监听器，以实时添加新设备到缓存
// * @author hwt
// * @date 2023/11/30
// */
//public class MyListener  extends DeviceEventAdapter {
//
//    @Setter
//    private LocalDevice localDevice;
//
//    @SneakyThrows
//    @Override
//    public void iAmReceived(RemoteDevice d) {
//        System.out.println("-----IAm received：" + d);
//    }
//}