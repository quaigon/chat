package com.example.kamil.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private List <Message> messageList = null;
    private Context contex;
    LayoutInflater layoutInflater;





    public MessageAdapter(Context context, List<Message> objects) {

        this.contex = context;
        this.messageList = objects;
        this.layoutInflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Message getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;


        if (null == view) {
            view = layoutInflater.inflate(R.layout.message_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }


        Message message = getItem(position);
        viewHolder.messageTextView.setText(message.getText());
        viewHolder.userNameTextView.setText(message.getNickname());
        viewHolder.dateTextView.setText(message.getDate());

        return view;
    }


    static class ViewHolder {
        private TextView userNameTextView;
        private TextView messageTextView;
        private TextView dateTextView;

        public ViewHolder (View convertView) {
            userNameTextView = (TextView) convertView.findViewById(R.id.user_name);
            messageTextView = (TextView) convertView.findViewById(R.id.message_content);
            dateTextView = (TextView) convertView.findViewById(R.id.date);
        }



    }

}
