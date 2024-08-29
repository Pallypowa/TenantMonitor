package com.bigfoot.tenantmonitor.enums;

import lombok.Getter;

@Getter
public enum PropertyCondition {
    NEW("NEW"),
    LIKE_NEW("LIKE_NEW"),
    RENOVATED("RENOVATED"),
    GOOD("GOOD"),
    FAIR("FAIR"),
    NEEDS_RENOVATION("NEEDS_RENOVATION"),
    UNFINISHED("UNFINISHED"),;

    private final String condition;

    PropertyCondition(String condition) {
        this.condition = condition;
    }

}
