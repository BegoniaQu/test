package com.yc.fresh.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户收货信息
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserShippingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 1-男 2-女
     */
    private Integer gender;

    /**
     * 联系手机
     */
    private String mobile;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 地理坐标x
     */
    private String locationX;

    /**
     * 地理坐标y
     */
    private String locationY;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String CONTACT = "contact";

    public static final String GENDER = "gender";

    public static final String MOBILE = "mobile";

    public static final String ADDRESS = "address";

    public static final String LOCATION_X = "location_x";

    public static final String LOCATION_Y = "location_y";

    public static final String CREATE_TIME = "create_time";

}
