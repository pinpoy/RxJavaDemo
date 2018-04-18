package jpush.test.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Desc
 * Created by xupeng on 2018/4/18.
 */
@Entity
public class AlarmClock {
    @Id
    private long id;
    private String name;
    private String tag;
    private String ringName;
    private String ringUrl;
    public String getRingUrl() {
        return this.ringUrl;
    }
    public void setRingUrl(String ringUrl) {
        this.ringUrl = ringUrl;
    }
    public String getRingName() {
        return this.ringName;
    }
    public void setRingName(String ringName) {
        this.ringName = ringName;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 1375961375)
    public AlarmClock(long id, String name, String tag, String ringName,
            String ringUrl) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.ringName = ringName;
        this.ringUrl = ringUrl;
    }
    @Generated(hash = 238396230)
    public AlarmClock() {
    }
}
