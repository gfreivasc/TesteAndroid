package com.gabrielfv.ibmtest.domain.funds

import com.gabrielfv.ibmtest.domain.funds.external.FundProvider
import javax.inject.Inject

class GetFundUseCase @Inject constructor(private val fundProvider: FundProvider) {

    operator fun invoke() = fundProvider.getFund()
}