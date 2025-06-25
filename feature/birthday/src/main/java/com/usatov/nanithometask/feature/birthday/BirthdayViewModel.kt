package com.usatov.nanithometask.feature.birthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usatov.nanithometask.core.common.formatting.BabyInfoFormatter
import com.usatov.nanithometask.domain.birthday.Birthday
import com.usatov.nanithometask.domain.birthday.SubscribeBirthdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    subscribe: SubscribeBirthdayUseCase,
    private val babyInfoFormatter: BabyInfoFormatter,
) : ViewModel() {

    val uiState: StateFlow<UiBirthday?> =
        subscribe()
            .map { it?.toUi() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private fun Birthday.toUi() = UiBirthday(
        nameLabel = babyInfoFormatter.formatName(name, R.string.today_name_is),
        ageLabel = babyInfoFormatter.formatAge(
            dob,
            R.plurals.age_months_suffix,
            R.plurals.age_years_suffix,
        ),
        ageResource = babyInfoFormatter.getAgeIcon(dob, R.array.digit_icons),
        theme = theme
    )
}