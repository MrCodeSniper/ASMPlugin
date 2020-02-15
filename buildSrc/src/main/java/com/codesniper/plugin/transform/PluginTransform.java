package com.codesniper.plugin.transform;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.builder.model.AndroidProject;
import com.codesniper.plugin.utils.TransformUtils;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    }
}
