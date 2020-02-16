package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import cn.wgn.website.service.IBaseService;

/**
 * <p>
 * ${entity} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}>,IBaseService {

}
</#if>