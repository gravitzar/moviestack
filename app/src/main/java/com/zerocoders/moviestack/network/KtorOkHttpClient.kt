package com.zerocoders.moviestack.network

import android.util.Log
import com.zerocoders.moviestack.Env.API_KEY
import com.zerocoders.moviestack.Env.DOMAIN
import com.zerocoders.moviestack.tmdb.MediaListItem
import com.zerocoders.moviestack.tmdb.Movie
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

private const val TIME_OUT_MS = 60_000L

object KtorOkHttpClient {
    fun getHttpClient() = HttpClient {
        expectSuccess = false

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KTOR-CLI", "log: $message")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT_MS
            connectTimeoutMillis = TIME_OUT_MS
            socketTimeoutMillis = TIME_OUT_MS
        }

        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = false
                ignoreUnknownKeys = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                prettyPrint = false

                val module = SerializersModule {
                    polymorphic(MediaListItem::class, Movie::class, Movie.serializer())
                }
                serializersModule = module
                classDiscriminator = "media_type"
            })
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("KTOR-CLI - HTTP status", "${response.status.value}")
                Log.d("KTOR-CLI - Response:", response.toString())
            }
        }

        install(DefaultRequest) {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            url {
                protocol = URLProtocol.HTTPS
                host = DOMAIN
                parameters.append("api_key", API_KEY)
            }
        }
    }
}