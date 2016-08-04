package com.fj.enums;

/**
 * Created by wanghe on 4/08/16.
 */
public enum SecKillStateEnum {

    SUCCESS(1, "Purchased Successfully!"),
    END(0, "SecKill Event Closed!"),
    REPEAT_PURCHASE(-1, "Repeat Purchase!"),
    INNER_ERROR(-2, "System Exception!"),
    DATA_REWRITE(-3, "Data distort!");

    private int state;
    private String stateInfo;

    SecKillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SecKillStateEnum stateOf(int index) {

        for (SecKillStateEnum state :
                values()) {

            if (state.getState() == index) {

                return state;
            }
        }
        return null;
    }
}