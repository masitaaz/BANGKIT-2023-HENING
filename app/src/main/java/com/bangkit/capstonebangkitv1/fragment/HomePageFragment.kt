package com.bangkit.capstonebangkitv1.fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstonebangkitv1.R
import com.bangkit.capstonebangkitv1.article.ArticleActivity
import com.bangkit.capstonebangkitv1.article.ArticleAdapter
import com.bangkit.capstonebangkitv1.article.ArticleAdapterHomePage
import com.bangkit.capstonebangkitv1.article.ArticleData
import com.bangkit.capstonebangkitv1.camera.CameraActivity
import com.bangkit.capstonebangkitv1.course.CourseActivity
import com.bangkit.capstonebangkitv1.databinding.FragmentHomePageBinding
import com.bangkit.capstonebangkitv1.games.GamesActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class HomePageFragment : Fragment() {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var rvStory: RecyclerView
    private var userArrayList = mutableListOf <ArticleData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        rvStory = binding.rvArticle
        rvStory.layoutManager = LinearLayoutManager(activity)
        rvStory.setHasFixedSize(true)

        userArrayList = arrayListOf<ArticleData>()
        getUserData()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSlide ()
        onClick(view)
        userLogin()

        binding.button2.setOnClickListener {
            val intent = Intent(requireActivity(), ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun imageSlide (){
        val pictSlider = binding.pictCarousel
        val pictList = ArrayList<SlideModel>()
        pictList.add(SlideModel("https://wallpaperaccess.com/full/2637581.jpg"))
        pictList.add(SlideModel("https://bit.ly/2YoJ77H"))
        pictList.add(SlideModel("https://bit.ly/2BteuF2"))
        pictList.add(SlideModel("https://bit.ly/3fLJf72"))

        pictSlider.setImageList(pictList, ScaleTypes.FIT)
    }

    fun onClick(view: View) {
        binding.apply {
            translate.setOnClickListener {
                val intent = Intent(requireActivity(), CameraActivity::class.java)
                startActivity(intent)
            }
            course.setOnClickListener {
                val intent = Intent(requireActivity(), CourseActivity::class.java)
                startActivity(intent)
            }
            games.setOnClickListener {
                val intent = Intent(requireActivity(), GamesActivity::class.java)
                startActivity(intent)
            }
            article.setOnClickListener {
                val intent = Intent(requireActivity(), ArticleActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun userLogin(){
        val userLogin = auth.currentUser

        binding.tvName.text = userLogin?.displayName

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
                rvStory.adapter = ArticleAdapterHomePage(userArrayList)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })

    }
}