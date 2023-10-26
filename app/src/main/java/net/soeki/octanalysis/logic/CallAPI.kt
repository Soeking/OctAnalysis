package net.soeki.octanalysis.logic

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import net.graphql.HalfHourlyReadingsQuery
import net.graphql.LoginMutation
import net.graphql.type.ObtainJSONWebTokenInput

class CallAPI {
    private val client =
        ApolloClient.Builder().serverUrl("https://api.oejp-kraken.energy/v1/graphql/").build()

    fun doLogin(input: ObtainJSONWebTokenInput): LoginMutation.ObtainKrakenToken? {
        return runBlocking {
            client.mutation(LoginMutation(input)).execute().data?.obtainKrakenToken
        }
    }

    fun getSupplyValue(
        account: String,
        fromDateTime: LocalDateTime,
        toDateTime: LocalDateTime
    ): List<HalfHourlyReadingsQuery.HalfHourlyReading?>? {
        return runBlocking {
            client.query(
                HalfHourlyReadingsQuery(
                    account,
                    Optional.present(fromDateTime),
                    Optional.present(toDateTime)
                )
            ).execute().data?.account?.properties?.electricitySupplyPoints?.halfHourlyReadings
        }
    }
}