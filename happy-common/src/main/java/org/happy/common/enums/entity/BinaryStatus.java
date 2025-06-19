package org.happy.common.enums.entity;

import org.happy.common.core.domain.code.I18nCode;

/**
 * Binary status enumeration.
 *
 * @author happy
 */
public enum BinaryStatus implements I18nCode<String> {
    SUCCESS,
    FAIL,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
