package com.sochic.sochic.LoginFolder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.sochic.sochic.LoginFolder.Adapter.RegisterCategoryAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.SpacesItemDecoration
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_add_info.*

class AddInfoActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)
        supportActionBar!!.hide()

        recycler.layoutManager = GridLayoutManager(applicationContext,3)
        recycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(15.0f,applicationContext).toInt(),3))

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

        view.setOnClickListener {
            hideKeyboard(this)
        }

        scrollView.setOnTouchListener { v, event ->
            hideKeyboard(this)
            return@setOnTouchListener true
        }
        var adapter = RegisterCategoryAdapter(applicationContext,this,categoryItem,categorySelectItem)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()

        nextBtn.setOnClickListener {

            finish()
            startActivity(Intent(applicationContext,RegisterCompleteActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        completeBtn.setOnClickListener {

            if(birthDayEdit.text.toString().isEmpty()) {
                ShowToast("생년월일을 입력해주세요.")
            }else {
                var getData = (recycler.adapter as RegisterCategoryAdapter).boolDatas
                var selectItem = ArrayList<String>()

                for (i in 0..getData.size) {
                    if( i == getData.size){
                        disposable.add(apiService.ADD_INFO_API(id_user,
                            birthDayEdit.text.toString(),
                            selectItem)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: TrueFalseAPI) {
                                    if(t.success) {
                                        finish()
                                        goTo(RegisterCompleteActivity::class.java)
                                    }else {
                                        ConnectionError()
                                    }
                                }
                            }))

                        Log.d("object",selectItem.toString())
                    }else {
                        if(getData.get(i)){
                            selectItem.add("$i")
                        }
                    }
                }
            }


        }

    }
}
