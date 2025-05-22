package org.happy.common.core.domain.code;

import org.jetbrains.annotations.Nullable;

/**
 * Dictionary item model.
 *
 * @param <K> Key type
 */
public record DictItem<K>(K key, String label, @Nullable String description) {
    public static <K> DictItem<K> of(K key, String label) {
        return new DictItem<>(key, label, null);
    }

    public static <K> DictItem<K> of(K key, String label, @Nullable String description) {
        return new DictItem<>(key, label, description);
    }
}