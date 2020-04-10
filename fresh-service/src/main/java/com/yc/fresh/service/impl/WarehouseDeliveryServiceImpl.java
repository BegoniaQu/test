package com.yc.fresh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.fresh.service.dto.DmBindingDTO;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.WarehouseDelivery;
import com.yc.fresh.service.mapper.WarehouseDeliveryMapper;
import com.yc.fresh.service.IWarehouseDeliveryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓库配送人员 服务实现类
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
@Service
public class WarehouseDeliveryServiceImpl extends ServiceImpl<WarehouseDeliveryMapper, WarehouseDelivery> implements IWarehouseDeliveryService {


    @Override
    public IPage<DmBindingDTO> doListBinding(Map<String, Object> qryMap) {
        Integer pn = (Integer) qryMap.get("pn");
        Integer ps = (Integer) qryMap.get("ps");
        IPage<DmBindingDTO> iPage = new Page<>(pn, ps);
        List<DmBindingDTO> records = this.baseMapper.listBinding(iPage, qryMap);
        iPage.setRecords(records);
        return iPage;
    }
}
