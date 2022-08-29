package com.erapps.newws_compose.utils

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Exclude(val serialize: Boolean = true, val deserialize: Boolean = true)
