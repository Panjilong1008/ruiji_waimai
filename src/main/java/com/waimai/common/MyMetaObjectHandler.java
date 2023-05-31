package com.waimai.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        Date date=new Date();
        metaObject.setValue("createTime", date);
        metaObject.setValue("updateTime",date);
        System.out.println(BaseContext.getCurrentId());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");

//        long id = Thread.currentThread().getId();
//        log.info("线程id为：{}",id);
        Date date=new Date();
        metaObject.setValue("updateTime",date);
        System.out.println(date.getTime());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());

    }
}
