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
import #(packageConfig.entityPackage).#(entityClassName);
import #(packageConfig.basePackage).model.request.Add#(entityClassName)Request;
import #(packageConfig.basePackage).model.request.Update#(entityClassName)Request;
import #(packageConfig.basePackage).model.response.#(entityClassName)Response;

/**
 * #(table.getComment()) 转换类。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */

public class #(entityClassName)Converter{

    /**
     * entity -> response
     *
     * @return
     */
    public static #(entityClassName)Response entityConvert2Response(#(entityClassName) #(firstCharToLowerCase(entityClassName))) {
        if (#(firstCharToLowerCase(entityClassName)) == null) {
            return null;
        }
        #(entityClassName)Response #(firstCharToLowerCase(entityClassName))Response = new #(entityClassName)Response();

        #for(column : table.columns)
        #if(column.getName()!="is_delete")
        #(firstCharToLowerCase(entityClassName))Response.#(column.setterMethod())(#(firstCharToLowerCase(entityClassName)).#(column.getterMethod())());
        #end
        #end

        return #(firstCharToLowerCase(entityClassName))Response;
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    public static #(entityClassName) addRequestConvert2Entity(Add#(entityClassName)Request add#(entityClassName)Request) {
        if (add#(entityClassName)Request == null) {
            return null;
        }
        #(entityClassName)  #(firstCharToLowerCase(entityClassName)) = new #(entityClassName)();

        #for(column : table.columns)
        #if(column.getName()!="create_time" && column.getName()!= "update_time"&& column.getName()!="id"&& column.getName()!="is_delete")
        #(firstCharToLowerCase(entityClassName)).#(column.setterMethod())(add#(entityClassName)Request.#(column.getterMethod())());
        #end
        #end

        return #(firstCharToLowerCase(entityClassName));
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    public static #(entityClassName) updateRequestConvert2Entity(Update#(entityClassName)Request update#(entityClassName)Request) {
        if (update#(entityClassName)Request == null) {
            return null;
        }
        #(entityClassName)  #(firstCharToLowerCase(entityClassName)) = new #(entityClassName)();

        #for(column : table.columns)
        #if(column.getName()!="create_time" && column.getName()!= "update_time"&& column.getName()!="is_delete")
        #(firstCharToLowerCase(entityClassName)).#(column.setterMethod())(update#(entityClassName)Request.#(column.getterMethod())());
        #end
        #end

        return #(firstCharToLowerCase(entityClassName));
    }}