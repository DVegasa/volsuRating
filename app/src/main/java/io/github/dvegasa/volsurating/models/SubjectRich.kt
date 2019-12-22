package io.github.dvegasa.volsurating.models

/**
 * 11.12.2019
 */

data class SubjectRich(
    var name: String,
    var rates: MutableList<Int>,
    var userRate: Int,
    var ekzamen: String = ""
)