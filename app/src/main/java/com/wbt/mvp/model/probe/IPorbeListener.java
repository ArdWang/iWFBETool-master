package com.wbt.mvp.model.probe;

import com.wbt.bean.ProbeBean;

import java.util.List;


/**
 * Created by Administrator on 2018/2/1.
 *
 */

public interface IPorbeListener {
    /**
     * 成功
     */
    void onProbeSuccess(List<ProbeBean.ProbesBean> probesBean);

    /**
     * 失败
     */
    void onProbeFail(String message);

    /**
     * 错误
     */
    void onProbeError(String message);
}
