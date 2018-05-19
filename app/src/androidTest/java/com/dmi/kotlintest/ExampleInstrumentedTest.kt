package com.dmi.kotlintest

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.dmi.kotlintest.common.clearPhone

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.dmi.kotlintest", appContext.packageName)
    }

    @Test
    fun getClearPhone() {
        val phone = "7 900 500-4545"
        assertEquals(phone.clearPhone(), "79005004545")
    }
}
