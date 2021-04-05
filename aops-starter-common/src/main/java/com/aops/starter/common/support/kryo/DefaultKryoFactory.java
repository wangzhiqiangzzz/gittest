package com.aops.starter.common.support.kryo;

import com.aops.starter.common.support.kryo.KryoFactory;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

public class DefaultKryoFactory implements KryoFactory {
    @Override
    public Kryo createKryo() {
        Kryo kryo = new Kryo();
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
        kryo.setReferences(false);
        return kryo;
    }
}
