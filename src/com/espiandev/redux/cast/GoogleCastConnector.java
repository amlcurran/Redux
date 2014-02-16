package com.espiandev.redux.cast;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;

public class GoogleCastConnector extends Cast.Listener implements CastConnector<CastDevice> {

    GoogleApiClient apiClient;
    private Context context;
    private String appId;
    private CastConnectorCallbacks connectorCallbacks = CastConnectorCallbacks.NONE;

    public GoogleCastConnector(Context context, String appId) {
        this.context = context;
        this.appId = appId;
    }

    @Override
    public void connect(CastDevice device) {
        Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions
                .builder(device, this);

        apiClient = new GoogleApiClient.Builder(context)
                .addApi(Cast.API, apiOptionsBuilder.build())
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionCallbacks)
                .build();

        apiClient.connect();
    }

    @Override
    public void setCallbacks(CastConnectorCallbacks connectorCallbacks) {
        this.connectorCallbacks = connectorCallbacks;
    }

    private ConnectionHandler connectionCallbacks = new ConnectionHandler() {

        @Override
        public void onConnected(Bundle bundle) {
            try {
                Cast.CastApi.launchApplication(apiClient, appId, false)
                        .setResultCallback(
                                new ResultCallback<Cast.ApplicationConnectionResult>() {
                                    @Override
                                    public void onResult(Cast.ApplicationConnectionResult result) {
                                        Status status = result.getStatus();
                                        if (status.isSuccess()) {
                                            try {
                                                RemoteMediaPlayer remoteMedia = new RemoteMediaPlayer();
                                                Cast.CastApi.setMessageReceivedCallbacks(apiClient, remoteMedia.getNamespace(), remoteMedia);
                                                connectorCallbacks.onConnected(new GoogleCastRemoteController(remoteMedia, apiClient));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectionSuspended(int i) {
            connectorCallbacks.onDisconnected();
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            connectorCallbacks.onConnectionFailed();
        }
    };

    @Override
    public void onVolumeChanged() {
        connectorCallbacks.onVolumeChanged(Cast.CastApi.getVolume(apiClient));
    }
}
