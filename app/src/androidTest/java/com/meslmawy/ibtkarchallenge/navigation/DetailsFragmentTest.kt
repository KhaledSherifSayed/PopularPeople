/*
 *
 * Copyright (c) 2020 Khaled  Sherif
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 */

package com.meslmawy.ibtkarchallenge.navigation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.meslmawy.ibtkarchallenge.domain.dto.People
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.presentation.details.DetailsFragment

@MediumTest
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    @Test
    fun personTitle_DisplayedInUi() {
        // GIVEN - Add active (incomplete) task to the DB
        val people = People(4494, "Lisa Ann Walter", 77.508,
            "Acting",1,"/p3wxUblbPwRVzTp7jW1lXISKIob.jpg"
        ,false,"Description Test",null)
        val fragmentArgs = Bundle().apply {
            putParcelable("people",people)
            putString("picture", "/p3wxUblbPwRVzTp7jW1lXISKIob.jpg")
        }
        launchFragmentInContainer<DetailsFragment>(
            fragmentArgs = fragmentArgs, // Bundle
            themeResId = R.style.AppTheme,
            factory = null
        )
        // make sure that the title/description are both shown and correct
        onView(withId(R.id.detail_title)).check(matches(withText("Lisa Ann Walter")))
        Thread.sleep(2000)
    }
}