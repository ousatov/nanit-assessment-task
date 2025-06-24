package com.usatov.nanithometask.feature.birthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usatov.nanithometask.core.common.resources.ResourceProvider
import com.usatov.nanithometask.domain.birthday.Birthday
import com.usatov.nanithometask.domain.birthday.SubscribeBirthdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    subscribe: SubscribeBirthdayUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    val uiState: StateFlow<UiBirthday?> =
        subscribe()
            .map { it?.toUi() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private fun Birthday.toUi() = UiBirthday(
        name = name,
        ageLabel = dob.format(),
        theme = theme
    )

    private fun Long.format(): String {
        val then = Calendar.getInstance().apply { timeInMillis = this@format }
        val now = Calendar.getInstance()

        val months =
            (now.get(Calendar.YEAR) - then.get(Calendar.YEAR)) * MONTHS_IN_YEAR +
                    (now.get(Calendar.MONTH) - then.get(Calendar.MONTH))
        val clamped = months.coerceAtLeast(0)

        return if (clamped < MONTHS_IN_YEAR) {
            resourceProvider.getQuantityString(R.plurals.plural_months, clamped, clamped)
        } else {
            val years = (clamped / MONTHS_IN_YEAR).coerceAtMost(MAX_YEARS)
            resourceProvider.getQuantityString(R.plurals.plural_years, years, years)
        }
    }

    companion object {
        private const val MONTHS_IN_YEAR = 12
        private const val MAX_YEARS = 9
    }
}