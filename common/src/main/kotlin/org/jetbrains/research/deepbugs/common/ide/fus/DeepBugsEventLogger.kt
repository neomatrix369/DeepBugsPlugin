package org.jetbrains.research.deepbugs.common.ide.fus

import com.intellij.internal.statistic.eventLog.*

object DeepBugsEventLogger {
    private val loggerProvider: StatisticsEventLoggerProvider = getEventLogProvider("DBP")

    val version: Int = loggerProvider.version

    fun log(group: EventLogGroup, action: String) {
        return loggerProvider.logger.log(group, action, false)
    }

    fun log(group: EventLogGroup, action: String, data: FeatureUsageData) {
        return loggerProvider.logger.log(group, action, data.build(), false)
    }
}
