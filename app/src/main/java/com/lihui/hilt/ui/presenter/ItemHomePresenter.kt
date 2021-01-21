package com.lihui.hilt.ui.presenter

import com.lihui.hilt.BR
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.ui.vm.HomeVm

/**
 *Created by Rui
 *on 2020/12/25
 */
object ItemHomePresenter {

    const val JIE = 1
    const val HUA = 2
    const val FA = 3

    /**
     * 改变头部图片
     */
    fun changeHeader(viewModel: HomeVm, model: ArticleModel) {
        viewModel.changeHeader {
            model.url = it ?: ""
            //notifyPropertyChanged 结合 bindAble 只更新条目中的一个字段
            model.notifyPropertyChanged(BR.url)
        }
    }

    /**
     * 接化发       接 化  发 三者互斥
     */
    fun jhf(viewModel: HomeVm, model: ArticleModel,jfa:Int){
        viewModel.jhf {
            when(jfa){
                JIE->{
                    model.isHua = false
                    model.isFa  = false
                    model.isJie = !model.isJie
                }
                HUA->{
                    model.isJie = false
                    model.isFa  = false
                    model.isHua = !model.isJie
                }
                FA->{
                    model.isJie = false
                    model.isHua  = false
                    model.isFa = !model.isJie
                }
            }
            model.notifyChange()
        }
    }


}