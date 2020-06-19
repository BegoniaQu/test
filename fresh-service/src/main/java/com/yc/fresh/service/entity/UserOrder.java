package com.yc.fresh.service.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.yc.fresh.common.cache.annotation.CacheId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户订单
 * </p>
 *
 * @author Quy
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 订单号
     */
    @CacheId
    private String orderId;

    /**
     * 交易编码
     */
    private String payTransId;

    /**
     * 备用
     */
    private String logTransId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货人联系电话
     */
    private String mobile;

    /**
     * 送货地址
     */
    private String address;

    /**
     * 0-待支付 1-待配送 2-配送中 3-待评价 4- 已完成 5-已取消
     */
    private Integer status;

    /**
     * 备注
     */
    private String comment;

    /**
     * 配送日期
     */
    private LocalDate deliveryDate;

    /**
     * 配送开始时间点
     */
    private String deliveryStartTime;

    /**
     * 配送结束时间点
     */
    private String deliveryEndTime;

    /**
     * 配送费
     */
    private BigDecimal deliveryFee;

    /**
     * 总金额
     */
    private BigDecimal amount;

    /**
     * 优惠券金额
     */
    private BigDecimal couponAmt;

    /**
     * 应付金额
     */
    private BigDecimal realAmt;

    /**
     * 实付金额,在收到支付回调后更新
     */
    private BigDecimal realPay;

    /**
     * 1-微信，2-支付宝
     */
    private Integer payWay;

    /**
     * 配送人员Id
     */
    private Long dmId;

    /**
     * 订单创建时间
     */
    private LocalDateTime orderCreateTime;

    /**
     * 订单支付时间
     */
    private LocalDateTime orderPaidTime;

    /**
     * 订单配送时间
     */
    private LocalDateTime orderDeliveringTime;

    /**
     * 订单完成时间
     */
    private LocalDateTime orderFinishedTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastModifiedTime;


    public static final String ID = "id";

    public static final String WAREHOUSE_CODE = "warehouse_code";

    public static final String USER_ID = "user_id";

    public static final String ORDER_ID = "order_id";

    public static final String PAY_TRANS_ID = "pay_trans_id";

    public static final String LOG_TRANS_ID = "log_trans_id";

    public static final String RECEIVER = "receiver";

    public static final String MOBILE = "mobile";

    public static final String ADDRESS = "address";

    public static final String STATUS = "status";

    public static final String COMMENT = "comment";

    public static final String DELIVERY_DATE = "delivery_date";

    public static final String DELIVERY_START_TIME = "delivery_start_time";

    public static final String DELIVERY_END_TIME = "delivery_end_time";

    public static final String DELIVERY_FEE = "delivery_fee";

    public static final String AMOUNT = "amount";

    public static final String COUPON_AMT = "coupon_amt";

    public static final String REAL_AMT = "real_amt";

    public static final String REAL_PAY = "real_pay";

    public static final String PAY_WAY = "pay_way";

    public static final String DM_ID = "dm_id";

    public static final String ORDER_CREATE_TIME = "order_create_time";

    public static final String ORDER_PAID_TIME = "order_paid_time";

    public static final String ORDER_DELIVERING_TIME = "order_delivering_time";

    public static final String ORDER_FINISHED_TIME = "order_finished_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

}
