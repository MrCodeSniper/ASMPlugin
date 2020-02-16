package com.codesniper.plugin.utils

object TransformUtils {

    fun print(msg: String) {
        if (!GlobalConfig.pluginConfig.printLog) return
        println("🐰 -----> $msg \n")
    }

    fun print(tag: String, msg: String) {
        if (!GlobalConfig.pluginConfig.printLog) return
        println("🐰 -----> TAG : $tag ; $msg \n")
    }

    fun classInPkgList(className: String, pkgList: List<String>): Boolean {

        if (pkgList.isEmpty()) return true

        pkgList.forEach {
            if (className.startsWith(it)) {
                return true
            }
        }
        return false
    }
}
