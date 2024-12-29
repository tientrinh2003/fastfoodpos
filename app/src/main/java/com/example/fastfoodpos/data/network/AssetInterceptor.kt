//package com.example.fastfoodpos.data.network
//
//import android.content.Context
//import okhttp3.Interceptor
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.Protocol
//import okhttp3.Response
//import okhttp3.ResponseBody.Companion.toResponseBody
//
//class AssetInterceptor(private val context: Context) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val url = request.url.toString()
//
//        if (url.startsWith("https://mock-api.com/menu.json")) {
//            println("AssetInterceptor: Intercepting request for $url")
//            val assetPath = "menu.json"
//            val inputStream = context.assets.open(assetPath)
//            val responseBody = inputStream.readBytes().toResponseBody("application/json".toMediaTypeOrNull())
//            println("AssetInterceptor: Successfully read data from $assetPath")
//            return Response.Builder()
//                .code(200)
//                .protocol(Protocol.HTTP_1_1)
//                .message("OK")
//                .request(request)
//                .body(responseBody)
//                .build()
//        }
//        println("AssetInterceptor: Proceeding with request for $url")
//        return chain.proceed(request)
//    }
//}