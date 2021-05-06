package com.by5388.demo.xml;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author Administrator  on 2021/4/15.
 */
public abstract class BaseXmlFragment extends Fragment {
    public static final String TAG = "BaseXmlFragment";
    public static final String XML_URL = "https://www.w3school.com.cn/example/xdom/books.xml";
    public static final String LOCAL_XML_FILE_NAME = "books.xml";
    private BookAdapter mBookAdapter = new BookAdapter();

    private XmlHelper mXmlHelper;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    protected XmlHelper.ResultCallback mResultCallback = new XmlHelper.ResultCallback() {
        @Override
        public void onSuccess(BookStore bookStore) {
            Log.d(TAG, "onSuccess: bookStore = " + bookStore);
            final boolean mainThread = isMainThread();
            final List<Book> bookList = bookStore.mBookList;
            if (bookList != null) {
                if (mainThread) {
                    mBookAdapter.setBooks(bookList);
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mBookAdapter.setBooks(bookList);
                        }
                    });
                }
            }
        }

        @Override
        public void onError(final String error) {
            final boolean mainThread = isMainThread();
            if (mainThread) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        private boolean isMainThread() {
            return Looper.myLooper() == Looper.getMainLooper();
        }
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run: " + Thread.currentThread().getName());
            mXmlHelper.queryXml(requireContext(), LOCAL_XML_FILE_NAME, mResultCallback);
        }
    };

    protected abstract XmlHelper createXmlHelper();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mXmlHelper = createXmlHelper();
    }

    protected void queryXml() {
        final XmlApplication application = XmlApplication.getApplication();
        final Handler workHandler = application.getWorkHandler();
        workHandler.post(mRunnable);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_xml_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = view.findViewById(android.R.id.list);
        final TextView empty = view.findViewById(android.R.id.empty);
        final CharSequence title = requireActivity().getTitle();
        if (title != null) {
            empty.setText(title);
        }
        listView.setEmptyView(empty);
        listView.setAdapter(mBookAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Book item = mBookAdapter.getItem(position);
                Toast.makeText(requireContext(), item.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queryXml();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        final XmlApplication application = XmlApplication.getApplication();
//        final Handler workHandler = application.getWorkHandler();
//        workHandler.removeCallbacks(mRunnable);
        mHandler.removeCallbacksAndMessages(null);
    }
}
