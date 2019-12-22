package io.github.dvegasa.volsurating.data_processing.network
import android.util.Log
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * 09.12.2019
 */
class VolsuRatingHtmlParser(private val doc: Document) {

    fun getSchoolboysData(): Map<Int, ArrayList<Int>> {
        val map = HashMap<Int, ArrayList<Int>>()
        val table = doc.getElementById("tab")

        val rows: Elements = table.select("tr")
        for (i in 1 until rows.size) {

            val schoolboyId = rows[i].select("td").first().text().toInt()

            val cols: Elements = rows[i].select("td")

            val rates = ArrayList<Int>()
            for (j in 1 until cols.size) {
                rates.add(cols[j].text().toInt())
            }
            map[schoolboyId] = rates
        }

        if (map.isEmpty()) {
            Log.e("ed__", "Map: ${map}\nDoc: ${doc}")
            throw Exception("Empty schoolboysData. Looks like error in URL")
        }

        return map
    }

    /**
     * 0 -- name
     * 1 -- zachet / ekzamen
     */
    fun getLessonsData(): ArrayList<Array<String>> {
        val list = ArrayList<Array<String>>()

        val row = doc.select("tr").first()
        val elements = row.allElements

//        print("==================\n")
//        for (e in elements) {
//            print(e.text() + "\n")
//        }
//        print("==================\n")


        for (i in 3 until elements.size step 4) {
            val lesson = Array(2) { "" }
            lesson[0] = elements[i].text()
            list.add(lesson)
        }

        var counter = 0
        for (i in 5 until elements.size step 4) {
            list[counter++][1] = elements[i].text()
        }
        return list
    }
}