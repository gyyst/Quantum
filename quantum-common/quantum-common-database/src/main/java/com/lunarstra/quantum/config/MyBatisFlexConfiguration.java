package com.lunarstra.quantum.config;

import com.lunarstra.quantum.key.generate.MyKeyGenerators;
import com.lunarstra.quantum.key.generate.UUIDKeyGenerator;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import com.mybatisflex.core.query.QueryColumnBehavior;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {

    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(
            auditMessage -> log.info("{},{}ms", auditMessage.getFullSql(), auditMessage.getElapsedTime()));
    }

    @Override
    public void customize(FlexGlobalConfig flexGlobalConfig) {
        QueryColumnBehavior.setIgnoreFunction(QueryColumnBehavior.IGNORE_BLANK);
        QueryColumnBehavior.setSmartConvertInToEquals(true);
        KeyGeneratorFactory.register(MyKeyGenerators.UUIDV7, new UUIDKeyGenerator());
    }
}
