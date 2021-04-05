package com.aops.starter.common.support.kryo;

import com.esotericsoftware.kryo.Kryo;

public interface KryoFactory {
    Kryo createKryo();
}
