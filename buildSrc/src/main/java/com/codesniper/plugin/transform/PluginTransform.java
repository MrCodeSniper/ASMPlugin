package com.codesniper.plugin.transform;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.builder.model.AndroidProject;
import com.codesniper.plugin.context.RabbitTransformInvocation;
import com.codesniper.plugin.monitor.MethodCostMonitorTransform;
import com.codesniper.plugin.rxentension.IoKt;
import com.codesniper.plugin.utils.TransformUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import kotlin.io.FilesKt;

public class PluginTransform extends Transform  {
    @Override
    public String getName() {
        return "debug-transform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return  TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        TransformUtils.print("🍊 rabbit RabbitFirstTransform run");
        ArrayList byteArraysTransforms = new ArrayList<RabbitAsmByteArrayTransformer>();
        byteArraysTransforms.add(new MethodCostMonitorTransform());


        List<RabbitClassByteCodeTransformer> rabbitClassByteCodeTransformers=new ArrayList<>();
        rabbitClassByteCodeTransformers.add(new AsmByteArrayTransformer(byteArraysTransforms));


        RabbitTransformInvocation rabbitTransformInvocation=new RabbitTransformInvocation(transformInvocation, rabbitClassByteCodeTransformers);

        if(rabbitTransformInvocation.isIncremental()){
            rabbitTransformInvocation.onPreTransform(rabbitTransformInvocation);
            rabbitTransformInvocation.doIncrementalTransform$buildSrc();
        }else {
            java.io.File file= IoKt.file(rabbitTransformInvocation.getBuildDir(),new String[]{"intermediates","transforms","dexBuilder"});
            if(file.exists()){
                FilesKt.deleteRecursively(file);
            }

            rabbitTransformInvocation.getOutputProvider().deleteAll();
            rabbitTransformInvocation.onPreTransform(rabbitTransformInvocation);
            rabbitTransformInvocation.doFullTransform$buildSrc();


        }

        rabbitTransformInvocation.onPostTransform(rabbitTransformInvocation);
        ForkJoinPool pool=rabbitTransformInvocation.getExecutor();
        pool.shutdown();
        pool.awaitTermination(1L,TimeUnit.MINUTES);
    }
}
