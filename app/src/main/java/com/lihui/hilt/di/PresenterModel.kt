package com.lihui.hilt.di


import android.content.Context

import androidx.fragment.app.FragmentActivity

import com.lihui.hilt.ui.fragment.HomeFragment

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped


/**
 *Created by Rui
 *on 2020/12/25
 */
@InstallIn(FragmentComponent::class)
@Module
object PresenterModel{
//
//    @Provides
//    @ActivityScoped
//    fun providerHomeFragment(@ActivityContext home: HomeFragment) = home
//
//    @Provides
//    @FragmentScoped
//    fun providerLifecycleOwner(@ActivityContext owner: Context) = (owner as FragmentActivity).lifecycle

    @Provides
    fun providerFragmentManager(@ActivityContext context: Context) = (context as FragmentActivity).supportFragmentManager
}