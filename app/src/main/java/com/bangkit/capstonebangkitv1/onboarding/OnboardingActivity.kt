package com.bangkit.capstonebangkitv1.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.bangkit.capstonebangkitv1.R
import com.bangkit.capstonebangkitv1.databinding.ActivityOnboardingBinding
import com.bangkit.capstonebangkitv1.login.LoginActivity
import com.bangkit.capstonebangkitv1.slider.ViewPagerAdapter

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var sliderAdapter: ViewPagerAdapter
    private var dots: Array<TextView?>? = null
    private lateinit var layouts: Array<Int>
    private val sliderChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            if (position == layouts.size.minus(1)) {
                binding.nextBtn.hide()
                binding.skipBtn.hide()
                binding.startBtn.show()
            } else {
                binding.nextBtn.show()
                binding.skipBtn.show()
                binding.startBtn.hide()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        dataSet()
        interactions()
    }

    private fun init() {
        layouts = arrayOf(
            R.layout.activity_onboarding1,
            R.layout.activity_onboarding2,
            R.layout.activity_onboarding3
        )

        sliderAdapter = ViewPagerAdapter(this, layouts)
    }

    private fun dataSet() {
        addBottomDots(0)

        binding.slider.apply {
            adapter = sliderAdapter
            addOnPageChangeListener(sliderChangeListener)
        }
    }

    private fun interactions() {
        binding.skipBtn.setOnClickListener {
            // Launch login screen
            navigateToLogin()
        }
        binding.startBtn.setOnClickListener {
            // Launch login screen
            navigateToLogin()
        }
        binding.nextBtn.setOnClickListener {
            val current = getCurrentScreen(+1)
            if (current < layouts.size) {
                binding.slider.currentItem = current
            } else {
                // Launch login screen
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
//        AppPrefs(this).setFirstTimeLaunch(false)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)

        binding.dotsLayout.removeAllViews()
        for (i in 0 until dots!!.size) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(resources.getColor(R.color.grey))
            binding.dotsLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty()) {
            dots!![currentPage]?.setTextColor(resources.getColor(R.color.button_google))
        }
    }

    private fun getCurrentScreen(i: Int): Int = binding.slider.currentItem.plus(i)

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }
}