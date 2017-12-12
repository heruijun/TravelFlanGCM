package gcm.heruijun.com.travelflangcm.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gcm.heruijun.com.travelflangcm.R;
import gcm.heruijun.com.travelflangcm.model.bean.ChatMessage;
import com.gcm.heruijun.base.ui.adapter.BaseAdapter;

/**
 * Created by heruijun on 2017/12/11.
 */

public class MessageAdapter<T extends ChatMessage> extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements BaseAdapter<T> {

    private static final String TAG = MessageAdapter.class.getSimpleName();

    protected static final int TYPE_TEXT_RIGHT = 1;
    protected static final int TYPE_TEXT_LEFT = 2;

    protected Context context;
    protected List<T> chatMessages;
    protected LayoutInflater inflater;
    protected MessageViewHolder mMessageViewHolder;

    private SparseIntArray containerLayoutRes = new SparseIntArray() {
        {
            put(TYPE_TEXT_LEFT, R.layout.list_item_text_left);
            put(TYPE_TEXT_RIGHT, R.layout.list_item_text_right);
        }
    };

    public MessageAdapter(Context context, List<T> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TEXT_LEFT:
                mMessageViewHolder = new TextMessageHolder(inflater.inflate(containerLayoutRes.get(viewType),
                        parent, false), R.id.msg_text_message);
                return mMessageViewHolder;
            case TYPE_TEXT_RIGHT:
                mMessageViewHolder = new TextMessageHolder(inflater.inflate(containerLayoutRes.get(viewType),
                        parent, false), R.id.msg_text_message);
                return mMessageViewHolder;
            default:
                Log.d(TAG, "onCreateViewHolder case default");
                return onCreateCustomViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        T chatMessage = getItem(position);
        int valueType = getItemViewType(position);

        switch (valueType) {
            case TYPE_TEXT_RIGHT:
                onBindViewMsgRightHolder((TextMessageHolder) holder, chatMessage, position);
                break;
            case TYPE_TEXT_LEFT:
                onBindViewMsgLeftHolder((TextMessageHolder) holder, chatMessage, position);
                break;
            default:
                onBindViewCustomHolder(holder, chatMessage, position);
                Log.i(TAG, "onBindViewHolder TYPE_ATTACHMENT_CUSTOM");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public T getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public void add(T item) {
        chatMessages.add(item);
        notifyItemInserted(chatMessages.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        T chatMessage = getItem(position);
        return chatMessage.isLeftMessage() ? TYPE_TEXT_LEFT : TYPE_TEXT_RIGHT;
    }

    // textview, imageview, attach, etc.
    protected abstract static class MessageViewHolder extends RecyclerView.ViewHolder {
        public MessageViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected static class TextMessageHolder extends MessageViewHolder {

        private TextView messageTextView;

        public TextMessageHolder(View itemView, @IdRes int msgId) {
            super(itemView);
            messageTextView = itemView.findViewById(msgId);
        }
    }

    protected MessageViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "You must create ViewHolder by your own");
        return null;
    }

    protected void onBindViewMsgLeftHolder(TextMessageHolder holder, T chatMessage, int position) {
        fillTextMessageHolder(holder, chatMessage, position, true);
    }

    protected void onBindViewMsgRightHolder(TextMessageHolder holder, T chatMessage, int position) {
        fillTextMessageHolder(holder, chatMessage, position, false);
    }

    private void fillTextMessageHolder(TextMessageHolder holder, T chatMessage, int position, boolean isLeftMessage) {
        holder.messageTextView.setText(chatMessage.getMessage());
    }

    protected void onBindViewCustomHolder(MessageViewHolder holder, T chatMessage, int position) {

    }

}
