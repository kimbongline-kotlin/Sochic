package com.sochic.sochic.SellerFolder

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.github.irshulx.EditorListener
import com.github.irshulx.models.EditorTextStyle
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.activity_editor.*
import top.defaults.colorpicker.ColorPickerPopup
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


class EditorActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sochic.sochic.R.layout.activity_editor)
        supportActionBar!!.hide()


        if(!intent.getStringExtra("html").toString().isEmpty()) {
            editor.render(intent.getStringExtra("html"))
        }



        boldBtn.setOnClickListener {
            editor.updateTextStyle(EditorTextStyle.BOLD)
        }

        hBtn.setOnClickListener {
            editor.updateTextStyle(EditorTextStyle.H2)
        }

        italicBtn.setOnClickListener {
            editor.updateTextStyle(EditorTextStyle.ITALIC)
        }

        intentBtn.setOnClickListener {
            editor.updateTextStyle(EditorTextStyle.INDENT)
        }

        outTentBtn.setOnClickListener {
            editor.updateTextStyle(EditorTextStyle.OUTDENT)
        }


        numberlicBtn.setOnClickListener {
            editor.insertList(true)
        }

        bulletBtn.setOnClickListener {
            editor.insertList(false)
        }

        backBtn.setOnClickListener {
            finish()
        }

        colorBtn.setOnClickListener {
            ColorPickerPopup.Builder(this)
                .initialColor(com.sochic.sochic.R.color.colorPrimary)
                .enableAlpha(true)
                .okTitle("선택")
                .cancelTitle("취소")
                .showIndicator(true)
                .showValue(false)
                .build()
                .show(bLayer,object : ColorPickerPopup.ColorPickerObserver() {
                    override fun onColorPicked(color: Int) {
                        editor.updateTextColor(colorHex(color))
                    }
                })
        }

        editor.setEditorImageLayout(R.layout.editor_img_view)
        editor.editorListener = object : EditorListener {
            override fun onRenderMacro(
                name: String?,
                props: MutableMap<String, Any>?,
                index: Int
            ): View {

                return view12
            }

            override fun onTextChanged(editText: EditText, text: Editable) {
                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            override fun onUpload(image: Bitmap, uuid: String) {

                Log.d("object",uuid)
                Log.d("object","hihi")
                //do your upload image operations here, once done, call onImageUploadComplete and pass the url and uuid as reference.
                editor.onImageUploadComplete(
                    "http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg",
                    uuid
                )

            }
        }
        imageBtn.setOnClickListener {
            editor.openImagePicker()
        }

        saveBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("html", editor.contentAsHTML)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }
    }

    private fun colorHex(color: Int): String {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return String.format(Locale.getDefault(), "#%02X%02X%02X", r, g, b)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode  , resultCode, data)
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data!!.data
            try {

                val bm = MediaStore.Images.Media.getBitmap(contentResolver, uri)
               addEdit(bm)

            } catch (e: FileNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
            Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_SHORT).show()
            // editor.RestoreState();
        }
    }

    fun addEdit(bit: Bitmap) {
        editor.insertImage(bit)
    }
}
