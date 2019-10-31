package com.sochic.sochic.MyPageFolder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sochic.sochic.LoginFolder.Adapter.RegisterCategoryAdapter
import com.sochic.sochic.LoginFolder.RegisterCompleteActivity
import com.sochic.sochic.MyPageFolder.API.ProfileLoadAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import com.sochic.sochic.Util.SpacesItemDecoration
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_add_info.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.backBtn
import kotlinx.android.synthetic.main.activity_profile.birthDayEdit
import kotlinx.android.synthetic.main.activity_profile.profileView
import kotlinx.android.synthetic.main.activity_profile.recycler
import kotlinx.android.synthetic.main.fragment_mypage.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileActivity : ScActivity() {

    private val PROFILE_RESULT = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }



        profileView.setOnClickListener {
            galleryOpen()
        }

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

        marketingBtn.setOnClickListener {
            marketingRadio.isSelected = !marketingRadio.isSelected
        }

        marketingRadio.setOnClickListener {
            marketingBtn.callOnClick()
        }

        var adapter = RegisterCategoryAdapter(applicationContext,this,categoryItem,categorySelectItem)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()

        getData()

        changeBtn.setOnClickListener {
            if(emailEdit.text.toString().isEmpty()){
                ShowToast("이메일 주소를 입력해주세요.")
            }else {
                var getData = (recycler.adapter as RegisterCategoryAdapter).boolDatas
                var selectItem = ArrayList<String>()
                var marKetSelect : Int = 0
                if(marketingRadio.isSelected) {
                    marKetSelect = 1
                }else {
                    marKetSelect = 0
                }
                for (i in 0..getData.size) {
                    if( i == getData.size){
                        disposable.add(apiService.PROFILE_INFO_MODIFY_API(id_user,emailEdit.text.toString(),
                            birthDayEdit.text.toString(),
                            selectItem,marKetSelect)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: TrueFalseAPI) {
                                    if(t.success) {
                                        ShowToast("프로필 정보가 변경되었습니다.")
                                    }else {
                                        ConnectionError()
                                    }
                                }
                            }))

                    }else {
                        if(getData.get(i)){
                            selectItem.add("$i")
                        }
                    }
                }
            }
        }

    }

    fun getData() {
        disposable.add(apiService.PROFILE_LOAD_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProfileLoadAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProfileLoadAPI) {
                    if(t.success) {
                        ScImage.CircleImage(t.profile_image,profileView)
                        idEdit.setText(t.email)
                        emailEdit.setText(t.email)
                        birthDayEdit.setText(t.birthday)

                        if(t.market_value == 1) {
                            marketingRadio.isSelected = true
                        }else {
                            marketingRadio.isSelected = false
                        }
                        for(i in 0..t.interest_response.size) {
                            if(i == t.interest_response.size) {

                            }else {
                                var data = (recycler.adapter as RegisterCategoryAdapter).boolDatas
                                data.set(t.interest_response.get(i).interest.toInt(),true)
                                (recycler.adapter as RegisterCategoryAdapter).notifyDataSetChanged()
                            }
                        }
                    }
                }
            }))
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
        startActivityForResult(Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI),PROFILE_RESULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PROFILE_RESULT && resultCode == Activity.RESULT_OK) {
            var imagePath = data!!.data
            var file = File(getRealPathFromURI(applicationContext,imagePath))
            var path = file.path
            runOnUiThread {
                changeProfileView(path)
            }
            profileChange(file)
        }
    }

    fun changeProfileView(name : String) {
        ScImage.CircleImage(name,profileView)
    }

    fun profileChange(file : File) {

        var builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("id_user",id_user)
        builder.addFormDataPart("image",file.name, RequestBody.create(
            MediaType.parse("multipart/form-data"),file))

        disposable.add(apiService.PROFILE_IMAGE_UPDATE_API(builder.build())
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TrueFalseAPI) {
                    if(t.success) {
                        ShowToast("프로필 사진을 변경하였습니다.")
                    }else {
                        ConnectionError()
                    }
                }
            }))

    }
}
