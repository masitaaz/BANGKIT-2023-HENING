package com.bangkit.capstonebangkitv1.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstonebangkitv1.databinding.ItemArticleBinding
import com.bumptech.glide.Glide

class ArticleAdapter(private val listStory: List<ArticleData>) : RecyclerView.Adapter<ArticleAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    inner class ListViewHolder(var binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: ArticleData) {
            binding.apply {
                Glide.with(itemView)
                    .load(userData.photoUrl)
                    .into(binding.imgItemPhoto)
                binding.tvItemName.text = userData.title
                binding.tvItemDescription.text = userData.text
            }
            itemView.setOnClickListener{
//                val intentToDetail = Intent(itemView.context, DetailStoryActivity::class.java)
//                intentToDetail.putExtra(DetailStoryActivity.NAME_EXTRA, userData.name)
//                intentToDetail.putExtra(DetailStoryActivity.DESCRIPTION_EXTRA, userData.description)
//                intentToDetail.putExtra(DetailStoryActivity.PHOTO_EXTRA, userData.photoUrl)
//                itemView.context.startActivity(intentToDetail)
            }

        }
    }
}