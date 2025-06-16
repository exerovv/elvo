package com.example.elvo.di

import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import com.example.elvo.domain.repositories.FaqRepository
import com.example.elvo.domain.repositories.OrderRepository
import com.example.elvo.domain.repositories.PopularRepository
import com.example.elvo.domain.repositories.RecipientRepository
import com.example.elvo.domain.usecase.popular.FetchPopularItemsUseCase
import com.example.elvo.domain.usecase.auth.LoginUseCase
import com.example.elvo.domain.usecase.auth.RefreshUseCase
import com.example.elvo.domain.usecase.auth.RegisterUseCase
import com.example.elvo.domain.usecase.faq.FetchFaqUseCase
import com.example.elvo.domain.usecase.order.AddOrderUseCase
import com.example.elvo.domain.usecase.order.FetchOrderHistoryUseCase
import com.example.elvo.domain.usecase.order.FetchOrderListUseCase
import com.example.elvo.domain.usecase.order.FetchSingleOrderUseCase
import com.example.elvo.domain.usecase.order.PayForOrderUseCase
import com.example.elvo.domain.usecase.recipient.AddRecipientUseCase
import com.example.elvo.domain.usecase.recipient.FetchRecipientListUseCase
import com.example.elvo.domain.usecase.recipient.FetchSingleRecipientUseCase
import com.example.elvo.domain.usecase.recipient.UpdateRecipientUseCase
import com.example.elvo.domain.usecase.user.FetchUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository, dataStoreRepository: DataStoreRepository): LoginUseCase {
        return LoginUseCase(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository, dataStoreRepository: DataStoreRepository): RegisterUseCase {
        return RegisterUseCase(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideRefreshUseCase(authRepository: AuthRepository, dataStoreRepository: DataStoreRepository): RefreshUseCase {
        return RefreshUseCase(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideFetchPopularItemsUseCase(popularRepository: PopularRepository): FetchPopularItemsUseCase {
        return FetchPopularItemsUseCase(popularRepository)
    }

    @Provides
    fun provideFetchFaqUseCase(faqRepository: FaqRepository): FetchFaqUseCase{
        return FetchFaqUseCase(faqRepository)
    }

    @Provides
    fun provideAddRecipientUseCase(recipientRepository: RecipientRepository): AddRecipientUseCase{
        return AddRecipientUseCase(recipientRepository)
    }

    @Provides
    fun provideUpdateRecipientUseCase(recipientRepository: RecipientRepository): UpdateRecipientUseCase{
        return UpdateRecipientUseCase(recipientRepository)
    }

    @Provides
    fun provideFetchRecipientListUseCase(recipientRepository: RecipientRepository): FetchRecipientListUseCase{
        return FetchRecipientListUseCase(recipientRepository)
    }

    @Provides
    fun provideFetchSingleRecipientUseCase(recipientRepository: RecipientRepository): FetchSingleRecipientUseCase{
        return FetchSingleRecipientUseCase(recipientRepository)
    }

    @Provides
    fun provideFetchUserInfoUseCase(dataStoreRepository: DataStoreRepository): FetchUserInfoUseCase{
        return FetchUserInfoUseCase(dataStoreRepository)
    }

    @Provides
    fun provideAddOrderUseCase(orderRepository: OrderRepository): AddOrderUseCase {
        return AddOrderUseCase(orderRepository)
    }

    @Provides
    fun provideFetchOrderListUseCase(orderRepository: OrderRepository): FetchOrderListUseCase {
        return FetchOrderListUseCase(orderRepository)
    }

    @Provides
    fun provideFetchOrderHistoryUseCase(orderRepository: OrderRepository): FetchOrderHistoryUseCase {
        return FetchOrderHistoryUseCase(orderRepository)
    }

    @Provides
    fun provideFetchSingleOrderUseCase(orderRepository: OrderRepository): FetchSingleOrderUseCase {
        return FetchSingleOrderUseCase(orderRepository)
    }

    @Provides
    fun providePayForOrderUseCase(orderRepository: OrderRepository): PayForOrderUseCase {
        return PayForOrderUseCase(orderRepository)
    }
}