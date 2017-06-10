package concaro.hackernews.app.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barryzhang.temptyview.TViewUtil;
import com.dgreenhalgh.android.simpleitemdecoration.linear.EndOffsetItemDecoration;
import com.sromku.simple.fb.entities.User;

import javax.inject.Inject;

import butterknife.BindView;
import concaro.hackernews.app.R;
import concaro.hackernews.app.presentation.activity.HNListCommentActivity;
import concaro.hackernews.app.presentation.adapter.HNCommentAdapter;
import concaro.hackernews.app.presentation.internal.di.component.DaggerHackerCommentListComponent;
import concaro.hackernews.app.presentation.internal.di.component.HackerCommentListComponent;
import concaro.hackernews.app.presentation.model.HNComment;
import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.presenter.HackerCommentListPresenter;
import concaro.hackernews.app.presentation.util.Functions;
import concaro.hackernews.app.presentation.view.HackerCommentListView;
import io.realm.Realm;

/**
 * Created by Concaro on 12/5/2016.
 */

public class HackerCommentListFragment extends concaro.hackernews.app.presentation.fragment.BaseFragment implements HackerCommentListView, SwipeRefreshLayout.OnRefreshListener, HNCommentAdapter.IBindView {

    private static String TAG = "HackerCommentListFragment";

    @BindView(R.id.srl)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.tvTitle)
    protected TextView tvTitle;
    @BindView(R.id.tvLink)
    protected TextView tvLink;

    @BindView(R.id.tvAuthor)
    protected TextView tvAuthor;
    @BindView(R.id.tvComments)
    protected TextView tvComments;

//    private FastAdapter mFastAdapter;
//    GenericItemAdapter<HNComment, HNCommentItem> genericItemAdapter;
//    private FooterAdapter<ProgressItem> footerAdapter;
//    private ClickListenerHelper mClickListenerHelper;
//    EndlessRecyclerOnScrollListener listener;

    HackerCommentListComponent component;
    @Inject
    HackerCommentListPresenter presenter;
    @Inject
    Realm realm;

    HNCommentAdapter adapter;

    User curUser;
    boolean loading = true;
    HNStory story;

    int fake_id = 8863;

    public static HackerCommentListFragment newInstance() {
        HackerCommentListFragment fragment = new HackerCommentListFragment();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Functions.showLogMessage(TAG, "onAttach");
        super.onAttach(context);
        initInjector();
        initPresenter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    protected Integer getLayout() {
        return R.layout.fragment_hn_comment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        init();
    }

    private void initInjector() {
        this.component = DaggerHackerCommentListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.component.inject(this);
    }

    private void initPresenter() {
        this.presenter.setView(this);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            int story_id = bundle.getInt("story_id", -1);
            story = (HNStory) bundle.getSerializable("story");
            this.presenter.initData(story_id, story);
        }
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
        }
        initSwiperRefresh();
        initRecyclerView();
        initData();

        ((HNListCommentActivity) getActivity()).initToolbar((Toolbar) getActivity().findViewById(R.id.toolbar));

//        this.presenter.getItems().addChangeListener(addChangeListener);
        startLoad();
    }

    private void initData() {
        if (story != null) {
            tvTitle.setText(Html.fromHtml(story.getTitle()));
            if (!TextUtils.isEmpty(story.getBy())) {
                tvAuthor.setVisibility(View.VISIBLE);
                tvAuthor.setText(" - " + String.valueOf(story.getBy()));
            } else {
                tvAuthor.setVisibility(View.GONE);
            }
            if (story.getKids() != null) {
                tvComments.setText(story.getKids().size() + " comments");
            } else {
                tvComments.setText("0" + " comments");
            }
            if (story.getTime() > 0) {
                tvTime.setText(Functions.getAbbreviatedTimeSpan(story.getTime()));
            }
            tvLink.setText(Functions.getDomainName(story.getUrl()));
        }
    }

    public void startLoad() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                presenter.initCommentFirstTime();
            }
        });
    }


    private void initSwiperRefresh() {
        refreshLayout.setColorSchemeResources(R.color.color_primary,
                R.color.color_primary,
                R.color.color_primary,
                R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new EndOffsetItemDecoration(getResources().getDimensionPixelSize(R.dimen.item_padding_top)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (adapter == null) {
            adapter = new HNCommentAdapter(presenter.getItems(), getActivity(), HackerCommentListFragment.this);
            recyclerView.setAdapter(adapter);
        }

//        mFastAdapter = new FastAdapter<>();
//        footerAdapter = new FooterAdapter<>();
//
//        genericItemAdapter = new GenericItemAdapter<>(new Function<HNComment, HNCommentItem>() {
//            @Override
//            public HNCommentItem apply(HNComment item) {
//                return new HNCommentItem(item).initListener(HackerCommentListFragment.this);
//            }
//        });
//        recyclerView.setAdapter(footerAdapter.wrap(genericItemAdapter.wrap(mFastAdapter)));
//
//        mFastAdapter.withOnClickListener(new FastAdapter.OnClickListener<HNCommentItem>() {
//            @Override
//            public boolean onClick(View v, IAdapter<HNCommentItem> adapter, HNCommentItem item, int position) {
//                return false;
//            }
//        });


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
//                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
//                int pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//
//                if (loading && presenter.canLoadMore()) {
//                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                        Functions.showLogMessage(TAG, "addOnScrollListener");
//                        if (loading && presenter.canLoadMore()) {
//                            loading = false;
//                            footerAdapter.clear();
//                            footerAdapter.add(new ProgressItem().withEnabled(false));
//
//                            loadingMore();
//                        }
//                    }
//                }
//            }
//        });
//        genericItemAdapter.setModel(presenter.getItems());
    }

    private void refreshAdapterList() {
        refreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
        TViewUtil.EmptyViewBuilder.getInstance(getActivity())
                .bindView(recyclerView);
    }

    private void refreshItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onPause() {
        Functions.showLogMessage(TAG, "onPause");
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onResume() {
        Functions.showLogMessage(TAG, "onResume");
        super.onResume();
        presenter.resume();
    }


    @Override
    public void onDestroy() {
        Functions.showLogMessage(TAG, "onDestroy");
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
        hideProgressDialog();
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Functions.showSnackbarLongMessage(getView(), message);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public Context context() {
        return getActivity();
    }


    @Override
    public void onRefresh() {
//        listener.resetPageCount();
        presenter.refresh();
    }

    public void loadingMore() {
        presenter.loadingMore();
    }

    @Override
    public void returnData(HNComment data) {
        if (getActivity() != null) {
            refreshAdapterList();
        }
    }

    @Override
    public void updateItemChanged(HNComment data, int position) {
        if (getActivity() != null) {
//            refreshAdapterList();
            refreshItemChanged(position);
        }
    }

    @Override
    public void onBindData(int id, int level, int position) {
        if (level <= 5) {
            presenter.getCommentDetail(false, level, id, position);
        }
    }
}
