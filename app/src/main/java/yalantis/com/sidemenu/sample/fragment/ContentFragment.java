package yalantis.com.sidemenu.sample.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chenyuting.com.nfccustomer.fragment.ReceiptListFragment;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";
    public static final String BUILDING = "Building";
    public static final String BOOK = "Book";
    public static final String PAINT = "Paint";
    public static final String CASE = "Case";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";
    private static String TAG = "ContentFragment";
    private static String CONTENT_TYPE = "CONTENT_TYPE";
    private String contentType;

    private View containerView;
    protected ImageView mImageView;
    protected ListView mListView;
    protected int res;
    private Bitmap bitmap;

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    public static ContentFragment newInstance(String contentType, List data){
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_TYPE, contentType);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(TAG, "onViewCreated");
        if(this.contentType!=null){
            this.containerView = view.findViewById(R.id.container_receipt_list);

        }
        else this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreated");
        res = getArguments().getInt(Integer.class.getName());
        this.contentType = getArguments().getString(CONTENT_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if(this.contentType!=null){
            rootView = inflater.inflate(R.layout.fragment_receipt_list, container, false);
            mListView = (ListView) rootView.findViewById(R.id.fragment_receipt_list_lv);
            mListView.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_expandable_list_item_1,getData()));

        }
        else {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mImageView = (ImageView) rootView.findViewById(R.id.image_content);
            mImageView.setClickable(true);
            mImageView.setFocusable(true);
            mImageView.setImageResource(res);
        }
        return rootView;
    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
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
                ContentFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

