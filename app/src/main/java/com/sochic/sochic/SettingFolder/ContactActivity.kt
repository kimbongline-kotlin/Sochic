package com.sochic.sochic.SettingFolder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.API.ContactAPI
import com.sochic.sochic.SettingFolder.Adapter.ContactAdapter
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_contact.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ContactActivity : ScActivity() {

    private val PROFILE_RESULT = 1234
    public var imageSelect : Boolean = false
    lateinit var imageFile : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!!.position == 0) {
                    writeLayer()
                }else {
                    listLayer()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        deleteBtn.setOnClickListener {

            imageSelect = false
            sView.visibility = View.GONE
            deleteBtn.visibility = View.GONE
            addBtn.visibility = View.VISIBLE
        }

        tabLayer.addTab(tabLayer.newTab().setText("문의하기"),true)
        tabLayer.addTab(tabLayer.newTab().setText("문의내역"))
    }

    fun writeLayer() {
        imageSelect = false
        writeLayer.visibility  = View.VISIBLE
        writeBtn.visibility = View.VISIBLE
        recycler.visibility = View.GONE
        titleEdit.setText("")
        contentsEdit.setText("")
        sView.visibility = View.GONE
        addBtn.visibility = View.VISIBLE
        deleteBtn.visibility = View.GONE

        addBtn.setOnClickListener {
            galleryOpen()
        }

        writeBtn.setOnClickListener {
            if(titleEdit.text.toString().isEmpty()) {
                ShowToast("문의 제목을 입력해주세요.")
            }else if(contentsEdit.text.toString().isEmpty()) {
                ShowToast("문의 내용을 입력해주세요.")
            }else {
                var builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart("id_user",id_user)
                builder.addFormDataPart("title",titleEdit.text.toString())
                builder.addFormDataPart("contents",contentsEdit.text.toString())
                if(imageSelect) {
                    builder.addFormDataPart("image",imageFile.name, RequestBody.create(
                        MediaType.parse("multipart/form-data"),imageFile))
                }


                disposable.add(apiService.CONTACT_WRITE_API(builder.build())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("문의 내용이 등록되었습니다.")
                                writeLayer()
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }
        }
    }

    fun listLayer() {
        writeLayer.visibility = View.GONE
        writeBtn.visibility = View.GONE
        recycler.visibility = View.VISIBLE

        disposable.add(apiService.CONTACT_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ContactAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ContactAPI) {

                    var tempData = ArrayList<ContactAPI.ContactList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<ContactAPI.ContactList>
                    }
                    var adapter = ContactAdapter(applicationContext,this@ContactActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
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
                imageFile = file
                imageSelect = true
                sView.visibility = View.VISIBLE
                deleteBtn.visibility = View.VISIBLE
                addBtn.visibility = View.GONE
                ScImage.image(path,sView)
            }
           // profileChange(file)
        }
    }
}
