
package com.sochic.sochic.MyPageFolder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sochic.sochic.MyPageFolder.API.ReviewAPI
import com.sochic.sochic.MyPageFolder.Adapter.ReviewItemAdapter
import com.sochic.sochic.MyPageFolder.Adapter.ReviewPhotoAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_review_write.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ReviewWriteActivity : ScActivity() {


    private val REVIEW_RESULT = 12345
    var fileList = ArrayList<File>()
    var getIdx : String = ""
    var oCode : String = ""
    var rate : Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")
        oCode = intent.getStringExtra("ocode")
        rRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        viewPhoto()

        getData()

        ratingbar.setOnRatingChangeListener {
            rate = it
        }

        completeBtn.setOnClickListener {
            if(fileList.size == 0) {
                ShowToast("최소 이미지 한장을 선택해주세요.")
            }else if(infoEdit.text.toString().isEmpty()) {
                ShowToast("리뷰 내용을 입력해주세요.")
            }else {
                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart("id_user", id_user)
                builder.addFormDataPart("contents", infoEdit.getText().toString())
                builder.addFormDataPart("sub_o_index", getIdx)
                builder.addFormDataPart("o_code",oCode)
                builder.addFormDataPart("rate","$rate")
                for (i in 0..fileList.size) {
                    if(i == fileList.size) {
                        disposable.add(apiService.REVIEW_WRITE_API(builder.build())
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: TrueFalseAPI) {
                                    if(t.success) {

                                        ShowToast("리뷰 작성이 완료되었습니다.")
                                        finish()
                                    }else {
                                        ConnectionError()
                                    }
                                }
                            }))
                    }else {
                        builder.addFormDataPart(
                            "image[]",
                            fileList.get(i).name,
                            RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                fileList.get(i)
                            )
                        )
                    }
                }

            }
        }



    }

    fun getData() {

        disposable.add(apiService.REVIEW_DETAIL_API(id_user,getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ReviewAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ReviewAPI) {

                    var tempData = ArrayList<ReviewAPI.ReviewList>()
                    if(t.success) {


                        tempData = t.response as ArrayList<ReviewAPI.ReviewList>
                    }
                    var adapter = ReviewItemAdapter(applicationContext,this@ReviewWriteActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun addImageAct() {
        if(fileList.size == 10) {
            ShowToast("리뷰 이미지는 최대 10장까지 가능합니다.")
        }else {
            galleryOpen()
        }
    }

    fun galleryOpen() {

        TedPermission.with(this)
            .setPermissionListener(object: PermissionListener {
                override fun onPermissionGranted() {
                    TakePickture()
                }

                override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                    ShowToast("권한 설정 후 이용해주세요.")
                }


            })
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) // 확인할 권한을 다중 인자로 넣어줍니다.
            .check()

    }

    fun TakePickture() {
        startActivityForResult(Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI),REVIEW_RESULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REVIEW_RESULT && resultCode == Activity.RESULT_OK) {
            var imagePath = data!!.data
            var file = File(getRealPathFromURI(applicationContext,imagePath))
            var path = file.path
            runOnUiThread {
                //changeProfileView(path)
            }
            fileList.add(file)
            viewPhoto()
          //  profileChange(file)
        }
    }

    fun viewPhoto() {
        var adapter = ReviewPhotoAdapter(applicationContext,this,fileList)
        rRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
