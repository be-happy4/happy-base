package org.happy.common.core.domain.tree;

public interface HierarchicalOrderedTree<
        K,
        O extends Comparable<O>,
        T extends HierarchicalOrderedTree<K, O, T>
        > extends HierarchicalTree<K, T>, IOrderedTree<K, O> {
}
