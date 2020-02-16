package com.codesniper.plugin.transform

import com.codesniper.plugin.context.TransformContext
import org.objectweb.asm.tree.ClassNode

/**
 * Represents class transformer
 */
interface RabbitAsmClassVisitorTransformer : TransformListener {

    /**
     * Transform the specified class node
     *
     * @param context The transform context
     * @param klass The class node to be transformed
     * @return The transformed class node
     */
    fun transform(context: TransformContext, klass: ClassNode, classFilePath:String ) = klass

}
