package org.happy.common.core.domain.tree.shaper;


import org.happy.common.core.domain.tree.HierarchicalTree;

import java.util.List;

/**
 * Interface for shaping a list of hierarchical tree records.
 *
 * @see HierarchicalTree
 */
public interface TreeShaper<T extends HierarchicalTree<?, T>> {
    <U extends T> List<U> shape(List<U> records);
}
