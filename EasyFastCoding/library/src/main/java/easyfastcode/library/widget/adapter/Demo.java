package easyfastcode.library.widget.adapter;

/**
 * Created by Administrator on 2015/11/28 0028.
 */
public class Demo {

    public void text() {
//        mListView = (ListView) findViewById(R.id.id_lv_main);
//        mAdapter = new QuickAdapter<Bean>(
//                SimpleTestActivity.this, R.layout.item_list, mDatas) {
//
//            @Override
//            protected void convert(BaseAdapterHelper helper, Bean item) {
//                helper.setText(R.id.tv_title, item.getTitle());
//                helper.setText(R.id.tv_describe, item.getDesc());
//                helper.setText(R.id.tv_phone, item.getPhone());
//                helper.setText(R.id.tv_time, item.getTime());
//                helper.getView(R.id.tv_title).setOnClickListener(l)
//            }
//        };
//        // 设置适配器
//        mListView.setAdapter(mAdapter);
    }

    public void text1() {
//        mListView = (ListView) findViewById(R.id.id_lv_main);
//
//        MultiItemTypeSupport<ChatMessage> multiItemTypeSupport = new MultiItemTypeSupport<ChatMessage>() {
//            @Override
//            public int getLayoutId(int position, ChatMessage msg) {
//                if (msg.isComMeg()) {
//                    return R.layout.main_chat_from_msg;
//                }
//                return R.layout.main_chat_send_msg;
//            }
//
//            @Override
//            public int getViewTypeCount() {
//                return 2;
//            }
//
//            @Override
//            public int getItemViewType(int postion, ChatMessage msg) {
//                if (msg.isComMeg()) {
//                    return ChatMessage.RECIEVE_MSG;
//                }
//                return ChatMessage.SEND_MSG;
//            }
//        };
//        initDatas();
//
//        mAdapter = new QuickAdapter<ChatMessage>(ChatActivity.this, mDatas,
//                multiItemTypeSupport) {
//            @Override
//            protected void convert(BaseAdapterHelper helper, ChatMessage item) {
//                switch (helper.layoutId) {
//                    case R.layout.main_chat_from_msg:
//                        helper.setText(R.id.chat_from_content, item.getContent());
//                        helper.setText(R.id.chat_from_name, item.getName());
//                        helper.setImageResource(R.id.chat_from_icon, item.getIcon());
//                        break;
//                    case R.layout.main_chat_send_msg:
//                        helper.setText(R.id.chat_send_content, item.getContent());
//                        helper.setText(R.id.chat_send_name, item.getName());
//                        helper.setImageResource(R.id.chat_send_icon, item.getIcon());
//                        break;
//                }
//
//            }
//        };
//        // 设置适配器
//        mListView.setAdapter(mAdapter);
    }

}
