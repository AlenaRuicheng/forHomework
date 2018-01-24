package kpi;

/**
 * Created by Ruicheng on 2018/1/24.
 */
public class KPIBean {
    private String browser; //用户使用的浏览器统计
    private String ip;      //页面用户独立ip数统计
    private String pv;      //网站pv量统计
    private String source;  //用户来源网址统计
    private String time;    //时间段用户访问量统计
    private String state;   //状态，是否成功访问

    public KPIBean(String browser, String ip, String source, String time, String pv, String state) {
        this.browser = browser;
        this.ip = ip;
        this.source = source;
        this.time = time;
        this.pv = pv;
        this.state = state;
    }

    public static KPIBean getKPI(String line) {
        String intermediate;
        String ip, source, time, pv, state, browser;
        int from, end;

        end = line.indexOf("-");
        ip = line.substring(0, end).trim();
        intermediate = line.substring(end);

        from = intermediate.indexOf("[");
        end = intermediate.indexOf("]") + 1;
        time = intermediate.substring(from, end);
        intermediate = intermediate.substring(end);

        from = intermediate.indexOf("\"") + 1;
        end = intermediate.indexOf("\"", from);
        pv = intermediate.substring(from, end).split(" ")[1];
        intermediate = intermediate.substring(end + 1).trim();

        state = intermediate.split(" ")[0];

        from = intermediate.indexOf("\"") + 1;
        end = intermediate.indexOf("\"", from);
        source = intermediate.substring(from, end);

        end = intermediate.lastIndexOf("\"");
        intermediate = intermediate.substring(0, end);
        from = intermediate.lastIndexOf("\"") + 1;
        String temp = intermediate.substring(from, end);
        browser = temp.split(" ")[0];


        return new KPIBean(browser, ip, source, time, pv, state);
    }

    @Override
    public String toString() {
        return "browser:{" + browser +
                "},\t ip:{" + ip +
                "},\t pv:{" + pv +
                "},\t source:{" + source +
                "},\t time:{" + time + '}';
    }

    public String getBrowser() {
        return browser;
    }

    public String getIp() {
        return ip;
    }

    public String getPv() {
        return pv;
    }

    public String getSource() {
        return source;
    }

    public String getTime() {
        return time;
    }

    public String getState() {
        return state;
    }
}
