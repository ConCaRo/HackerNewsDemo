package concaro.hackernews.app.presentation.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import concaro.hackernews.app.R;
import concaro.hackernews.app.presentation.model.HNComment;
import concaro.hackernews.app.presentation.util.Functions;

/**
 * Created by Concaro on 12/29/2015.
 */
public class HNCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    IBindView listener;

    public interface IBindView {
        void onBindData(int id, int level, int position);
    }

    List<View> headers = new ArrayList<>();
    List<View> footers = new ArrayList<>();
    public static final int TYPE_HEADER = 111;
    public static final int TYPE_FOOTER = 222;
    public static final int TYPE_ITEM = 333;

    private List<HNComment> items;
    private Activity context;
    private LayoutInflater mInflater;

    public HNCommentAdapter(List<HNComment> items, Activity context, IBindView listener) {
        this.context = context;
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        if (items == null) {
            throw new NullPointerException(
                    "items must not be null");
        }
        this.items = items;
    }

    public void clear() {
        if (items != null) {
            items.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.item_hn_comment,
                            parent,
                            false);
            return new ViewHolder(itemView);
        } else {
            //create a new framelayout, or inflate from a resource
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            //make sure it fills the space
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new HeaderFooterViewHolder(frameLayout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //check what type of view our position is
        if (position < headers.size()) {
            View v = headers.get(position);
            //add our view to a header view and display it
            bindHeaderFooter((HeaderFooterViewHolder) holder, v);
        } else if (position >= headers.size() + items.size()) {
            View v = footers.get(position - items.size() - headers.size());
            //add oru view to a footer view and display it
            bindHeaderFooter((HeaderFooterViewHolder) holder, v);
        } else {
            //it's one of our items, display as required
            bindHolder((ViewHolder) holder, position - headers.size());
        }
    }

    private void bindHeaderFooter(HeaderFooterViewHolder vh, View view) {
        //empty out our FrameLayout and replace with our header/footer
        vh.base.removeAllViews();
        vh.base.addView(view);
    }

    private void bindHolder(final ViewHolder viewHolder, final int position) {
        final HNComment item = getItem(position);
        // Load comment here
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.cardView.getLayoutParams();
        int left = context.getResources().getDimensionPixelSize(R.dimen.item_padding) * item.getLevel();
        layoutParams.setMargins(left, 0, 0, 0);
        viewHolder.cardView.setLayoutParams(layoutParams);

        if (item.isAvailable()) {
            if (!TextUtils.isEmpty(item.getText())) {
                viewHolder.tvMessage.setText(Html.fromHtml(item.getText()));
            }
            if (item.getTime() > 0) {
                viewHolder.tvTime.setText(Functions.getAbbreviatedTimeSpan(item.getTime()));
            }
            if (!TextUtils.isEmpty(item.getBy())) {
                viewHolder.tvAuthor.setVisibility(View.VISIBLE);
                viewHolder.tvAuthor.setText(" - " + String.valueOf(item.getBy()));
            } else {
                viewHolder.tvAuthor.setVisibility(View.GONE);
            }
        } else {
            viewHolder.tvMessage.setText("---------");
            viewHolder.tvTime.setText("-");
            viewHolder.tvAuthor.setText("-");
            if (listener != null) {
                listener.onBindData(item.getId(), item.getLevel(), viewHolder.getAdapterPosition());
            }
        }

    }


    @Override
    public int getItemCount() {
        return headers.size() + items.size() + footers.size();
    }

    public HNComment getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvAuthor)
        protected TextView tvAuthor;
        @BindView(R.id.tvMessage)
        protected TextView tvMessage;
        @BindView(R.id.cardView)
        protected CardView cardView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    final static class HeaderHolder extends RecyclerView.ViewHolder {
        //@Bind(R.id.header_merchant)
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            header = (TextView) itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //check what type our position is, based on the assumption that the order is headers > items > footers
        if (position < headers.size()) {
            return TYPE_HEADER;
        } else if (position >= headers.size() + items.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    //add a header to the adapter
    public void addHeader(View header) {
        if (header != null && !headers.contains(header)) {
            headers.add(header);
            //animate
            notifyItemInserted(headers.size() - 1);
        }
    }

    //remove a header from the adapter
    public void removeHeader(View header) {
        if (header != null && headers.contains(header)) {
            //animate
            notifyItemRemoved(headers.indexOf(header));
            headers.remove(header);
            if (header.getParent() != null) {
                ((ViewGroup) header.getParent()).removeView(header);
            }
        }
    }

    //add a footer to the adapter
    public void addFooter(View footer) {
        if (footer != null && !footers.contains(footer)) {
            footers.add(footer);
            //animate
            notifyItemInserted(headers.size() + items.size() + footers.size() - 1);
        }
    }

    //remove a footer from the adapter
    public void removeFooter(View footer) {
        if (footer != null && footers.contains(footer)) {
            //animate
            notifyItemRemoved(headers.size() + items.size() + footers.indexOf(footer));
            footers.remove(footer);
            if (footer.getParent() != null) {
                ((ViewGroup) footer.getParent()).removeView(footer);
            }
        }
    }

    //our header/footer RecyclerView.ViewHolder is just a FrameLayout
    public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        FrameLayout base;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            this.base = (FrameLayout) itemView;
        }
    }
}

