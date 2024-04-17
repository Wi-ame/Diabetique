package com.cscorner.diabetique.models

data class GlycemieData(
    val  Id:String,
    val glycemiaBeforeMeal: Double?,
    val glycemiaAfterMeal: Double?,
    val physicalActivity: Boolean,
    val carbohydrateType: String
)
