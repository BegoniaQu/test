package com.yc.fresh.service.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 仓库
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 仓库编码
     */
    private String code;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 仓库具体地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 坐标x
     */
    private String locationX;

    /**
     * 坐标y
     */
    private String locationY;

    /**
     * 起送金额
     */
    private BigDecimal thresholdAmount;

    /**
     * 1-有效，0-无效
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


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String ADDRESS = "address";

    public static final String CONTACT = "contact";

    public static final String MOBILE = "mobile";

    public static final String LOCATION_X = "location_x";

    public static final String LOCATION_Y = "location_y";

    public static final String THRESHOLD_AMOUNT = "threshold_amount";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

}
