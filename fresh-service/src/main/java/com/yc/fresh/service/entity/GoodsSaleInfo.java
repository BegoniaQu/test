package com.yc.fresh.service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 售卖商品信息
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsSaleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "goods_id", type = IdType.NONE)
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    /**
     * sku编码
     */
    private Long skuId;

    private Integer fCategoryId;
    private Integer sCategoryId;

    /**
     * 主图
     */
    private String mPicPath;

    /**
     * 原价
     */
    private BigDecimal rawPrice;

    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 起售数量(如火龙果两个一起卖)
     */
    private Integer bundles;

    /**
     * 计量值
     */
    private Integer saleCv;

    /**
     * 单位
     */
    private String unit;

    /**
     * 描述文字
     */
    private String description;

    /**
     * 详细文案
     */
    private String descrPath;

    /**
     * 状态：1-有效，0-废弃，2-上架，3-下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedTime;

    @TableField(exist = false)
    private Integer inventory;


    public static final String GOODS_ID = "goods_id";

    public static final String GOODS_NAME = "goods_name";

    public static final String WAREHOUSE_CODE = "warehouse_code";

    public static final String SKU_ID = "sku_id";

    public static final String F_CATEGORY_ID = "f_category_id";

    public static final String S_CATEGORY_ID = "s_category_id";

    public static final String M_PIC_PATH = "m_pic_path";

    public static final String RAW_PRICE = "raw_price";

    public static final String SALE_PRICE = "sale_price";

    public static final String SALE_CV = "sale_cv";

    public static final String UNIT = "unit";

    public static final String DESCRIPTION = "description";

    public static final String DESCR_PATH = "descr_path";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

}
