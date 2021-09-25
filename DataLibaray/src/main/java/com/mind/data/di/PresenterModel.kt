package com.mind.data.di


import android.content.Context
import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext


/**
 *Created by Rui
 *on 2020/12/25
 */
//@InstallIn(FragmentComponent::class)
//@Module
//object PresenterModel{
////
////    @Provides
////    @ActivityScoped
////    fun providerHomeFragment(@ActivityContext home: HomeFragment) = home
////
////    @Provides
////    @FragmentScoped
////    fun providerLifecycleOwner(@ActivityContext owner: Context) = (owner as FragmentActivity).lifecycle
//
//    @Provides
//    fun providerFragmentManager(@ActivityContext context: Context) = (context as FragmentActivity).supportFragmentManager
//}