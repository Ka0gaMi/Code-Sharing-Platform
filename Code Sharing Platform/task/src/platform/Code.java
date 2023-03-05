package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "CODES")
public class Code {

    @Id
    private String uuid;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private long time;

    @Column(nullable = false)
    private  int views;

    private boolean isRestricted;

    private boolean isTimeRestricted;

    private boolean isViewsRestricted;

    public Code() {
        this.uuid = UUID.randomUUID().toString();
        this.date = FormatDateTime.formatTime(LocalDateTime.now());
        this.isRestricted = false;
        this.isTimeRestricted = false;
        this.isViewsRestricted = false;
    }

    public Code(String code, long time, int views) {
        this.uuid = UUID.randomUUID().toString();
        this.code = code;
        this.time = time;
        this.views = views;
        this.date = FormatDateTime.formatTime(LocalDateTime.now());
        this.isRestricted = (time > 0 || views > 0);
        this.isTimeRestricted = (time > 0);
        this.isViewsRestricted = (views > 0);
    }

    @JsonIgnore
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    @JsonIgnore
    public boolean isRestricted() {
        return isRestricted;
    }

    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }

    @JsonIgnore
    public boolean isTimeRestricted() {
        return isTimeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        isTimeRestricted = timeRestricted;
    }

    @JsonIgnore
    public boolean isViewsRestricted() {
        return isViewsRestricted;
    }

    public void setViewsRestricted(boolean viewsRestricted) {
        isViewsRestricted = viewsRestricted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code1 = (Code) o;
        return time == code1.time && views == code1.views && isRestricted == code1.isRestricted && isTimeRestricted == code1.isTimeRestricted && isViewsRestricted == code1.isViewsRestricted && Objects.equals(uuid, code1.uuid) && Objects.equals(code, code1.code) && Objects.equals(date, code1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, code, date, time, views, isRestricted, isTimeRestricted, isViewsRestricted);
    }
}

