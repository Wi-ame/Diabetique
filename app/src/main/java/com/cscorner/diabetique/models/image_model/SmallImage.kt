package com.cscorner.diabetique.models.image_model

class SmallImage {
        private   lateinit var  url:String
        private    var width:Int=0
        private var height:Int=0

        constructor(url: String, width: Int, height: Int) {
            this.url = url
            this.width = width
            this.height = height
        }

}