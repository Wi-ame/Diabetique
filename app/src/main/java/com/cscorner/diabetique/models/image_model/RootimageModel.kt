package com.cscorner.diabetique.models.image_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
    class RootimageModel {
        @SerializedName("LARGE")
        @Expose()
        private lateinit var  largeimage:LargeImage
        @SerializedName("SMALL")
        @Expose()
        private lateinit var smallimage:SmallImage
        @SerializedName("THUMBNAIL")
        @Expose()

        private lateinit var thumbNail:ThumbNail
        @SerializedName("REGULAR")
        @Expose()
        private lateinit var regularImage:RegularImage

        constructor(
            largeimage: LargeImage,
            smallimage: SmallImage,
            thumbNail: ThumbNail,
            regularImage: RegularImage
        ) {
            this.largeimage = largeimage
            this.smallimage = smallimage
            this.thumbNail = thumbNail
            this.regularImage = regularImage
        }
    }
