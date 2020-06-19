package com.yc.fresh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.service.entity.WarehouseStock;
import com.yc.fresh.service.mapper.WarehouseStockMapper;
import com.yc.fresh.service.IWarehouseStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * 库存 服务实现类
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
@Service
public class WarehouseStockServiceImpl extends ServiceImpl<WarehouseStockMapper, WarehouseStock> implements IWarehouseStockService {


    @Override
    public List<WarehouseStock> findBySkuId(Long skuId) {
        Assert.notNull(skuId, "null skuId");
        QueryWrapper<WarehouseStock> queryWrapper = Wrappers.query();
        queryWrapper.eq(WarehouseStock.SKU_ID, skuId);
        return this.list(queryWrapper);
    }
}
