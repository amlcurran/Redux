package com.espiandev.redux.cast;

public interface CastConnector<T> {
    void connect(T device);

    void setCallbacks(CastConnectorCallbacks connectorCallbacks);

    interface CastConnectorCallbacks {

        CastConnectorCallbacks NONE = new CastConnectorCallbacks() {

            @Override
            public void onConnected(RemoteController remoteMedia) {

            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onConnectionFailed() {

            }

            @Override
            public void onVolumeChanged(double volume) {

            }
        };

        void onConnected(RemoteController remoteMedia);

        void onDisconnected();

        void onConnectionFailed();

        void onVolumeChanged(double volume);
    }

}
