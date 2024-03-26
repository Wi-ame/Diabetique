package com.cscorner.diabetique.models.image_model

class ThumbNail(
    private var url: String,
    private var width: Int,
    private var height: Int
) {
    constructor() : this("", 0, 0) {
        // Constructeur vide
    }

    // Getters et Setters pour les propriétés
    fun getUrl(): String {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getWidth(): Int {
        return width
    }

    fun setWidth(width: Int) {
        this.width = width
    }

    fun getHeight(): Int {
        return height
    }

    fun setHeight(height: Int) {
        this.height = height
    }

}
