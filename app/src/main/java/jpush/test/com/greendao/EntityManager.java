package jpush.test.com.greendao;

import com.anye.greendao.gen.UserDao;

/**
 * Desc
 * Created by xupeng on 2018/4/18.
 */

public class EntityManager {
    private static EntityManager entityManager;
    public UserDao userDao;

    /**
     * 创建User表实例
     *
     * @return
     */
    public UserDao getUserDao(){
        userDao = GreenDaoManager.getInstance().getSession().getUserDao();
        return userDao;
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static EntityManager getInstance() {
        if (entityManager == null) {
            entityManager = new EntityManager();
        }
        return entityManager;
    }
}
