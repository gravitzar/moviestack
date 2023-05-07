package com.zerocoders.moviestack.utils

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request
import org.schabi.newpipe.extractor.downloader.Response
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException
import java.util.concurrent.TimeUnit


class DownloaderImpl private constructor(
    private val builder: OkHttpClient.Builder
) : Downloader() {
    private var client: OkHttpClient = builder
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    private val mCookies: HashMap<String, String> = HashMap()

    fun getContentLength(url: String): Long {
        return try {
            head(url).getHeader("Content-Length")?.toLong() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun getCookie(key: String): String? {
        return mCookies[key]
    }

    fun setCookie(key: String, cookie: String) {
        mCookies[key] = cookie
    }

    fun removeCookie(key: String) {
        mCookies.remove(key)
    }

    fun getCookies(url: String): String {
        val youtubeCookie = if (url.contains(YOUTUBE_DOMAIN)) getCookie(YOUTUBE_RESTRICTED_MODE_COOKIE_KEY) else null

        // Recaptcha cookie is always added TODO: not sure if this is necessary
        val cookieList = listOf(
            youtubeCookie,
            getCookie(RECAPTCHA_COOKIES_KEY)
        )
        return cookieList.filterNotNull()
            .flatMap {
                it.split("; *")
            }
            .distinct()
            .joinToString(separator = "; ")
    }

    override fun execute(request: Request): Response {
        val httpMethod = request.httpMethod()
        val url = request.url()
        val headers = request.headers()
        val dataToSend = request.dataToSend()
        var requestBody: RequestBody? = null
        if (dataToSend != null) {
            requestBody = dataToSend.toRequestBody()
        }

        val requestBuilder: okhttp3.Request.Builder = okhttp3.Request.Builder()
            .method(httpMethod, requestBody).url(url)
            .addHeader("User-Agent", USER_AGENT)

        val cookies = getCookies(url)
        if (cookies.isNotBlank()) {
            requestBuilder.addHeader("Cookie", cookies)
        }

        headers.entries.forEach { (headerName, headerValueList) ->
            if (headerValueList.size > 1) {
                requestBuilder.removeHeader(headerName)
                headerValueList.forEach { headerValue ->
                    requestBuilder.addHeader(headerName, headerValue)
                }
            }
            if (headerValueList.size == 1) {
                requestBuilder.header(headerName, headerValueList.first())
            }
        }

        val response = client.newCall(requestBuilder.build()).execute()

        if (response.code == 429) {
            response.close()
            throw ReCaptchaException("reCaptcha Challenge requested", url)
        }

        val body = response.body
        var responseBodyToReturn: String? = null

        if (body != null) {
            responseBodyToReturn = body.string()
        }

        val latestUrl = response.request.url.toString()

        return Response(
            response.code,
            response.message,
            response.headers.toMultimap(),
            responseBodyToReturn,
            latestUrl
        )
    }

    companion object {
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; rv:91.0) Gecko/20100101 Firefox/91.0"
        const val YOUTUBE_RESTRICTED_MODE_COOKIE_KEY = "youtube_restricted_mode_key"
        const val YOUTUBE_RESTRICTED_MODE_COOKIE = "PREF=f2=8000000"
        const val YOUTUBE_DOMAIN = "youtube.com"
        const val RECAPTCHA_COOKIES_KEY = "recaptcha_cookies"

//        https://www.youtube.com/watch?v=l-jBcewRW70

        //        https://www.youtube.com/watch?v=oc_o5DZM3qc
        @Volatile
        private var INSTANCE: DownloaderImpl? = null

        fun getInstance(): DownloaderImpl = INSTANCE ?: init()

        fun init(builder: OkHttpClient.Builder? = null): DownloaderImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DownloaderImpl(
                    builder = builder ?: OkHttpClient.Builder()
                ).also { INSTANCE = it }
            }
        }
    }
}