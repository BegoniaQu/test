package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */

@Getter
@Setter
@RespData
@ApiModel(description = "分类返回信息")
public class GdCategoryRespVO {

    private Integer id;
    private String name;
    private String picUrl;
    @ApiModelProperty("父ID")
    private Integer pId; //parentId
    //private List<GdSecondCategory> scl;



   /* @Getter
    @Setter
    public static class GdSecondCategory{
        private Integer sId;
        private String name;
    }*/
}
