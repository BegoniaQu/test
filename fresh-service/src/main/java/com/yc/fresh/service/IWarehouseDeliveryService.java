package com.yc.fresh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.service.dto.DmBindingDTO;
import com.yc.fresh.service.entity.WarehouseDelivery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓库配送人员 服务类
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
public interface IWarehouseDeliveryService extends IService<WarehouseDelivery> {

    IPage<DmBindingDTO> doListBinding(Map<String, Object> paramMap);
}
