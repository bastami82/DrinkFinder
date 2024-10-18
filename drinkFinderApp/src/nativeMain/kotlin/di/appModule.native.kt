package di

import io.ktor.client.engine.darwin.Darwin
import networking.createHttpClient

actual val client = createHttpClient(engine = Darwin.create())