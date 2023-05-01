package com.jmzd.ghazal.foodappmvi.utils.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivity @Inject constructor(private val manager: ConnectivityManager, private val request: NetworkRequest)
    : ConnectivityStatus {

    override fun observe(): Flow<ConnectivityStatus.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityStatus.Status.Available) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityStatus.Status.Unavailable) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityStatus.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityStatus.Status.Lost) }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.registerDefaultNetworkCallback(callback)
            } else {
                manager.registerNetworkCallback(request, callback)
            }
            awaitClose {
                manager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}