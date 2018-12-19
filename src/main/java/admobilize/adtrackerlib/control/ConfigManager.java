package admobilize.adtrackerlib.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Antonio Vanegas @hpsaturn on 11/10/17.
 */

public class ConfigManager {

    private static final String TAG = ConfigManager.class.getSimpleName();

    public static final String action_detector_webhook = "admobilize.broadcast.data";

    private static final String action_get_detector_id = "ACTION_GET_ADMOBILIZE_DETECTOR_ID";
    private static final String action_on_detector_id = "ACTION_ON_ADMOBILIZE_DETECTOR_ID";


    private final Context ctx;
    private final OnDetectorActionListener eventListener;

    private String action_name;
    private String action_url;
    private String action_enable;
    private String action_start;
    private String action_stop;
    private String action_orientation;
    private String action_orientation_status;

    public ConfigManager(Context ctx, OnDetectorActionListener listener) {
        this.ctx = ctx;
        this.eventListener = listener;
        registerReceiver("");
        getDetectorId();
    }

    public void getDetectorId() {
        Intent intent = new Intent(action_get_detector_id);
        ctx.sendBroadcast(intent);
    }

    private void registerReceiver(String detectorId) {

        action_name = "ACTION" + detectorId + "NAME";
        action_url = "ACTION" + detectorId + "URL";
        action_enable = "ACTION" + detectorId + "ENABLE";
        action_start = "ACTION" + detectorId + "SERVICE_START";
        action_stop = "ACTION" + detectorId + "SERVICE_STOP";
        action_orientation = "ACTION" + detectorId + "ORIENTATION";
        action_orientation_status = "ACTION" + detectorId + "ORIENTATION_STATUS";

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action_name);
        intentFilter.addAction(action_url);
        intentFilter.addAction(action_enable);
        intentFilter.addAction(action_start);
        intentFilter.addAction(action_stop);
        intentFilter.addAction(action_orientation);
        intentFilter.addAction(action_orientation_status);
        intentFilter.addAction(action_get_detector_id);
        intentFilter.addAction(action_on_detector_id);

        ctx.registerReceiver(mReceiver, intentFilter);

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

            if (action.equals(action_name)) {

                eventListener.onChangeName(
                        intent.getExtras().getString(BroadcastKeys.CAMERA_NAME)
                );

            } else if (action.equals(action_url)) {

                eventListener.onChangeUrl(
                        intent.getExtras().getString(BroadcastKeys.CAMERA_URL)
                );

            } else if (action.equals(action_enable)) {

                eventListener.onServiceEnable(
                        intent.getExtras().getBoolean(BroadcastKeys.CAMERA_ENABLE)
                );

            } else if (action.equals(action_start)) {

                eventListener.onServiceStart();

            } else if (action.equals(action_stop)) {

                String msg = intent.getExtras().getString(BroadcastKeys.SERVICE_MSG);
                eventListener.onServiceStop(msg);

            }  else if (action.equals(action_orientation_status)) {

                boolean isOrientationAllowed = intent.getExtras().getBoolean(BroadcastKeys.CAMERA_ORIENTATION_ALLOWED);
                eventListener.onUpdateOrientationAllowed(isOrientationAllowed);

            } else if (action.equals(action_orientation)) {

                String orientation = intent.getExtras().getString(BroadcastKeys.CAMERA_ORIENTATION);
                eventListener.onUpdateOrientation(orientation);

            } else if (action.equals(action_on_detector_id)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    String detectorId = extras.getString(BroadcastKeys.KEY_DETECTOR_ID);
                    Log.d(TAG, "new detectorId: " + detectorId);
                    registerReceiver(detectorId);
                    eventListener.onDetectorId(detectorId);
                }

            } else if (action.equals(action_detector_webhook)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    String data = extras.getString("webhook");
                    if(data!=null){
                        Log.d(TAG, "onDetectorData: " + data);
                        eventListener.onDetectorData(data);
                    }
                }
            }
        }
    };

    public void unregister() {
        ctx.unregisterReceiver(mReceiver);
    }

}
