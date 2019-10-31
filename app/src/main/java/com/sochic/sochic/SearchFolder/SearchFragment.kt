package com.sochic.sochic.SearchFolder


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sochic.sochic.HomeFolder.HomeActivity
import com.sochic.sochic.MyPageFolder.MyProductActivity
import com.sochic.sochic.R
import kotlinx.android.synthetic.main.fragment_search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subBtn1.setOnClickListener {
            (activity as HomeActivity).searchSubFragment(subBtn1.text.toString())
        }

        subBtn2.setOnClickListener {
            (activity as HomeActivity).searchSubFragment(subBtn2.text.toString())
        }

        subBtn3.setOnClickListener {
            (activity as HomeActivity).searchSubFragment(subBtn3.text.toString())
        }

        subBtn4.setOnClickListener {
            (activity as HomeActivity).searchSubFragment(subBtn4.text.toString())
        }

        subBtn5.setOnClickListener {
            (activity as HomeActivity).searchSubFragment(subBtn5.text.toString())
        }

        subBtn6.setOnClickListener {
            (activity as HomeActivity).searchSubFragment(subBtn6.text.toString())
        }

        subBtn7.setOnClickListener {
            (activity as HomeActivity).searchSubFragment(subBtn7.text.toString())
        }

        searchBtn.setOnClickListener {
            startActivity(Intent(context,SearchKeywordActivity::class.java ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        cartBtn.setOnClickListener {
            startActivity(
                Intent(context, MyProductActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra("type","1"))
        }
    }

}
