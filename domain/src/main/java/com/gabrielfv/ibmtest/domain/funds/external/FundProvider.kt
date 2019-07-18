package com.gabrielfv.ibmtest.domain.funds.external

import com.gabrielfv.ibmtest.domain.funds.model.Fund
import io.reactivex.Single

interface FundProvider {
    fun getFund(): Single<Fund>
}