package org.happy.common.core.domain.tree.gen;

import lombok.extern.slf4j.Slf4j;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class HierarchyTableSqlGen {
    protected static final List<Field> FIELDS = Stream.of(HierarchyTableConfig.class.getDeclaredFields()).toList();

    protected final STGroup templateGroup;

    public HierarchyTableSqlGen() {
        this.templateGroup = new STGroupFile("templates/sql/hierarchy_table.stg");
    }


    public String generateSQL(HierarchyTableConfig config) {
        ST template = templateGroup.getInstanceOf("generate");
        for (var field : FIELDS) {
            try {
                field.setAccessible(true);
                var name = field.getName();
                var value = field.get(config);
                template.add(name, value);
                log.trace("{}: {}", name, value);
                field.setAccessible(false);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return template.render();
    }

    public static void main(String[] args) {
        var generator = new HierarchyTableSqlGen();

        var config = HierarchyTableConfig.builder()
                .tableName("test_ordered_dept")
                .dropIfExists(true)
                .supportsLTree(true)
                .otherColumns(List.of("order", "create_time"))
                .build();

        String sql = generator.generateSQL(config);
        System.out.println(sql);
    }
}
