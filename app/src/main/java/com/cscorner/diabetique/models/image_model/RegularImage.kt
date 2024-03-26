package com.cscorner.diabetique.models.image_model

class RegularImage(
    private var url: String,
    private var width: Int,
    private var height: Int
) {
    constructor() : this("", 0, 0) {
        // Constructeur vide
    }

    // Méthodes getters et setters pour la propriété url
    fun getUrl(): String {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    // Méthodes getters et setters pour la propriété width
    fun getWidth(): Int {
        return width
    }

    fun setWidth(width: Int) {
        this.width = width
    }

    // Méthodes getters et setters pour la propriété height
    fun getHeight(): Int {
        return height
    }

    fun setHeight(height: Int) {
        this.height = height
    }
}
