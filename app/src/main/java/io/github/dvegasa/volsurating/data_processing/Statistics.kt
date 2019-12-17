package io.github.dvegasa.volsurating.data_processing

import io.github.dvegasa.volsurating.Emoji
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
            val othersSum = Array(count) { 0 }

            for (subj in dataset) {
                for (i in subj.rates.indices) {
                    othersSum[i] += subj.rates[i]
                }
            }
            othersSum.sortDescending()
            for (i in othersSum.indices) {
                if (othersSum[i] == userSum) {
                    return i + 1
                }
            }
            return -1
        }

        fun getEmojiForSubject(subj: SubjectRich): String {
            if (subj.isEmpty()) {
                return Emoji.question
            }

            val (iFirst, iSecond) = getTercelBordersIndicies(subj)
            return when {
                subj.userRate >= getSufficientRate(subj) -> Emoji.check
                subj.userRate < subj.rates[iFirst] -> Emoji.exlamation
                subj.userRate < subj.rates[iSecond] -> Emoji.novice
                else -> Emoji.thumbUp
            }
        }

        fun getMedian(subj: SubjectRich): Int {
            val sorted = subj.rates.sorted()
            return if (sorted.size % 2 != 0) {
                sorted[sorted.size / 2]
            } else {
                (sorted[sorted.size / 2] + sorted[sorted.size / (2 + 1)]) / 2
            }
        }

        fun getTercelBordersIndicies(subj: SubjectRich): Array<Int> {
            val iFirst = subj.rates.size / 3
            val iSecond = subj.rates.size / 3 * 2
            return arrayOf(iFirst, iSecond)
        }

        fun getSufficientRate(subj: SubjectRich): Int {
            return when {
                subj.ekzamen.contains("Экзамен") -> 71
                subj.ekzamen.contains("Зачет с оценкой") -> 71
                subj.ekzamen.contains("Зачет") -> 60
                else -> 999
            }
        }
    }
}