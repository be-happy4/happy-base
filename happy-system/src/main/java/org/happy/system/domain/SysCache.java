package org.happy.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.happy.common.utils.StringUtils;

/**
 * 缓存信息
 *
 * @author happy
 */
@Setter
@Getter
public class SysCache {
    /**
     * 缓存名称
     */
    private String cacheName = "";

    /**
     * 缓存键名
     */
    private String cacheKey = "";

    /**
     * 缓存内容
     */
    private String cacheValue = "";

    /**
     * 备注
     */
    private String remark = "";

    public SysCache() {

    }

    public SysCache(String cacheName, String remark) {
        this.cacheName = cacheName;
        this.remark = remark;
    }

    public SysCache(String cacheName, String cacheKey, String cacheValue) {
        this.cacheName = cacheName.replace(":", "");
        this.cacheKey = cacheKey.replace(cacheName, "");
        this.cacheValue = cacheValue;
    }

}
