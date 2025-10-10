#set(tableComment = table.getComment())
#set(isCacheExample = serviceImplConfig.cacheExample)
#set(primaryKey = table.getPrimaryKey())
#set(entityClassName = table.buildEntityClassName())
#set(basePackage = packageConfig.getBasePackage())
package #(packageConfig.serviceImplPackage);

import #(basePackage).common.PageRequest;
import #(basePackage).model.converter.#(entityClassName)Converter;
import #(basePackage).model.request.Add#(entityClassName)Request;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import #(basePackage).model.request.Update#(entityClassName)Request;
import #(basePackage).model.response.#(entityClassName)Response;
import #(basePackage).repository.#(entityClassName)Repository;

import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.List;

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
#if(isCacheExample)
@CacheConfig(cacheNames = "#(firstCharToLowerCase(entityClassName))")
#end
public class Base#(table.buildServiceImplClassName()) {
    @Resource
    protected #(entityClassName)Repository #(firstCharToLowerCase(entityClassName))Repository;

    /**
     * 保存#(tableComment)
     *
     * @param add#(entityClassName)Request 新增#(tableComment)
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    public Boolean save(Add#(entityClassName)Request add#(entityClassName)Request) {
        return #(firstCharToLowerCase(entityClassName))Repository.save(#(entityClassName)Converter.addRequestConvert2Entity(add#(entityClassName)Request));
    }

    /**
     * 更新#(tableComment)
     *
     * @param update#(entityClassName)Request 更新#(tableComment)
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    public Boolean updateById(Update#(entityClassName)Request update#(entityClassName)Request) {
        return #(firstCharToLowerCase(entityClassName))Repository.updateById(#(entityClassName)Converter.updateRequestConvert2Entity(update#(entityClassName)Request));

    }

    /**
     * 查询所有#(tableComment)
     *
     * @return 列表
     */
    public List<#(entityClassName)Response> listResponse() {
        return #(firstCharToLowerCase(entityClassName))Repository.list().stream().map(#(entityClassName)Converter::entityConvert2Response).toList();
    }

    /**
     * 根据id查询#(tableComment)
     *
     * @param id 主键
     * @return #(tableComment)
     */
    public #(entityClassName)Response getResponseById(Serializable id) {
        return #(entityClassName)Converter.entityConvert2Response(#(firstCharToLowerCase(entityClassName))Repository.getById(id));
    }

    /**
     * 根据id删除#(tableComment)
     *
     * @return 列表
     */
    public Boolean removeById(Serializable id) {
        return #(firstCharToLowerCase(entityClassName))Repository.removeById(id);
    }

    /**
     * 分页查询#(tableComment)
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    public Page<#(entityClassName)Response> page(PageRequest pageRequest) {
        return #(firstCharToLowerCase(entityClassName))Repository.pageAs(
                new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()),
                new QueryWrapper(),
               #(entityClassName)Response.class);
    }

#if(isCacheExample)
    @Override
    @CacheEvict(allEntries = true)
    public boolean remove(QueryWrapper query) {
        return #(firstCharToLowerCase(entityClassName))Repository.remove(query);
    }

    @Override
    @CacheEvict(key = "#id")
    public boolean removeById(Serializable id) {
        return #(firstCharToLowerCase(entityClassName))Repository.removeById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return #(firstCharToLowerCase(entityClassName))Repository.removeByIds(ids);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean update(#(entityClassName) entity, QueryWrapper query) {
        return #(firstCharToLowerCase(entityClassName))Repository.update(entity, query);
    }

    @Override
    @CacheEvict(key = "#entity.#(primaryKey)")
    public boolean updateById(#(entityClassName) entity, boolean ignoreNulls) {
        return #(firstCharToLowerCase(entityClassName))Repository.updateById(entity, ignoreNulls);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean updateBatch(Collection<#(entityClassName)> entities, int batchSize) {
        return #(firstCharToLowerCase(entityClassName))Repository.updateBatch(entities, batchSize);
    }

    @Override
    @Cacheable(key = "#id")
    public #(entityClassName) getById(Serializable id) {
        return #(firstCharToLowerCase(entityClassName))Repository.getById(id);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public #(entityClassName) getOne(QueryWrapper query) {
        return #(firstCharToLowerCase(entityClassName))Repository.getOne(query);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> R getOneAs(QueryWrapper query, Class<R> asType) {
        return #(firstCharToLowerCase(entityClassName))Repository.getOneAs(query, asType);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public Object getObj(QueryWrapper query) {
        return #(firstCharToLowerCase(entityClassName))Repository.getObj(query);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> R getObjAs(QueryWrapper query, Class<R> asType) {
        return #(firstCharToLowerCase(entityClassName))Repository.getObjAs(query, asType);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public List<Object> objList(QueryWrapper query) {
        return #(firstCharToLowerCase(entityClassName))Repository.objList(query);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> List<R> objListAs(QueryWrapper query, Class<R> asType) {
        return #(firstCharToLowerCase(entityClassName))Repository.objListAs(query, asType);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public List<#(entityClassName)> list(QueryWrapper query) {
        return #(firstCharToLowerCase(entityClassName))Repository.list(query);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> List<R> listAs(QueryWrapper query, Class<R> asType) {
        return #(firstCharToLowerCase(entityClassName))Repository.listAs(query, asType);
    }

    /**
     * @deprecated 无法通过注解进行缓存操作。
     */
    @Override
    @Deprecated
    public List<#(entityClassName)> listByIds(Collection<? extends Serializable> ids) {
        return #(firstCharToLowerCase(entityClassName))Repository.listByIds(ids);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public long count(QueryWrapper query) {
        return #(firstCharToLowerCase(entityClassName))Repository.count(query);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #page.getPageSize() + ':' + #page.getPageNumber() + ':' + #query.toSQL()")
    public <R> Page<R> pageAs(Page<R> page, QueryWrapper query, Class<R> asType) {
        return #(firstCharToLowerCase(entityClassName))Repository.pageAs(page, query, asType);
    }

#end
}