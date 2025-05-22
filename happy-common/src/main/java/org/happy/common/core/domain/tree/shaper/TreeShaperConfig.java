package org.happy.common.core.domain.tree.shaper;

import org.happy.common.core.domain.tree.HierarchicalTree;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public interface TreeShaperConfig<T extends HierarchicalTree<?, T>> {
    /**
     * Whether node must be root or has a parent.
     *
     * @return true if the node is root or has a parent, false otherwise.
     */
    boolean rootRestrict();

    /**
     * Function to build the top root.
     *
     * @return null indicates do not use top root.
     */
    @Nullable
    <U extends T> Function<List<U>, U> topRoot();


    @SuppressWarnings("unchecked")
    static <T extends HierarchicalTree<?, T>> TreeShaperConfig<T> getDefault() {
        return (TreeShaperConfig<T>) DefaultTreeShaperConfig.DEFAULT;
    }
}
