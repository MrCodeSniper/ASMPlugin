package com.codesniper.plugin.monitor


import com.codesniper.plugin.context.TransformContext
import com.codesniper.plugin.transform.RabbitAsmByteArrayTransformer
import com.susion.rabbit.gradle.transform.visitor.MethodCostClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassReader.EXPAND_FRAMES
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

/**
 * susionwang at 2020-01-15
 */
class MethodCostMonitorTransform : RabbitAsmByteArrayTransformer {

    override fun transform(
            context: TransformContext,
            bytes: ByteArray,
            classFilePath: String
    ): ByteArray {

        val classReader = ClassReader(bytes)
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val classVisitor = MethodCostClassVisitor(
            Opcodes.ASM5,
            classWriter
        )
        classReader.accept(classVisitor, EXPAND_FRAMES)
        return classWriter.toByteArray()
    }

}