package mek.stripeterminal.mappings

import com.stripe.stripeterminal.external.models.ConnectionConfiguration
import mek.stripeterminal.api.BluetoothConnectionConfigurationApi
import mek.stripeterminal.api.ConnectionConfigurationApi
import mek.stripeterminal.api.HandoffConnectionConfigurationApi
import mek.stripeterminal.api.InternetConnectionConfigurationApi
import mek.stripeterminal.api.TapToPayConnectionConfigurationApi
import mek.stripeterminal.api.UsbConnectionConfigurationApi
import mek.stripeterminal.api.PlatformError
import mek.stripeterminal.plugin.ReaderDelegatePlugin

fun ConnectionConfigurationApi.toHost(readerDelegate: ReaderDelegatePlugin): ConnectionConfiguration {
    return when(this) {
        is BluetoothConnectionConfigurationApi -> ConnectionConfiguration.BluetoothConnectionConfiguration(
            locationId = locationId,
            autoReconnectOnUnexpectedDisconnect = autoReconnectOnUnexpectedDisconnect,
            bluetoothReaderListener = readerDelegate
        )
//        is EmbeddedConnectionConfigurationApi -> ConnectionConfiguration.EmbeddedConnectionConfiguration(
//            posConnectionType = ,
//            listener = readerDelegate,
//            supportsOfflineMode = supportsOfflineMode,
//            supportsOfflineSetupIntents = supportsOfflineSetupIntents,
//            shouldActivateWithExpandedLocation = shouldActivateWithExpandedLocation,
//            shouldGenerateOfflineSessionToken = shouldGenerateOfflineSessionToken,
//        )
        is HandoffConnectionConfigurationApi -> throw PlatformError(
            "mek_stripe_terminal",
            "HandoffConnectionConfiguration is not supported. Add com.stripe:stripterminal-appsondevices to your dependencies."
        )
        is InternetConnectionConfigurationApi -> ConnectionConfiguration.InternetConnectionConfiguration(
            failIfInUse = failIfInUse,
            internetReaderListener = readerDelegate,
        )
        is TapToPayConnectionConfigurationApi -> ConnectionConfiguration.TapToPayConnectionConfiguration(
            locationId = locationId,
            autoReconnectOnUnexpectedDisconnect = autoReconnectOnUnexpectedDisconnect,
            tapToPayReaderListener = readerDelegate,
            onBehalfOf = onBehalfOf
        )
        is UsbConnectionConfigurationApi -> ConnectionConfiguration.UsbConnectionConfiguration(
            locationId = locationId,
            autoReconnectOnUnexpectedDisconnect = autoReconnectOnUnexpectedDisconnect,
            usbReaderListener = readerDelegate
        )
    }
}