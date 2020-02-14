package com.yc.fresh.service.impl;

import com.yc.fresh.service.entity.UserOrder;
import com.yc.fresh.service.mapper.UserOrderMapper;
import com.yc.fresh.service.IUserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户订单 服务实现类
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements IUserOrderService {

}
