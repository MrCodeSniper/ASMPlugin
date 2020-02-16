package com.codesniper.plugin


import com.android.build.gradle.AppExtension
import com.codesniper.plugin.rxentension.getAndroid
import com.codesniper.plugin.transform.PluginTransform
import com.codesniper.plugin.utils.GlobalConfig
import com.codesniper.plugin.utils.RabbitConfigExtension

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

class DebugPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        //load custom config
        val config = project.extensions.create("rabbitConfig", RabbitConfigExtension::class.java)

        project.afterEvaluate {
            GlobalConfig.pluginConfig = config
            GlobalConfig.ioMethodCall.clear()
        }

        //        项目中gradle执行的时候，会先解析setting.gradle,
        //        然后是build.gradle,如果想在解析build.gradle之前做点事，
        //        可以使用project.beforeEvaluate如果想在解析build.gradle之后做点事可以project.afterEvaluate。
        project.afterEvaluate { println("测试") }
        project.getAndroid<AppExtension>().apply {
            registerTransform(PluginTransform())
        }
    }
}
