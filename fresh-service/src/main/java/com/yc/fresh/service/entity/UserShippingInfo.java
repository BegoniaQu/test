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
 * @since 2020-04-09
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
     * 地址类型：1-家 2-公司，3-其他
     */
    private Integer addrType;

    /**
     * 是否是默认地址 1-是 0-否
     */
    private Integer defaultAddr;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String CONTACT = "contact";

    public static final String MOBILE = "mobile";

    public static final String ADDRESS = "address";

    public static final String LOCATION_X = "location_x";

    public static final String LOCATION_Y = "location_y";

    public static final String ADDR_TYPE = "addr_type";

    public static final String DEFAULT_ADDR = "default_addr";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

}
