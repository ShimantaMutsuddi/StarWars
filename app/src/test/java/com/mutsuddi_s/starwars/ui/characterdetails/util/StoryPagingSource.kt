package com.mutsuddi_s.starwars.ui.characterdetails.util

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mutsuddi_s.starwars.data.model.people.Character


class CharacterPagingSource : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> =
        LoadResult.Page(emptyList(), 0, 1)

    companion object {
        fun snapshot(items: List<Character>): PagingData<Character> {
            return PagingData.from(items)
        }
    }
}