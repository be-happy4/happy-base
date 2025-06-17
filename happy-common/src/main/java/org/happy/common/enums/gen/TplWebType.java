package org.happy.common.enums.gen;

import org.happy.common.core.domain.code.I18nCode;

public enum TplWebType implements I18nCode<String> {
    ELEMENT_UI,
    ELEMENT_PLUS,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
