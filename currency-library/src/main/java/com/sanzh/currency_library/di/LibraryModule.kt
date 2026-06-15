package com.sanzh.currency_library.di


import com.sanzh.currency_library.data.mapper.CurrencyMapper
import com.sanzh.currency_library.data.remote.CurrencyApi
import com.sanzh.currency_library.data.repository.CurrencyRepositoryImpl
import com.sanzh.currency_library.domain.interactor.CurrencyInteractor
import com.sanzh.currency_library.domain.repository.CurrencyRepository
import com.sanzh.currency_library.domain.usecase.ConvertCurrencyUseCase
import com.sanzh.currency_library.domain.usecase.GetCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LibraryModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://open.er-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideCurrencyMapper(): CurrencyMapper = CurrencyMapper

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        api: CurrencyApi,
        mapper: CurrencyMapper
    ): CurrencyRepository = CurrencyRepositoryImpl(api, mapper)

    @Provides
    @Singleton
    fun provideGetCurrenciesUseCase(repository: CurrencyRepository): GetCurrenciesUseCase =
        GetCurrenciesUseCase(repository)

    @Provides
    @Singleton
    fun provideConvertCurrencyUseCase(): ConvertCurrencyUseCase = ConvertCurrencyUseCase()

    @Provides
    @Singleton
    fun provideCurrencyInteractor(
        getCurrenciesUseCase: GetCurrenciesUseCase,
        convertCurrencyUseCase: ConvertCurrencyUseCase
    ): CurrencyInteractor = CurrencyInteractor(getCurrenciesUseCase, convertCurrencyUseCase)
}