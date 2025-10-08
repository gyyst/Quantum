#set(withLombok = entityConfig.isWithLombok())
#set(withSwagger = entityConfig.isWithSwagger())
#set(swaggerVersion = entityConfig.getSwaggerVersion())
#set(withActiveRecord = entityConfig.isWithActiveRecord())
#set(entityClassName = table.buildEntityClassName())
package #(packageConfig.entityPackage);

import com.mybatisflex.core.handler.JacksonTypeHandler;
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

/**
 * #(table.getComment()) 响应类。
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
public class #(entityClassName)Response#if(withActiveRecord) extends Model<#(entityClassName)>#else#(table.buildExtends(isBase))#(table.buildImplements())#end  {

#for(column : table.columns)
   #if(column.getName()!="is_delete")
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
       #if(comment.startsWith("json"))
       @Column(typeHandler = JacksonTypeHandler.class)
       #end
       private #(column.propertySimpleType) #(column.property)#if(hasText(column.propertyDefaultValue)) = #(column.propertyDefaultValue)#end;
   #end

#end
}