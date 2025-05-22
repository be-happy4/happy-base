package org.happy.common.core.domain.tree.shaper;

import lombok.NoArgsConstructor;
import org.happy.common.core.domain.tree.HierarchicalOrderedTree;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class DefaultOrderedTreeShaper<T extends HierarchicalOrderedTree<?, ?, T>>
        extends DefaultTreeShaper<T> {
    public DefaultOrderedTreeShaper(TreeShaperConfig<T> config) {
        super(config);
    }

    @Override
    public <U extends T> List<U> shape(List<U> records) {
        return super.shape(records);
    }

    @Override
    protected <U extends T> List<U> handleList(List<U> records) {
        records.sort(Comparator.comparing(o -> o.getOrder()));
        return super.handleList(records);
    }
}
