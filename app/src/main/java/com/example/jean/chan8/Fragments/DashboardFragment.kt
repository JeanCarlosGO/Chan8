package com.example.jean.chan8.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jean.chan8.Models.Post

import com.example.jean.chan8.R

class DashboardFragment : Fragment() {

    private var columnCount = 1

    //private var listener: OnPostSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("DashboardFragment", "onCreateView");
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Set adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if(context is OnPostSelectedListener) {
            listener = context
        } else {
            throw  RuntimeException("$context must implement OnPostSelectedListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    interface OnPostSelectedListener {
        fun onPostSelected(post: Post)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
            DashboardFragment()
        }
    }
}
