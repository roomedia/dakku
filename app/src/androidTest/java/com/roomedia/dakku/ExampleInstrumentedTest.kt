package com.roomedia.dakku

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.roomedia.dakku.util.toWeekString
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
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        assertEquals("03월 07일 - 03월 13일", simpleDateFormat.parse("2021-03-10")!!.toWeekString(localeDateFormat))
    }
}
