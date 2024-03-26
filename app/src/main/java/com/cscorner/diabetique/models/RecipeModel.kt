package com.cscorner.diabetique.models

import com.cscorner.diabetique.models.image_model.RootimageModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecipeModel {
    var label: String = ""
        private set
    var image: String = ""
        private set
    var source: String = ""
        private set
    var yield: Float = 0.0f
        private set
    var calories: Float = 0.0f
        private set
    var totalWeights: Float = 0.0f
        private set
    @SerializedName("images")
    @Expose
    lateinit var rootImageModem: RootimageModel
        private set
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

