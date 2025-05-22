package org.happy.common.core.domain.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ITree<K> {
    @NotNull
    K getId();

    /**
     * Get parent id.
     *
     * @return parent id
     */
    @Nullable
    K getPid();

    default boolean isRoot() {
        return getPid() == null;
    }

    default boolean hasParent() {
        return getPid() != null;
    }
}
