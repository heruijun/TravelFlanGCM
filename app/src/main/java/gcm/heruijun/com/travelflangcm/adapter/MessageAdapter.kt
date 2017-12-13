package gcm.heruijun.com.travelflangcm.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import gcm.heruijun.com.travelflangcm.R
import gcm.heruijun.com.travelflangcm.data.protocol.ChatMessage
import com.gcm.heruijun.base.ui.adapter.BaseAdapter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by heruijun on 2017/12/11.
 */

class MessageAdapter<T : ChatMessage>(protected var context: Context, protected var chatMessages: MutableList<T>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(), BaseAdapter<T>, AnkoLogger {

    protected var inflater: LayoutInflater
    protected lateinit var mMessageViewHolder: MessageViewHolder

    private val containerLayoutRes = object : SparseIntArray() {
        init {
            put(TYPE_TEXT_LEFT, R.layout.list_item_text_left)
            put(TYPE_TEXT_RIGHT, R.layout.list_item_text_right)
        }
    }

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder? {
        when (viewType) {
            TYPE_TEXT_LEFT -> {
                mMessageViewHolder = TextMessageHolder(inflater.inflate(containerLayoutRes.get(viewType),
                        parent, false), R.id.msg_text_message)
                return mMessageViewHolder
            }
            TYPE_TEXT_RIGHT -> {
                mMessageViewHolder = TextMessageHolder(inflater.inflate(containerLayoutRes.get(viewType),
                        parent, false), R.id.msg_text_message)
                return mMessageViewHolder
            }
            else -> {
                Log.d(TAG, "onCreateViewHolder case default")
                return onCreateCustomViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val chatMessage = getItem(position)
        val valueType = getItemViewType(position)

        when (valueType) {
            TYPE_TEXT_RIGHT -> onBindViewMsgRightHolder(holder as TextMessageHolder, chatMessage, position)
            TYPE_TEXT_LEFT -> onBindViewMsgLeftHolder(holder as TextMessageHolder, chatMessage, position)
            else -> {
                onBindViewCustomHolder(holder, chatMessage, position)
                info("onBindViewHolder TYPE_ATTACHMENT_CUSTOM")
            }
        }
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun getItem(position: Int): T {
        return chatMessages[position]
    }

    override fun add(item: T) {
        chatMessages.add(item)
        notifyItemInserted(chatMessages.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = getItem(position)
        return if (chatMessage.isLeftMessage) TYPE_TEXT_LEFT else TYPE_TEXT_RIGHT
    }

    // textview, imageview, attach, etc.
    abstract class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    protected class TextMessageHolder(itemView: View, @IdRes msgId: Int) : MessageViewHolder(itemView) {

        val messageTextView: TextView

        init {
            messageTextView = itemView.findViewById(msgId)
        }
    }

    protected fun onCreateCustomViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder? {
        error("You must create ViewHolder by your own")
        return null
    }

    protected fun onBindViewMsgLeftHolder(holder: TextMessageHolder, chatMessage: T, position: Int) {
        fillTextMessageHolder(holder, chatMessage, position, true)
    }

    protected fun onBindViewMsgRightHolder(holder: TextMessageHolder, chatMessage: T, position: Int) {
        fillTextMessageHolder(holder, chatMessage, position, false)
    }

    private fun fillTextMessageHolder(holder: TextMessageHolder, chatMessage: T, position: Int, isLeftMessage: Boolean) {
        holder.messageTextView.text = chatMessage.message
    }

    protected fun onBindViewCustomHolder(holder: MessageViewHolder, chatMessage: T, position: Int) {

    }

    companion object {

        protected val TYPE_TEXT_RIGHT = 1
        protected val TYPE_TEXT_LEFT = 2
    }

}
