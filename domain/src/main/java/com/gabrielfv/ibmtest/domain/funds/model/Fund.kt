package com.gabrielfv.ibmtest.domain.funds.model

import com.google.gson.annotations.SerializedName

data class Fund(
    val title: String,
    val fundName: String,
    val whatIs: String,
    val definition: String,
    val riskTitle: String,
    val risk: Int,
    val infoTitle: String,
    val moreInfo: FundMoreInfo,
    val info: List<FundInfo>,
    val downInfo: List<FundDownloadableInfo>
)

data class FundVsCDI(
    val fund: Double,
    val CDI: Double
)

data class FundMoreInfo(
    val month: FundVsCDI,
    val year: FundVsCDI,
    @SerializedName("12months") val twMonth: FundVsCDI
)

data class FundInfo(
    val name: String,
    val data: String
)

data class FundDownloadableInfo(
    val name: String,
    val data: String?
)