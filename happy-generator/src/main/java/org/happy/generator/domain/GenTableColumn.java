package org.happy.generator.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.happy.common.core.domain.BaseEntity;
import org.happy.common.enums.entity.QueryOperator;
import org.happy.common.enums.entity.gen.GenJavaType;
import org.happy.common.enums.entity.gen.HtmlType;
import org.happy.common.utils.StringUtils;


/**
 * 代码生成业务字段表 gen_table_column
 *
 * @author happy
 */
@Setter
@Getter
public class GenTableColumn extends BaseEntity {

    /**
     * 编号
     */
    private Long columnId;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private GenJavaType javaType;

    /**
     * JAVA字段名
     */
    @NotBlank(message = "Java属性不能为空")
    private String javaField;

    /**
     * 是否主键
     */
    private Boolean isPk;

    /**
     * 是否自增
     */
    private Boolean isIncrement;

    /**
     * 是否必填
     */
    private Boolean isRequired;

    /**
     * 是否为插入字
     */
    private Boolean isInsert;

    /**
     * 是否编辑字段
     */
    private Boolean isEdit;

    /**
     * 是否列表字段
     */
    private Boolean isList;

    /**
     * 是否查询字段
     */
    private Boolean isQuery;

    /**
     * 查询方式
     */
    private QueryOperator queryType;

    /**
     * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
     */
    private HtmlType htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;

    public String getCapJavaField() {
        return StringUtils.capitalize(javaField);
    }

    public boolean isPk() {
        return this.isPk;
    }

    public boolean isIncrement() {
        return this.isIncrement;
    }


    public boolean isRequired() {
        return this.isRequired;
    }

    public boolean isInsert() {
        return this.isInsert;
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public boolean isList() {
        return this.isList;
    }

    public boolean isQuery() {
        return this.isQuery;
    }

    public boolean isSuperColumn() {
        return isSuperColumn(this.javaField);
    }

    public static boolean isSuperColumn(String javaField) {
        return StringUtils.equalsAnyIgnoreCase(javaField,
                // BaseEntity
                "createBy", "createTime", "updateBy", "updateTime", "remark",
                // TreeEntity
                "parentName", "parentId", "orderNum", "ancestors");
    }

    public boolean isUsableColumn() {
        return isUsableColumn(javaField);
    }

    public static boolean isUsableColumn(String javaField) {
        // isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
        return StringUtils.equalsAnyIgnoreCase(javaField, "parentId", "orderNum", "remark");
    }

    public String readConverterExp() {
        String remarks = StringUtils.substringBetween(this.columnComment, "（", "）");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(remarks)) {
            for (String value : remarks.split(" ")) {
                if (StringUtils.isNotEmpty(value)) {
                    Object startStr = value.subSequence(0, 1);
                    String endStr = value.substring(1);
                    sb.append(startStr).append("=").append(endStr).append(",");
                }
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            return this.columnComment;
        }
    }
}
