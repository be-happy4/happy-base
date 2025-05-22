package org.happy.common.core.domain.tree;

import org.jetbrains.annotations.NotNull;

public interface INameTree<K> extends ITree<K> {
    @NotNull
    String getName();
}
