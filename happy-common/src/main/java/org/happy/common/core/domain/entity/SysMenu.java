package org.happy.common.core.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.happy.common.config.serializer.TextualBooleanDeserializer;
import org.happy.common.config.serializer.TextualBooleanSerializer;
import org.happy.common.core.domain.BaseEntity;
import org.happy.common.enums.entity.DataStatus;
import org.happy.common.enums.entity.MenuType;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表 sys_menu
 *
 * @author happy
 */
@Getter
@Setter
public class SysMenu extends BaseEntity {

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 路由名称，默认和路由地址相同的驼峰格式（注意：因为vue3版本的router会删除名称相同路由，为避免名字的冲突，特殊情况可以自定义）
     */
    private String routeName;

    /**
     * 是否为外链
     */
    @NotNull
    @JsonSerialize(using = TextualBooleanSerializer.class)
    @JsonDeserialize(using = TextualBooleanDeserializer.class)
    private Boolean isFrame;

    /**
     * 是否缓存
     */
    @NotNull
    @JsonSerialize(using = TextualBooleanSerializer.class)
    @JsonDeserialize(using = TextualBooleanDeserializer.class)
    private Boolean isCache;

    /**
     * 类型
     */
    private MenuType menuType;

    /**
     * 显示状态
     */
    @JsonSerialize(using = TextualBooleanSerializer.class)
    @JsonDeserialize(using = TextualBooleanDeserializer.class)
    private Boolean hidden;

    /**
     * 菜单状态
     */
    private DataStatus status;

    /**
     * 权限字符串
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单
     */
    private List<SysMenu> children = new ArrayList<>();

    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
    public String getMenuName() {
        return menuName;
    }

    @NotNull(message = "显示顺序不能为空")
    public Integer getOrderNum() {
        return orderNum;
    }

    @Size(min = 0, max = 200, message = "路由地址不能超过200个字符")
    public String getPath() {
        return path;
    }

    @Size(min = 0, max = 200, message = "组件路径不能超过255个字符")
    public String getComponent() {
        return component;
    }

    @NotNull(message = "菜单类型不能为空")
    public MenuType getMenuType() {
        return menuType;
    }

    @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    public String getPerms() {
        return perms;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("menuId", getMenuId())
                .append("menuName", getMenuName())
                .append("parentId", getParentId())
                .append("orderNum", getOrderNum())
                .append("path", getPath())
                .append("component", getComponent())
                .append("query", getQuery())
                .append("routeName", getRouteName())
                .append("isFrame", getIsFrame())
                .append("IsCache", getIsCache())
                .append("menuType", getMenuType())
                .append("hidden", getHidden())
                .append("status ", getStatus())
                .append("perms", getPerms())
                .append("icon", getIcon())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
