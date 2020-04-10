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
 * sku信息
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * sku编码，主键
     */
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;

    /**
     * 名称
     */
    private String skuName;


    private Integer fCategoryId;
    private Integer sCategoryId;

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

    private Integer status; //0-未使用，1-已使用

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastModifiedTime;


    public static final String SKU_ID = "sku_id";

    public static final String SKU_NAME = "sku_name";

    public static final String UNIT_TYPE = "unit_type";

    public static final String UNIT = "unit";

    public static final String SPEC = "spec";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

    public static final String F_CATEGORY_ID = "f_category_id";

    public static final String S_CATEGORY_ID = "s_category_id";

    public static final String STATUS = "status";
}
