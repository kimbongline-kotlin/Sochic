package com.sochic.sochic.SearchFolder


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.HomeFolder.HomeActivity
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SubCategoryAPI
import com.sochic.sochic.SearchFolder.API.SubCategorySearchAPI
import com.sochic.sochic.SearchFolder.Adapter.SearchResultAdapter
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.SpacesItemDecoration
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.fragment_search_sub.*

/**
 * A simple [Fragment] subclass.
 */
class SearchSubFragment : Fragment() {

    var keyword : String = ""
    lateinit var mCon : Context
    lateinit var mAct : Activity
    lateinit var kAct : ScActivity
    var categoryIdx : String = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCon = context!!
        mAct = activity!!
        kAct = activity as ScActivity
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_sub, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("object",keyword)

        backBtn.setOnClickListener {
            (activity as HomeActivity).searchBackAction()
        }

        titleLabel.setText(keyword)

        when (keyword) {
            "Outer" -> {
                categoryIdx = "0"
            }
            "Top" -> {
                categoryIdx = "1"
            }
            "Pants" -> {
                categoryIdx = "2"
            }
            "Dress" -> {
                categoryIdx = "3"
            }
            "Knit" -> {
                categoryIdx = "4"
            }
            "Shoes" -> {
                categoryIdx = "5"
            }
            "Bag" -> {
                categoryIdx = "6"
            }
        }


        tabLayer.addTab(tabLayer.newTab().setText("ALL"),true)

        kAct.disposable.add(kAct.apiService.SUB_CATEGORY_API(categoryIdx)
            .subscribeOn(kAct.io)
            .observeOn(kAct.thread)
            .subscribeWith(object : DisposableSingleObserver<SubCategoryAPI> () {
                override fun onError(e: Throwable?) {
                    kAct.ConnectionError()
                }

                override fun onSuccess(t: SubCategoryAPI) {
                    if(t.success) {
                        for (i in 0..t.response.size) {
                            if(i == t.response.size) {
                                recycler.layoutManager = GridLayoutManager(mCon,2)
                                recycler.addItemDecoration(SpacesItemDecoration(kAct.convertDpToPixel(10.0f,mCon).toInt(),2))
                                getItem("-1")
                                tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                                    override fun onTabReselected(tab: TabLayout.Tab?) {

                                    }

                                    override fun onTabSelected(tab: TabLayout.Tab?) {
                                        Log.d("object",tab!!.position.toString())
                                        if(tab!!.position == 0) {
                                            getItem("-1")
                                        }else {
                                            getItem(t.response.get(tab!!.position-1).sub_category_idx)
                                        }
                                    }

                                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                                    }
                                })


                            }else {
                                tabLayer.addTab(tabLayer.newTab().setText(t.response.get(i).name))
                            }
                        }
                    }
                }
            }))




//


    }

    fun getItem(sIdx : String) {

        kAct.disposable.add(kAct.apiService.SUB_CATEGORY_SEARCH_API(categoryIdx,sIdx)
            .subscribeOn(kAct.io)
            .observeOn(kAct.thread)
            .subscribeWith(object : DisposableSingleObserver<SubCategorySearchAPI> () {
                override fun onError(e: Throwable?) {
                    kAct.ConnectionError()
                }

                override fun onSuccess(t: SubCategorySearchAPI) {
                    var tempData = ArrayList<SubCategorySearchAPI.SubCategorySearchList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<SubCategorySearchAPI.SubCategorySearchList>
                    }
                    var adapter = SearchResultAdapter(mCon,mAct,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

}
