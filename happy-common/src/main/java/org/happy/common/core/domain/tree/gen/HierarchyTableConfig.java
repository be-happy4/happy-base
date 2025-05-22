package org.happy.common.core.domain.tree.gen;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@Getter
@RequiredArgsConstructor
public class HierarchyTableConfig {
    @NotNull
    private final String tableName;
    private final String viewName;
    private final String cteName;
    private final String pathIndexName;
    private final String idCol;
    private final String nameCol;
    private final String pidCol;
    private final String sep;
    private final List<String> otherColumns;
    private final boolean dropIfExists;
    /**
     * Does database supports ltree type.
     *
     * @see <a href="https://www.postgresql.org/docs/current/ltree.html">
     * PostgreSQL - ltree — hierarchical tree-like data type</a>
     */
    private final boolean supportsLTree;

    public static Builder builder() {
        return new Builder();
    }

    @Accessors(fluent = true, chain = true)
    @Getter
    @Setter
    public static class Builder {
        @NotNull
        private String tableName;
        private String viewName;
        private String cteName;
        private String pathIndexName;
        private String idCol = "id";
        private String nameCol = "name";
        private String pidCol = "pid";
        private String sep = ".";
        private List<String> otherColumns = List.of();
        private boolean dropIfExists = false;
        private boolean supportsLTree = false;

        public HierarchyTableConfig build() {
            if (viewName == null) {
                viewName = tableName + "_hierarchy";
            }
            if (cteName == null) {
                cteName = tableName + "_cte";
            }
            if (pathIndexName == null) {
                pathIndexName = "path_gist_unique_idx_" + tableName;
            }
            return new HierarchyTableConfig(
                    tableName, viewName, cteName, pathIndexName, idCol, nameCol, pidCol,
                    sep, otherColumns, dropIfExists, supportsLTree);
        }
    }
}
