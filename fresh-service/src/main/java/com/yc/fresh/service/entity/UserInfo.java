package com.yc.fresh.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台用户
 * </p>
 *
 * @author Quy
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 1-普通用户 2-VIP
     */
    private Integer userType;

    /**
     * vip过期时间
     */
    private LocalDate vipExpiredTime;

    /**
     * 微信openid
     */
    private String wxOpenId;

    /**
     * 口令
     */
    private String tk;

    /**
     * 口令过期时间
     */
    private LocalDateTime tkExpiredTime;

    /**
     * 0-封禁，1-有效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastModifiedTime;


    public static final String USER_ID = "user_id";

    public static final String NICK_NAME = "nick_name";

    public static final String MOBILE = "mobile";

    public static final String USER_TYPE = "user_type";

    public static final String VIP_EXPIRED_TIME = "vip_expired_time";

    public static final String WX_OPEN_ID = "wx_open_id";

    public static final String TK = "tk";

    public static final String TK_EXPIRED_TIME = "tk_expired_time";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

}
