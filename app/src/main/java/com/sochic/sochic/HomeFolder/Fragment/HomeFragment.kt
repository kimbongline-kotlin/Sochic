package com.sochic.sochic.HomeFolder.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.HomeFolder.Adapter.HomePageAdapter
import com.sochic.sochic.MyPageFolder.MyProductActivity
import com.sochic.sochic.R
import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        cartBtn.setOnClickListener {
            startActivity(
                Intent(context, MyProductActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra("type","1"))
        }


        tabLayer.addTab(tabLayer.newTab().setText("OPEN"),true)
        tabLayer.addTab(tabLayer.newTab().setText("NEW"))
        tabLayer.addTab(tabLayer.newTab().setText("BEST"))
        tabLayer.addTab(tabLayer.newTab().setText("FOLLOW"))

        var adapter = HomePageAdapter(activity!!.supportFragmentManager,tabLayer.tabCount)
        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 1
        tabLayer.setupWithViewPager(viewpager)
        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                gridBtn.isSelected = false
                viewpager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        gridBtn.setOnClickListener {
            gridBtn.isSelected = !gridBtn.isSelected


            when (viewpager.currentItem) {
                0 -> {
                    ((viewpager.adapter as HomePageAdapter).instantiateItem(viewpager,0) as Home_openFragment).itemShow(checkGrid())
                }

                1 -> {
                    ((viewpager.adapter as HomePageAdapter).instantiateItem(viewpager,1) as Home_newFragment).itemShow(checkGrid())
                }

                2 -> {
                    ((viewpager.adapter as HomePageAdapter).instantiateItem(viewpager,2) as Home_bestFragment).itemShow(checkGrid())
                }

                3 -> {
                    ((viewpager.adapter as HomePageAdapter).instantiateItem(viewpager,3) as Home_followFragment).itemShow(checkGrid())
                }
            }


        }


    }

    public fun checkGrid() : Boolean {
        return gridBtn.isSelected
    }
}
