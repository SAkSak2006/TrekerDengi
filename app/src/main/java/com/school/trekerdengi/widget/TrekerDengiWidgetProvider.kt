package com.school.trekerdengi.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.school.trekerdengi.R

class TrekerDengiWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_trekerdengi)
        // Загрузи данные из SharedPreferences или Repository (placeholder)
        views.setTextViewText(R.id.tv_today_total, "Сегодня: 0 руб")
        views.setProgressBar(R.id.pb_progress, 100, 0, false)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}