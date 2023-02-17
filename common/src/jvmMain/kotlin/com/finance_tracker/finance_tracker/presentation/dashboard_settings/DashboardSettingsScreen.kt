package com.finance_tracker.finance_tracker.presentation.dashboard_settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.ui.DraggableItem
import com.finance_tracker.finance_tracker.core.ui.dragContainerForDragHandle
import com.finance_tracker.finance_tracker.core.ui.rememberDragDropState
import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import com.finance_tracker.finance_tracker.presentation.dashboard_settings.views.DashboardItem
import com.finance_tracker.finance_tracker.presentation.dashboard_settings.views.DashboardSettingsAppBar

private val DashboardWidgetsContentPadding = 16.dp

@Composable
internal fun DashboardSettingsScreen() {
    StoredViewModel<DashboardSettingsViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action,
                baseLocalsStorage
            )
        }

        Column {
            DashboardSettingsAppBar(
                onBackClick = viewModel::onBackClick
            )

            val dashboardItems by viewModel.dashboardWidgets.collectAsState()
            DragDashboardWidgetColumn(
                widgets = dashboardItems,
                onClick = viewModel::onDashboardItemClick,
                onSwap = viewModel::onDashboardItemPositionChange
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DragDashboardWidgetColumn(
    widgets: List<DashboardWidgetData>,
    onClick: (DashboardWidgetData) -> Unit,
    onSwap: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight

    val list = widgets.toMutableStateList()

    val listState = rememberLazyListState()
    val dragDropState = rememberDragDropState(listState, list) { fromIndex, toIndex ->
        list.add(toIndex, list.removeAt(fromIndex))
        onSwap.invoke(fromIndex, toIndex)
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(
            top = DashboardWidgetsContentPadding,
            start = DashboardWidgetsContentPadding,
            end = DashboardWidgetsContentPadding,
            bottom = DashboardWidgetsContentPadding + navigationBarsHeight
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(list, key = { _, item -> item.id }) { index, widget ->
            DraggableItem(dragDropState, index) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
                Card(
                    elevation = elevation,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .dragContainerForDragHandle(dragDropState = dragDropState, key = widget.id)
                ) {
                    DashboardItem(
                        data = widget,
                        onClick = { onClick.invoke(widget) },
                    )
                }
            }
        }
    }
}