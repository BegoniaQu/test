package com.yc.fresh.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 仓库配送人员
 * </p>
 *
 * @author Quy
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehouseDelivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    /**
     * 配送人
     */
    private String deliverer;

    /**
     * 配送人联系方式
     */
    private String delivererMobile;

    /**
     * 头像
     */
    private String photo;


    public static final String ID = "id";

    public static final String WAREHOUSE_CODE = "warehouse_code";

    public static final String DELIVERER = "deliverer";

    public static final String DELIVERER_MOBILE = "deliverer_mobile";

    public static final String PHOTO = "photo";

}
