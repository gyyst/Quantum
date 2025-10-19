package com.lunarstra.quantum;

import com.lunarstra.quantum.generate.AddRequestGenerator;
import com.lunarstra.quantum.generate.BaseServiceImplGenerator;
import com.lunarstra.quantum.generate.ConverterGenerator;
import com.lunarstra.quantum.generate.EntityGenerator;
import com.lunarstra.quantum.generate.RepositoryGenerator;
import com.lunarstra.quantum.generate.ResponseGenerator;
import com.lunarstra.quantum.generate.UpdateRequestGenerator;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodegenLauncher {

    public static final Map<String, String> typeMapping = new HashMap<>() {
        {
            put("List", "import java.util.List;");
            put("Set", "import java.util.Set;");
            put("Map", "import java.util.Map;");
        }
    };

    private static final Map<String, HikariDataSource> dataSourceHashMap = new HashMap<>();

    public static void main(String[] args) {
        // 配置数据源
        HikariDataSource dataSource = dataSourceHashMap.get("test");
        // 创建配置内容，两种风格都可以。
        startGenCode(dataSource);
    }

    /**
     * 开始生成代码
     *
     * @param dataSource
     */
    public static void startGenCode(DataSource dataSource, List<String> tableList) {
        // 创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        // GlobalConfig globalConfig = createGlobalConfigUseStyle2();
        setGenCodeTable(tableList, globalConfig);
        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        // 生成代码
        generator.generate();
    }

    /**
     * 开始生成代码
     *
     * @param dataSource
     */
    public static void startGenCode(DataSource dataSource, String... tableList) {
        // 创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        // GlobalConfig globalConfig = createGlobalConfigUseStyle2();
        setGenCodeTable(Arrays.stream(tableList).toList(), globalConfig);
        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        // 生成代码
        generator.generate();
    }

    /**
     * 设置生成的表
     *
     * @param tableList
     * @param globalConfig
     */
    private static void setGenCodeTable(List<String> tableList, GlobalConfig globalConfig) {
        globalConfig.setGenerateTable(tableList.toArray(String[]::new));
    }

    /**
     * 设置类型映射
     */
    private static void registerType() {
        JdbcTypeMapping.setTypeMapper((_, table, column) -> {
            if (column.getName().equals("is_delete")) {
                return Boolean.class.getName();
            }
            if (column.getComment().startsWith("type:")) {
                String className = column.getComment().split("\\s+")[0].split(":")[1];
                column.setComment(column.getComment().split("\\s+")[1]);
                return className;
            }
            return null;
        });
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        registerType();
        GeneratorFactory.registerGenerator("addRequest", new AddRequestGenerator());
        GeneratorFactory.registerGenerator("updateRequest", new UpdateRequestGenerator());
        GeneratorFactory.registerGenerator("response", new ResponseGenerator());
        GeneratorFactory.registerGenerator("converter", new ConverterGenerator());
        GeneratorFactory.registerGenerator("entity", new EntityGenerator());
        GeneratorFactory.registerGenerator("repository", new RepositoryGenerator());
        GeneratorFactory.registerGenerator("baseServiceImpl", new BaseServiceImplGenerator());
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        // 设置根包
        globalConfig.setBasePackage("com.lunarstra.quantum");
        globalConfig.setEntityJdkVersion(25);
        // 设置表前缀和只生成哪些表
        //        globalConfig.setGenerateSchema("schema");
        //        globalConfig.setTablePrefix("tb_");

        // 设置全局逻辑删除
        globalConfig.getStrategyConfig().setLogicDeleteColumn("is_delete");

        // 设置生成 entity 并启用 Lombok
        globalConfig.setEntityTemplatePath("template/entity.tpl");
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        globalConfig.setEntityPackage("com.lunarstra.quantum.model.entity");

        globalConfig.getEntityConfig().setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);
        globalConfig.setEntityOverwriteEnable(true);

        // 设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);
        globalConfig.setMapperAnnotation(true);
        // globalConfig.setMapperTemplatePath("template/mapper.tpl");
        globalConfig.setMapperOverwriteEnable(true);

        // 设置生成controller
        globalConfig.setControllerTemplatePath("template/controller.tpl");
        globalConfig.setControllerGenerateEnable(true);
        globalConfig.setControllerOverwriteEnable(true);
        // 设置生成service
        globalConfig.setServiceImplGenerateEnable(true);
        globalConfig.setServiceImplTemplatePath("template/serviceImpl.tpl");
        globalConfig.setServiceImplPackage("com.lunarstra.quantum.service");
        globalConfig.setServiceImplClassSuffix("Service");
        globalConfig.setServiceImplOverwriteEnable(true);
        
        // 可以单独配置某个列
        //        ColumnConfig columnConfig = new ColumnConfig();
        //        columnConfig.setColumnName("is_delete");
        //        columnConfig.setLogicDelete(true);
        //        globalConfig.setColumnConfig("account", columnConfig);

        return globalConfig;
    }

    public static GlobalConfig createGlobalConfigUseStyle2() {
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        // 设置根包
        globalConfig.getPackageConfig().setBasePackage("com.test");

        // 设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
            .setGenerateSchema("schema")
            .setTablePrefix("tb_")
            .setGenerateTable("account", "account_session");

        // 设置生成 entity 并启用 Lombok
        globalConfig.enableEntity().setWithLombok(true);

        // 设置生成 mapper
        globalConfig.enableMapper();

        // 可以单独配置某个列
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.getStrategyConfig().setColumnConfig("account", columnConfig);

        return globalConfig;
    }

}
