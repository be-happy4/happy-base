package org.happy.common.enums;

import lombok.Getter;
import org.happy.common.core.domain.code.I18nCode;

/**
 * Data status
 *
 * @author happy
 */
@Getter
public enum DataStatus implements I18nCode<String> {
    OK("正常"),
    DISABLE("停用"),
    DELETED("删除");

    private final String info;

    DataStatus(String info) {
        this.info = info;
    }

    @Override
    public String getCode() {
        return name();
    }
}
