package aps.fithom.startandroidapp.data.local

import kotlinx.serialization.json.Json

object STUB {
    private val recipesAsString = "[\n" +
            "    {\n" +
            "        \"id\": 0,\n" +
            "        \"title\": \"Бургеры\",\n" +
            "        \"description\": \"Рецепты всех популярных видов бургеров\",\n" +
            "        \"imageUrl\": \"burger.png\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"title\": \"Десерты\",\n" +
            "        \"description\": \"Самые вкусные рецепты десертов специально для вас\",\n" +
            "        \"imageUrl\": \"dessert.png\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"title\": \"Пицца\",\n" +
            "        \"description\": \"Пицца на любой вкус и цвет. Лучшая подборка для тебя\",\n" +
            "        \"imageUrl\": \"pizza.png\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"title\": \"Рыба\",\n" +
            "        \"description\": \"Печеная, жареная, сушеная, любая рыба на твой вкус\",\n" +
            "        \"imageUrl\": \"fish.png\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4,\n" +
            "        \"title\": \"Супы\",\n" +
            "        \"description\": \"От классики до экзотики: мир в одной тарелке\",\n" +
            "        \"imageUrl\": \"soup.png\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 5,\n" +
            "        \"title\": \"Салаты\",\n" +
            "        \"description\": \"Хрустящий калейдоскоп под соусом вдохновения\",\n" +
            "        \"imageUrl\": \"salad.png\"\n" +
            "    }\n" +
            "]"
    private val categories = Json.decodeFromString<List<Category>>(recipesAsString)
    fun getCategories(): List<Category> = categories
}