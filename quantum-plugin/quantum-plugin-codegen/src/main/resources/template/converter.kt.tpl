#set(withLombok = entityConfig.isWithLombok())
#set(withSwagger = entityConfig.isWithSwagger())
#set(swaggerVersion = entityConfig.getSwaggerVersion())
#set(withActiveRecord = entityConfig.isWithActiveRecord())
#set(entityClassName = table.buildEntityClassName())
#set(basePackage = packageConfig.getBasePackage())
package #(packageConfig.entityPackage)
#for(importClass : table.buildImports(isBase))
import #(importClass)
#end
import #(packageConfig.entityPackage).#(entityClassName)
import #(packageConfig.basePackage).model.request.Add#(entityClassName)Request
import #(packageConfig.basePackage).model.request.Update#(entityClassName)Request
import #(packageConfig.basePackage).model.response.#(entityClassName)Response

/**
 * #(table.getComment()) 转换类。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
object #(entityClassName)Converter {
    /**
     * entity -> response
     *
     * @return
     */
    @JvmStatic
    fun entityConvert2Response(#(firstCharToLowerCase(entityClassName)): #(entityClassName)?): #(entityClassName)Response? {
        #(firstCharToLowerCase(entityClassName))?.let { entity ->
            return #(entityClassName)Response().apply {
                #for(column : table.columns)
                #if(column.getName()!="is_delete")
                #(column.getProperty()) = entity.#(column.getProperty())
                #end
                #end
            }
        }
        return null
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun addRequestConvert2Entity(add#(entityClassName)Request: Add#(entityClassName)Request?): #(entityClassName)? {
        add#(entityClassName)Request?.let { request ->
            return #(entityClassName)().apply {
                #for(column : table.columns)
                #if(column.getName()!="create_time" && column.getName()!= "update_time"&& column.getName()!="id"&& column.getName()!="is_delete")
                #(column.getProperty()) = request.#(column.getProperty())
                #end
                #end
            }
        }
        return null
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun updateRequestConvert2Entity(update#(entityClassName)Request: Update#(entityClassName)Request?): #(entityClassName)? {
        update#(entityClassName)Request?.let { request ->
            return #(entityClassName)().apply {
                #for(column : table.columns)
                #if(column.getName()!="create_time" && column.getName()!= "update_time"&& column.getName()!="is_delete")
                #(column.getProperty()) = request.#(column.getProperty())
                #end
                #end
            }
        }
        return null
    }
}