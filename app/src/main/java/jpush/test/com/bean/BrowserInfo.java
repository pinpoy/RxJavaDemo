package jpush.test.com.bean;

import java.io.Serializable;

/**
 * Created by jesgoo on 2019/3/8.
 */

public class BrowserInfo implements Serializable{


    /**
     * cookie :
     * proxy :
     * referer :
     * ua : Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36
     * url : https://cpu.baidu.com/1043/fda9215b?scid=23749
     */

    private String cookie;
    private String proxy;
    private String referer;
    private String ua;
    private String url;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
