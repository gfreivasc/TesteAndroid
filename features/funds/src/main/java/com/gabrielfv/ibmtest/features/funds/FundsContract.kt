package com.gabrielfv.ibmtest.features.funds

import com.gabrielfv.ibmtest.domain.funds.model.Fund
import com.gabrielfv.ibmtest.libraries.core.CorePresenter

interface FundsContract {

    interface View {
        fun inflateInfo(fund: Fund)
    }

    interface Presenter : CorePresenter
}