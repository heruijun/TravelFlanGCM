package com.gcm.heruijun.base.ui.adapter;

import java.util.List;

/**
 * Created by heruijun on 2017/12/11.
 */
public interface BaseAdapter<T> {

    T getItem(int position);

    int getItemViewType(int position);

    void add(T item);

}