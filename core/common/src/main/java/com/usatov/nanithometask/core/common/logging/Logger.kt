package com.usatov.nanithometask.core.common.logging

interface Logger {
    fun init()

    fun d(message: String)
    fun d(tag: String, message: String)
    fun e(message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun i(message: String)
    fun i(tag: String, message: String)
    fun w(message: String)
    fun w(tag: String, message: String)
}