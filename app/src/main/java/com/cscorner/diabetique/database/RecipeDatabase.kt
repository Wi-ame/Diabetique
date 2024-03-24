package com.cscorner.diabetique.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cscorner.diabetique.dao.RecipeDao
import com.cscorner.diabetique.entities.Recipes

@Database(entities = [Recipes::class], version = 1, exportSchema = false)
   abstract class RecipeDatabase : RoomDatabase(){
       companion object{
           var recipesDatabse :RecipeDatabase?=null
           @Synchronized
           fun  getDatabase(context : Context): RecipeDatabase{
               if(recipesDatabse!=null){
                   recipesDatabse= Room.databaseBuilder(
                       context, RecipeDatabase::class.java,"recipe.db"
                   ).build()
               }
               return recipesDatabse!!
           }
       }


}