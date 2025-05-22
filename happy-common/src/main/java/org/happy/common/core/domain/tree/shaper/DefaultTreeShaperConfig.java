package org.happy.common.core.domain.tree.shaper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.behappy.common.tree.HierarchicalTree;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

@Accessors(fluent = true)
@Getter
@Setter
@RequiredArgsConstructor
public class DefaultTreeShaperConfig<T extends HierarchicalTree<?, T>> implements TreeShaperConfig<T> {
    static final DefaultTreeShaperConfig<?> DEFAULT = DefaultTreeShaperConfig.builder().build();
    private final boolean rootRestrict;

    @Nullable
    @Override
    public <U extends T> Function<List<U>, U> topRoot() {
        return null;
    }

    public static <T extends HierarchicalTree<?, T>> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T extends HierarchicalTree<?, T>> {
        private boolean rootRestrict = true;

        public Builder<T> rootRestrict(boolean rootRestrict) {
            this.rootRestrict = rootRestrict;
            return this;
        }

        public DefaultTreeShaperConfig<T> build() {
            return new DefaultTreeShaperConfig<T>(this.rootRestrict);
        }
    }
}
