package com.fintech.denispok.fintechproject.utilities

import com.fintech.denispok.fintechproject.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InjectorUtilsModule::class])
interface InjectorComponent {

    fun inject(application: App)

}
