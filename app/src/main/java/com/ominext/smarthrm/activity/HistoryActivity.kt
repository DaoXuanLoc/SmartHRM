package com.ominext.smarthrm.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ominext.smarthrm.R
import com.ominext.smarthrm.adapter.RecyclerViewAdapter
import com.ominext.smarthrm.model.History
import com.ominext.smarthrm.presenter.HistoryPresenter
import com.ominext.smarthrm.view.IHistory

class HistoryActivity : AppCompatActivity(), IHistory {

    private var presenter: HistoryPresenter?= null
    private var historys: ArrayList<History> = ArrayList()
    lateinit var mAdapter: RecyclerViewAdapter

    lateinit var btnBack: ImageView
    lateinit var rvHistory: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initView()
        test()
    }

    fun initView(){
        btnBack = findViewById(R.id.btn_history_back)
        rvHistory = findViewById(R.id.rv_history)
        mAdapter = RecyclerViewAdapter(historys)
        rvHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHistory.adapter = mAdapter
    }

    fun initData(){
        presenter = HistoryPresenter(this)
        presenter?.getHistoryData()
    }

    private fun test(){
        var abcd: ArrayList<History> = ArrayList()
        for (i in 0 .. 20) {
            val abc =  History().apply {
                date = "12/12/${201}${i}"
                checkIn = "${i}"
                checkOut = "${i+40}"
            }
            abcd.add(abc)
        }

        mAdapter.setDate(abcd)
        mAdapter.notifyDataSetChanged()
    }

    override fun onLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(data: ArrayList<History>) {
        mAdapter.setDate(data)
    }

    override fun onFail(err: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}