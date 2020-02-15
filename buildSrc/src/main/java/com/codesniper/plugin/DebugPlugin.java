package com.codesniper.plugin;


import com.android.build.gradle.AppExtension;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class DebugPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
//        项目中gradle执行的时候，会先解析setting.gradle,
//        然后是build.gradle,如果想在解析build.gradle之前做点事，
//        可以使用project.beforeEvaluate如果想在解析build.gradle之后做点事可以project.afterEvaluate。
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println("测试");
            }
        });
        AppExtension appExtension= (AppExtension) project.getExtensions().getByName("android");
    }
}
