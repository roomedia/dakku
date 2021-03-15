package com.roomedia.dakku

import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.util.splitByWeek
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat

class ExampleUnitTest {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun isInSameWeek_isTrue() {
        val data1 = Diary(simpleDateFormat.parse("2021-03-07")!!, "")
        val data2 = Diary(simpleDateFormat.parse("2021-03-10")!!, "")
        assertTrue(data1.isInSameWeek(data2))
    }

    @Test
    fun splitByWeek_isCorrect() {
        val dataset = listOf(
            Diary(simpleDateFormat.parse("2021-02-10")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-10")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-13")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-14")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-15")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-18")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-20")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-21")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-21")!!, ""),
            Diary(simpleDateFormat.parse("2021-02-27")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-01")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-02")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-04")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-04")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-05")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-06")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-08")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-10")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-12")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-13")!!, ""),
            Diary(simpleDateFormat.parse("2021-03-14")!!, ""),
        )
        val expected = listOf(
            listOf(
                Diary(simpleDateFormat.parse("2021-02-10")!!, ""),
                Diary(simpleDateFormat.parse("2021-02-10")!!, ""),
                Diary(simpleDateFormat.parse("2021-02-13")!!, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-02-14")!!, ""),
                Diary(simpleDateFormat.parse("2021-02-15")!!, ""),
                Diary(simpleDateFormat.parse("2021-02-18")!!, ""),
                Diary(simpleDateFormat.parse("2021-02-20")!!, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-02-21")!!, ""),
                Diary(simpleDateFormat.parse("2021-02-21")!!, ""),
                Diary(simpleDateFormat.parse("2021-02-27")!!, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-03-01")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-02")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-04")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-04")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-05")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-06")!!, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-03-08")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-10")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-12")!!, ""),
                Diary(simpleDateFormat.parse("2021-03-13")!!, ""),
            ),
            listOf(
                Diary(simpleDateFormat.parse("2021-03-14")!!, ""),
            ),
        )
        val actual = dataset.splitByWeek()
        assertEquals(expected, actual)
    }
}
