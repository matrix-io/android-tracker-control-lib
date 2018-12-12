package admobilize.adtrackerlib.control;

/**
 * Created by Antonio Vanegas @hpsaturn on 11/10/17.
 */

public interface OnDetectorActionListener {

    void onChangeName(String name);

    void onChangeUrl(String url);

    void onServiceEnable(boolean enable);

    void onServiceStart();

    void onServiceStop(String msg);

    void onRequestConfigDetails();

    void onUpdateConfigDetails(ConfigParameters params);

    void onRequestPreview();

    void onUpdatePreview(byte[] preview);

    void onUpdateOrientationAllowed(boolean isOrientationAllowed);

    void onUpdateOrientation(String orientation);

    void onDetectorId(String detectorId);

    void onDetectorData(String data);

}
