package com.aops.starter.common.support.snowflake;

import lombok.Getter;
import lombok.Setter;

public class SnowflakeIdGenerator {

    /**
     * 开始时间 2017-01-01
     */
    @Setter
    @Getter
    private long twepoch = 1483200000000L;

    private static final long CUSTOM_ID_BITS = 12L;

    private final long customIdBits;

    private static final long SEQ_BITS = 12L;

    private final long sequenceBits;

    private long sequence=0;

    private long lastTimestamp = 0;

    private final long customId;

    public SnowflakeIdGenerator(long workerId){
        this(workerId,CUSTOM_ID_BITS,SEQ_BITS);
    }

    public SnowflakeIdGenerator (long workerId,long customIdBits,long sequenceBits){
        this.customIdBits = customIdBits;
        this.sequenceBits = sequenceBits;
        this.customId = workerId & (~(-1L<<customIdBits));
    }

    public synchronized long nextId(){
        long timestamp = timeGen();
        if (timestamp == lastTimestamp){
            sequence = (sequence + 1) & (~(-1L << sequenceBits));
            if (sequence==0){
                timestamp = tilNextMillis();
            }
        }else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp-twepoch)<<(sequenceBits + customIdBits)) | (customId << sequenceBits) | sequence;

    }

    private long tilNextMillis() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp){
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
