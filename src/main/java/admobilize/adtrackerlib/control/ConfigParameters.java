package admobilize.adtrackerlib.control;

/**
 * Created by Antonio Vanegas @hpsaturn on 11/15/17.
 */

public class ConfigParameters {

    public String name;

    public String url;

    public String orientation;

    public boolean isOrientationChangeAllowed;

    public boolean isDetectionRunning;

    public ConfigParameters(String name) {
        this.name = name;
    }

    public ConfigParameters(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
