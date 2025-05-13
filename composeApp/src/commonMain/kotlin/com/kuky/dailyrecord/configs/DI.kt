package com.kuky.dailyrecord.configs

import com.kuky.dailyrecord.pages.edit.RecordEditRepository
import com.kuky.dailyrecord.pages.edit.RecordEditViewModel
import com.kuky.dailyrecord.pages.home.HomeRepository
import com.kuky.dailyrecord.pages.home.HomeViewModel
import com.kuky.dailyrecord.testcase.TestCategoryCase
import com.kuky.dailyrecord.testcase.TestRecordCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

//import org.koin.ksp.generated.module

//@Module
//@ComponentScan("com.kuky.dailyrecord")
//class AppModule

//fun appModule() = listOf(AppModule().module)

val testCaseModules = module {
    factoryOf(::TestRecordCase)
    factoryOf(::TestCategoryCase)
}

val provideUtilsModule = module {
    singleOf(::GlobalState)
}

val repositoryModule = module {
    factoryOf(::HomeRepository)
    factoryOf(::RecordEditRepository)
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::RecordEditViewModel)
}