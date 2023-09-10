package com.ean.project.model.enums;

/**
 * 状态枚举
 *
 * */
public enum PostStatusEnum {

    ONLINE("上线", 1),
    OFFLINE("下线", 0);

    private final String text;

    private final int value;

    PostStatusEnum(String text, int value) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
