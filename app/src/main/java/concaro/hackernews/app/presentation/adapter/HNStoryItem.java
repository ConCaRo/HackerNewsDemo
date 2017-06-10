package concaro.hackernews.app.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.helpers.ClickListenerHelper;
import com.mikepenz.fastadapter.items.GenericAbstractItem;
import com.mikepenz.fastadapter.listeners.ClickEventHook;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import concaro.hackernews.app.R;
import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.util.Functions;

/**
 * Created by mikepenz on 28.12.15.
 */
public class HNStoryItem extends GenericAbstractItem<HNStory, HNStoryItem, HNStoryItem.ViewHolder> {

    public HNStoryItem(HNStory item) {
        super(item);
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
        return R.layout.item_hn_story;
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
        HNStory item = getModel();

        viewHolder.tvTitle.setText(Html.fromHtml(item.getTitle()));
        if (!TextUtils.isEmpty(item.getBy())) {
            viewHolder.tvAuthor.setVisibility(View.VISIBLE);
            viewHolder.tvAuthor.setText(" - " + String.valueOf(item.getBy()));
        } else {
            viewHolder.tvAuthor.setVisibility(View.GONE);
        }
        viewHolder.tvNumber.setText(String.valueOf(viewHolder.getAdapterPosition() + 1));
        viewHolder.tvPoint.setText(item.getScore() + "p");
        if (item.getKids() != null) {
            viewHolder.tvComments.setText(item.getKids().size() + " comments");
        } else {
            viewHolder.tvComments.setText("0" + " comments");
        }
        if (item.getTime() > 0) {
            viewHolder.tvTime.setText(Functions.getAbbreviatedTimeSpan(item.getTime()));
        }
        viewHolder.tvLink.setText(Functions.getDomainName(item.getUrl()));
        if(TextUtils.isEmpty(item.getUrl())) {
            viewHolder.imvGotoApp.setVisibility(View.GONE);
        } else {
            viewHolder.imvGotoApp.setVisibility(View.VISIBLE);
        }
        
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
        @BindView(R.id.tvTime)
        TextView tvTime;

        @BindView(R.id.tvNumber)
        protected TextView tvNumber;
        @BindView(R.id.tvPoint)
        protected TextView tvPoint;
        @BindView(R.id.tvTitle)
        protected TextView tvTitle;
        @BindView(R.id.tvLink)
        protected TextView tvLink;

        @BindView(R.id.tvAuthor)
        protected TextView tvAuthor;
        @BindView(R.id.tvComments)
        protected TextView tvComments;
        @BindView(R.id.imvGotoApp)
        protected ImageView imvGotoApp;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface HNStoryItemClick {
        void onClickGotoApp(HNStoryItem item, ImageView imv);
    }

    public static class ItemClick extends ClickEventHook<HNStoryItem> {
        HNStoryItemClick listener;

        public ItemClick(HNStoryItemClick listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v, int position, FastAdapter<HNStoryItem> fastAdapter, HNStoryItem item) {
            if (listener != null) {
                if (v.getId() == R.id.imvGotoApp) {
                    listener.onClickGotoApp(item, (ImageView) v);
                }
            }
        }

        @Override
        public List<View> onBindMany(@NonNull RecyclerView.ViewHolder viewHolder) {
            if (viewHolder instanceof HNStoryItem.ViewHolder) {
                return ClickListenerHelper.toList(((ViewHolder) viewHolder).imvGotoApp);
            }
            return super.onBindMany(viewHolder);
        }

    }


}
