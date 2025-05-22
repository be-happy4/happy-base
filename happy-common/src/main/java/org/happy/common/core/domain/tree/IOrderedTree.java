package org.happy.common.core.domain.tree;

import org.jetbrains.annotations.NotNull;

public interface IOrderedTree<K, O extends Comparable<O>> extends INameTree<K> {
    @NotNull
    O getOrder();
}
