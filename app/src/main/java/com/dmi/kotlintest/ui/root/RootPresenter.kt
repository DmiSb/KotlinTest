package com.dmi.kotlintest.ui.root

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dmi.kotlintest.data.network.core.HttpService
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@InjectViewState
class RootPresenter(injector: KodeinInjector) : MvpPresenter<RootView>() {

    private val service: HttpService by injector.instance()
    private var subscription: Disposable? = null
    private var sortByDate: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setDateSort(sortByDate)
        loadDataInfo()
    }

    fun loadDataInfo() {
        viewState.showProgress()
        service.getEndpoints()
                .subscribeBy(
                        onSuccess = { list ->
                            viewState.hideProgress()
                            viewState.setList(
                                    list.sortedWith(
                                            sortBy( { if (sortByDate) it.date else it.sort} )
                                    )
                            )
                        },
                        onError = { throwable ->
                            viewState.hideProgress()
                            viewState.alert(throwable.message)
                        }
                )
    }

    fun startTimer() {
        subscription = Observable.interval(0, 10, TimeUnit.SECONDS)
                .timeInterval()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { loadDataInfo() }
    }

    fun stopTimer() {
        subscription?.dispose()
        subscription = null
    }

    fun setServerSort() {
        sortByDate = false
        loadDataInfo()
    }

    fun setDateSort() {
        sortByDate = true
        loadDataInfo()
    }

    private fun <T> sortBy(vararg selectors: (T) -> Comparable<*>?): Comparator<T> {
        return Comparator { a, b -> compareValuesBy(a, b, *selectors) }
    }
}