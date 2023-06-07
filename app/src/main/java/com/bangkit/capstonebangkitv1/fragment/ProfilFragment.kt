package com.bangkit.capstonebangkitv1.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.capstonebangkitv1.R
import com.bangkit.capstonebangkitv1.databinding.FragmentHomePageBinding
import com.bangkit.capstonebangkitv1.databinding.FragmentProfilBinding
import com.bangkit.capstonebangkitv1.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Suppress("UNREACHABLE_CODE")
class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        userLogin()
        signOut()
        return root
    }

    private fun signOut() {
        binding.logOut.setOnClickListener{
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    fun userLogin(){
        val userLogin = auth.currentUser

        binding.tvNamePhoto.text = userLogin?.displayName
        binding.textUsername.text = userLogin?.phoneNumber
        binding.textName.text = userLogin?.displayName
        binding.textEmail.text = userLogin?.email
        Glide.with(this).load(userLogin?.photoUrl).into(
            binding.photoProfile
        )
    }

    fun uploadImageFirebase(){

    }
}