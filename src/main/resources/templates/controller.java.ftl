package ${package.Controller};

import cn.wgn.framework.aspectj.annotation.Authorize;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}> {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName} ${table.serviceName?substring(1)?uncap_first};

    /**
     * 查
     *
     * @param id id
     * @return Entity
     */
    @Override
    @Authorize
    protected ${entity} getById(Long id) {
        return ${table.serviceName?substring(1)?uncap_first}.getById(id);
    }
<#if cfg.contentCud?exists&&cfg.contentCud>
    /**
     * 增
     *
     * @param entity entity
     * @return Boolean
     */
    @Override
    @Authorize
    protected Boolean post(${entity} entity) {
        return ${table.serviceName?substring(1)?uncap_first}.save(entity);
    }

    /**
     * 改
     *
     * @param entity entity
     * @return Boolean
     */
    @Override
    @Authorize
    protected Boolean put(${entity} entity) {
        Integer v = this.getById(entity.getId()).getVersion();
        entity.setVersion(v);
        return ${table.serviceName?substring(1)?uncap_first}.updateById(entity);
    }

    /**
     * 删
     *
     * @param id id
     * @return Boolean
     */
    @Override
    @Authorize
    protected Boolean delete(Long id) {
        return ${table.serviceName?substring(1)?uncap_first}.removeById(id);
    }
</#if>

    /**
     * 数量
     *
     * @return Integer
     */
    @Override
    @Authorize
    protected Integer count() {
        return ${table.serviceName?substring(1)?uncap_first}.count();
    }
}
</#if>
