package com.roomedia.dakku

import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.ui.util.splitByWeek
import com.roomedia.dakku.ui.util.toWeekString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat

class ExtensionsUnitTest {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun isInSameWeek_isTrue() {
        val data1 = Diary(simpleDateFormat.parse("2021-03-07")!!.time, "")
        val data2 = Diary(simpleDateFormat.parse("2021-03-10")!!.time, "")
        assertTrue(data1.isInSameWeek(data2))
    }

    @Test
    fun splitByWeek_isCorrect() {
        val dataset = listOf(
            Diary(simpleDateFormat.parse("2021-02-10")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-10")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-13")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-14")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-15")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-18")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-20")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-21")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-21")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-02-27")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-01")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-02")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-04")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-04")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-05")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-06")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-08")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-10")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-12")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-13")!!.time, ""),
            Diary(simpleDateFormat.parse("2021-03-14")!!.time, ""),
        )
        val expected = listOf(
            listOf(
                Diary(simpleDateFormat.parse("2021-02-10")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-02-10")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-02-13")!!.time, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-02-14")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-02-15")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-02-18")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-02-20")!!.time, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-02-21")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-02-21")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-02-27")!!.time, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-03-01")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-02")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-04")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-04")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-05")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-06")!!.time, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-03-08")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-10")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-12")!!.time, ""),
                Diary(simpleDateFormat.parse("2021-03-13")!!.time, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-03-14")!!.time, ""),
            ),
        )
        val actual = dataset.splitByWeek()
        assertEquals(expected, actual)
    }

    @Test
    fun toWeekString_isCorrect() {
        val localeDateFormat = SimpleDateFormat("MM??? dd???")
        val actual = listOf(
            Diary(SimpleDateFormat("yyyy-MM-dd").parse("2021-03-10")!!.time, "")
        ).toWeekString(localeDateFormat)
        assertEquals("03??? 07??? - 03??? 13???", actual)
    }
}
