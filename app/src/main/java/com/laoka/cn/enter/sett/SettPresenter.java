package com.laoka.cn.enter.sett;

import com.laoka.cn.base.mvp.BasePresenter;

/**
 * @author 张海洋
 * @Date on 2019/04/11.
 * @org 上海..科技有限公司
 * @describe
 */
public class SettPresenter extends BasePresenter<SettContract.Model,SettContract.View>{
    @Override
    protected SettContract.Model createModel() {
        return new SettModel();
    }


    /**
     *   上传图片和其他操作
     */



}
