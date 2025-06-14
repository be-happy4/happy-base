package org.happy.common.core.domain.code;


import org.happy.common.utils.StringUtils;

/**
 * code interface that supports i18n
 *
 * @param <K> Key type
 * @see I18nItem
 * @see ICode
 */
public interface I18nCode<K> extends ICode<K>, I18nItem {
    /**
     * To dict item.
     *
     * @return dict item
     */
    default DictItem<K> toDictItem() {
        return DictItem.of(getCode(), getTranslatedKey());
    }

    @Override
    default String getI18nKey() {
        return getI18nPrefix() + StringUtils.camelCase2KebabCase(String.valueOf(getCode()));
    }

    default String getI18nPrefix() {
        return StringUtils.camelCase2KebabCase(getClass().getSimpleName()) + ".";
    }
}
