package jpush.test.com.rxjavademo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by jesgoo on 2018/5/15.
 */

@Root(name = "order", strict = false)
public class Orderxml {
    @Element(name = "orderid", required = false)
    public String orderid;

    @Element(name = "productid", required = false)
    public String productid;

    @Element(name = "num", required = false)
    public String num;

    @Element(name = "ordercash", required = false)
    public String ordercash;

    @Element(name = "productname", required = false)
    public String productname;

    @Element(name = "sporderid", required = false)
    public String sporderid;

    @Element(name = "mobile", required = false)
    public String mobile;

    @Element(name = "resultno", required = false)
    public String resultno;

    @Element(name = "fundbalance", required = false)
    public String fundbalance;

    public String getOrderid() {
        return orderid;
    }

    public String getProductid() {
        return productid;
    }

    public String getNum() {
        return num;
    }

    public String getOrdercash() {
        return ordercash;
    }

    public String getProductname() {
        return productname;
    }

    public String getSporderid() {
        return sporderid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getResultno() {
        return resultno;
    }

    public String getFundbalance() {
        return fundbalance;
    }
}