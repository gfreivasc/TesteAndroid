package com.gabrielfv.ibmtest.features.funds

import android.util.Log
import com.gabrielfv.ibmtest.domain.funds.GetFundUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class FundsPresenter(
    private val view: FundsContract.View,
    private val getFundUseCase: GetFundUseCase
) : FundsContract.Presenter {
    private val disposables = mutableListOf<Disposable>()

    override fun start() {
        getFundUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.inflateInfo(it) },
                { Log.e("ERROR", "failed: ", it) }
            )
            .let { disposables.add(it) }
    }

    override fun dispose() {
        disposables.forEach { it.dispose() }
    }
}