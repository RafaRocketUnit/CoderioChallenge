package com.rafarocket.coderiochallenge.domain.model

data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for: List<KnownFor>,
    val known_for_department: String?,
    val name: String?,
    val original_name: String?,
    val popularity: Double,
    val profile_path: String?
)