package com.isinonet.ismartnet.utils;


import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * jdbc工具类, 提供连接池
 * Created by Administrator on 2018-06-06.
 * 2018-06-06
 */
public class JDBCHelper {
    public static SqlSessionFactory sessionFactory;

    //创建能执行映射文件中sql的sqlSession
    public static SqlSession getSession(){
        if(sessionFactory == null) {
            try {
                //使用MyBatis提供的Resources类加载mybatis的配置文件
                Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");
                //构建sqlSession的工厂
                sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory.openSession();
    }

}
