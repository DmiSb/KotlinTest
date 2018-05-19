package com.dmi.kotlintest.ui.auth

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dmi.kotlintest.R
import com.dmi.kotlintest.common.BaseActivity
import com.dmi.kotlintest.common.clearPhone
import com.dmi.kotlintest.common.orZero
import com.dmi.kotlintest.ui.root.RootActivity
import com.dmi.kotlintest.utils.SimpleTextWatcher
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_auth.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser

class AuthActivity : BaseActivity(), AuthView {

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @ProvidePresenter
    fun providePresenter() = AuthPresenter(injector)

    private lateinit var phoneListener: SimpleTextWatcher
    private lateinit var passwordListener: SimpleTextWatcher

    override fun provideOverridingModule() = Kodein.Module {
        bind<KodeinInjector>() with instance(this@AuthActivity.injector)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        authSignInBtn.setOnClickListener { presenter.signIn(
                authPhone.text.toString().clearPhone(), authPassword.text.toString()
        )}

        phoneListener = SimpleTextWatcher(authPhone, {s ->
            if (s.isNullOrEmpty())
                setPhoneError(ValidateError.EMPTY)
            else
                setPhoneError(ValidateError.NONE)
        })

        passwordListener = SimpleTextWatcher(authPassword, {s ->
            when {
                s?.length.orZero() < AuthPresenter.PASSWORD_MIN_LENGTH -> setPasswordError(ValidateError.SMALL_LENGTH)
                s?.length.orZero() > AuthPresenter.PASSWORD_MAX_LENGTH -> setPasswordError(ValidateError.BIG_LENGTH)
                else -> setPasswordError(ValidateError.NONE)
            }
        })
    }

    override fun setPhoneMask(phoneMask: String?) {
        phoneMask?.let {
            val maskString = it.replace("Х", "_")
            val slots = UnderscoreDigitSlotsParser().parseSlots(maskString)
            val mask = MaskImpl.createTerminated(slots)
            mask.isHideHardcodedHead = false
            val formatWatcher = MaskFormatWatcher(mask)
            formatWatcher.installOn(authPhone)

            authPhone.setText(maskString)
        }
    }

    override fun onResume() {
        super.onResume()
        authPhone.addTextChangedListener(phoneListener)
        authPassword.addTextChangedListener(passwordListener)
    }

    override fun onPause() {
        super.onPause()
        authPhone.removeTextChangedListener(phoneListener)
        authPassword.removeTextChangedListener(passwordListener)
    }

    override fun setPhoneError(validateError: ValidateError) {
        when (validateError) {
            ValidateError.NONE -> authPhone.error = null
            else -> authPhone.error = getString(R.string.auth_phone_error_empty)
        }
    }

    override fun setPasswordError(validateError: ValidateError) {
        when (validateError) {
            ValidateError.SMALL_LENGTH -> authPassword.error = getString(R.string.auth_password_error_less)
            ValidateError.BIG_LENGTH -> authPassword.error = getString(R.string.auth_password_error_more)
            else -> authPhone.error = null
        }
    }

    override fun showRoot() {
        val intent = Intent(this, RootActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun showAthError() {
        alert(getString(R.string.auth_failed))
    }
}