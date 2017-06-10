package concaro.hackernews.app.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barryzhang.temptyview.TViewUtil;
import com.dgreenhalgh.android.simpleitemdecoration.linear.EndOffsetItemDecoration;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.adapters.GenericItemAdapter;
import com.mikepenz.fastadapter.helpers.ClickListenerHelper;
import com.mikepenz.fastadapter.utils.Function;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.realm.Realm;
import concaro.hackernews.app.R;
import concaro.hackernews.app.presentation.activity.HNListStoryActivity;
import concaro.hackernews.app.presentation.adapter.HNStoryItem;
import concaro.hackernews.app.presentation.fragment.*;
import concaro.hackernews.app.presentation.internal.di.component.DaggerHackerNewListStoryComponent;
import concaro.hackernews.app.presentation.internal.di.component.HackerNewListStoryComponent;
import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.presenter.HackerNewListStoryPresenter;
import concaro.hackernews.app.presentation.util.Functions;
import concaro.hackernews.app.presentation.view.HackerNewListStoryView;

/**
 * Created by Concaro on 12/5/2016.
 */

public class HackerNewListStoryFragment extends concaro.hackernews.app.presentation.fragment.BaseFragment implements HackerNewListStoryView, SwipeRefreshLayout.OnRefreshListener {

    private static String TAG = "HackerNewListStoryFragment";

    @BindView(R.id.srl)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private FastAdapter mFastAdapter;
    GenericItemAdapter<HNStory, HNStoryItem> genericItemAdapter;
    private FooterAdapter<ProgressItem> footerAdapter;
    private ClickListenerHelper mClickListenerHelper;
    EndlessRecyclerOnScrollListener listener;

    HackerNewListStoryComponent component;
    @Inject
    HackerNewListStoryPresenter presenter;
    @Inject
    Realm realm;

    boolean loading = true;

    public static HackerNewListStoryFragment newInstance() {
        HackerNewListStoryFragment fragment = new HackerNewListStoryFragment();
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
        return R.layout.fragment_hn_stories;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        init();
    }

    private void initInjector() {
        this.component = DaggerHackerNewListStoryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.component.inject(this);
    }

    private void initPresenter() {
        this.presenter.setView(this);
        this.presenter.initData();
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
        }
        initSwiperRefresh();
        initRecyclerView();

        ((HNListStoryActivity) getActivity()).initToolbar((Toolbar) getActivity().findViewById(R.id.toolbar));

//        this.presenter.getItems().addChangeListener(addChangeListener);
        startLoad();
    }

    public void startLoad() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                presenter.getListStory(true);
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
        mFastAdapter = new FastAdapter<>();
        footerAdapter = new FooterAdapter<>();

        genericItemAdapter = new GenericItemAdapter<>(new Function<HNStory, HNStoryItem>() {
            @Override
            public HNStoryItem apply(HNStory item) {
                return new HNStoryItem(item);
            }
        });
        recyclerView.setAdapter(footerAdapter.wrap(genericItemAdapter.wrap(mFastAdapter)));

        mFastAdapter.withOnClickListener(new FastAdapter.OnClickListener<HNStoryItem>() {
            @Override
            public boolean onClick(View v, IAdapter<HNStoryItem> adapter, HNStoryItem item, int position) {
                navigator.navigateToHNListCommentActivity(getActivity(), item.getModel().getId(), item.getModel(), "");
                return false;
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (loading && presenter.canLoadMore()) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        Functions.showLogMessage(TAG, "addOnScrollListener");
                        if (loading && presenter.canLoadMore()) {
                            loading = false;
                            footerAdapter.clear();
                            footerAdapter.add(new ProgressItem().withEnabled(false));

                            loadingMore();
                        }
                    }
                }
            }
        });

        genericItemAdapter.setModel(presenter.getItems());
    }

    private void refreshAdapterList() {
        loading = true;
        footerAdapter.clear();
        refreshLayout.setRefreshing(false);
        genericItemAdapter.setModel(presenter.getItems());
        genericItemAdapter.notifyDataSetChanged();
        TViewUtil.EmptyViewBuilder.getInstance(getActivity())
                .bindView(recyclerView);
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
    public void returnData(List<HNStory> data) {
        refreshAdapterList();
    }
}
