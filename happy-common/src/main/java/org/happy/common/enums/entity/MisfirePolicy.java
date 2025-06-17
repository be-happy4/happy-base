package org.happy.common.enums.entity;

import org.happy.common.core.domain.code.I18nCode;

public enum MisfirePolicy implements I18nCode<String> {
    DEFAULT,
    IGNORE_MISFIRES,
    FIRE_AND_PROCEED,
    DO_NOTHING,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
