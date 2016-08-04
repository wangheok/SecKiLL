package com.fj.dto;

/**
 * Expose the seckill url
 * Created by wanghe on 4/08/16.
 */
public class Exposer {
    /*
    If the seckill started
     */
    private boolean isExposed;

    /*
    encrypt
     */
    private String md5;

    private long secKillId;

    /*
    Current System time (millisecond)
     */
    private long currentSysTime;

    private long startTime;
    private long endTime;

    public Exposer(boolean isExposed, String md5, long secKillId) {
        this.isExposed = isExposed;
        this.md5 = md5;
        this.secKillId = secKillId;
    }

    public Exposer(boolean isExposed, long secKillId, long currentSysTime, long startTime, long endTime) {
        this.isExposed = isExposed;
        this.secKillId = secKillId;
        this.currentSysTime = currentSysTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Exposer(boolean isExposed, long secKillId) {
        this.isExposed = isExposed;
        this.secKillId = secKillId;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public long getCurrentSysTime() {
        return currentSysTime;
    }

    public void setCurrentSysTime(long currentSysTime) {
        this.currentSysTime = currentSysTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "isExposed=" + isExposed +
                ", md5='" + md5 + '\'' +
                ", secKillId=" + secKillId +
                ", currentSysTime=" + currentSysTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
