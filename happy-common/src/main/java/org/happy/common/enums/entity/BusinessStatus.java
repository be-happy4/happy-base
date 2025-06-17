package org.happy.common.enums.entity;

import org.happy.common.core.domain.code.I18nCode;

/**
 * Business status enumeration.
 *
 * @author happy
 */
public enum BusinessStatus implements I18nCode<String> {
    NORMAL,
    ABNORMAL,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
