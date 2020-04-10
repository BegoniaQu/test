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
 * @since 2020-04-09
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
     * 配送人Id
     */
    private Long dmId;

    private String dmName;


    public static final String ID = "id";

    public static final String WAREHOUSE_CODE = "warehouse_code";

    public static final String DM_ID = "dm_id";

}
