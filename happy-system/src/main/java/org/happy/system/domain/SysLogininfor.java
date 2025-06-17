package org.happy.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.happy.common.annotation.Excel;
import org.happy.common.annotation.Excel.ColumnType;
import org.happy.common.core.domain.BaseEntity;
import org.happy.common.enums.entity.AccessStatus;
import org.happy.common.utils.TimeUtils;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author happy
 */
@Setter
@Getter
public class SysLogininfor extends BaseEntity {

    /**
     * ID
     */
    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String userName;

    /**
     * 登录状态
     */
    @Excel(name = "登录状态")
    private AccessStatus status;

    /**
     * 登录IP地址
     */
    @Excel(name = "登录地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Excel(name = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Excel(name = "浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    private String os;

    /**
     * 提示消息
     */
    @Excel(name = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = TimeUtils.PATTERN_DEFAULT)
    @Excel(name = "访问时间", width = 30, dateFormat = TimeUtils.PATTERN_DEFAULT)
    private Date loginTime;

}
