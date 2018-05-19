package com.dmi.kotlintest.common

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.arellomobile.mvp.MvpDelegate
import com.dmi.kotlintest.R
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import android.view.LayoutInflater

abstract class BaseActivity : KodeinAppCompatActivity() {

    private var delegate: MvpDelegate<out BaseActivity>? = null
    private var progressDialog: AlertDialog? = null

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        getMvpDelegate().onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    private fun getMvpDelegate(): MvpDelegate<out BaseActivity> {
        if (delegate == null)
            delegate = MvpDelegate<BaseActivity>(this)

        return delegate!!
    }

    override fun onStart() {
        super.onStart()
        getMvpDelegate().onAttach()
    }

    override fun onResume() {
        super.onResume()
        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        getMvpDelegate().onSaveInstanceState(outState!!)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()
        getProgressDialog()?.dismiss()
        getMvpDelegate().onDetach()
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        super.onDestroy()
        getMvpDelegate().onDestroyView()

        if (isFinishing) {
            getMvpDelegate().onDestroy()
        }
    }

    fun alert(message: String?) {
        val text = if (message.isNullOrEmpty()) getString(R.string.err_unknown_try_later) else message

        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(R.string.button_ok, { dialog, _ -> dialog.dismiss() } )
                .create()
                .show()
    }

    fun showProgress() {
        getProgressDialog()?.show()
    }

    fun hideProgress() {
        getProgressDialog()?.dismiss()
    }

    private fun getProgressDialog() : AlertDialog? {
        if (progressDialog == null) {
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_progress, null)
            dialogBuilder.setView(dialogView)
            dialogBuilder.setCancelable(false)
            progressDialog = dialogBuilder.create()
        }
        return progressDialog
    }
}