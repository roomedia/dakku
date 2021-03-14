package com.roomedia.dakku

import com.roomedia.dakku.util.isInSameWeek
import com.roomedia.dakku.util.splitByWeek
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat

class ExampleUnitTest {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun isInSameWeek_isTrue() {
        val expected = simpleDateFormat.parse("2021-03-07")!!
        assertTrue(simpleDateFormat.parse("2021-03-10")!!.isInSameWeek(expected))
    }

    @Test
    fun splitByWeek_isCorrect() {
        val dataset = listOf(
            simpleDateFormat.parse("2021-02-10")!!,
            simpleDateFormat.parse("2021-02-10")!!,
            simpleDateFormat.parse("2021-02-13")!!,
            simpleDateFormat.parse("2021-02-14")!!,
            simpleDateFormat.parse("2021-02-15")!!,
            simpleDateFormat.parse("2021-02-18")!!,
            simpleDateFormat.parse("2021-02-20")!!,
            simpleDateFormat.parse("2021-02-21")!!,
            simpleDateFormat.parse("2021-02-21")!!,
            simpleDateFormat.parse("2021-02-27")!!,
            simpleDateFormat.parse("2021-03-01")!!,
            simpleDateFormat.parse("2021-03-02")!!,
            simpleDateFormat.parse("2021-03-04")!!,
            simpleDateFormat.parse("2021-03-04")!!,
            simpleDateFormat.parse("2021-03-05")!!,
            simpleDateFormat.parse("2021-03-06")!!,
            simpleDateFormat.parse("2021-03-08")!!,
            simpleDateFormat.parse("2021-03-10")!!,
            simpleDateFormat.parse("2021-03-12")!!,
            simpleDateFormat.parse("2021-03-13")!!,
            simpleDateFormat.parse("2021-03-14")!!,
        )
        val expected = listOf(
            listOf(
                simpleDateFormat.parse("2021-02-10")!!,
                simpleDateFormat.parse("2021-02-10")!!,
                simpleDateFormat.parse("2021-02-13")!!,
            ),
            listOf(
                simpleDateFormat.parse("2021-02-14")!!,
                simpleDateFormat.parse("2021-02-15")!!,
                simpleDateFormat.parse("2021-02-18")!!,
                simpleDateFormat.parse("2021-02-20")!!,
            ),
            listOf(
                simpleDateFormat.parse("2021-02-21")!!,
                simpleDateFormat.parse("2021-02-21")!!,
                simpleDateFormat.parse("2021-02-27")!!,
            ),
            listOf(
                simpleDateFormat.parse("2021-03-01")!!,
                simpleDateFormat.parse("2021-03-02")!!,
                simpleDateFormat.parse("2021-03-04")!!,
                simpleDateFormat.parse("2021-03-04")!!,
                simpleDateFormat.parse("2021-03-05")!!,
                simpleDateFormat.parse("2021-03-06")!!,
            ),
            listOf(
                simpleDateFormat.parse("2021-03-08")!!,
                simpleDateFormat.parse("2021-03-10")!!,
                simpleDateFormat.parse("2021-03-12")!!,
                simpleDateFormat.parse("2021-03-13")!!,
            ),
            listOf(
                simpleDateFormat.parse("2021-03-14")!!,
            ),
        )
        val actual = dataset.splitByWeek()
        assertEquals(expected, actual)
    }
}
