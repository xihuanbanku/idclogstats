package com.isinonet.tests;

import com.isinonet.ismartnet.beans.UserBean;
import com.isinonet.ismartnet.mapper.UserMapper;
import com.isinonet.ismartnet.utils.JDBCHelper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

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
}
