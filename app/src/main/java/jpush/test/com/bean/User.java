package jpush.test.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Desc
 * Created by xupeng on 2018/4/18.
 */
@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private int age;
    private boolean isBoy;
    public boolean getIsBoy() {
        return this.isBoy;
    }
    public void setIsBoy(boolean isBoy) {
        this.isBoy = isBoy;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1724489812)
    public User(Long id, String name, int age, boolean isBoy) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isBoy = isBoy;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    
}
