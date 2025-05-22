package org.happy.common.core.domain.tree.shaper;

import lombok.RequiredArgsConstructor;
import org.happy.common.core.domain.Pair;
import org.happy.common.core.domain.tree.HierarchicalTree;
import org.happy.common.core.domain.tree.ITree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultMutableTreeShaper<T extends HierarchicalTree<?, T>> implements TreeShaper<T> {
    protected final TreeShaperConfig<T> config;

    public DefaultMutableTreeShaper() {
        this(TreeShaperConfig.getDefault());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U extends T> List<U> shape(List<U> records) {
        var map = records.stream()
                .map(r -> new Pair<>(r, new ArrayList<U>()))
                .collect(Collectors.toMap(
                        x -> x.getFirst().getId(),
                        Function.identity()));
        var roots = new Pair<U, ArrayList<U>>(null, new ArrayList<>());
        // TODO: 2025/4/30 check root restrict
        records.forEach(r -> map.getOrDefault(r.getPid(), roots).getSecond().add(r));
        return map.values().stream()
                .map(e -> (U) e.getFirst()
                        .withChildren(e.getSecond()))
                .filter(ITree::isRoot)
                .toList();
    }
}
