package com.example.elvo.data.implementations


class AuthServiceImpl {

//    companion object {
//        private const val BASE_URL = ""
//        private var authApi: AuthApi? = null
//    }
//
//    init {
//        if (authApi == null) {
//            val client = OkHttpClient.Builder()
//                .build()
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            authApi = retrofit.create(AuthApi::class.java)
//        }
//    }
//
//    fun signIn(username: String, password: String): Single<AuthResponse> {
//        val request = AuthRequest(username, password)
//        return Single.fromCallable {
//            val response = authApi!!.signIn(request)
//            if (response.isSuccessful && response.body() != null) {
//                response.body()!!
//            } else {
//                throw Exception(response.errorBody()?.string() ?: "Unknown error")
//            }
//        }.subscribeOn(Schedulers.io())
//    }
}
