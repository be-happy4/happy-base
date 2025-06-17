package org.happy.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.happy.common.core.domain.BaseEntity;
import org.happy.common.enums.entity.NoticeStatus;
import org.happy.common.enums.entity.NoticeType;
import org.happy.common.xss.Xss;

/**
 * 通知公告表 sys_notice
 *
 * @author happy
 */
@Getter
@Setter
public class SysNotice extends BaseEntity {

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型
     */
    private NoticeType noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态
     */
    private NoticeStatus status;

    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    public String getNoticeTitle() {
        return noticeTitle;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeId", getNoticeId())
                .append("noticeTitle", getNoticeTitle())
                .append("noticeType", getNoticeType())
                .append("noticeContent", getNoticeContent())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
