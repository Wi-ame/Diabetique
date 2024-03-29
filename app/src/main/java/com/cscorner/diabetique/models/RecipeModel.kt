package com.cscorner.diabetique.models

import com.cscorner.diabetique.models.image_model.RootimageModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class RecipeModel {
    var label: String = ""
    var image: String = ""
    var source: String = ""
    var yield: Float = 0.0f
    var calories: Float = 0.0f
    var totalWeights: Float = 0.0f
    @SerializedName("images")
    @Expose
    lateinit var rootImageModem: RootimageModel

    constructor() {
        // Constructeur vide
    }
    constructor(
        label: String,
        image: String,
        source: String,
        yield: Float,
        calories: Float,
        totalWeights: Float,
        rootImageModem: RootimageModel
    ) {
        this.label = label
        this.image = image
        this.source = source
        this.yield = yield
        this.calories = calories
        this.totalWeights = totalWeights
        this.rootImageModem = rootImageModem
    }
}


