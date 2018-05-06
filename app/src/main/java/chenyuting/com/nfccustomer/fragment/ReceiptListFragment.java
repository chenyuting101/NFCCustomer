package chenyuting.com.nfccustomer.fragment;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chenyuting.com.nfccustomer.adapter.ReceiptListViewAdapter;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.MainActivity;
import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.fragment.ContentFragment;

/**
 * Created by chenyuting on 8/19/17.
 */

public class ReceiptListFragment extends Fragment implements AbsListView.OnScrollListener, ScreenShotable {
    public View loadmoreView;
    public LayoutInflater inflater;
    public ListView listView;
    public int last_index;
    public int total_index;
    public List<String> firstList = new ArrayList<String>();//表示首次加载的list
    public List<String> nextList = new ArrayList<String>();//表示出现刷新之后需要显示的list
    public boolean isLoading = false;//表示是否正处于加载状态
    public ReceiptListViewAdapter adapter;
    MainActivity.MyOnTouchListener myOnTouchListener;
    private final String TAG = "ReceiptListFragment";
    private View view;
    private View containerView;
    private Bitmap bitmap;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_receipt_list);
//        inflater = LayoutInflater.from(this);
//        loadmoreView = inflater.inflate(R.layout.load_more, null);//获得刷新视图
//        loadmoreView.setVisibility(View.VISIBLE);//设置刷新视图默认情况下是不可见的
//        listView = (ListView) findViewById(R.id.listView);
//        initList(10, 10);
//        adapter = new ListViewAdapter(this, firstList);
//        listView.setOnScrollListener(this);
//        listView.addFooterView(loadmoreView,null,false);
//        listView.setAdapter(adapter);
//    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(TAG, "onViewCreated");
            this.containerView = view.findViewById(R.id.container_receipt_list);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_receipt_list, container,
                false);
        this.view = view;
        listView = (ListView) view.findViewById(R.id.fragment_receipt_list_lv);
        inflater = LayoutInflater.from(getActivity());
        loadmoreView = inflater.inflate(R.layout.list_load_more, null);//获得刷新视图
        loadmoreView.setVisibility(View.VISIBLE);//设置刷新视图默认情况下是不可见的
        initList(20, 20);
        adapter = new ReceiptListViewAdapter(getActivity(), firstList);
        listView.setOnScrollListener(this);
        listView.addFooterView(loadmoreView,null,false);
        listView.setAdapter(adapter);

        return view;
    }
    /**
     * 初始化我们需要加载的数据
     * @param firstCount
     * @param nextCount
     */
    public void initList(int firstCount,int nextCount)
    {
        for(int i = 0;i < firstCount;i++)
        {
            firstList.add("第"+(i+1)+"个开始加载");
        }
        for(int i = 0;i < firstCount;i++)
        {
            nextList.add("第"+(i+1)+"个开始加载");
        }
        for(int i = 0;i < nextCount;i++)
        {
            nextList.add("刷新之后第"+(i+1)+"个开始加载");
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        last_index = firstVisibleItem+visibleItemCount;
        total_index = totalItemCount;
        System.out.println("last:  "+last_index);
        System.out.println("total:  "+total_index);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE))
        {
            //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
            if(!isLoading)
            {
                //不处于加载状态的话对其进行加载
                isLoading = true;
                //设置刷新界面可见
                loadmoreView.setVisibility(View.VISIBLE);
                onLoad();
            }
        }
    }

    /**
     * 刷新加载
     */
    public void onLoad()
    {
        try {
            //模拟耗时操作
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(adapter == null)
        {
            adapter = new ReceiptListViewAdapter(getActivity(), firstList);
            listView.setAdapter(adapter);
        }else
        {
            adapter.updateView(nextList);
        }
        loadComplete();//刷新结束
    }

    /**
     * 加载完成
     */
    public void loadComplete()
    {
        loadmoreView.setVisibility(View.GONE);//设置刷新界面不可见
        isLoading = false;//设置正在刷新标志位false
        listView.removeFooterView(loadmoreView);//如果是最后一页的话，则将其从ListView中移出
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ReceiptListFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

}
