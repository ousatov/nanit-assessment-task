package com.usatov.nanithometask.core.common.logging

import timber.log.Timber

// SIMPLIFIED. SHOULD BE IN ANOTHER MODULE
class TimberLogger : Logger {
    private var initialized = false

    override fun init() {
        if (!initialized) {
            Timber.plant(Timber.DebugTree())
            initialized = true
        }
    }

    override fun d(message: String) = Timber.d(message)
    override fun d(tag: String, message: String) = Timber.tag(tag).d(message)

    override fun e(message: String, throwable: Throwable?) =
        Timber.e(throwable, message)

    override fun e(tag: String, message: String, throwable: Throwable?) =
        Timber.tag(tag).e(throwable, message)

    override fun i(message: String) = Timber.i(message)
    override fun i(tag: String, message: String) = Timber.tag(tag).i(message)

    override fun w(message: String) = Timber.w(message)
    override fun w(tag: String, message: String) = Timber.tag(tag).w(message)

}