package com.usatov.nanithometask.data.connect

import com.usatov.nanithometask.core.common.TAG
import com.usatov.nanithometask.core.common.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketDataSourceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val logger: Logger
) : SocketDataSource {

    private var webSocket: WebSocket? = null

    override fun subscribe(ip: String, port: Int): Flow<SocketState> = callbackFlow {
        logger.d(TAG, "subscribe: ip = $ip port = $port")
        val request = Request.Builder()
            .url("ws://$ip:$port/nanit")
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                logger.d(TAG, "onOpen -> $response")
                trySend(SocketState.Connected).isSuccess
                ws.send(REQUEST_WORD)
                webSocket = ws
            }

            override fun onMessage(ws: WebSocket, text: String) {
                logger.d(TAG, "onMessage -> $text")
                trySend(SocketState.Data(text)).isSuccess
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                logger.d(TAG, "onFailure -> $t")
                trySend(SocketState.Error(t)).isSuccess
                close(t)
            }

            override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                logger.d(TAG, "onClosed -> $reason")
                trySend(SocketState.Disconnected).isSuccess
                close()
            }
        }

        okHttpClient.newWebSocket(request, listener)

        awaitClose {
            webSocket?.close(1000, "Client closed")
            webSocket = null
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        private val REQUEST_WORD = "HappyBirthday"
    }
}
