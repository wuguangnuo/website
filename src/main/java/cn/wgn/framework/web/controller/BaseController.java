package cn.wgn.framework.web.controller;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.PageUtil;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.PageDomain;
import cn.wgn.framework.web.entity.BaseEntity;
import cn.wgn.framework.web.entity.JobEntity;
import cn.wgn.framework.web.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Base Controller
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/16 10:53
 */
public abstract class BaseController<T extends BaseEntity> {
    public Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * 设置请求分页数据
     * （已从 HttpServletRequest 中获取分页信息）
     */
    protected void startPage() {
        PageDomain pageDomain = PageUtil.getPage();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = pageDomain.getOrderBy();
        PageHelper.startPage(pageNum, pageSize, orderBy);
    }

    /**
     * 返回 分页对象
     *
     * @param data
     * @return
     */
    protected ApiRes<PageInfo<T>> pageData(List<T> data) {
        if (data == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(new PageInfo<>(data));
        }
    }

    /**
     * 返回 实体对象
     *
     * @param data
     * @return
     */
    protected ApiRes<T> resData(T data) {
        if (data == null) {
            return ApiRes.fail("查询为空");
        } else {
            return ApiRes.suc(data);
        }
    }

    @GetMapping("{id}")
    @ApiOperation("*根据主键查询数据")
    public ApiRes<T> get(HttpServletRequest request, @PathVariable("id") Long id) {
        // 代理AOP，使被代理的对象可被注解修饰
        Object obj = ((BaseController) AopContext.currentProxy()).getById(id);
        if (obj instanceof ApiRes) {
            return (ApiRes) obj;
        }

        T res = (T) obj;
        if (res == null) {
            return ApiRes.fail("查询为空");
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping("post")
    @ApiOperation("*新增/更新数据")
    public ApiRes<String> post(HttpServletRequest request, @RequestBody T t) {
        if (t.getId() == null || t.getId() <= 0) {
            // 代理AOP，使被代理的对象可被注解修饰
            Object obj = ((BaseController) AopContext.currentProxy()).post(t);
            if (obj instanceof ApiRes) {
                return (ApiRes) obj;
            }
            Boolean flag = (Boolean) obj;
            if (flag == null || !flag) {
                return ApiRes.fail("插入失败");
            } else {
                return ApiRes.suc(String.valueOf(t.getId()));
            }
        } else {
            return this.put(request, t);
        }
    }

    @PutMapping("put")
    @ApiOperation("*修改数据")
    public ApiRes<String> put(HttpServletRequest request, @RequestBody T t) {
        if (t.getId() == null || t.getId() <= 0) {
            return ApiRes.err("修改失败，缺少主键[id]");
        }
        // 代理AOP，使被代理的对象可被注解修饰
        Object obj = ((BaseController) AopContext.currentProxy()).put(t);
        if (obj instanceof ApiRes) {
            return (ApiRes) obj;
        }
        Boolean flag = (Boolean) obj;
        if (flag == null || !flag) {
            return ApiRes.fail("修改失败");
        } else {
            return ApiRes.suc("修改成功");
        }
    }

    @DeleteMapping("delete")
    @ApiOperation("*删除数据")
    public ApiRes<String> delete(HttpServletRequest request, @RequestParam("id") Long id) {
        // 代理AOP，使被代理的对象可被注解修饰
        Object obj = ((BaseController) AopContext.currentProxy()).delete(id);
        if (obj instanceof ApiRes) {
            return (ApiRes) obj;
        }

        Boolean flag = (Boolean) obj;
        if (flag == null || !flag) {
            return ApiRes.fail("删除失败");
        } else {
            return ApiRes.suc("删除成功");
        }
    }

    @GetMapping("count")
    @ApiOperation("*查询数据条数")
    public ApiRes<Integer> count(HttpServletRequest request) {
        // 代理AOP，使被代理的对象可被注解修饰
        Object obj = ((BaseController) AopContext.currentProxy()).count();
        if (obj instanceof ApiRes) {
            return (ApiRes) obj;
        }

        Integer res = (Integer) obj;
        if (res == null) {
            return ApiRes.err("查询失败");
        } else {
            return ApiRes.suc(res);
        }
    }

    /**
     * 查
     *
     * @param id id
     * @return Entity
     */
    protected Object getById(Long id) {
        LOG.error("必须重写'getById'方法");
        throw new CommonException("必须重写'getById'方法");
    }

    /**
     * 增
     *
     * @param t Entity
     * @return Boolean
     */
    protected Object post(T t) {
        LOG.error("必须重写'post'方法");
        throw new CommonException("必须重写'post'方法");
    }

    /**
     * 改
     *
     * @param t Entity
     * @return Boolean
     */
    protected Object put(T t) {
        LOG.error("必须重写'put'方法");
        throw new CommonException("必须重写'put'方法");
    }

    /**
     * 删
     *
     * @param id id
     * @return Boolean
     */
    protected Object delete(Long id) {
        LOG.error("必须重写'delete'方法");
        throw new CommonException("必须重写'delete'方法");
    }

    /**
     * 数量
     *
     * @return
     */
    protected Object count() {
        LOG.error("必须重写'count'方法");
        throw new CommonException("必须重写'count'方法");
    }
}
