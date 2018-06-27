package com.isinonet.tests;

import com.isinonet.ismartnet.beans.UserBean;
import com.isinonet.ismartnet.beans.Website;
import com.isinonet.ismartnet.mapper.UserMapper;
import com.isinonet.ismartnet.mapper.WebsiteMapper;
import com.isinonet.ismartnet.utils.JDBCHelper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2018-06-06.
 * 2018-06-06
 */
public class MapperTest {

    @Test
    public void insertUser() {
        SqlSession session = JDBCHelper.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserBean user = new UserBean("懿", "1314520");
        try {
            mapper.insertUser(user);
            System.out.println(user.toString());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void findAll() {

        SqlSession session = JDBCHelper.getSession();
        WebsiteMapper mapper = session.getMapper(WebsiteMapper.class);
        List<Website> websites = mapper.findAll();
        websites.forEach( x-> System.out.println(x));
    }
}
