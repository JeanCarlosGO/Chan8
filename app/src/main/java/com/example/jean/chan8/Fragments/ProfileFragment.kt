package com.example.jean.chan8.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jean.chan8.MainActivity
import com.example.jean.chan8.R
import com.google.firebase.auth.FirebaseUser
import com.myhexaville.smartimagepicker.ImagePicker
import com.myhexaville.smartimagepicker.OnImagePickedListener

//import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private var user: FirebaseUser? = null
    //private var listener: OnProfileChangeListener? = null
    private var imagePicker: ImagePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            user = it.getParcelable(ARG_USER) as FirebaseUser
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("ProfileFragment", "onCreateView");

        var view: View? = inflater.inflate(R.layout.fragment_profile, null)
        var userImage = view?.findViewById(R.id.profileUserImage) as ImageView
        var userName = view?.findViewById(R.id.profileUserName) as TextView
        var btnSignOut = view?.findViewById(R.id.profileBtnSignOut) as Button

        val imageUri = if (user?.photoUrl != null) {
            user?.photoUrl
        } else {
            R.drawable.ic_account_circle_primary_64dp
        }
        Glide.with(view)
                .load(imageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(userImage)

        userImage.setOnClickListener {
            if (imagePicker == null) {
                imagePicker = ImagePicker(activity, this, OnImagePickedListener { uri ->
                    Glide.with(view).load(uri).into(userImage)
                })
            }
            imagePicker?.choosePicture(true)
        }

        userName.text = if (user?.displayName != null) {
            user?.displayName
        } else if (user?.email != null) {
            user?.email
        } else {
            "User Name"
        }

        btnSignOut.setOnClickListener { view -> (activity as MainActivity).signOut(view) }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if(context is OnProfileChangeListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    interface OnProfileChangeListener {
        fun onProfileChange(fbUser: FirebaseUser?)
    }

    companion object {
        const val ARG_USER = "user"
        @JvmStatic
        fun newInstance() {
            ProfileFragment()
        }
    }
}
