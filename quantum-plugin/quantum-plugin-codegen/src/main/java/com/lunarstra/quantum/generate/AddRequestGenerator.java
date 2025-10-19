package com.lunarstra.quantum.generate;

import com.lunarstra.quantum.CodegenLauncher;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRequestGenerator implements IGenerator {

    @Override
    public String getTemplatePath() {
        return "template/addRequest.tpl";
    }

    @Override
    public void setTemplatePath(String s) {

    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {

        // 处理枚举表达式
        //        List<Map<String, Object>> enumDefinitions = new ArrayList<>();
        //        table.getColumns().forEach(column -> {
        //            String comment = column.getComment();
        //            if (comment != null && comment.startsWith("enum:")) {
        //                Map<String, Object> enumDef = parseEnumExpression(comment, column.getProperty());
        //                if (enumDef != null) {
        //                    enumDefinitions.add(enumDef);
        //                    // 修改字段类型为枚举类型
        //                    column.setPropertyType(table.buildEntityClassName() + "." + enumDef.get("enumName"));
        //                    // 修改注释，移除枚举表达式部分
        //                    column.setComment(comment.replaceAll("enum:.*?\\)", "").trim());
        //                }
        //            }
        //        });
        List<Map<String, Object>> enumDefinitions = EntityGenerator.enumDefinitionsContext.get();
        enumDefinitions.forEach(enumDefinition -> {
            String enumName = enumDefinition.get("enumName").toString();
            enumDefinition.put("enumName",
                enumName.contains(".") ? enumName : table.buildEntityClassName() + "." + enumName);
        });
        // 总是生成请求类，不依赖于entityGenerateEnable
        PackageConfig packageConfig = globalConfig.getPackageConfig();
        EntityConfig entityConfig = globalConfig.getEntityConfig();

        String entityPackagePath = (packageConfig.getBasePackage() + ".model.request").replace(".", "/");
        File entityJavaFile = new File(packageConfig.getSourceDir(),
            entityPackagePath + "/" + "Add" + table.buildEntityClassName() + "Request" + ".java");

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
        params.put("enumDefinitions", enumDefinitions);
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

    /**
     * 解析枚举表达式
     * 格式：enum:EnumName(VALUE1:CODE1:DESC1, VALUE2:CODE2:DESC2, ...)
     *
     * @param comment   字段注释
     * @param fieldName 字段名
     * @return 枚举定义的Map，包含enumName和enumValues
     */
    private Map<String, Object> parseEnumExpression(String comment, String fieldName) {
        try {
            // 匹配枚举表达式
            Pattern pattern = Pattern.compile("enum:(\\w+)\\((.*?)\\)");
            Matcher matcher = pattern.matcher(comment);

            if (!matcher.find()) {
                return null;
            }

            String enumName = matcher.group(1);
            String enumValuesStr = matcher.group(2);

            // 解析枚举值
            List<Map<String, String>> enumValues = new ArrayList<>();
            String[] valuePairs = enumValuesStr.split(",");

            for (String valuePair : valuePairs) {
                String[] parts = valuePair.trim().split(":");
                if (parts.length >= 3) {
                    Map<String, String> enumValue = new HashMap<>();
                    enumValue.put("name", parts[0].trim());
                    enumValue.put("code", parts[1].trim());
                    enumValue.put("description", parts[2].trim());
                    enumValues.add(enumValue);
                }
            }

            Map<String, Object> enumDefinition = new HashMap<>();
            enumDefinition.put("enumName", enumName);
            enumDefinition.put("enumValues", enumValues);
            enumDefinition.put("fieldName", fieldName);

            return enumDefinition;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}