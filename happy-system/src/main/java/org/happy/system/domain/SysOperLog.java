package org.happy.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.happy.common.annotation.Excel;
import org.happy.common.annotation.Excel.ColumnType;
import org.happy.common.core.domain.BaseEntity;
import org.happy.common.enums.entity.BusinessStatus;
import org.happy.common.enums.entity.BusinessType;
import org.happy.common.enums.entity.OperatorType;
import org.happy.common.utils.TimeUtils;

import java.util.Date;

/**
 * 操作日志记录表 oper_log
 *
 * @author happy
 */
@Setter
@Getter
public class SysOperLog extends BaseEntity {

    /**
     * 日志主键
     */
    @Excel(name = "操作序号", cellType = ColumnType.NUMERIC)
    private Long operId;

    /**
     * 操作模块
     */
    @Excel(name = "操作模块")
    private String title;

    /**
     * 业务类型
     */
    @Excel(name = "业务类型")
    private BusinessType businessType;

    /**
     * 业务类型数组
     */
    private Integer[] businessTypes;

    /**
     * 请求方法
     */
    @Excel(name = "请求方法")
    private String method;

    /**
     * 请求方式
     */
    @Excel(name = "请求方式")
    private String requestMethod;

    /**
     * 操作类别
     */
    @Excel(name = "操作类别")
    private OperatorType operatorType;

    /**
     * 操作人员
     */
    @Excel(name = "操作人员")
    private String operName;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String deptName;

    /**
     * 请求url
     */
    @Excel(name = "请求地址")
    private String operUrl;

    /**
     * 操作地址
     */
    @Excel(name = "操作地址")
    private String operIp;

    /**
     * 操作地点
     */
    @Excel(name = "操作地点")
    private String operLocation;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数")
    private String operParam;

    /**
     * 返回参数
     */
    @Excel(name = "返回参数")
    private String jsonResult;

    /**
     * 操作状态
     */
    @Excel(name = "状态")
    private BusinessStatus status;

    /**
     * 错误消息
     */
    @Excel(name = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = TimeUtils.PATTERN_DEFAULT)
    @Excel(name = "操作时间", width = 30, dateFormat = TimeUtils.PATTERN_DEFAULT)
    private Date operTime;

    /**
     * 消耗时间
     */
    @Excel(name = "消耗时间", suffix = "毫秒")
    private Long costTime;

}
