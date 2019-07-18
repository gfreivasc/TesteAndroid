package com.gabrielfv.ibmtest.features.funds

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabrielfv.ibmtest.domain.funds.model.Fund
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_funds.*
import javax.inject.Inject

class FundsFragment : Fragment(), FundsContract.View {
    @Inject
    lateinit var presenter: FundsContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_funds, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun inflateInfo(fund: Fund) {
        title.text = fund.title
        fundName.text = fund.fundName
        whatIs.text = fund.whatIs
        definition.text = fund.definition
        riskTitle.text = fund.riskTitle

        infoTitle.text = fund.infoTitle
        monthlyFund.text = fund.moreInfo.month.fund.toPercentage()
        monthlyCDI.text = fund.moreInfo.month.CDI.toPercentage()
        yearlyFund.text = fund.moreInfo.year.fund.toPercentage()
        yearlyCDI.text = fund.moreInfo.year.CDI.toPercentage()
        twMonthFund.text = fund.moreInfo.twMonth.fund.toPercentage()
        twMonthCDI.text = fund.moreInfo.twMonth.CDI.toPercentage()
    }

    private fun Double.toPercentage() = "${toString()}%"

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        presenter.dispose()
        super.onDetach()
    }
}