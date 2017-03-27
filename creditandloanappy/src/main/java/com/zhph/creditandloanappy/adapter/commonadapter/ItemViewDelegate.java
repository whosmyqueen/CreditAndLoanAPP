package com.zhph.creditandloanappy.adapter.commonadapter;

/**
 * Created by 郑志辉 on 16/8/16.
 */
public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
