package com.dmi.kotlintest.ui.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dmi.kotlintest.data.network.core.HttpService
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class AuthPresenter(injector: KodeinInjector): MvpPresenter<AuthView>() {

    companion object {
        const val PASSWORD_MIN_LENGTH = 5
        const val PASSWORD_MAX_LENGTH = 15
    }

    private val service: HttpService by injector.instance()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showProgress()
        service.getPhoneMask()
                .subscribeBy(
                        onSuccess = { it ->
                            viewState.hideProgress()
                            viewState.setPhoneMask(it.phoneMask)
                        },
                        onError = { throwable ->
                            viewState.hideProgress()
                            viewState.alert(throwable.message)
                        }
                )
    }

    fun signIn(phoneNumber: String, password: String) {
        if (phoneNumber.isEmpty() || password.isEmpty() ||
                password.length < PASSWORD_MIN_LENGTH || password.length > PASSWORD_MAX_LENGTH)
            return

        viewState.showProgress()
        service.auth(phoneNumber, password)
                .subscribeBy(
                        onSuccess = { it ->
                            if (it) {
                                viewState.hideProgress()
                                viewState.showRoot()
                            } else {
                                viewState.hideProgress()
                                viewState.showAthError()
                            }
                        },
                        onError = { throwable ->
                            viewState.hideProgress()
                            viewState.alert(throwable.message)
                        }
                )
    }
}