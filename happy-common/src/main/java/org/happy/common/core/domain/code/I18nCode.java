package org.happy.common.core.domain.code;


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
}