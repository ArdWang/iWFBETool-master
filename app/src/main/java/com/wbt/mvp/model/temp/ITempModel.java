package com.wbt.mvp.model.temp;

import com.wbt.mvp.model.login.ILoginListener;

/**
 * Created by Administrator on 2018/1/18.
 */

public interface ITempModel {
    /**
     * 提取的一个登陆方法，当然还可以有其它方法，比如获取数据，保存用户信息之类
     */
    public void getTemp(String tpdvid,ITempListener tempListener);
}
