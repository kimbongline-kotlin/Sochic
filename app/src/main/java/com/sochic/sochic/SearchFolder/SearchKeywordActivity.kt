package com.sochic.sochic.SearchFolder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.SearchFolder.Adapter.RecentKeywordAdapter
import kotlinx.android.synthetic.main.activity_search_keyword.*
import java.util.*
import kotlin.collections.ArrayList


class SearchKeywordActivity : ScActivity() {

    var getRecentItem = ArrayList<String>()

    private val RECENT_KEYWORD = "sochic_recent_keyword"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sochic.sochic.R.layout.activity_search_keyword)
        supportActionBar!!.hide()


        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
       // getKeyword()

        backBtn.setOnClickListener {
            finish()
        }
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
    }

    fun performSearch() {
        hideKeyboard(this)
        if(keywordEdit.text.toString().isEmpty()) {
            ShowToast("검색어를 입력해주세요.")
        }else {
            getRecentItem.add(keywordEdit.text.toString())
            setStringArrayPref(applicationContext,RECENT_KEYWORD,getRecentItem)
            getKeyword()
            goToResult(keywordEdit.text.toString())
        }

    }


    override fun onResume() {
        super.onResume()
        getKeyword()
    }
    fun getKeyword() {
        getRecentItem = getStringArrayPref(applicationContext,RECENT_KEYWORD)
        Collections.reverse(getRecentItem)
        if(getRecentItem != null) {
           var adapter = RecentKeywordAdapter(applicationContext,this,getRecentItem)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }else {

        }
    }

    fun removeItem(item : ArrayList<String>) {
        Collections.reverse(item)
        setStringArrayPref(applicationContext,RECENT_KEYWORD,item)
        getKeyword()
    }

    fun goToResult(keyword : String) {
        startActivity(Intent(applicationContext,SearchResultActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("keyword",keyword))
        keywordEdit.setText("")
    }
}
