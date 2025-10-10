#set(withLombok = entityConfig.isWithLombok())
#set(withSwagger = entityConfig.isWithSwagger())
#set(swaggerVersion = entityConfig.getSwaggerVersion())
#set(withActiveRecord = entityConfig.isWithActiveRecord())
#set(entityClassName = table.buildEntityClassName())
#set(basePackage = packageConfig.getBasePackage())
package #(packageConfig.entityPackage);

#for(importClass : table.buildImports(isBase))
import #(importClass);
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
#end
#if(withLombok)
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
#end
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
#if(hasText((buildImports)))
#(buildImports)
#end
/**
 * #(table.getComment()) 修改请求类。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
#if(withLombok)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
@Api("#(tableComment)接口")
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
@Tag(name = "#(tableComment)接口")
#end
public class Update#(entityClassName)Request#if(withActiveRecord) extends Model<#(entityClassName)>#else#(table.buildExtends(isBase))#(table.buildImplements())#end  {

#for(column : table.columns)
   #if(column.getName()!="create_time" && column.getName()!= "update_time"&& column.getName()!="is_delete")
    #set(comment = javadocConfig.formatColumnComment(column.comment))
       #if(hasText(comment))
       /**
        * #(comment)
        */
       #end
       #if(withSwagger && swaggerVersion.getName() == "FOX")
       @ApiModelProperty("#(column.comment)")
       #end
       #if(withSwagger && swaggerVersion.getName() == "DOC")
       @Schema(description = "#(column.comment)")
       #end
       #if(column.isPrimaryKey()==true)
       #if(column.getPropertySimpleType()=="String")
       @NotBlank(message = "#(column.property)不能为空")
       #else
       @NotNull(message = "#(column.property)不能为空")
       #end
       #end
       private #(column.propertySimpleType) #(column.property)#if(hasText(column.propertyDefaultValue)) = #(column.propertyDefaultValue)#end;
   #end

#end
}