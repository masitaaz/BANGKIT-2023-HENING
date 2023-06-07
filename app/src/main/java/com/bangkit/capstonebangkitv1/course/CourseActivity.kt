package com.bangkit.capstonebangkitv1.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstonebangkitv1.R

class CourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        supportActionBar?.apply {
            title = getString(R.string.course)
        }
    }
}