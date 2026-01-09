//package nz.adjmunro.util.network
//
//import okhttp3.Interceptor
//import okhttp3.Request
//import okhttp3.RequestBody
//import okhttp3.Response
//import okio.Buffer
//import java.io.IOException
//import java.nio.charset.Charset
//
///**
// * Intercepts OkHttp requests and attaches the RequestHeaders for Authentication
// */
//class RequestInterceptor(
//    private val authDataSource: AuthDataSource,
//    private val analytics: Analytics,
//    private val environmentConfig: EnvironmentConfig
//) : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val original = chain.request()
//        val note = "[${original.method}] Request: ${original.shortenedUrl} ${original.requestBody}"
//        analytics.addNote(note.obscureValues())
//        val builder = original.newBuilder()
//            .header(
//                "Authorization",
//                "Bearer " + authDataSource.authentication?.accessToken
//            )
//            .header("Accept-Language", "en")
//            .header("User-Agent", getUserAgent())
//            .header("Content-Type", "application/json")
//            .method(original.method, original.body)
//        if (original.header("accept") == null) {
//            //Only add accept header if one doesn't exist already
//            builder.header("Accept", "application/json")
//        }
//        val newRequest = builder.build()
//
//        return chain.proceed(newRequest).also { logRequest(original, it) }
//    }
//
//    private fun logRequest(request: Request, response: Response) {
//        try {
//            val note = "[${request.method}] Response (${response.codeDescription}): ${request.shortenedUrl} ${request.requestBody}"
//            analytics.addNote(note.obscureValues())
//        } catch (e: Exception) { // Very general catch as it's better to not log at all than to throw an error trying to log
//            analytics.logException(e)
//        }
//    }
//
//    /**
//     * Most (but not all) HTTP response code causes.
//     *
//     * *If you type the number, copilot knows what all the codes are supposed to mean!*
//     *
//     * See https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status
//     */
//    private inline val Response.codeDescription: String
//        get() = when(code) {
//            200 -> "200 OK"
//            201 -> "201 Created"
//            202 -> "202 Accepted"
//            204 -> "204 No Content"
//            205 -> "205 Reset Content"
//            206 -> "206 Partial Content"
//            400 -> "400 Bad Request"
//            401 -> "401 Unauthorized"
//            403 -> "403 Forbidden"
//            404 -> "404 Not Found"
//            408 -> "408 Request Timeout"
//            409 -> "409 Conflict"
//            413 -> "413 Payload Too Large"
//            414 -> "414 URI Too Long"
//            418 -> "418 I'm a teapot"
//            429 -> "429 Too Many Requests"
//            431 -> "431 Request Header Fields Too Large"
//            500 -> "500 Internal Server Error"
//            502 -> "502 Bad Gateway"
//            503 -> "503 Service Unavailable"
//            504 -> "504 Gateway Timeout"
//            505 -> "505 HTTP Version Not Supported"
//            508 -> "508 Loop Detected"
//            511 -> "511 Network Authentication Required"
//            else -> "$code"
//        }
//
//    /**
//     * Returns a shortened version of the URL for logging purposes.
//     * This removes query parameters and fragments to avoid logging sensitive information.
//     */
//    private inline val Request.shortenedUrl: String
//        get() = url.toUrl().toExternalForm().replace(
//            oldValue = environmentConfig.BASE_URL,
//            newValue = ""
//        )
//
//    private fun String.obscureValues(): String {
//        val keysToObscure = listOf(
//            "password", "reset_password", "client_secret", "client_id",
//            "refresh_token", "access_token", "existing_password", "new_password"
//        )
//        val obscured = keysToObscure.fold(this) { acc, key ->
//            acc.replace(Regex("(\"$key\":\")(.*?)(\")"), "$1***$3")
//        }
//        return obscured
//    }
//
//    private inline val Request.requestBody: String
//        get() {
//            val body: RequestBody = body ?: return ""
//            val charset: Charset = body.contentType()?.charset() ?: Charsets.UTF_8
//            val buffer = Buffer()
//
//            body.writeTo(sink = buffer)
//
//            return "\nRequestBody: ${buffer.readString(charset)}"
//        }
//
//    private fun getUserAgent() =
//        "orbit-remit-android/${environmentConfig.VERSION_NAME} (${System.getProperty("http.agent")?.split(
//            Regex("[()]")
//        )?.get(1)})"
//}
