package com.bacnet.util;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkBuilder;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyReferences;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.ReadListener;
import com.serotonin.bacnet4j.util.RequestUtils;

import java.util.List;

/**
 * 通用工具类
 * @author hwt
 * @date 2023/11/24
 */
public class BacnetUtils {

    public static IpNetwork getIpNetwork(final String BIND_ADDRESS,final String SUB_NET_ADDRESS,final Integer NETWORK_PREFIX_LENGTH,final Integer PORT) {
        IpNetwork ipNetwork = new IpNetworkBuilder()
                .withLocalBindAddress(BIND_ADDRESS)
                .withSubnet(SUB_NET_ADDRESS, NETWORK_PREFIX_LENGTH)
                .withPort(PORT)
                .withReuseAddress(true)
                .build();
        ipNetwork.enableBBMD();
        return ipNetwork;
    }


    public static PropertyValues readOiPropertiesValue(final LocalDevice localDevice, final RemoteDevice remoteDevice,
                                                       final List<ObjectIdentifier> ois, final ReadListener callback, PropertyIdentifier propertyIdentifier) throws BACnetException {
        if (ois.size()==0){
            return new PropertyValues();
        }
        final PropertyReferences refs = new PropertyReferences();
        for (final ObjectIdentifier oid:ois){
            refs.add(oid,propertyIdentifier);
        }
        return RequestUtils.readProperties(localDevice,remoteDevice,refs,false,callback);
    }

}
