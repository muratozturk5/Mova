package com.muratozturk.mova.common

import android.os.CountDownTimer

/**
 * Created by luis rafael on 16/02/19.
 */
class MyCountDownTimer(startTime: Long, interval: Long, private val func: () -> Unit) :
    CountDownTimer(startTime, interval) {
    override fun onFinish() = func()
    override fun onTick(timer: Long) {}
}