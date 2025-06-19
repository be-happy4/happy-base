package org.happy.common.enums.entity.gen;

import org.happy.common.core.domain.code.I18nCode;

public enum HtmlType implements I18nCode<String> {
    INPUT,
    TEXTAREA,
    SELECT,
    RADIO,
    CHECKBOX,
    DATETIME,
    IMAGE_UPLOAD,
    FILE_UPLOAD,
    EDITOR,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
