package com.codesniper.plugin.visitor

import com.codesniper.plugin.RabbitTracerEventNotifier


/**
 * susionwang at 2020-01-02
 */
object MethodTracer {

    private var methodStartTime: Long = 0
    val CLASS_PATH = "com/susion/rabbit/tracer/MethodTracer"
    val METHOD_RECORD_METHOD_START = "recordMethodStart"
    val METHOD_RECORD_METHOD_END = "recordMethodEnd"
    val METHOD_RECORD_METHOD_END_PARAMS = "(Ljava/lang/String;)V"

    fun recordMethodStart() {
        methodStartTime = System.currentTimeMillis()
    }

    fun recordMethodEnd(name: String) {
        RabbitTracerEventNotifier.methodCostNotifier.methodCost(name, System.currentTimeMillis() - methodStartTime)
    }
}
