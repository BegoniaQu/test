package com.yc.fresh.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品副图
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsSalePic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 副图片路径
     */
    private String sPicPath;


    public static final String ID = "id";

    public static final String GOODS_ID = "goods_id";

    public static final String SORT = "sort";

    public static final String S_PIC_PATH = "s_pic_path";

}
