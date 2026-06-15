package com.sanzh.converterapp.di

// All bindings are provided by LibraryModule in the currency-library module.
// This file is a placeholder for any app-specific DI needs.

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule