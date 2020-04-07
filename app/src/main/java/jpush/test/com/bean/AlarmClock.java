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
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String tag;
    private String ringName;
    private String ringUrl;
    @Generated(hash = 1797426906)
    public AlarmClock(Long id, String name, String tag, String ringName,
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getRingName() {
        return this.ringName;
    }
    public void setRingName(String ringName) {
        this.ringName = ringName;
    }
    public String getRingUrl() {
        return this.ringUrl;
    }
    public void setRingUrl(String ringUrl) {
        this.ringUrl = ringUrl;
    }

}
