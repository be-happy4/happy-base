package org.happy.generator.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.happy.common.core.domain.BaseEntity;
import org.happy.common.enums.gen.GenType;
import org.happy.common.enums.gen.TplCategory;
import org.happy.common.enums.gen.TplWebType;

import java.util.List;

import static org.happy.common.enums.gen.TplCategory.*;

/**
 * 业务表 gen_table
 *
 * @author happy
 */
@Getter
@Setter
public class GenTable extends BaseEntity {
    /**
     * 编号
     */
    private Long tableId;

    /**
     * 表名称
     */
    @NotBlank(message = "表名称不能为空")
    private String tableName;

    /**
     * 表描述
     */
    @NotBlank(message = "表描述不能为空")
    private String tableComment;

    /**
     * 关联父表的表名
     */
    private String subTableName;

    /**
     * 本表关联父表的外键名
     */
    private String subTableFkName;

    /**
     * 实体类名称(首字母大写)
     */
    @NotBlank(message = "实体类名称不能为空")
    private String className;

    /**
     * 使用的模板
     */
    private TplCategory tplCategory;

    /**
     * 前端类型
     */
    private TplWebType tplWebType;

    /**
     * 生成包路径
     */
    @NotBlank(message = "生成包路径不能为空")
    private String packageName;

    /**
     * 生成模块名
     */
    @NotBlank(message = "生成模块名不能为空")
    private String moduleName;

    /**
     * 生成业务名
     */
    @NotBlank(message = "生成业务名不能为空")
    private String businessName;

    /**
     * 生成功能名
     */
    @NotBlank(message = "生成功能名不能为空")
    private String functionName;

    /**
     * 生成作者
     */
    @NotBlank(message = "作者不能为空")
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    private GenType genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    private String genPath;

    /**
     * 主键信息
     */
    private GenTableColumn pkColumn;

    /**
     * 子表信息
     */
    private GenTable subTable;

    /**
     * 表列信息
     */
    @Valid
    private List<GenTableColumn> columns;

    /**
     * 其它生成选项
     */
    private String options;

    /**
     * 树编码字段
     */
    private String treeCode;

    /**
     * 树父编码字段
     */
    private String treeParentCode;

    /**
     * 树名称字段
     */
    private String treeName;

    /**
     * 上级菜单ID字段
     */
    private Long parentMenuId;

    /**
     * 上级菜单名称字段
     */
    private String parentMenuName;

    public boolean isSub() {
        return SUB == tplCategory;
    }

    public boolean isTree() {
        return TREE == tplCategory;
    }

    public boolean isCrud() {
        return CRUD == tplCategory;
    }
}