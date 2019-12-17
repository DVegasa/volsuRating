package io.github.dvegasa.volsurating.data_processing

import io.github.dvegasa.volsurating.models.SubjectRich

/**
 * 16.12.2019
 */
class Statistics {
    companion object {
        fun userSum(dataset: ArrayList<SubjectRich>): Int {
            var sum = 0
            for (subjectRich in dataset) {
                sum += subjectRich.userRate
            }
            return sum
        }

        fun userRating(dataset: ArrayList<SubjectRich>): Int {
            val userSum = userSum(dataset)
            val count = dataset[0].rates.size
            val othersSum = Array(count) {0}

            for (subj in dataset) {
                for (i in subj.rates.indices) {
                    othersSum[i] += subj.rates[i]
                }
            }
            othersSum.sortDescending()
            for (i in othersSum.indices) {
                if (othersSum[i] == userSum) {
                    return i+1
                }
            }
            return -1
        }
    }
}