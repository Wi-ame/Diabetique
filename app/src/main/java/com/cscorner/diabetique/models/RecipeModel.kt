package com.cscorner.diabetique.models

import com.cscorner.diabetique.models.image_model.RootimageModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class RecipeModel {
    @SerializedName("url")
    var url: String = ""
    var label: String = ""
    var image: String = ""
    var source: String = ""
    var yield: Float = 0.0f

    @SerializedName("totalNutrients")
    @Expose
    lateinit var totalNutrients: Map<String, Nutrient>

    @SerializedName("images")
    @Expose
    lateinit var rootImageModem: RootimageModel
    lateinit var cautions: List<String>
    var ingredients: List<Ingredient> = listOf()

    constructor() {
        cautions = emptyList()
    }
    constructor(
        label: String,
        image: String,
        source: String,
        yield: Float,
        calories: Float,
        cautions: List<String>,
        rootImageModem: RootimageModel


    ) {
        this.label = label
        this.image = image
        this.source = source
        this.yield = yield
        this.rootImageModem = rootImageModem
        this.cautions = cautions
    }

    class Ingredient(
        var text: String = "",
        var quantity: Float = 0.0f,
        var measure: String = "",
        var food: String = "",
        var weight: Float = 0.0f,
        var foodId: String = ""
    )

    class Nutrient(
        @SerializedName("label") val label: String,
        @SerializedName("quantity") val quantity: Float,
        @SerializedName("unit") val unit: String
    )
}



