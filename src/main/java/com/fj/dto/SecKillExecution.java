package com.fj.dto;

import com.fj.entity.SuccessKilled;
import com.fj.enums.SecKillStateEnum;

/**
 * Pack the result of seckill execution
 * Created by wanghe on 4/08/16.
 */
public class SecKillExecution {

    private long secKillId;

    /*
    The state of seckill result
     */
    private int state;

    /*
    Display the state information
     */
    private String stateInfo;

    /*
    successfully seckill object
     */
    private SuccessKilled successKilled;

    public SecKillExecution(long secKillId, SecKillStateEnum stateEnum, SuccessKilled successKilled) {
        this.secKillId = secKillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SecKillExecution(long secKillId, SecKillStateEnum stateEnum) {
        this.secKillId = secKillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SecKillExecution{" +
                "secKillId=" + secKillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
