#set(withLombok = entityConfig.isWithLombok())
#set(withSwagger = entityConfig.isWithSwagger())
#set(swaggerVersion = entityConfig.getSwaggerVersion())
#set(withActiveRecord = entityConfig.isWithActiveRecord())
#set(entityClassName = table.buildEntityClassName())
#set(jdkVersion = entityConfig.getJdkVersion())
#set(basePackage = packageConfig.getBasePackage())
package #(packageConfig.entityPackage);

#if(jdkVersion >= 14)
import java.io.Serial;
#end
import com.mybatisflex.core.handler.JacksonTypeHandler;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
#for(importClass : table.buildImports(isBase))
import #(importClass);
#end
#if(withActiveRecord)
import com.mybatisflex.core.activerecord.Model;
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
import io.swagger.v3.oas.annotations.media.Schema;
#end
#if(withLombok)
#if(withActiveRecord)
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
#else
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
#end
#end
#if(hasText((buildImports)))
#(buildImports)
#end
/**
 * #(table.getComment()) 实体类。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
#if(withLombok)
#if(withActiveRecord)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
#else
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
#end
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
@ApiModel("#(table.getComment())")
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
@Schema(description = "#(table.getComment())")
#end
#(table.buildTableAnnotation())
public class #(entityClassName)#if(withActiveRecord) extends Model<#(entityClassName)>#else#(table.buildExtends(isBase))#(table.buildImplements())#end  {

    #if(jdkVersion >= 14)
    @Serial
    #end
    private static final long serialVersionUID = -1L;

#for(column : table.columns)
    #set(comment = javadocConfig.formatColumnComment(column.comment))
    #if(hasText(comment))
    /**
     * #(comment)
     */
    #end
    #set(annotations = column.buildAnnotations())
    #if(hasText(annotations))
    #(annotations)
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
#if(!withLombok)
    #if(withActiveRecord)
    public static #(entityClassName) create() {
        return new #(entityClassName)();
    }

    #end
    #for(column: table.columns)
    public #(column.propertySimpleType) #(column.getterMethod())() {
        return #(column.property);
    }

    #if(withActiveRecord)
    public #(entityClassName) #(column.setterMethod())(#(column.propertySimpleType) #(column.property)) {
        this.#(column.property) = #(column.property);
        return this;
    }
    #else
    public void #(column.setterMethod())(#(column.propertySimpleType) #(column.property)) {
        this.#(column.property) = #(column.property);
    }
    #end

    #end
#end
#if(enumDefinitions && enumDefinitions.size() > 0)
#for(enumDef : enumDefinitions)
#set(enumName = enumDef.enumName)
#set(enumValues = enumDef.enumValues)
#set(fieldName = enumDef.fieldName)

    @Getter
    @AllArgsConstructor
    public enum #(enumName) {
#for(enumValue : enumValues)
        /**
         * #(enumValue.description)
         */
        #(enumValue.name)(#(enumValue.code), "#(enumValue.description)")#(for.index < enumValues.size() - 1 ? "," : ";")

#end

        @EnumValue
        private final int code;

        private final String description;
    }
#end
#end}
