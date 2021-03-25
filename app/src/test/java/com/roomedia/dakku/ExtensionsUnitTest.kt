package com.roomedia.dakku

import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.util.splitByWeek
import com.roomedia.dakku.util.toWeekString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat

class ExtensionsUnitTest {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun isInSameWeek_isTrue() {
        val data1 = Diary(0, simpleDateFormat.parse("2021-03-07")!!.time, "")
        val data2 = Diary(0, simpleDateFormat.parse("2021-03-10")!!.time, "")
        assertTrue(data1.isInSameWeek(data2))
    }

    @Test
    fun splitByWeek_isCorrect() {
        val dataset = listOf(
            Diary(0, simpleDateFormat.parse("2021-02-10")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-10")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-13")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-14")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-15")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-18")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-20")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-21")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-21")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-02-27")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-01")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-02")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-04")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-04")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-05")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-06")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-08")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-10")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-12")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-13")!!.time, ""),
            Diary(0, simpleDateFormat.parse("2021-03-14")!!.time, ""),
        )
        val expected = listOf(
            listOf(
                Diary(0, simpleDateFormat.parse("2021-02-10")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-02-10")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-02-13")!!.time, ""),
            ),
            listOf(
                Diary(0, simpleDateFormat.parse("2021-02-14")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-02-15")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-02-18")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-02-20")!!.time, ""),
            ),
            listOf(
                Diary(0, simpleDateFormat.parse("2021-02-21")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-02-21")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-02-27")!!.time, ""),
            ),
            listOf(
                Diary(0, simpleDateFormat.parse("2021-03-01")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-02")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-04")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-04")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-05")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-06")!!.time, ""),
            ),
            listOf(
                Diary(0, simpleDateFormat.parse("2021-03-08")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-10")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-12")!!.time, ""),
                Diary(0, simpleDateFormat.parse("2021-03-13")!!.time, ""),
            ),
            listOf(
                Diary(0, simpleDateFormat.parse("2021-03-14")!!.time, ""),
            ),
        )
        val actual = dataset.splitByWeek()
        assertEquals(expected, actual)
    }

    @Test
    fun toWeekString_isCorrect() {
        val localeDateFormat = SimpleDateFormat("MM월 dd일")
        val actual = listOf(
            Diary(0, SimpleDateFormat("yyyy-MM-dd").parse("2021-03-10")!!.time, "")
        ).toWeekString(localeDateFormat)
        assertEquals("03월 07일 - 03월 13일", actual)
    }
}
