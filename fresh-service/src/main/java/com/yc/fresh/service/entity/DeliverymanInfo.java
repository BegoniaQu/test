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
 * 配送人员表
 * </p>
 *
 * @author Quy
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeliverymanInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配送人员ID
     */
    @TableId(value = "dm_id", type = IdType.AUTO)
    private Long dmId;

    /**
     * 姓名
     */
    private String dmName;

    /**
     * 头像照片
     */
    private String dmPhoto;

    /**
     * 身份证号
     */
    private String dmIcNum;

    /**
     * 身份证正面
     */
    private String dmIcPicZ;

    /**
     * 身份证反面
     */
    private String dmIcPicF;

    /**
     * 手机号
     */
    private String mobile;

    private String nowAddress;

    /**
     * 0-待审核，1-审核中，2-正常 3-审核不通过
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


    public static final String DM_ID = "dm_id";

    public static final String DM_NAME = "dm_name";

    public static final String DM_PHOTO = "dm_photo";

    public static final String DM_IC_NUM = "dm_ic_num";

    public static final String DM_IC_PIC_Z = "dm_ic_pic_z";

    public static final String DM_IC_PIC_F = "dm_ic_pic_f";

    public static final String MOBILE = "mobile";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_MODIFIED_TIME = "last_modified_time";

}
