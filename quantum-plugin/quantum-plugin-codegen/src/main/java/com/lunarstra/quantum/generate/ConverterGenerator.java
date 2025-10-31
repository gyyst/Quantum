package com.lunarstra.quantum.generate;

import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConverterGenerator implements IGenerator {

    @Override
    public String getTemplatePath() {
        return "template/converter.kt.tpl";
    }

    @Override
    public void setTemplatePath(String s) {

    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {

        if (!globalConfig.isEntityGenerateEnable()) {
            return;
        }

        PackageConfig packageConfig = globalConfig.getPackageConfig();
        EntityConfig entityConfig = globalConfig.getEntityConfig();

        String entityPackagePath = (packageConfig.getBasePackage() + ".model.converter").replace(".", "/");
        File entityJavaFile = new File(packageConfig.getSourceDir(),
            entityPackagePath + "/" + table.buildEntityClassName() + "Converter" + ".kt");

        if (entityJavaFile.exists() && !entityConfig.isOverwriteEnable()) {
            return;
        }

        Map<String, Object> params = new HashMap<>(8);
        params.put("table", table);
        params.put("entityConfig", entityConfig);
        params.put("packageConfig", packageConfig);
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        params.put("isBase", false);

        globalConfig.getTemplateConfig().getTemplate().generate(params, getTemplatePath(), entityJavaFile);
    }
}