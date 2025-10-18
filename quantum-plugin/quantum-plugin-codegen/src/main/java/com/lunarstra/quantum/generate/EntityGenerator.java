package com.lunarstra.quantum.generate;

import com.lunarstra.quantum.key.generate.MyKeyGenerators;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityGenerator implements IGenerator {

    @Override
    public String getTemplatePath() {
        return "/template/entity.tpl";
    }

    @Override
    public void setTemplatePath(String templatePath) {

    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {

        table.getColumns().forEach(column -> {
            if (column.isPrimaryKey() && !column.getAutoIncrement() && column.getRawType()
                .equalsIgnoreCase("varchar")) {

                column.getColumnConfig().setKeyType(KeyType.Generator);
                column.getColumnConfig().setKeyValue(MyKeyGenerators.UUIDV7);
            }
        });
        if (!globalConfig.isEntityGenerateEnable()) {
            return;
        }

        PackageConfig packageConfig = globalConfig.getPackageConfig();
        EntityConfig entityConfig = globalConfig.getEntityConfig();

        String entityPackagePath = packageConfig.getEntityPackage().replace(".", "/");
        File entityJavaFile = new File(packageConfig.getSourceDir(),
            entityPackagePath + "/" + table.buildEntityClassName() + ".java");

        if (entityJavaFile.exists() && !entityConfig.isOverwriteEnable()) {
            return;
        }

        Map<String, Object> params = new HashMap<>(8);
        params.put("table", table);
        params.put("entityConfig", entityConfig);
        params.put("packageConfig", packageConfig);
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        params.put("buildImports", buildImports(table));
        params.put("isBase", false);

        globalConfig.getTemplateConfig().getTemplate().generate(params, getTemplatePath(), entityJavaFile);
    }

    private String buildImports(Table table) {
        List<Column> columns = table.getColumns();
        StringBuilder stringBuilder = new StringBuilder();
        columns.forEach(column -> {
            String columnType = column.getPropertyType();
            for (String s : CodegenLauncher.typeMapping.keySet()) {
                if (columnType.contains(s)) {
                    stringBuilder.append(CodegenLauncher.typeMapping.get(s)).append("\n");
                }
            }
        });
        return stringBuilder.toString();
    }
}