package org.happy.common.core.domain.tree.shaper;

import lombok.RequiredArgsConstructor;
import org.happy.common.core.domain.Pair;
import org.happy.common.core.domain.tree.HierarchicalTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class DefaultTreeShaper<T extends HierarchicalTree<?, T>> implements TreeShaper<T> {
    protected final TreeShaperConfig<T> config;

    public DefaultTreeShaper() {
        this(TreeShaperConfig.getDefault());
    }

    @Override
    public <U extends T> List<U> shape(List<U> records) {
        var graph = buildGraph(records);
        var root = buildRecursively(records, graph, records.size());
        var topRoot = config.<U>topRoot();
        if (topRoot != null) {
            return Collections.singletonList(topRoot.apply(root));
        }
        return root;
    }

    protected final <U extends T> List<ArrayList<Integer>> buildGraph(List<U> records) {
        var size = records.size();
        var map = IntStream.range(0, size)
                .mapToObj(i -> new Pair<>(i, records.get(i)))
                .collect(Collectors.toMap(p -> p.getSecond().getId(), Pair::getFirst));
        var graph = IntStream.range(0, size + 1)
                .mapToObj(_ -> new ArrayList<Integer>())
                .toList();
        for (int i = 0; i < size; i++) {
            var record = records.get(i);
            assert record.isRoot() ^ record.hasParent();
            if (record.isRoot()) {
                graph.get(size).add(i);
                continue;
            }
            var parent = map.get(record.getPid());
            if (config.rootRestrict() && parent == null) {
                throw new IllegalArgumentException("Parent not found: " + record.getPid());
            }
            graph.get(parent).add(i);
        }
        return graph;
    }

    @SuppressWarnings("unchecked")
    protected final <U extends T> List<U> buildRecursively(
            List<U> records, List<ArrayList<Integer>> graph, int node) {
        var items = graph.get(node);
        if (items.isEmpty()) {
            return Collections.emptyList();
        }
        var result = new ArrayList<U>(items.size());
        for (var i : items) {
            var record = records.get(i);
            var children = this.buildRecursively(records, graph, i);
            result.add((U) record.withChildren(children));
        }
        return Collections.unmodifiableList(handleList(result));
    }

    protected <U extends T> List<U> handleList(List<U> records) {
        return records;
    }
}
