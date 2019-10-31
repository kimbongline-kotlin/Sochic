package com.sochic.sochic.BrandFolder


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.BrandFolder.API.BrandAPI
import com.sochic.sochic.BrandFolder.Adapter.BrandFilterAdapter
import com.sochic.sochic.BrandFolder.Adapter.HomeBrandAdapter
import com.sochic.sochic.MyPageFolder.MyProductActivity
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.FilterActivity
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.fragment_brand.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BrandFragment : Fragment() {

    lateinit var mCon : Context
    lateinit var mAct : Activity
    lateinit var kAct : ScActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCon = context!!
        mAct = activity!!
        kAct = mAct as ScActivity
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brand, container, false)
    }
    var categoryItem = ArrayList<String>()
    var ageItem = ArrayList<String>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(mCon,RecyclerView.VERTICAL,false)

        getItem()
        cartBtn.setOnClickListener {
            startActivity(
                Intent(context, MyProductActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra("type","1"))
        }

        tabs.setOnClickListener {
            filterBtn.callOnClick()
        }
        filterBtn.setOnClickListener {
            startActivityForResult(Intent(mCon,FilterActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),8288)
        }
        fRecycler.layoutManager = LinearLayoutManager(mCon,RecyclerView.HORIZONTAL,false)


        categoryItem.add("심플베이직")
        categoryItem.add("모던시크")
        categoryItem.add("오피스룩")
        categoryItem.add("러블리")
        categoryItem.add("페미닌")
        categoryItem.add("큐트")
        categoryItem.add("섹시")
        categoryItem.add("유니크")
        categoryItem.add("빈티지")


        ageItem.add("20대 초중반")
        ageItem.add("20대 중후반")
        ageItem.add("30대 초중반")
        ageItem.add("30대 중후반")
        ageItem.add("40대 초중반")
        ageItem.add("40대 중반이후")

        filterIcon.setOnClickListener {
            if(filterIcon.isSelected) {
                filterIcon.isSelected = false
                fRecycler.visibility = View.GONE
                textView11.visibility = View.VISIBLE

                getItem()
            }else {
                filterBtn.callOnClick()
            }
        }

    }

    fun getItem() {
        kAct.disposable.add(kAct.apiService.BRAND_API(kAct.id_user)
            .subscribeOn(kAct.io)
            .observeOn(kAct.thread)
            .subscribeWith(object : DisposableSingleObserver<BrandAPI> () {
                override fun onError(e: Throwable?) {
                    kAct.ConnectionError()
                }

                override fun onSuccess(t: BrandAPI) {
                    var tempData = ArrayList<BrandAPI.BrandList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<BrandAPI.BrandList>
                    }
                    var adapter = HomeBrandAdapter(mCon,mAct,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun getFilterItem(quick : Int, age_group : ArrayList<String>, interst : ArrayList<String>) {
        kAct.disposable.add(kAct.apiService.BRAND_FILTER_API(kAct.id_user,quick,age_group,interst)
            .subscribeOn(kAct.io)
            .observeOn(kAct.thread)
            .subscribeWith(object : DisposableSingleObserver<BrandAPI> () {
                override fun onError(e: Throwable?) {
                    kAct.ConnectionError()
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
                    }
                    var adapter = HomeBrandAdapter(mCon,mAct,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 8288){
            var quickValue = data!!.getIntExtra("quick",0)
            var ageGroup = data!!.getStringArrayListExtra("age_group")
            var interest = data!!.getStringArrayListExtra("interest")
            var selectItem = ArrayList<String>()

            if(quickValue == 0 && ageGroup.size == 0 && interest.size == 0){
                getItem()
                filterIcon.isSelected = false
                selectItem.clear()
                textView11.visibility = View.VISIBLE
                fRecycler.visibility = View.GONE
                var adapter = BrandFilterAdapter(mCon,mAct,selectItem)
                fRecycler.adapter = adapter
                adapter.notifyDataSetChanged()
            }else {
                getFilterItem(quickValue,ageGroup,interest)

                filterIcon.isSelected = true
                if(quickValue == 1) {
                    selectItem.add("신상품")
                }else if(quickValue == 2) {
                    selectItem.add("인기순")
                }else if(quickValue == 3) {
                    selectItem.add("리뷰순")
                }
                fRecycler.setOnClickListener {
                    filterBtn.callOnClick()
                }
                for (i in 0 .. ageGroup.size) {
                    if( i == ageGroup.size) {
                        for (k in 0.. interest.size) {

                            if(k == interest.size) {
                                textView11.visibility = View.GONE
                                fRecycler.visibility = View.VISIBLE
                                var adapter = BrandFilterAdapter(mCon,mAct,selectItem)
                                fRecycler.adapter = adapter
                                adapter.notifyDataSetChanged()

                            }else {
                                selectItem.add(categoryItem.get(k))
                            }
                        }
                    }else {
                        selectItem.add(ageItem.get(i))
                    }


                }
            }
        }

    }


}
