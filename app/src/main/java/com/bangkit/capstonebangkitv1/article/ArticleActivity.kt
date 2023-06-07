package com.bangkit.capstonebangkitv1.article

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstonebangkitv1.R
import com.bangkit.capstonebangkitv1.databinding.ActivityArticleBinding
import com.bangkit.capstonebangkitv1.databinding.ActivityHomepageBinding
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var rvStory: RecyclerView
    private var userArrayList = mutableListOf <ArticleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.article)
        }

        rvStory = binding.rvArticle
        rvStory.layoutManager = LinearLayoutManager(this)
        rvStory.setHasFixedSize(true)

        userArrayList = arrayListOf<ArticleData>()
        getUserData()
    }

    private fun getUserData() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("data")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                for(list in snapshot.children){
                    val id = list.key
                    val title = list.child("title").value.toString()
                    val text = list.child("text").value.toString()
                    val photoUrl = list.child("photoUrl").value.toString()

                    val artikel = ArticleData(id = id, title=title, text = text, photoUrl = photoUrl)
                    userArrayList.add(artikel)
                }
                Log.e("successsss", "size: ${userArrayList.size}")
                rvStory.adapter = ArticleAdapter(userArrayList)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }


        })

    }
}