package cn.wgn.website.service.impl;

import cn.wgn.website.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/23 22:08
 */
@Slf4j
@Service
public class BaseServiceImpl implements IBaseService {
    public Logger LOG = LoggerFactory.getLogger(getClass());
}
