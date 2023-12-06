package com.jjanjjan.franchise.shared.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText

class FranchiseRepository {
    private val client = HttpClient()
    suspend fun getFranchise(): String {
        val response = client.get("https://apis.data.go.kr/1130000/FftcBrandFrcsStatsService/getBrandFrcsStats") {
            parameter("serviceKey", "JKoTdeNxB2Af758DGnCSNcEmk2IotkpWygmtgovaYoSFGFy71Tdl/FmsuXvLZaqYIEQ+JyiinovYBR3eJxImTA==")
            parameter("pageNo", "1")
            parameter("numOfRows", "1")
            parameter("resultType", "json")
            parameter("yr", "2022")

        }
        return response.bodyAsText()
    }
}