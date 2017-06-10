package concaro.hackernews.app.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.items.GenericAbstractItem;

import java.util.List;

import butterknife.ButterKnife;
import concaro.hackernews.app.R;
import concaro.hackernews.app.presentation.model.HNComment;

/**
 * Created by mikepenz on 28.12.15.
 */
public class HNCommentItem extends GenericAbstractItem<HNComment, HNCommentItem, HNCommentItem.ViewHolder> {

    IBindView listener;

    public interface IBindView {
        void onBindData(int id, int level, int position);
    }

    public HNCommentItem(HNComment item) {
        super(item);
    }

    public HNCommentItem initListener(IBindView listener) {
        this.listener = listener;
        return this;
    }

    /**
     * defines the type defining this item. must be unique. preferably an id
     *
     * @return the type
     */
    @Override
    public int getType() {
        return R.id.notification_item_id;
    }

    /**
     * defines the layout which will be used for this item in the list
     *
     * @return the layout for this item
     */
    @Override
    public int getLayoutRes() {
        return R.layout.item_hn_comment;
    }

    /**
     * binds the data of this item onto the viewHolder
     *
     * @param viewHolder the viewHolder of this item
     */
    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        //get the context
        Context ctx = viewHolder.itemView.getContext();
        HNComment item = getModel();
//
//        NotificationData notificationData = item.getData();
//        //define our data for the view

//        if (!TextUtils.isEmpty(item.getCreated_at())) {
//            viewHolder.tvTime.setReferenceTime(item.getCreated_at());
//        }
//        //load glide
//        NotificationUser user = notificationData.getParams().getUser();



    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    /**
     * our ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
