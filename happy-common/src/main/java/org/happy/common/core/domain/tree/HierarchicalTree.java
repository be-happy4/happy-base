package org.happy.common.core.domain.tree;

import java.util.List;

public interface HierarchicalTree<K, T extends HierarchicalTree<K, T>> extends INameTree<K> {
    List<T> getChildren();

    T withChildren(List<? extends T> children);
}
