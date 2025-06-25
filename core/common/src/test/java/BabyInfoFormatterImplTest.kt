package com.usatov.nanithometask.core.common

import com.google.common.truth.Truth.assertThat
import com.usatov.nanithometask.core.common.formatting.BabyInfoFormatter
import com.usatov.nanithometask.core.common.formatting.BabyInfoFormatterImpl
import com.usatov.nanithometask.core.common.resources.ResourceProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.eq
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import java.util.Calendar


class BabyInfoFormatterImplTest {

    @Mock
    lateinit var resourceProvider: ResourceProvider
    private lateinit var sut: BabyInfoFormatter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = BabyInfoFormatterImpl(resourceProvider)
        `when`(resourceProvider.getDrawableFromArray(any(), any()))
            .thenAnswer { it.getArgument<Int>(1) }
    }

    @Test
    fun `getAgeIcon returns correct index for 3 months old`() {
        val TEST_MONTH = 3
        val dobMillis =
            Calendar.getInstance().apply { add(Calendar.MONTH, -TEST_MONTH) }.timeInMillis

        val iconId = sut.getAgeIcon(dobMillis, DIGIT_ICONS_ARRAY_RES)

        assertThat(iconId).isEqualTo(TEST_MONTH)
        verify(resourceProvider).getDrawableFromArray(DIGIT_ICONS_ARRAY_RES, TEST_MONTH)
    }

    @Test
    fun `getAgeIcon treats 3 months minus 1 day as 2 months`() {
        val EXPECTED_MONTHS = 2
        val TEST_MONTHS = 3
        val TEST_DAYS = -1

        val dobMillis = Calendar.getInstance().apply {
            add(Calendar.MONTH, -TEST_MONTHS)
            add(Calendar.DAY_OF_MONTH, -TEST_DAYS)
        }.timeInMillis

        val iconId = sut.getAgeIcon(dobMillis, DIGIT_ICONS_ARRAY_RES)

        assertThat(iconId).isEqualTo(EXPECTED_MONTHS)
        verify(resourceProvider).getDrawableFromArray(DIGIT_ICONS_ARRAY_RES, EXPECTED_MONTHS)
    }

    @Test
    fun `formatAge returns pluralised years when age is over a year`() {
        val TEST_YEAR = 2
        val TEST_MONTH = 1
        val STUB_YEARS = "years"
        val STUB_MONTHS = "months"
        val dobMillis = Calendar.getInstance().apply {
            add(Calendar.YEAR, -TEST_YEAR)
            add(Calendar.MONTH, -TEST_MONTH)
        }.timeInMillis

        `when`(
            resourceProvider.getQuantityString(eq(PLURAL_MONTHS_RES), any(), any())
        ).thenAnswer { "${it.getArgument<Int>(1)} $STUB_MONTHS" }

        `when`(
            resourceProvider.getQuantityString(eq(PLURAL_YEARS_RES), any(), any())
        ).thenAnswer { "${it.getArgument<Int>(1)} $STUB_YEARS" }

        val txt = sut.formatAge(
            dobMillis,
            PLURAL_MONTHS_RES,
            PLURAL_YEARS_RES
        )

        assertThat(txt).isEqualTo("$TEST_YEAR $STUB_YEARS")
        verify(resourceProvider).getQuantityString(PLURAL_YEARS_RES, 2, 2)
        verifyNoMoreInteractions(resourceProvider)
    }


    companion object {
        const val DIGIT_ICONS_ARRAY_RES = 0x01
        const val PLURAL_MONTHS_RES = 0x02
        const val PLURAL_YEARS_RES = 0x03
    }
}