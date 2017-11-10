package com.chootdev.railwaydic.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.chootdev.railwaydic.R
import com.chootdev.railwaydic.adapters.StationListAdapter
import com.chootdev.railwaydic.helpers.FillDatabase
import com.chootdev.railwaydic.helpers.SnacManager
import com.chootdev.railwaydic.models.Station
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container_search.*

class MainActivity : AppCompatActivity(), FillDatabase.FillDBCallback, StationListAdapter.StationFilterCallback {

    private var adapter: StationListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        FillDatabase().init(this@MainActivity, this)
        FillDatabase().requestDataPopulation()
    }

    private fun initView() {
        edtSearch.isEnabled = false
        edtSearch.isCursorVisible = false
    }

    private fun populateList(stations: List<Station>) {
        adapter = StationListAdapter(this@MainActivity, this, stations)
        rvList.setHasFixedSize(true)
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.itemAnimator = DefaultItemAnimator()

        setUpSearch()
    }

    private fun setUpSearch() {
        edtSearch.isEnabled = true
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 0)
                    btnClear.visibility = View.VISIBLE
                else
                    btnClear.visibility = View.INVISIBLE

                adapter!!.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        edtSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@OnEditorActionListener true
            }

            false
        })

        edtSearch.setOnClickListener { edtSearch.isCursorVisible = true }

        btnClear.setOnClickListener {
            btnClear.visibility = View.INVISIBLE
            edtSearch.setText("")
        }
    }

    private fun performSearch() {
        edtSearch.clearFocus()
        edtSearch.isCursorVisible = false
        val `in` = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(edtSearch.windowToken, 0)
    }

    override fun onPopulateFinished(status: Boolean, stations: List<Station>?) {
        if (status) {
            if (stations != null) {
                populateList(stations)
            }
        } else {
            var message = SnacManager()
            message.makeMessage(this@MainActivity, 2, "Something went wrong...")
        }
    }

    override fun onStationFilter(isHavingResults: Boolean) {
        println(isHavingResults)
    }
}
