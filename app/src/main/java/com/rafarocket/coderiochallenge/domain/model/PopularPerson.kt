package com.rafarocket.coderiochallenge.domain.model

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafarocket.coderiochallenge.data.database.entities.PopularPersonEntity


data class PopularPerson(
    val page: Int,
    val results: List<Person>,
    val total_pages: Int,
    val total_results: Int
)

fun PopularPersonEntity.toPopularPerson(): PopularPerson {
    val result = getPersonsList(results)
    return PopularPerson(page, result, total_pages, total_results)
}

private fun getPersonsList(result: String?): List<Person> {
    val trade = if (result.isNullOrEmpty())
        return listOf()
    else
        JsonParser().parse(result).asJsonArray

    val list = mutableListOf<Person>()
    for (item in trade) {

        val knownFor = if ((item as JsonObject).get("known_for").asString != "null")
            Gson().fromJson(item.get("known_for").asString, JsonArray::class.java)
            else
                JsonArray()

        val person = Person(
            adult = item.get("adult").asBoolean,
            gender = item.get("gender").asInt,
            id = item.get("id").asInt,
            known_for = getKnownList(knownFor),
            known_for_department = item.get("known_for_department").asString,
            name = item.get("name").asString,
            original_name = item.get("original_name").asString,
            popularity = item.get("popularity").asDouble,
            profile_path = item.get("profile_path").asString
        )
        list.add(person)
    }
    return list
}


private fun getKnownList(array: JsonArray): List<KnownFor> {
    val list = mutableListOf<KnownFor>()
    for (item in array) {

        val genre =  if ((item as JsonObject).get("genre_ids").asString != "null")
            Gson().fromJson(item.get("genre_ids").asString, JsonArray::class.java) ?: JsonArray()
            else
                JsonArray()

        val originCountry = if (item.get("origin_country").asString != "null")
            Gson().fromJson(item.get("origin_country").asString, JsonArray::class.java)
            else
                JsonArray()

        val known = KnownFor(
            adult = getItemBoolean(item, "adult"),
            backdrop_path = getItemString(item, "backdrop_path"),
            first_air_date = getItemString(item, "first_air_date"),
            genre_ids = getGenreList(genre),
            id = getItemInt(item,"id"),
            media_type = getItemString(item,"media_type"),
            name = getItemString(item,"name"),
            origin_country = getOriginCountryList(originCountry),
            original_language = getItemString(item,"original_language"),
            original_name = getItemString(item,"original_name"),
            original_title = getItemString(item,"original_title"),
            overview = getItemString(item,"overview"),
            popularity = getItemDouble(item,"popularity"),
            poster_path = getItemString(item,"poster_path"),
            release_date = getItemString(item,"release_date"),
            title = getItemString(item,"title"),
            video = getItemBoolean(item,"video"),
            vote_average = getItemDouble(item,"vote_average"),
            vote_count = getItemInt(item,"vote_count"),
        )
        list.add(known)
    }
    return list
}

private fun getGenreList(array: JsonArray): List<Int> {
    return if (array.isEmpty) {
        listOf()
    } else {
        array.map {
            Gson().fromJson(Gson().toJson(it), Int::class.java)
        }
    }
}

private fun getOriginCountryList(array: JsonArray): List<String> {
    return if (array.isEmpty) {
        listOf()
    } else {
        array.map {
            Gson().fromJson(Gson().toJson(it), String::class.java)
        }
    }
}

private fun getItemBoolean(json: JsonObject, key: String): Boolean {
    return if (json.get(key) != null) json.get(key).asBoolean else false
}

private fun getItemString(json: JsonObject, key: String): String {
    return if (json.get(key) != null) json.get(key).asString else ""
}

private fun getItemDouble(json: JsonObject, key: String): Double {
    return if (json.get(key) != null) json.get(key).asDouble else 0.0
}

private fun getItemInt(json: JsonObject, key: String): Int {
    return if (json.get(key) != null) json.get(key).asInt else 0
}

