package com.roomedia.dakku

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.ui.list.adapter.WeeklyDiaryAdapter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun toWeekString_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val pattern = appContext.getString(R.string.locale_date_format)
        val localeDateFormat = SimpleDateFormat(pattern)

        val dummyAdapter = WeeklyDiaryAdapter(appContext, listOf())
        val method = dummyAdapter::class.java.getDeclaredMethod("toWeekString", Diary::class.java, SimpleDateFormat::class.java)
        method.isAccessible = true

        val dummyData = Diary(SimpleDateFormat("yyyy-MM-dd").parse("2021-03-10")!!, "")
        val actual = method.invoke(dummyAdapter, dummyData, localeDateFormat)
        assertEquals("03월 07일 - 03월 13일", actual)
    }
}
