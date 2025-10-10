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

import java.util.HashMap;
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

    static {
        // user
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightuser");
        hikariDataSource.setUsername("insightuser");
        hikariDataSource.setPassword("a55fbe9d008c3af4");
        dataSourceHashMap.put("user", hikariDataSource);

        // storage
        HikariDataSource hikariDataSource1 = new HikariDataSource();
        hikariDataSource1.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightstorage");
        hikariDataSource1.setUsername("insightstorage");
        hikariDataSource1.setPassword("08ee50be21598787");
        dataSourceHashMap.put("storage", hikariDataSource1);
        // bi
        HikariDataSource hikariDataSource2 = new HikariDataSource();
        hikariDataSource2.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightbi");
        hikariDataSource2.setUsername("insightbi");
        hikariDataSource2.setPassword("425873ceea37d316");
        dataSourceHashMap.put("bi", hikariDataSource2);
        // api
        HikariDataSource hikariDataSource3 = new HikariDataSource();
        hikariDataSource3.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightapi");
        hikariDataSource3.setUsername("insightapi");
        hikariDataSource3.setPassword("4d9c54e2876c24a7");
        dataSourceHashMap.put("api", hikariDataSource3);
        // oj
        HikariDataSource hikariDataSource4 = new HikariDataSource();
        hikariDataSource4.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightoj");
        hikariDataSource4.setUsername("insightoj");
        hikariDataSource4.setPassword("f380649a8f094f4b");
        dataSourceHashMap.put("oj", hikariDataSource4);
        // basic
        HikariDataSource hikariDataSource5 = new HikariDataSource();
        hikariDataSource5.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightbasic");
        hikariDataSource5.setUsername("insightbasic");
        hikariDataSource5.setPassword("KDi6zznHoOymY48C");
        dataSourceHashMap.put("basic", hikariDataSource5);

        HikariDataSource hikariDataSource6 = new HikariDataSource();
        hikariDataSource6.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/kkbotss");
        hikariDataSource6.setUsername("kkbotss");
        hikariDataSource6.setPassword("oTXf877cy0bBPvKx");
        dataSourceHashMap.put("kkbotss", hikariDataSource6);

        HikariDataSource hikariDataSource7 = new HikariDataSource();
        hikariDataSource7.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightsensor");
        hikariDataSource7.setUsername("insightsensor");
        hikariDataSource7.setPassword("FbxZCSKTQ1yruLFB");
        dataSourceHashMap.put("sensor", hikariDataSource7);

        HikariDataSource test = new HikariDataSource();
        test.setJdbcUrl("jdbc:mysql://mysql.sqlpub.com:3306/insightsensor");
        test.setUsername("insightsensor");
        test.setPassword("FbxZCSKTQ1yruLFB");
        dataSourceHashMap.put("test", test);
    }

    public static void main(String[] args) {
        // 配置数据源
        HikariDataSource dataSource = dataSourceHashMap.get("test");
        // 创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        // GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        // 生成代码
        generator.generate();
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
        globalConfig.setGenerateTable("test_info");

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
