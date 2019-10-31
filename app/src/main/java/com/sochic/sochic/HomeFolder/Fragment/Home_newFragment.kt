package com.sochic.sochic.HomeFolder.Fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.HomeFolder.API.HomeItemAPI
import com.sochic.sochic.HomeFolder.Adapter.HomeGridFeedAdapter
import com.sochic.sochic.HomeFolder.Adapter.HomeListFeedAdapter
import com.sochic.sochic.HomeFolder.HomeActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.SpacesItemDecoration
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.fragment_home_new.*
import kotlinx.android.synthetic.main.fragment_home_new.gridLayer
import kotlinx.android.synthetic.main.fragment_home_new.recycler
import kotlinx.android.synthetic.main.fragment_home_open.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Home_newFragment : Fragment() {

    lateinit var mAct : Activity
    lateinit var mCon : Context
    lateinit var kAct : ScActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAct = activity!!
        mCon = context!!
        kAct = mAct as ScActivity
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_new, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(context!!,RecyclerView.VERTICAL,false)

        gridLayer.layoutManager = GridLayoutManager(context!!,3)
        gridLayer.addItemDecoration(SpacesItemDecoration(kAct.convertDpToPixel(5.0f,mCon).toInt(),3))


        recycler.visibility = View.VISIBLE
        gridLayer.visibility = View.GONE

        getItem()
    }


    fun getItem(){
        kAct.disposable.add(kAct.apiService.NEW_ITEM_API(kAct.id_user)
            .subscribeOn(kAct.io)
            .observeOn(kAct.thread)
            .subscribeWith(object : DisposableSingleObserver<HomeItemAPI>() {
                override fun onError(e: Throwable?) {
                    kAct.ConnectionError()
                }

                override fun onSuccess(t: HomeItemAPI) {
                    var tempData = ArrayList<HomeItemAPI.HomeItemList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<HomeItemAPI.HomeItemList>
                    }

                    var adapter = HomeListFeedAdapter(context!!,activity!!,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()

                    var gAdapter = HomeGridFeedAdapter(mCon,mAct,tempData)
                    gridLayer.adapter = gAdapter
                    gAdapter.notifyDataSetChanged()
                }
            }))
    }

    public fun itemShow(grid : Boolean) {

        if(grid) {
            gridLayer.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        }else {
            gridLayer.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }
    }



    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser && isResumed) {

            if((activity as HomeActivity).supportFragmentManager.fragments.get(0) == HomeFragment()) {
                if(((activity as HomeActivity).supportFragmentManager.fragments.get(0) as HomeFragment).checkGrid()) {
                    gridLayer.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                }else {
                    gridLayer.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                }
            }

        }
    }
}
