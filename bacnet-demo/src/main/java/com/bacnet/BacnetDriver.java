//package com.bacnet;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.wlinker.driver.common.AbstractDriver;
//import com.bacnet.domain.BacnetBaseBean;
//import com.bacnet.domain.BacnetLocalDeviceBean;
//import com.bacnet.domain.BacnetObject;
//import com.bacnet.util.BacnetLocalDeviceHelper;
//import com.bacnet.util.BacnetReadHandler;
//import lombok.SneakyThrows;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Bacnet驱动
// * @author hwt
// * @date 2023/11/30
// */
//public class BacnetDriver extends AbstractDriver<BacnetLocalDeviceBean, BacnetBaseBean, BacnetObject> {
//    private static final BacnetObject NULL_OBJ = new BacnetObject();
//
//    @Override
//    protected String getConnectionKey() {
//        return connectionMap.get("localBindAddress").toString();
//    }
//
//    public BacnetDriver(Map<String, Object> connectionMap) {
//        super(connectionMap);
//    }
//
//    @Override
//    public BacnetLocalDeviceBean getConnectionBean() {
//        return BeanUtil.toBean(connectionMap, BacnetLocalDeviceBean.class);
//    }
//
//    @Override
//    public Boolean open() throws Exception {
//        return BacnetLocalDeviceHelper.connect(getConnectionBean());
//    }
//
//    @SneakyThrows
//    @Override
//    public Boolean isOpen() {
//        return BacnetLocalDeviceHelper.isOpen(getConnectionBean());
//    }
//
//    @Override
//    public List<BacnetObject> discovery() {
//        return BacnetReadHandler.discoveryDeviceForList(getConnectionBean());
//    }
//
//    @Override
//    public BacnetObject read(BacnetBaseBean dto) {
//        return BacnetReadHandler.read(getConnectionBean(), dto).orElse(NULL_OBJ);
//    }
//
//    @Override
//    public Boolean close() {
//        return BacnetLocalDeviceHelper.close(getConnectionBean());
//    }
//}
