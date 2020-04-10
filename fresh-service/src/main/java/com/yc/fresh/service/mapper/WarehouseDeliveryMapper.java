package com.yc.fresh.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.service.dto.DmBindingDTO;
import com.yc.fresh.service.entity.WarehouseDelivery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓库配送人员 Mapper 接口
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
public interface WarehouseDeliveryMapper extends BaseMapper<WarehouseDelivery> {

    List<DmBindingDTO> listBinding(IPage page, @Param("map") Map<String, Object> map);
}
