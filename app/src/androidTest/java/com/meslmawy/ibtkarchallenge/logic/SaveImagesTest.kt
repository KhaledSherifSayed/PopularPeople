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

package com.meslmawy.ibtkarchallenge.logic

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
import androidx.test.espresso.matcher.ViewMatchers
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.domain.dto.PersonImage
import com.meslmawy.ibtkarchallenge.presentation.details.DetailsFragment
import com.meslmawy.ibtkarchallenge.presentation.photo.PhotoFragment
import org.hamcrest.CoreMatchers

@MediumTest
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveImagesTest {

    @Test
    fun personTitle_DisplayedInUi() {
        // GIVEN - Add active (incomplete) task to the DB
        val image = PersonImage(400, 600,0,0.0,"/jKiSaeRO4IPJoHsjGRqMpdrZu88.jpg", 0.66666666666667)
        val fragmentArgs = Bundle().apply {
            putParcelable("Image",image)
            putString("picture", "/jKiSaeRO4IPJoHsjGRqMpdrZu88.jpg")
            putString("name","Lisa Ann Walter")
        }
        launchFragmentInContainer<PhotoFragment>(
            fragmentArgs = fragmentArgs, // Bundle
            themeResId = R.style.AppTheme,
            factory = null
        )
        // make sure that the title/description are both shown and correct
        onView(withId(R.id.fab)).check(matches(ViewMatchers.withTagValue(CoreMatchers.equalTo(R.drawable.ic_add))))
        Thread.sleep(2000)
    }
}