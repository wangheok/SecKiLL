package com.fj.entity;

import java.util.Date;

/**
 * Created by wanghe on 3/08/16.
 */
public class SuccessKilled {

    private long secKillId;
    private long userMobile;
    private int state;
    private Date createTime;

    private SecKill secKill;

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(long userMobile) {
        this.userMobile = userMobile;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "secKillId=" + secKillId +
                ", userMobile=" + userMobile +
                ", state=" + state +
                ", createTime=" + createTime +
                ", secKill=" + secKill +
                '}';
    }
}
