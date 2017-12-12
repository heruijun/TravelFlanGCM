package com.gcm.heruijun.base.ui.adapter

/**
 * Created by heruijun on 2017/12/11.
 */
interface BaseAdapter<T> {

    fun getItem(position: Int): T

    fun getItemViewType(position: Int): Int

    fun add(item: T)

}