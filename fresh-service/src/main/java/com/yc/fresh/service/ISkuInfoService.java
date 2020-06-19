package com.yc.fresh.service;

import com.yc.fresh.service.entity.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
public interface ISkuInfoService extends IService<SkuInfo> {

    void updateStatusToUsed(SkuInfo t);
}
