import com.cscorner.diabetique.models.RecipeModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RootObjectModel {
    @SerializedName("recipe")
    @Expose
    var recipeModel: RecipeModel? = null
    constructor(recipeModel: RecipeModel?) {
        this.recipeModel = recipeModel
    }


}