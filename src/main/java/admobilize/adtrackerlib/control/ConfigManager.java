package admobilize.adtrackerlib.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Antonio Vanegas @hpsaturn on 11/10/17.
 */

public class ConfigManager {

    private static final String TAG = ConfigManager.class.getSimpleName();

    private final String detectorId;
    private final Context ctx;
    private final OnConfigEventListener eventListener;

    private String action_name;
    private String action_url;
    private String action_enable;
    private String action_start;
    private String action_stop;
    private String action_update_config_details;
    private String action_request_preview;
    private String action_update_preview;
    private String action_request_config_details;
    private String action_orientation;
    private String action_orientation_status;
    private String action_get_detector_id;
    private String action_on_detector_id;

    public ConfigManager(String detectorId, Context ctx, OnConfigEventListener listener) {
        this.detectorId=detectorId;
        this.ctx=ctx;
        this.eventListener=listener;
        registerReceiver();
    }

    private void registerReceiver() {

        action_name = "ACTION"+detectorId+"NAME";
        action_url = "ACTION"+detectorId+"URL";
        action_enable = "ACTION"+detectorId+"ENABLE";
        action_start =  "ACTION"+detectorId+"SERVICE_START";
        action_stop = "ACTION"+detectorId+"SERVICE_STOP";
        action_request_config_details = "ACTION"+detectorId+"REQUEST_DETAILS";
        action_update_config_details = "ACTION"+detectorId+"CONFIG_DETAILS";
        action_request_preview = "ACTION"+detectorId+"REQUEST_PREVIEW";
        action_update_preview = "ACTION"+detectorId+"UPDATE_PREVIEW";
        action_orientation = "ACTION"+detectorId+"ORIENTATION";
        action_orientation_status = "ACTION"+detectorId+"ORIENTATION_STATUS";
        action_get_detector_id = "ACTION_GET_ADMOBILIZE_DETECTOR_ID";
        action_on_detector_id = "ACTION_ON_ADMOBILIZE_DETECTOR_ID";

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action_name);
        intentFilter.addAction(action_url);
        intentFilter.addAction(action_enable);
        intentFilter.addAction(action_start);
        intentFilter.addAction(action_stop);
        intentFilter.addAction(action_request_config_details);
        intentFilter.addAction(action_update_config_details);
        intentFilter.addAction(action_request_preview);
        intentFilter.addAction(action_update_preview);
        intentFilter.addAction(action_orientation);
        intentFilter.addAction(action_orientation_status);
        intentFilter.addAction(action_get_detector_id);
        intentFilter.addAction(action_on_detector_id);

        ctx.registerReceiver(mReceiver,intentFilter);

    }

    public void setCameraName(String name) {
        Intent intent = new Intent(action_name);
        intent.putExtra(BroadcastKeys.CAMERA_NAME, name);
        ctx.sendBroadcast(intent);
    }

    public void setCameraUrl(String url) {
        Intent intent = new Intent(action_url);
        intent.putExtra(BroadcastKeys.CAMERA_URL, url);
        ctx.sendBroadcast(intent);
    }

    public void setServiceEnable(boolean enable) {
        Intent intent = new Intent(action_enable);
        intent.putExtra(BroadcastKeys.CAMERA_ENABLE, enable);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        ctx.sendBroadcast(intent);
    }

    public void setServiceStart() {
        Intent intent = new Intent(action_start);
        ctx.sendBroadcast(intent);
    }

    public void setServiceStop(String message) {
        Intent intent = new Intent(action_stop);
        intent.putExtra(BroadcastKeys.SERVICE_MSG, message);
        ctx.sendBroadcast(intent);
    }

    public void requestConfigDetails() {
        Intent intent = new Intent(action_request_config_details);
        ctx.sendBroadcast(intent);
    }

    public void updateConfigDetails(ConfigParameters params) {
        Intent intent = new Intent(action_update_config_details);
        intent.putExtra(BroadcastKeys.CAMERA_NAME, params.name);
        intent.putExtra(BroadcastKeys.CAMERA_URL, params.url);
        intent.putExtra(BroadcastKeys.CAMERA_ORIENTATION, params.orientation);
        intent.putExtra(BroadcastKeys.CAMERA_ORIENTATION_ALLOWED, params.isOrientationChangeAllowed);
        intent.putExtra(BroadcastKeys.CAMERA_ENABLE, params.isDetectionRunning);
        ctx.sendBroadcast(intent);
    }

    public void requestCameraPreview() {
        Intent intent = new Intent(action_request_preview);
        ctx.sendBroadcast(intent);
    }

    public void updateCameraPreview(byte [] preview) {
        Intent intent = new Intent(action_update_preview);
        intent.putExtra(BroadcastKeys.CAMERA_PREVIEW, preview);
        ctx.sendBroadcast(intent);
    }

    public void setOrientationAllowed(boolean status) {
        Intent intent = new Intent(action_orientation_status);
        intent.putExtra(BroadcastKeys.CAMERA_ORIENTATION_ALLOWED, status);
        ctx.sendBroadcast(intent);
    }

    public void setOrientation(String orientation) {
        Intent intent = new Intent(action_orientation);
        intent.putExtra(BroadcastKeys.CAMERA_ORIENTATION, orientation);
        ctx.sendBroadcast(intent);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if(action.equals(action_name)) {

                eventListener.onChangeName(intent.getExtras().getString(BroadcastKeys.CAMERA_NAME));

            }
            else if(action.equals(action_url)) {

                eventListener.onChangeUrl(intent.getExtras().getString(BroadcastKeys.CAMERA_URL));

            }
            else if(action.equals(action_enable)) {

                eventListener.onServiceEnable(intent.getExtras().getBoolean(BroadcastKeys.CAMERA_ENABLE));

            }
            else if(action.equals(action_start)) {

                eventListener.onServiceStart();

            }
            else if(action.equals(action_stop)) {

                String msg = intent.getExtras().getString(BroadcastKeys.SERVICE_MSG);
                eventListener.onServiceStop(msg);

            }
            else if(action.equals(action_request_config_details)) {

                eventListener.onRequestConfigDetails();

            }
            else if(action.equals(action_update_config_details)) {

                String name = intent.getExtras().getString(BroadcastKeys.CAMERA_NAME);
                String url  = intent.getExtras().getString(BroadcastKeys.CAMERA_URL);
                String orientation  = intent.getExtras().getString(BroadcastKeys.CAMERA_ORIENTATION);
                boolean isOrientationChangeAllowed = intent.getExtras().getBoolean(BroadcastKeys.CAMERA_ORIENTATION_ALLOWED);
                boolean enable = intent.getExtras().getBoolean(BroadcastKeys.CAMERA_ENABLE);
                ConfigParameters params = new ConfigParameters(name, url);
                params.orientation = orientation;
                params.isOrientationChangeAllowed = isOrientationChangeAllowed;
                params.isDetectionRunning = enable;
                eventListener.onUpdateConfigDetails(params);

            }
            else if(action.equals(action_request_preview)) {

                eventListener.onRequestPreview();

            }
            else if(action.equals(action_update_preview)) {

                byte[] preview = intent.getExtras().getByteArray(BroadcastKeys.CAMERA_PREVIEW);
                eventListener.onUpdatePreview(preview);

            }
            else if(action.equals(action_orientation_status)){

                boolean isOrientationAllowed = intent.getExtras().getBoolean(BroadcastKeys.CAMERA_ORIENTATION_ALLOWED);
                eventListener.onUpdateOrientationAllowed(isOrientationAllowed);

            }
            else if(action.equals(action_orientation)){

                String orientation = intent.getExtras().getString(BroadcastKeys.CAMERA_ORIENTATION);
                eventListener.onUpdateOrientation(orientation);

            }
            else if(action.equals(action_get_detector_id)){

                // for external apps
                eventListener.onGetDetectorId(detectorId);
                Intent intentCallback = new Intent(action_on_detector_id);
                intentCallback.putExtra(BroadcastKeys.DETECTOR_ID, detectorId);
                ctx.sendBroadcast(intentCallback);

            }

        }
    };

    public void unregister() {
        ctx.unregisterReceiver(mReceiver);
    }

    public String getDetectorId() {
        return detectorId;
    }
}
