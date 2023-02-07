package com.finance_tracker.finance_tracker.data.data_sources

import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import com.finance_tracker.finance_tracker.data.mappers.fullTransactionMapper
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.data.TransactionsEntityQueries
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionsPagingSource(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val accountId: Long? = null,
): PagingSource<Long, Transaction>() {

    override fun getRefreshKey(state: PagingState<Long, Transaction>): Long? = null

    override suspend fun load(params: PagingSourceLoadParams<Long>): PagingSourceLoadResult<Long, Transaction> {
        try {
            val nextPage = params.key ?: 0
            val transactionList = if (accountId == null) {
                getAllFullTransactionsPaginated(
                    page = nextPage,
                    limit = params.loadSize.toLong()
                )
            } else {
                getAllFullTransactionsPaginatedByAccountId(
                    page = nextPage,
                    limit = params.loadSize.toLong(),
                    id = accountId
                )
            }

            return PagingSourceLoadResultPage(
                data = transactionList,
                prevKey = if (nextPage == 0L) {
                    null
                } else {
                    nextPage - transactionList.size
                },
                nextKey = if (transactionList.isEmpty()) {
                    null
                } else {
                    nextPage + transactionList.size
                }
            )
        } catch (exception: IOException) {
            return PagingSourceLoadResultError(exception)
        }
    }

    private suspend fun getAllFullTransactionsPaginated(
        page: Long,
        limit: Long
    ): List<Transaction> {
        return withContext(Dispatchers.Default) {
            transactionsEntityQueries.getAllFullTransactionsPaginated(
                limit = limit,
                offset = page,
                mapper = fullTransactionMapper
            ).executeAsList()
        }
    }

    private suspend fun getAllFullTransactionsPaginatedByAccountId(
        page: Long,
        limit: Long,
        id: Long,
    ): List<Transaction> {
        return withContext(Dispatchers.Default) {
            transactionsEntityQueries.getFullTransactionsByAccountIdPaginated(
                limit = limit,
                offset = page,
                mapper = fullTransactionMapper,
                accountId = id,
            ).executeAsList()
        }
    }
}

class TransactionsPagingSourceFactory(
    private val transactionsEntityQueries: TransactionsEntityQueries
) {
    fun create(accountId: Long): com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSource {
        return com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSource(
            transactionsEntityQueries,
            accountId
        )
    }
}