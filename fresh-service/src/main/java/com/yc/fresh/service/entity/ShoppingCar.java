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
 * 购物车
 * </p>
 *
 * @author Quy
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShoppingCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String GOODS_ID = "goods_id";

    public static final String NUM = "num";

    public static final String CREATE_TIME = "create_time";

}
