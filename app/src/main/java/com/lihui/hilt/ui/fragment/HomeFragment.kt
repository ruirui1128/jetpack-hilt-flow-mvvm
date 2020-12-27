package com.lihui.hilt.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.model.BannerDataModel
import com.lihui.hilt.ui.act.JhfActivity
import com.lihui.hilt.ui.adapter.ArticleAdapter
import com.lihui.hilt.ui.vm.HomeVm
import com.lihui.hilt.uitl.ToastUtil
import com.lihui.indiamall.util.ClickUtil
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.init
import com.rui.libray.ext.initLoadMore
import com.rui.libray.ext.loadMore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeVm>(), BGABanner.Delegate<ImageView, BannerDataModel>,
        BGABanner.Adapter<ImageView, BannerDataModel> {

    /**
     * fastmock接口不保存数据!!!  不保存数据!!!  不保存数据!!!
     * 仅仅是为了演示
     */

    private lateinit var adapter: ArticleAdapter

    private var pageNumber = 1

    public override val viewModelConfig: ViewModelConfig<HomeVm>
        get() = ViewModelConfig<HomeVm>(R.layout.fragment_home)
                .addViewModel(viewModels<HomeVm>().value, BR.homeVm)

    override fun init(savedInstanceState: Bundle?) {

        initView()
        initAdapter()
        initVm()
        initData(true)
    }

    private fun initAdapter() {
        adapter = ArticleAdapter(viewModel, this)
        //加载更多 loadMore = getArticleList()   在扩展里面
        adapter.initLoadMore { getArticleList() }
        rcvArticle.adapter = adapter
        adapter.setOnItemChildClickListener { ada, view, position ->
            if (ClickUtil.isFastDoubleClick) return@setOnItemChildClickListener
            when (view.id) {
                R.id.ivCollect -> {
                    //数据请求成功 更新状态
                    viewModel.getCollect {
                        val item = adapter.getItem(position)
                        item.isCollect = !item.isCollect
                        item.notifyChange()
                    }
                }
            }
        }

        adapter?.setOnItemClickListener { ada, view, position ->
            val item = adapter.getItem(position)
            val intent = Intent(activity,JhfActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(JhfActivity.JHA_DATA,item)
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    private fun initData(firstLoad: Boolean = false) {
        pageNumber = 1
        viewModel.getBanner { banner.setData(it, null) }
        getArticleList(firstLoad)
    }

    //获取文章数据
    private fun getArticleList(firstLoad: Boolean = false) {
        viewModel.getArticleList(pageNumber, firstLoad) { adapter.loadMoreModule.loadMoreFail() }
    }

    private fun initVm() {
        //文章数据返回
        viewModel.articleResult.observe(this, Observer {
            pageNumber = adapter.loadMore(it.list, pageNumber) { loadSirShowEmpty() }
        })
    }

    private fun initView() {
        swipe.init { initData(false) }
        banner.setAdapter(this)
        banner.setDelegate(this)
    }

    override fun onBannerItemClick(
            banner: BGABanner?,
            itemView: ImageView?,
            model: BannerDataModel?,
            position: Int
    ) {
        ToastUtil.toast("点击了$position")
    }

    override fun fillBannerItem(
            banner: BGABanner?,
            itemView: ImageView?,
            model: BannerDataModel?,
            position: Int
    ) {
        itemView?.context ?: return
        Glide.with(itemView.context)
                .load(model?.imagePath)
                .into(itemView)
    }
}