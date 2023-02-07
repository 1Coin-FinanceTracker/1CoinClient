package com.finance_tracker.finance_tracker.presentation.dashboard_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LazyDragColumn
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
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
            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
            LazyDragColumn(
                items = dashboardItems,
                contentPadding = PaddingValues(
                    top = DashboardWidgetsContentPadding,
                    start = DashboardWidgetsContentPadding,
                    end = DashboardWidgetsContentPadding,
                    bottom = DashboardWidgetsContentPadding + navigationBarsHeight
                ),
                onSwap = viewModel::onDashboardItemPositionChange,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                itemShape = { RoundedCornerShape(12.dp) }
            ) { _, item ->
                DashboardItem(
                    data = item,
                    onClick = { viewModel.onDashboardItemClick(item) }
                )
            }
        }
    }
}