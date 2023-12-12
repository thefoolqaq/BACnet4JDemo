//package com.bacnet.domain;
//
//import lombok.*;
//
//import java.io.Serializable;
//import java.util.Objects;
//
///**
// * @author hwt
// * @date 2023/11/30
// */
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//public class BacnetObject implements Serializable {
//
//    /**KEY，注意：大多数情况下有的系统一个KEY代表一个监控项，有的厂家，例如电表一个KEY代表一个电表设备*/
//    private String objectIdentifier;
//
//    /**名称*/
//    private String objectName;
//
//    /**数值*/
//    private String presentValue;
//
//    /**活动文本*/
//    private String activeText;
//
//    /**非活动文本*/
//    private String inactiveText;
//
//    /**单位*/
//    private String units;
//
//    /**描述*/
//    private String description;
//
//
//    /**
//     * 设备id
//     */
//    private String remoteId;
//
//    /**
//     * 远端id:bacnet_设备id_属性id
//     */
//    private String originalId;
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(objectIdentifier);
//    }
//
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        } else if (obj == null) {
//            return false;
//        } else if (this.getClass() != obj.getClass()) {
//            return false;
//        } else {
//            BacnetObject other = (BacnetObject)obj;
//            if (this.objectIdentifier == null) {
//                return other.objectIdentifier == null;
//            } else{
//                return this.objectIdentifier.equals(other.objectIdentifier);
//            }
//        }
//    }
//
//}
