package com.yc.fresh.service.impl;

import com.yc.fresh.service.entity.UserInfo;
import com.yc.fresh.service.mapper.UserInfoMapper;
import com.yc.fresh.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台用户 服务实现类
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
