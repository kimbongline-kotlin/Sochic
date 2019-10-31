package com.sochic.sochic.SearchFolder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.BrandFolder.API.BrandAPI
import com.sochic.sochic.BrandFolder.Adapter.HomeBrandAdapter
import com.sochic.sochic.HomeFolder.API.HomeItemAPI
import com.sochic.sochic.HomeFolder.Adapter.HomeGridFeedAdapter
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SearchTagAPI
import com.sochic.sochic.SearchFolder.API.SubCategorySearchAPI
import com.sochic.sochic.SearchFolder.Adapter.RecentKeywordAdapter
import com.sochic.sochic.SearchFolder.Adapter.ResultTagAdapter
import com.sochic.sochic.SearchFolder.Adapter.SearchResultAdapter
import com.sochic.sochic.SearchFolder.Adapter.SearchResultGridAdapter
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.SpacesItemDecoration
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_search_result.*
import java.util.*
import kotlin.collections.ArrayList

class SearchResultActivity : ScActivity() {

    var getRecentItem = ArrayList<String>()

    private val RECENT_KEYWORD = "sochic_recent_keyword"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        supportActionBar!!.hide()

        keywordEdit.setText(intent.getStringExtra("keyword"))
        backBtn.setOnClickListener {

            finish()
        }

        getKeyword()

        keywordEdit.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener  false
        }

        keywordDeleteBtn.setOnClickListener {
            keywordEdit.setText("")
            hideKeyboard(this)
        }

        searchBtn.setOnClickListener {
            performSearch()
        }

        tabLayer.addTab(tabLayer.newTab().setText("PRODUCT"),true)
        tabLayer.addTab(tabLayer.newTab().setText("BRAND"))
        tabLayer.addTab(tabLayer.newTab().setText("TAG"))

        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                recycler.visibility = View.GONE
                noneLabel.visibility = View.GONE
                searchAction()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
        searchAction()

    }

    fun performSearch() {
        hideKeyboard(this)
        if(keywordEdit.text.toString().isEmpty()) {
            ShowToast("검색어를 입력해주세요.")

            getRecentItem.add(keywordEdit.text.toString())
            setStringArrayPref(applicationContext,RECENT_KEYWORD,getRecentItem)
            getKeyword()

            searchAction()

        }else {
            getRecentItem.add(keywordEdit.text.toString())
            setStringArrayPref(applicationContext,RECENT_KEYWORD,getRecentItem)
            getKeyword()

            searchAction()

        }


    }

    fun searchAction() {

        when (tabLayer.selectedTabPosition) {
            0 -> {
                productSearch()
            }

            1 -> {
                brandSearch()
            }

            2 -> {
                tagSearch()
            }
        }
    }

    fun productSearch() {
        recycler.layoutManager = GridLayoutManager(applicationContext,2)
        if(recycler.itemDecorationCount != 0) {
            recycler.removeItemDecorationAt(0)
        }
        recycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(10.0f,applicationContext).toInt(),2))
        disposable.add(apiService.KEYWORD_SEARCH_PRODUCT_API(id_user,keywordEdit.text.toString())
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SubCategorySearchAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SubCategorySearchAPI) {
                    var tempData = ArrayList<SubCategorySearchAPI.SubCategorySearchList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<SubCategorySearchAPI.SubCategorySearchList>
                        noneLabel.visibility = View.GONE
                        recycler.visibility  = View.VISIBLE
                    }else {
                        noneLabel.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                        noneLabel.setText("'${keywordEdit.text.toString()}'에 대한 상품이 없어요 :(")
                    }
                    var adapter= SearchResultAdapter(applicationContext,this@SearchResultActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun brandSearch() {
        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        if(recycler.itemDecorationCount != 0) {
            recycler.removeItemDecorationAt(0)
        }

        disposable.add(apiService.KEYWORD_SEARCH_BRAND_API(id_user,keywordEdit.text.toString())
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<BrandAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: BrandAPI) {
                    var tempData = ArrayList<BrandAPI.BrandList>()
                    if(t.success) {
                        noneLabel.visibility = View.GONE
                        recycler.visibility = View.VISIBLE
                        tempData = t.response as ArrayList<BrandAPI.BrandList>
                    }else {
                        noneLabel.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                        noneLabel.setText("'${keywordEdit.text.toString()}'에 대한 인플루언서 브랜드가 없어요 :(")
                    }
                    var adapter = HomeBrandAdapter(applicationContext,this@SearchResultActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun tagSearch() {
        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        if(recycler.itemDecorationCount != 0) {
            recycler.removeItemDecorationAt(0)
        }



        disposable.add(apiService.KEYWORD_SEARCH_TAG_FIRST_API(id_user,keywordEdit.text.toString())
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SearchTagAPI>() {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: SearchTagAPI) {

                    var tempData = ArrayList<SearchTagAPI.SearchTagList>()
                    if(t.success) {
                        recycler.visibility = View.VISIBLE
                        noneLabel.visibility = View.GONE
                        tempData = t.response as ArrayList<SearchTagAPI.SearchTagList>
                    }else {
                        noneLabel.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                        noneLabel.setText("'${keywordEdit.text.toString()}'에 대한 태그가 없어요 :(")
                    }
                    var adapter = ResultTagAdapter(applicationContext,this@SearchResultActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }

    fun tagResult(keyword : String) {
        recycler.visibility = View.GONE
        noneLabel.visibility = View.GONE
        recycler.layoutManager = GridLayoutManager(applicationContext,3)
        if(recycler.itemDecorationCount != 0) {
            recycler.removeItemDecorationAt(0)
        }
        recycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(5.0f,applicationContext).toInt(),3))

        disposable.add(apiService.KEYWORD_SEARCH_TAG_SEC_API(id_user,keywordEdit.text.toString())
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SubCategorySearchAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SubCategorySearchAPI) {
                    var tempData = ArrayList<SubCategorySearchAPI.SubCategorySearchList> ()
                    if(t.success) {
                        tempData = t.response as ArrayList<SubCategorySearchAPI.SubCategorySearchList>
                        noneLabel.visibility = View.GONE
                        recycler.visibility = View.VISIBLE
                    }else {
                        noneLabel.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                        noneLabel.setText("'${keywordEdit.text.toString()}'에 대한 태그가 없어요 :(")
                    }
                    var adapter = SearchResultGridAdapter(applicationContext,this@SearchResultActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun getKeyword() {
        getRecentItem = getStringArrayPref(applicationContext,RECENT_KEYWORD)

    }
}
