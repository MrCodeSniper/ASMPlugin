package com.codesniper.plugin.transform

import com.codesniper.plugin.context.TransformContext


/**
 * susionwang at 2019-11-13
 */
interface TransformListener {

    fun onPreTransform(context: TransformContext){}

    fun onPostTransform(context: TransformContext){}

}
