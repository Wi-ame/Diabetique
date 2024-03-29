package doct_fragment
import RootObjectModel
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.R
import com.cscorner.diabetique.adapter.RecipeAdapter
import com.cscorner.diabetique.apis.APIClient
import com.cscorner.diabetique.response.SearchRecipes
import com.cscorner.diabetique.utils.APICredentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenuFragment : Fragment() {

    private lateinit var recylerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var searchView: SearchView
    private var recipes: ArrayList<RootObjectModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        searchView = view.findViewById(R.id.searchView)
        searchView.queryHint = "Type here to search"
        recylerView = view.findViewById(R.id.recyclerView)
        adapter = RecipeAdapter(this, recipes)
        recylerView.layoutManager = LinearLayoutManager(context)



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchRecipes(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return view
    }

    private fun searchRecipes(query: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APICredentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiClient: APIClient = retrofit.create(APIClient::class.java)
        val searchRecipesCall: Call<SearchRecipes> =
            apiClient.searchRecipes(
                APICredentials.TYPE,
                query,
                APICredentials.APP_ID,
                APICredentials.API_kEY
            )

        searchRecipesCall.enqueue(object : Callback<SearchRecipes> {
            override fun onResponse(
                call: Call<SearchRecipes>,
                response: Response<SearchRecipes>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val foodRecipes = response.body()?.foodRecipes
                    val newRecipes = ArrayList<RootObjectModel>()

                    foodRecipes?.forEach { hit ->
                        val recipeModel = hit.recipeModel // Accédez à l'objet de recette à partir de chaque "hit"
                        if (recipeModel != null) {
                            val rootObjectModel = RootObjectModel(recipeModel)
                            newRecipes.add(rootObjectModel)
                            // Logging des détails de la recette (facultatif)
                            Log.d(TAG, "Label: ${recipeModel.label}")
                            Log.d(TAG, "Source: ${recipeModel.source}")
                            Log.d(TAG, "Yield: ${recipeModel.yield}")
                            Log.d(TAG, "Calories: ${recipeModel.calories}")
                            Log.d(TAG, "Total Weight: ${recipeModel.totalWeights}")
                        }
                    }

                    recipes = newRecipes

                    adapter = RecipeAdapter(this@MenuFragment, recipes)
                    recylerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<SearchRecipes>, t: Throwable) {
                Log.v(TAG, "onFailure(): ${t.message}")
            }
        })

    }
}
