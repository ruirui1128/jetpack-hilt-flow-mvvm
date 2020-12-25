package com.lihui.hilt.di


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.lihui.hilt.ui.fragment.HomeFragment
import com.lihui.hilt.ui.vm.HomeVm
import com.rui.libray.base.BaseFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext


/**
 *Created by Rui
 *on 2020/12/25
 */
@InstallIn(FragmentComponent::class)
@Module
object PresenterModel{

//    @Provides
//    fun providerHomeVm(@ActivityContext home: HomeFragment) = home.viewModelConfig.getViewModel()

    @Provides
    fun providerFragmentManager(@ActivityContext context: Context) = (context as FragmentActivity).supportFragmentManager
}