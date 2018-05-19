package com.dmi.kotlintest.ui.root

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dmi.kotlintest.common.BaseView
import com.dmi.kotlintest.data.model.DataInfo

@StateStrategyType(AddToEndSingleStrategy::class)
interface RootView: BaseView {
    fun setList(items: List<DataInfo>)
    fun setDateSort(dateSort: Boolean)
}