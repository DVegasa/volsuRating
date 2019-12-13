package io.github.dvegasa.volsurating.models

/**
 * 11.12.2019
 */

data class UserData(
    val zachetkaId: Int,
    val semestr: Int,
    val groupName: String,
    val planId: String
) {
    fun dataLink() =
        """https://volsu.ru/rating/?plan_id=$planId&zach=All&semestr=$semestr&group=$groupName"""

}