package suitmedia.co.id.ievents.activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import suitmedia.co.id.ievents.R
import suitmedia.co.id.ievents.classes.*
import suitmedia.co.id.ievents.classes.adapter.ListGuests
import suitmedia.co.id.ievents.classes.sqlite.DatabaseHelper
import suitmedia.co.id.ievents.classes.sqlite.DatabaseHelperData
import suitmedia.co.id.ievents.databinding.GuestsBinding

class Guests : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding : GuestsBinding
    private var adapter: ListGuests? = null
    var list = ArrayList<ModelGuest>()
    private lateinit var dbLocal : DatabaseHelper
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GuestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initContent()
    }

    private fun initContent() {
        PropertyStatusBar().changeColor(R.color.orange,this)
        dbLocal = DatabaseHelper(this)
        binding.tab.setOnRefreshListener(this)
        val gridLayoutManager =  GridLayoutManager(this, 2)
        binding.rvGuests.layoutManager = gridLayoutManager
        dbLocal.deleteAllData(DatabaseHelperData().tableEvents)
        restApi("1")
        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.i("cek", "page$page")
                val pageGuests = page+1
                restApi(pageGuests.toString())
            }
        }
        binding.rvGuests.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
    }

    private fun restApi(page: String) {
        showLoading()
        val apiInterface = ApiInterface.create().getGuests(page, "10")

        apiInterface.enqueue(object : Callback<ModelResult> {
            override fun onFailure(call: Call<ModelResult>?, t: Throwable?) {
                hideLoading()
            }

            override fun onResponse(
                call: Call<ModelResult>,
                response: retrofit2.Response<ModelResult>
            ) {
                Log.i("cek", "response$response")
                val listGuest = response.body()?.getResult()!!
                for (i in listGuest.indices){
                    val getData: ModelGuests = listGuest[i]
                    val paramsInsert = ContentValues()
                    paramsInsert.put(DatabaseHelperData().fieldIdEvents, getData.getId())
                    paramsInsert.put(DatabaseHelperData().fieldEmail, getData.getEmail())
                    paramsInsert.put(DatabaseHelperData().fieldFirstName, getData.getFirstName())
                    paramsInsert.put(DatabaseHelperData().fieldLastName, getData.getLastName())
                    paramsInsert.put(DatabaseHelperData().fieldAvatar, getData.getAvatar())
                    dbLocal.insertData(DatabaseHelperData().tableEvents, paramsInsert)
                    list.add(ModelGuest(getData.getId(), getData.getEmail(), getData.getFirstName(), getData.getLastName(), getData.getAvatar()))
                }
                if (adapter == null){
                    adapter = ListGuests(list, this@Guests)
                    binding.rvGuests.adapter = adapter
                }else{
                    adapter?.notifyDataSetChanged()
                }
                hideLoading()
            }
        })
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.tab.isRefreshing = false
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onRefresh() {
        list.clear()
        scrollListener?.resetState()
        binding.tab.isRefreshing = true
        restApi("1")
    }
}