package com.sochic.sochic.SearchFolder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.Adapter.FilterAgeAdapter
import com.sochic.sochic.SearchFolder.Adapter.FilterStyleAdapter
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : ScActivity() {

    var quickValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        supportActionBar!!.hide()

        ageRecycler.layoutManager = GridLayoutManager(applicationContext,3)
        ageRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(15.0f,applicationContext).toInt(),3))

        styleRecycler.layoutManager = GridLayoutManager(applicationContext,3)
        styleRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(15.0f,applicationContext).toInt(),3))


        backBtn.setOnClickListener {

            finish()

        }

        initBtn.setOnClickListener {
            initRecycler()
            initBtns()
            newBtn.callOnClick()
        }
        initRecycler()
        initBtns()

        newBtn.setOnClickListener {
            if(newBtn.isSelected) {
                initBtns()
                quickValue = 0
            }else {
                initBtns()
                newBtn.isSelected = true
                newBtn.setTextColor(resources.getColor(R.color.blackColor))
                quickValue = 1
            }

        }

        newBtn.callOnClick()

        bestBtn.setOnClickListener {
            if(bestBtn.isSelected) {
                initBtns()
                quickValue = 0
                newBtn.isSelected = true
                newBtn.setTextColor(resources.getColor(R.color.blackColor))
                quickValue = 1
            }else {
                initBtns()
                bestBtn.isSelected = true
                bestBtn.setTextColor(resources.getColor(R.color.blackColor))
                quickValue = 2

            }

        }

        reviewBtn.setOnClickListener {
            if(reviewBtn.isSelected) {
                initBtns()
                quickValue = 0
                newBtn.isSelected = true
                newBtn.setTextColor(resources.getColor(R.color.blackColor))
                quickValue = 1
            }else {
                initBtns()
                reviewBtn.isSelected = true
                reviewBtn.setTextColor(resources.getColor(R.color.blackColor))
                quickValue = 3

            }

        }

        applyBtn.setOnClickListener {

            var getAgeData = (ageRecycler.adapter as FilterAgeAdapter).boolDatas
            var getInterestData = (styleRecycler.adapter as FilterStyleAdapter).boolDatas
            var postAgeData = ArrayList<String>()
            var postInterestData = ArrayList<String>()
            for (i in 0..getAgeData.size-1) {
                if(getAgeData.get(i)){
                    postAgeData.add("$i")
                }
            }

            for (i in 0..getInterestData.size-1) {
                if(getInterestData.get(i)) {
                    postInterestData.add("$i")
                }
            }

            var intent = Intent()
            intent.putExtra("quick",quickValue)
            intent.putExtra("age_group",postAgeData)
            intent.putExtra("interest",postInterestData)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    fun initBtns() {
        newBtn.isSelected = false
        bestBtn.isSelected = false
        reviewBtn.isSelected = false

        newBtn.setTextColor(resources.getColor(R.color.borderLineColor))
        bestBtn.setTextColor(resources.getColor(R.color.borderLineColor))
        reviewBtn.setTextColor(resources.getColor(R.color.borderLineColor))
        quickValue = 0


    }

    fun initRecycler(){
        var categoryItem = ArrayList<String>()
        categoryItem.add("심플베이직")
        categoryItem.add("모던시크")
        categoryItem.add("오피스룩")
        categoryItem.add("러블리")
        categoryItem.add("페미닌")
        categoryItem.add("큐트")
        categoryItem.add("섹시")
        categoryItem.add("유니크")
        categoryItem.add("빈티지")

        var categorySelectItem = ArrayList<Boolean>()
        categorySelectItem.add(false)
        categorySelectItem.add(false)
        categorySelectItem.add(false)
        categorySelectItem.add(false)
        categorySelectItem.add(false)
        categorySelectItem.add(false)
        categorySelectItem.add(false)
        categorySelectItem.add(false)
        categorySelectItem.add(false)


        var ageItem = ArrayList<String>()
        ageItem.add("20대 초중반")
        ageItem.add("20대 중후반")
        ageItem.add("30대 초중반")
        ageItem.add("30대 중후반")
        ageItem.add("40대 초중반")
        ageItem.add("40대 중반이후")

        var ageBoolItem = ArrayList<Boolean>()
        ageBoolItem.add(false)
        ageBoolItem.add(false)
        ageBoolItem.add(false)
        ageBoolItem.add(false)
        ageBoolItem.add(false)
        ageBoolItem.add(false)


        var ageAdapter = FilterAgeAdapter(applicationContext,this,ageItem,ageBoolItem)
        var styleAdapter = FilterStyleAdapter(applicationContext,this,categoryItem,categorySelectItem)

        ageRecycler.adapter = ageAdapter
        styleRecycler.adapter = styleAdapter
        ageAdapter.notifyDataSetChanged()
        styleAdapter.notifyDataSetChanged()
    }
}
