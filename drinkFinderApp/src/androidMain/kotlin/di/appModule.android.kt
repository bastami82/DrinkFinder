package di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import networking.createHttpClient

actual val client: HttpClient = createHttpClient(engine = OkHttp.create())