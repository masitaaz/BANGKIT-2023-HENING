package com.bangkit.capstonebangkitv1.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstonebangkitv1.R

class GamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        supportActionBar?.apply {
            title = getString(R.string.games)
        }
    }
}