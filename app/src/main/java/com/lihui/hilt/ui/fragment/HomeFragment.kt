package com.lihui.hilt.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.ui.vm.MainViewModel
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.ViewModelConfig
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
class HomeFragment : BaseFragment<MainViewModel>() {

    override val viewModelConfig: ViewModelConfig<MainViewModel>
        get() = ViewModelConfig<MainViewModel>(R.layout.fragment_home)
            .addViewModel(viewModels<MainViewModel>().value,BR.homeVm)

    override fun init(savedInstanceState: Bundle?) {

    }
}