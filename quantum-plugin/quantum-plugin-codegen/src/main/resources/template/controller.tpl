#set(tableComment = table.getComment())
#set(entityClassName = table.buildEntityClassName())
#set(entityVarName = firstCharToLowerCase(entityClassName))
#set(serviceVarName = firstCharToLowerCase(table.buildServiceClassName()))
#set(basePackage = packageConfig.getBasePackage())
package #(packageConfig.controllerPackage);

import #(basePackage).common.BaseResponse;
import #(basePackage).common.PageRequest;
import #(basePackage).annotation.RedisLimit;
import #(basePackage).constant.LimitType;
import com.mybatisflex.core.paginate.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import #(packageConfig.entityPackage).#(entityClassName);
import #(packageConfig.basePackage).model.request.Add#(entityClassName)Request;
import #(packageConfig.basePackage).model.request.Update#(entityClassName)Request;
import #(packageConfig.basePackage).model.response.#(entityClassName)Response;
import #(packageConfig.servicePackage).#(table.buildServiceClassName());
import lombok.extern.slf4j.Slf4j;
#if(controllerConfig.restStyle)
import org.springframework.web.bind.annotation.RestController;
#else
import org.springframework.stereotype.Controller;
#end
#if(controllerConfig.superClass != null)
import #(controllerConfig.buildSuperClassImport());
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
#end
import java.io.Serializable;
import java.util.List;

/**
 * #(tableComment) 控制层。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
@Slf4j
#if(controllerConfig.restStyle)
@RestController
#else
@Controller
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
@Api("#(tableComment)接口")
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
@Tag(name = "#(tableComment)接口")
#end
@RequestMapping("/#(firstCharToLowerCase(entityClassName))")
@RedisLimit(redisKeyPrefix = "#(table.buildControllerClassName())", limitType = LimitType.METHOD)
public class #(table.buildControllerClassName()) #if(controllerConfig.superClass)extends #(controllerConfig.buildSuperClassName()) #end {

    @Autowired
    private #(table.buildServiceClassName()) #(serviceVarName);

    /**
     * 添加#(tableComment)。
     *
     * @param add#(entityClassName)Request #(tableComment)添加请求类
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    #if(withSwagger && swaggerVersion.getName() == "FOX")
    @ApiOperation("保存#(tableComment)")
    #end
    #if(withSwagger && swaggerVersion.getName() == "DOC")
    @Operation(summary = "保存#(tableComment)", description="保存#(tableComment)")
    #end
    public BaseResponse<Boolean> save(@RequestBody @Validated #if(withSwagger && swaggerVersion.getName() == "FOX")@ApiParam("#(tableComment)") #end #if(withSwagger && swaggerVersion.getName() == "DOC")@Parameter(description="#(tableComment)")#end Add#(entityClassName)Request add#(entityClassName)Request) {
        return BaseResponse.success(#(serviceVarName).save(add#(entityClassName)Request));
    }

    /**
     * 根据主键删除#(tableComment)。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    #if(withSwagger && swaggerVersion.getName() == "FOX")
    @ApiOperation("根据主键#(tableComment)")
    #end
    #if(withSwagger && swaggerVersion.getName() == "DOC")
    @Operation(summary = "根据主键删除#(tableComment)",description="根据主键删除#(tableComment)")
    #end
    public BaseResponse<Boolean> remove(@PathVariable #if(withSwagger && swaggerVersion.getName() == "FOX")@ApiParam("#(tableComment)主键") #end #if(withSwagger && swaggerVersion.getName() == "DOC")@Parameter(description="#(tableComment)主键")#end Serializable id) {
        return BaseResponse.success(#(serviceVarName).removeById(id));
    }

    /**
     * 根据主键更新#(tableComment)。
     *
     * @param update#(entityClassName)Request #(tableComment)修改请求类
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    #if(withSwagger && swaggerVersion.getName() == "FOX")
    @ApiOperation("根据主键更新#(tableComment)")
    #end
    #if(withSwagger && swaggerVersion.getName() == "DOC")
    @Operation(summary = "根据主键更新#(tableComment)",description="根据主键更新#(tableComment)")
    #end
    public BaseResponse<Boolean> update(@RequestBody @Validated #if(withSwagger && swaggerVersion.getName() == "FOX")@ApiParam("#(tableComment)主键") #end #if(withSwagger && swaggerVersion.getName() == "DOC")@Parameter(description="#(tableComment)主键")#end Update#(entityClassName)Request update#(entityClassName)Request) {
        return BaseResponse.success(#(serviceVarName).updateById(update#(entityClassName)Request));
    }

    /**
     * 查询所有#(tableComment)。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    #if(withSwagger && swaggerVersion.getName() == "FOX")
    @ApiOperation("查询所有#(tableComment)")
    #end
    #if(withSwagger && swaggerVersion.getName() == "DOC")
    @Operation(summary = "查询所有#(tableComment)",description="查询所有#(tableComment)")
    #end
    public BaseResponse<List<#(entityClassName)Response>> list() {
        return BaseResponse.success(#(serviceVarName).listResponse());
    }

    /**
     * 根据#(tableComment)主键获取详细信息。
     *
     * @param id #(tableComment)主键
     * @return #(tableComment)详情
     */
    @GetMapping("getInfo/{id}")
    #if(withSwagger && swaggerVersion.getName() == "FOX")
    @ApiOperation("根据主键获取#(tableComment)")
    #end
    #if(withSwagger && swaggerVersion.getName() == "DOC")
    @Operation(summary = "根据主键获取#(tableComment)",description="根据主键获取#(tableComment)")
    #end
    public BaseResponse<#(entityClassName)Response> getInfo(@PathVariable #if(withSwagger && swaggerVersion.getName() == "FOX")@ApiParam("#(tableComment)主键") #if(withSwagger && swaggerVersion.getName() == "DOC")@Parameter(description="#(tableComment)主键")#end#end Serializable id) {
        return BaseResponse.success(#(serviceVarName).getResponseById(id));
    }

    /**
     * 分页查询#(tableComment)。
     *
     * @param pageRequest 分页请求类
     * @return 分页对象
     */
    @PostMapping("page")
    #if(withSwagger && swaggerVersion.getName() == "FOX")
    @ApiOperation("分页查询#(tableComment)")
    #end
    #if(withSwagger && swaggerVersion.getName() == "DOC")
    @Operation(summary = "分页查询#(tableComment)",description="分页查询#(tableComment)")
    #end
    public BaseResponse<Page<#(entityClassName)Response>> page(@RequestBody @Validated #if(withSwagger && swaggerVersion.getName() == "FOX")@ApiParam("分页信息") #end #if(withSwagger && swaggerVersion.getName() == "DOC")@Parameter(description="分页信息")#end PageRequest pageRequest) {
        return BaseResponse.success(#(serviceVarName).page(pageRequest));
    }

}