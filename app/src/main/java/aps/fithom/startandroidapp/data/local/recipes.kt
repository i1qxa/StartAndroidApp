package aps.fithom.startandroidapp.data.local

import kotlinx.serialization.json.Json

object STUB {
    private val recipesAsString = """[
    {
        "id": 0,
        "title": "Бургеры",
        "description": "Рецепты всех популярных видов бургеров",
        "imageUrl": "burger.png"
    },
    {
        "id": 1,
        "title": "Десерты",
        "description": "Самые вкусные рецепты десертов специально для вас",
        "imageUrl": "dessert.png"
    },
    {
        "id": 2,
        "title": "Пицца",
        "description": "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
        "imageUrl": "pizza.png"
    },
    {
        "id": 3,
        "title": "Рыба",
        "description": "Печеная, жареная, сушеная, любая рыба на твой вкус",
        "imageUrl": "fish.png"
    },
    {
        "id": 4,
        "title": "Супы",
        "description": "От классики до экзотики: мир в одной тарелке",
        "imageUrl": "soup.png"
    },
    {
        "id": 5,
        "title": "Салаты",
        "description": "Хрустящий калейдоскоп под соусом вдохновения",
        "imageUrl": "salad.png"
    }
]"""
    private val categories = Json.decodeFromString<List<Category>>(recipesAsString)
    fun getCategories(): List<Category> = categories
}