package com.example.megalifecompose.di

import com.ccink.domain.di.DomainModule
import com.ccink.model.config.ConfigService
import com.example.megalifecompose.App
import com.example.megalifecompose.ui.first_pers_choosen.FirstPersChoosenViewModel
import com.example.megalifecompose.ui.forgot_password.ForgotPasswordViewModel
import com.example.megalifecompose.ui.login.LoginViewModel
import com.example.megalifecompose.ui.main.home.HomeViewModel
import com.example.megalifecompose.ui.main.store.StoreViewModel
import com.example.megalifecompose.ui.new_password.NewPasswordViewModel
import com.example.megalifecompose.ui.support.SupportScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DomainModule::class])
@Singleton
interface AppComponent {

    fun inject(viewModel: StoreViewModel)
    fun inject(viewModel: LoginViewModel)
    fun inject(viewModel: ForgotPasswordViewModel)
    fun inject(viewModel: NewPasswordViewModel)
    fun inject(viewModel: FirstPersChoosenViewModel)
    fun inject(viewModel: SupportScreenViewModel)
    fun inject(viewModel: HomeViewModel)
    fun configService(): ConfigService


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun build(): AppComponent
    }
}

//123qweT@