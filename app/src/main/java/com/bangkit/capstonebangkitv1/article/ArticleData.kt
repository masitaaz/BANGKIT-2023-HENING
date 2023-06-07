package com.bangkit.capstonebangkitv1.article


data class ArticleData(
    var id: String? = null,
    var text: String? = null,
    var title: String? = null,
    var photoUrl: String? = null
){
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}