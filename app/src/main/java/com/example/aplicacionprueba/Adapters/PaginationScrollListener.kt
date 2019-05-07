package com.example.aplicacionprueba.Adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener: RecyclerView.OnScrollListener(){

    lateinit var layoutManager : LinearLayoutManager

    fun PaginationScrollListener(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.getChildCount()
        val totalItemCount = layoutManager.getItemCount()
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= getTotalPageCount()
            ) {
                loadMoreItems()
            }
        }

    }

    fun loadMoreItems() {}
    fun getTotalPageCount(): Int {return 1}
    fun isLastPage(): Boolean {return true}
    fun isLoading(): Boolean {return true}
}



