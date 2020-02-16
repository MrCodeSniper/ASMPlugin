package com.codesniper.asm

import android.content.Context
import android.os.Looper
import com.codesniper.plugin.RabbitTracerEventNotifier


internal class RabbitMethodMonitor(override var isOpen: Boolean = false) : RabbitMonitorProtocol {

    private val T = javaClass.simpleName
    private val slowMethodThreshold = 50L

    private val methodCostListener = object : RabbitTracerEventNotifier.MethodCostEvent {
        override fun methodCost(methodStr: String, time: Long) {
            val monitorCurrentThread = if (true) {
                Thread.currentThread().name == Looper.getMainLooper().thread.name
            } else {
                true
            }
            if (time > slowMethodThreshold && monitorCurrentThread) {
                saveSlowMethod(methodStr, time)
            }
        }
    }

    override fun open(context: Context) {
        RabbitTracerEventNotifier.methodCostNotifier = methodCostListener
    }

    override fun close() {
        RabbitTracerEventNotifier.methodCostNotifier =
            RabbitTracerEventNotifier.FakeMethodCostListener()
    }

    private fun saveSlowMethod(methodStr: String, time: Long) {

        val fullClassName = methodStr.split("&").firstOrNull() ?: ""
        val methodName = methodStr.split("&").lastOrNull() ?: ""
        val classNameStartIndex = fullClassName.lastIndexOf(".")

        if (classNameStartIndex > 0) {

            val className =
                fullClassName.subSequence(classNameStartIndex + 1, fullClassName.length).toString()
            val pkgName = fullClassName.subSequence(0, classNameStartIndex).toString()
            RabbitLog.d(
                TAG_MONITOR,
                "slow method --> $pkgName -> $className -> $methodName -> $time ms"
            )
//
//            val slowMethod = RabbitSlowMethodInfo().apply {
//                this.pkgName = pkgName
//                this.className = className
//                this.methodName = methodName
//                this.costTimeMs = time
//                this.time = System.currentTimeMillis()
//                callStack = RabbitMonitorUtils.traceToString(5, Thread.currentThread().stackTrace, 15)
//            }
//
//            RabbitDbStorageManager.save(slowMethod)
        }
    }

    override fun getMonitorInfo() = RabbitMonitorProtocol.SLOW_METHOD

}