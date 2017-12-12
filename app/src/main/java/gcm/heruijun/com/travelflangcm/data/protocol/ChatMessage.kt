package gcm.heruijun.com.travelflangcm.data.protocol

/**
 * Created by heruijun on 2017/12/11.
 */
data class ChatMessage(val message: String, val isLeftMessage: Boolean)     // in production we should bind by user role