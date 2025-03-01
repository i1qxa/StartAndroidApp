package aps.fithom.startandroidapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.local.getDrawableOrNullFromAssetsByPath
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Recipe
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_FAVORITE_SET
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_NAME
import kotlinx.coroutines.launch

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository(application)
    private val portionAmountLD = MutableLiveData<Int>(1)

    //    private val _recipeStateLD = recipesRepository.selectedRecipeLD.switchMap { MutableLiveData(RecipeState(it.toRecipe(), it.recipeDB.inFavorite)) }
    private val _recipeStateLD = MediatorLiveData<RecipeState>().apply {
        addSource(recipesRepository.selectedRecipeLD){ recipeDB ->
            this.value?.let {
                this.value = it.copy(recipe = recipeDB.toRecipe())
                this.value = it.copy(isInFavorite = recipeDB.recipeDB.inFavorite)
            }
        }
        addSource(portionAmountLD){ portionAmount ->
            this.value?.let {
                this.value = it.copy(portionAmount = portionAmount)
            }
        }
    }
    val recipeStateLD: LiveData<RecipeState>
        get() = _recipeStateLD

//    private val prefs by lazy {
//        application.baseContext.getSharedPreferences(
//            PREFS_NAME,
//            Context.MODE_PRIVATE
//        )
//    }

//    init {
//        Log.i("!!!", "RecipeViewModel init block")
//        _recipeStateLD.value =
//            _recipeStateLD.value?.copy(isInFavorite = true) ?: RecipeState(isInFavorite = true)
//    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            recipesRepository.fetchRecipe(recipeId)
//            val recipe = recipesRepository.getRecipeById(recipeId).await()
//            val isInFavorite = getFavoritesFromPrefs()?.contains(recipeId.toString()) == true
//            _recipeStateLD.postValue(
//                _recipeStateLD.value?.copy(
//                    recipe = recipe,
//                    isInFavorite = isInFavorite,
//                )
//            )
        }
    }

//    private fun getFavoritesFromPrefs(): HashSet<String>? {
//        return prefs.getStringSet(PREFS_FAVORITE_SET, null)?.toHashSet()
//    }
//
//    private fun saveFavorites(setOfIds: HashSet<String>) {
//        prefs.edit().putStringSet(PREFS_FAVORITE_SET, setOfIds).apply()
//    }

    fun onFavoritesClicked() {
        viewModelScope.launch {
            recipesRepository.changeInFavoriteState()
        }
//        _recipeStateLD.value?.recipe?.id?.toString()?.let { recipeId ->
//            val updatedSetOfFavorite = getFavoritesFromPrefs() ?: HashSet()
//            if (updatedSetOfFavorite.contains(recipeId)) {
//                updatedSetOfFavorite.remove(recipeId)
//                _recipeStateLD.value = _recipeStateLD.value?.copy(isInFavorite = false)
//            } else {
//                updatedSetOfFavorite.add(recipeId)
//                _recipeStateLD.value = _recipeStateLD.value?.copy(isInFavorite = true)
//            }
//            saveFavorites(updatedSetOfFavorite)
//        }
    }

    fun updatePortionAmount(amount: Int) {
        portionAmountLD.value = amount
    }


    data class RecipeState(
        val recipe: Recipe? = null,
        val isInFavorite: Boolean = false,
        val portionAmount: Int = 1,
    )

}