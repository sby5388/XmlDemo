package com.by5388.demo.xml;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author Administrator  on 2021/4/15.
 */
public class BookAdapter extends BaseAdapter {

    private List<Book> mBooks;


    public BookAdapter() {
    }

    public void setBooks(List<Book> books) {
        mBooks = books;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mBooks == null) {
            return 0;
        }
        return mBooks.size();
    }

    @Override
    public Book getItem(int position) {
        if (mBooks == null || mBooks.isEmpty()) {
            return null;
        }
        return mBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bind(getItem(position));

        return convertView;
    }

    private static class ViewHolder {
        private final TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(android.R.id.text1);
        }

        private void bind(Book book) {
            if (book == null) {
                return;
            }
            mTextView.setText(book.mTitle);
        }
    }
}
