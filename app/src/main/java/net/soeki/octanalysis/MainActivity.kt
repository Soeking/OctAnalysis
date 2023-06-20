package net.soeki.octanalysis

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.runBlocking
import net.graphql.GetVersionQuery
import net.soeki.octanalysis.ui.theme.OctAnalysisTheme

class MainActivity : ComponentActivity() {
    val client =
        ApolloClient.Builder().serverUrl("https://api.oejp-kraken.energy/v1/graphql/").build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OctAnalysisTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        runBlocking {
            val number = client.query(GetVersionQuery()).execute().data?.krakenVersion?.number
            Log.d("graphql test", number ?: "null")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OctAnalysisTheme {
        Greeting("Android")
    }
}