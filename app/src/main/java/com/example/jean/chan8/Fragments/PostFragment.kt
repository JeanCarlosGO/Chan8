package com.example.jean.chan8.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jean.chan8.Models.Content
import com.example.jean.chan8.Models.Post

import com.example.jean.chan8.R
import java.lang.RuntimeException

class PostFragment : Fragment() {

    //private var listener: OnCreatePostListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("PostFragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if(context is OnCreatePostListener) {
            listener = context
        } else {
            throw  RuntimeException("$context must implement OnCreatePostListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    interface OnCreatePostListener {
        fun onCreatePost(content: Content)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
            PostFragment()
        }
    }
}
