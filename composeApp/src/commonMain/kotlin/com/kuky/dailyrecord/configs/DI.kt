package com.kuky.dailyrecord.configs

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

//import org.koin.ksp.generated.module

//@Module
//@ComponentScan("com.kuky.dailyrecord")
//class AppModule

//fun appModule() = listOf(AppModule().module)

val provideUtilsModule = module {
    singleOf(::GlobalState)
}