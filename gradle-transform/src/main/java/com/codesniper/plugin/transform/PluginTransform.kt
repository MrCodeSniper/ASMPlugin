package com.codesniper.plugin.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.builder.model.AndroidProject
import com.codesniper.plugin.context.RabbitTransformInvocation
import com.codesniper.plugin.monitor.MethodCostMonitorTransform
import com.codesniper.plugin.rxentension.file
import com.codesniper.plugin.utils.TransformUtils

import java.io.IOException
import java.util.concurrent.TimeUnit

class PluginTransform : Transform() {
    override fun getName(): String {
        return "debug-transform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
            TransformManager.CONTENT_CLASS

    override fun isIncremental() = true

    override fun getScopes(): MutableSet<QualifiedContent.ScopeType> =
            TransformManager.SCOPE_FULL_PROJECT

    override fun transform(transformInvocation: TransformInvocation?) {

        if (transformInvocation == null) return

        TransformUtils.print("🍊 rabbit RabbitFirstTransform run")

        val classVisitorTransforms = listOf(
                RabbitPluginConfigTransform()
        )

        val byteArraysTransforms = listOf(MethodCostMonitorTransform())

        RabbitTransformInvocation(
                transformInvocation,
                listOf(
                        AsmClassVisitorTransformer(classVisitorTransforms),
                        AsmByteArrayTransformer(byteArraysTransforms)
                )
        ).apply {
            if (isIncremental) {
                onPreTransform(this)
                doIncrementalTransform()
            } else {
                //删除老的构建内容
                buildDir.file(AndroidProject.FD_INTERMEDIATES, "transforms", "dexBuilder")
                        .let { dexBuilder ->
                            if (dexBuilder.exists()) {
                                dexBuilder.deleteRecursively()
                            }
                        }
                outputProvider.deleteAll()
                onPreTransform(this)
                doFullTransform()
            }

            onPostTransform(this)

        }.executor.apply {
            shutdown()
            awaitTermination(1, TimeUnit.MINUTES)
        }
    }

}
