package com.example.jean.chan8

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.example.jean.chan8.Fragments.DashboardFragment
import com.example.jean.chan8.Fragments.PostFragment
import com.example.jean.chan8.Fragments.ProfileFragment
import com.example.jean.chan8.Models.Content
import com.example.jean.chan8.Models.Post
import com.google.firebase.auth.FirebaseUser

class MainActivity : AuthAbstractActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bundle.putParcelable("user", mAuth.currentUser)

        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment = when (item.itemId) {
            R.id.navigation_home -> PostFragment()
            R.id.navigation_dashboard -> DashboardFragment()
            R.id.navigation_profile -> ProfileFragment()
            else -> null
        }
        fragment?.arguments = bundle
        return loadContent(fragment)
    }

    /*override fun onCreatePost(content: Content) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostSelected(post: Post) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProfileChange(fbUser: FirebaseUser?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

    private fun loadContent(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .addToBackStack(null)
                    .commit()
            return true
        }
        return false
    }

    override fun updateUI(user: FirebaseUser?) {
        super.updateUI(user)

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        protected val TAG = "Main"
        private var bundle = Bundle()
    }
}
