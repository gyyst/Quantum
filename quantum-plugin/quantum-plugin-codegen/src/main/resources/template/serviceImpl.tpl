#set(tableComment = table.getComment())
#set(isCacheExample = serviceImplConfig.cacheExample)
#set(primaryKey = table.getPrimaryKey())
#set(entityClassName = table.buildEntityClassName())
package #(packageConfig.serviceImplPackage);

import lombok.extern.slf4j.Slf4j;

import com.lunarstra.quantum.service.base.Base#(table.buildServiceImplClassName());

import org.springframework.stereotype.Service;
#if(isCacheExample)
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
#end

/**
 * #(table.getComment()) 服务层实现。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
@Service
#if(isCacheExample)
@CacheConfig(cacheNames = "#(firstCharToLowerCase(entityClassName))")
#end
@Slf4j
public class #(table.buildServiceImplClassName()) extends Base#(table.buildServiceImplClassName()) {
    @Resource
    private #(entityClassName)Repository #(firstCharToLowerCase(entityClassName))Repository;

}