#set(tableComment = table.getComment())
#set(primaryKey = table.getPrimaryKey())
#set(entityClassName = table.buildEntityClassName())
#set(basePackage = packageConfig.getBasePackage())
package #(packageConfig.entityPackage);

import #(basePackage).common.PageRequest;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import #(packageConfig.entityPackage).#(table.buildEntityClassName());
import #(packageConfig.mapperPackage).#(table.buildMapperClassName());
import org.springframework.stereotype.Repository;


/**
 * #(table.getComment()) 数据访问层。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
@Slf4j
@Repository
public class #(entityClassName)Repository extends #(serviceImplConfig.buildSuperClassName())<#(table.buildMapperClassName()), #(table.buildEntityClassName())>  {

}