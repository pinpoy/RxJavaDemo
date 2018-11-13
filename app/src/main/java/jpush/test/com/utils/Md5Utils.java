package jpush.test.com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jesgoo on 2018/5/16.
 */

public class Md5Utils {
    public static String MD5HEX(String str) {

        byte[] digesta = null;
        try {
            MessageDigest alga =
                    MessageDigest.getInstance("MD5");
            alga.update(str.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            System.out.println("非法摘要算法");
        }
        return byte2hex(digesta);
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs;
            }
        }
        return hs;
    }

    public static String getxmlvalue(String keyname, String srcstr) {
        if (srcstr.indexOf("<" + keyname + ">") >= 0) {
            srcstr = srcstr.substring(srcstr.indexOf("<" + keyname + ">") +
                    keyname.length() + 2, srcstr
                    .indexOf("</" + keyname + ">"));
        } else {
            srcstr = "";
        }
        return srcstr;
    }

    public static String getinfo(String str) {
        if (str.indexOf("<resultno>0</resultno>") >= 0) {
            str = "发送成功";
        } else if (str.indexOf("<resultno>1</resultno>") >= 0) {
            str = "充值成功";
        } else if (str.indexOf("<resultno>2</resultno>") >= 0) {
            str = "充值中";
        } else if (str.indexOf("<resultno>9</resultno>") >= 0) {
            str = "充值失败";
        } else if (str.indexOf("<resultno>5001</resultno>") >= 0) {
            str = "代理商不存在";
        } else if (str.indexOf("<resultno>5002</resultno>") >= 0) {
            str = "代理商余额不足";
        } else if (str.indexOf("<resultno>5003</resultno>") >= 0) {
            str = "此商品暂时不可购买,详情联系平台商务咨询";
        } else if (str.indexOf("<resultno>5004</resultno>") >= 0) {
            str = "充值号码与所选商品不符";
        } else if (str.indexOf("<resultno>5005</resultno>") >= 0) {
            str = "充值请求验证错误";
        } else if (str.indexOf("<resultno>5006</resultno>") >= 0) {
            str = "代理商订单号重复(人工核实)";
        } else if (str.indexOf("<resultno>5007</resultno>") >= 0) {
            str = "所查询订单不存在(请进一步人工核实)";
        } else if (str.indexOf("<resultno>5008</resultno>") >= 0) {
            str = "交易亏损不能充值";
        } else if (str.indexOf("<resultno>5009</resultno>") >= 0) {
            str = "Ip不符";
        } else if (str.indexOf("<resultno>5010</resultno>") >= 0) {
            str = "商品编号与充值金额不符";
        } else if (str.indexOf("<resultno>5011</resultno>") >= 0) {
            str = "商品数量不支持	";
        } else if (str.indexOf("<resultno>5012</resultno>") >= 0) {
            str = "缺少必要参数或参数值不合法";
        } else if (str.indexOf("<resultno>5013</resultno>") >= 0) {
            str = "相同号码已经提交充值或被加入平台黑名单";
        } else if (str.indexOf("<resultno>9999</resultno>") >= 0) {
            str = "未知错误,需进入平台查询核实";
        } else {
            str = getxmlvalue("resultno", str);
        }
        return str;
    }
}
