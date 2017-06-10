package concaro.hackernews.app.presentation.model;


import concaro.hackernews.app.presentation.model.*;

/**
 * Created by CONCARO on 10/27/2015.
 */
public class ErrorResponse {

    private concaro.hackernews.app.presentation.model.StatusEntity meta;
    private String timestamp;
    private String timezone;

    public concaro.hackernews.app.presentation.model.StatusEntity getMeta() {
        return meta;
    }

    public void setMeta(concaro.hackernews.app.presentation.model.StatusEntity meta) {
        this.meta = meta;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
