package com.bangkit.capstonebangkitv1.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bangkit.capstonebangkitv1.R
import com.bangkit.capstonebangkitv1.databinding.ActivityHomepageBinding
import com.bangkit.capstonebangkitv1.fragment.HomePageFragment
import com.bangkit.capstonebangkitv1.fragment.ProfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding
    val homeFragment = HomePageFragment()
    val profilFragment = ProfilFragment()
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setCurrentFragment(homeFragment)
        bottomNav()
    }

    private fun bottomNav(){
        bottomNav = binding.bottomNav
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homepage -> setCurrentFragment(homeFragment)
                R.id.profilpage -> setCurrentFragment(profilFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment:Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayout, fragment)
            commit()
        }
}