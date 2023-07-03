package net.soeki.octanalysis.logic

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.runBlocking
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
}