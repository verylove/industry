package com.fable.industry.api.common.helper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.sql.Connection;

/**
 * @author jiangmingjun
 * @create 2018/3/27
 */
public class SessionFTemplate implements ApplicationListener {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    private static SqlSession sqlSession;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        sqlSession = sqlSessionFactory.openSession();
    }

    public static Connection getSessionConnection() {
        return sqlSession.getConnection();
    }
}
