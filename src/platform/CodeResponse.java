package platform;

public class CodeResponse {

    private String code;

    private String date;

    private long time;

    private int views;

    public CodeResponse() {}

    public CodeResponse(String code, long time, int views) {
        this.code = code;
        this.time = time;
        this.views = views;
    }

    public CodeResponse(String code, String date, long time, int views) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
