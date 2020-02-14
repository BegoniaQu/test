package com.yc.fresh.common.utils;


import com.yc.fresh.common.ResultBean;

/**
 * Created by quyang on 2018/4/2.
 */
public class ResultUtils {

    public static <T> ResultBean ok(T data){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setData(data);
        resultBean.setMsg("ok");
        return resultBean;
    }

    public static  ResultBean ok(){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setData("");
        resultBean.setMsg("ok");
        return resultBean;
    }

    public static  ResultBean fail(int code, String errMsg){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setData("");
        resultBean.setMsg(errMsg);
        return resultBean;
    }

}
