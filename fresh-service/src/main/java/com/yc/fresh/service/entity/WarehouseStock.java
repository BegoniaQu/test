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
 * 库存
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehouseStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    /**
     * sku编码
     */
    private Long skuId;

    /**
     * sku名称
     */
    private String skuName;

    private Integer fCategoryId;
    private Integer sCategoryId;
    /**
     * 入库数量
     */
    private Integer num;

    /**
     * 可售数量
     */
    private Integer saleableNum;

    /**
     * 单位类型，1-包装单位 2-重量单位g
     */
    private Integer unitType;

    /**
     * 单位(如包装单位：瓶、袋、盒)，为最小售卖单位，如果入库按箱入，则就要按箱卖
     */
    private String unit;

    /**
     * 规格，如12*1.5L, 320g, 200ml; 如果unit_type=2，则此处空白
     */
    private String spec;

    /**
     * 进价,当有价格波动时，此处为加权平均进价
     */
    private BigDecimal costPrice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastModifiedTime;


    public static final String ID = "id";

    public static final String WAREHOUSE_CODE = "warehouse_code";

    public static final String SKU_ID = "sku_id";

    public static final String SKU_NAME = "sku_name";

    public static final String NUM = "num";

    public static final String SALEABLE_NUM = "saleable_num";

    public static final String UNIT_TYPE = "unit_type";

    public static final String UNIT = "unit";

    public static final String SPEC = "spec";

    public static final String COST_PRICE = "cost_price";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

    public static final String F_CATEGORY_ID = "f_category_id";
    public static final String S_CATEGORY_ID = "s_category_id";
}
