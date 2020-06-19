package com.yc.fresh.service.impl;

import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.entity.SkuInfo;
import com.yc.fresh.service.enums.SkuStatusEnum;
import com.yc.fresh.service.mapper.SkuInfoMapper;
import com.yc.fresh.service.ISkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements ISkuInfoService {


    @Override
    public void updateStatusToUsed(SkuInfo t) {
        if (t.getStatus() == SkuStatusEnum.unuse.getV()) {
            SkuInfo skuUpt = new SkuInfo();
            skuUpt.setSkuId(t.getSkuId());
            skuUpt.setStatus(SkuStatusEnum.used.getV());
            skuUpt.setLastModifiedTime(DateUtils.getCurrentDate());
            this.updateById(skuUpt);
        }
    }
}
