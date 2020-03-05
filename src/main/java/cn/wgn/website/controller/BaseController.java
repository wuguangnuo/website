package cn.wgn.website.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Controller
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/16 10:53
 */
@Slf4j
public class BaseController extends ApiController {
    public Logger LOG = LoggerFactory.getLogger(getClass());
}
