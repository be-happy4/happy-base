package org.happy.common.enums.gen;

import org.happy.common.core.domain.code.I18nCode;

public enum GenType implements I18nCode<String> {
    ZIP,
    CUSTOM,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
