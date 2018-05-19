package com.dmi.kotlintest.ui.auth

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dmi.kotlintest.common.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface AuthView : BaseView {
    fun setPhoneMask(phoneMask: String?)
    fun setPhoneError(validateError: ValidateError)
    fun setPasswordError(validateError: ValidateError)
    fun showRoot()
    fun showAthError()
}

enum class ValidateError{
    NONE, EMPTY, NOT_VALID, SMALL_LENGTH, BIG_LENGTH
}