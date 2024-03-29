package com.cscorner.diabetique.response

import RootObjectModel
import androidx.annotation.NonNull
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchRecipes {
    var from: Int = 0
    var to: Int = 0
    var count: Int = 0
    @SerializedName("hits")
    @Expose()
    var foodRecipes: Array<RootObjectModel>? = null

    @NonNull
    override fun toString(): String {
        return "SearchRecipes(from=$from, to=$to, count=$count, foodRecipes=${foodRecipes?.contentToString()})"
    }
}
