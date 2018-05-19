package com.dmi.kotlintest.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class BaseRecyclerAdapter<T>(
        val layout: Int = 0,
        private var items: List<T>,
        private val holderFactory: ((view: View) -> BaseRecyclerHolder<T>),
        private val onItemClick: ((item: T, position: Int) -> Unit)? = null
) : RecyclerView.Adapter<BaseRecyclerHolder<T>>() {

    open fun obtainLayout(viewType: Int): Int {
        return layout
    }

    private fun inflateItemView(parent: ViewGroup?, viewType: Int): View {
        val inflater = LayoutInflater.from(parent?.context)
        return inflater.inflate(obtainLayout(viewType), parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecyclerHolder<T>, position: Int) {
        val item = items[position]
        holder.bindView(item, onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerHolder<T> {
        val view = inflateItemView(parent, viewType)
        return holderFactory.invoke(view)
    }

    override fun getItemCount() = items.size

    fun setDataSet(dataSet: List<T>) {
        items = dataSet
        notifyDataSetChanged()
    }
}