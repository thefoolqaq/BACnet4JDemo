//package com.bacnet.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;
//
///**
// * @author hwt
// * @date 2023/11/30
// */
//@lombok.Data
//@Accessors(chain = true)
//@AllArgsConstructor
//@NoArgsConstructor
//public class BacnetLocalDeviceBean {
//    /**
//     * 软驱程序在局域网内的ip地址,需要与楼控在同一网段
//     * example:192.168.0.111
//     * required = true
//     */
//    private String localBindAddress;
//    /**
//     * 广播域地址,当部分设备因为组网问题无法扫到时需要同时指定广播域地址
//     * example:255.255.255.0
//     * required = false
//     */
//    private String broadcastAddress;
//    /**
//     * 网络前缀长度,需要配合广播域broadcastAddress一起使用
//     * example:24
//     * required = false
//     */
//    private Integer networkPrefixLength;
//    /**
//     * 外围设备ip地址,跨网段扫描时使用
//     * example:192.168.2.111
//     * required = false
//     */
//    private String foreignDeviceAddress;
//
//    /**
//     * 请求的最小设备id
//     * example:0
//     * required = false
//     */
//    private Integer minId;
//    /**
//     * 请求的最大设备id
//     * example:4194302
//     * required = false
//     */
//    private Integer maxId;
//
//
//}
