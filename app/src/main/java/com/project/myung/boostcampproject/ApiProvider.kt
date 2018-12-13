package com.project.myung.boostcampproject

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun provideMovieApi(baseURL: String): MovieAPI = Retrofit.Builder()

    // 통신할 서버의 주소를 입력합니다.
    .baseUrl(baseURL)

    // 네트워크 요청 로그를 표시해 줍니다.
    .client(provideOkHttpClient(provideLoggingInterceptor()))

    // 받은 응답을 옵서버블 형태로 변환해 줍니다.
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())

    // 서버에서 json 형식으로 데이터를 보내고 이를 파싱해서 받아옵니다.
    .addConverterFactory(GsonConverterFactory.create())

    .build()
    .create(MovieAPI::class.java)


// 네트뭐크 통신에 사용할 클라이언트 객체를 생성합니다.
private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    val b = OkHttpClient.Builder()
    // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
    b.addInterceptor(interceptor)
    return b.build()
}

// 네트워크 요청/응답을 로그에 표시하는 Interceptor 객체를 생성합니다.
private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    return interceptor
}