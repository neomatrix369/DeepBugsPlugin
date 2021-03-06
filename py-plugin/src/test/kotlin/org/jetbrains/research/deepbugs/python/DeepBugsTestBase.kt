package org.jetbrains.research.deepbugs.python

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.fixtures.impl.CodeInsightTestFixtureImpl
import org.jetbrains.research.deepbugs.common.DeepBugsPlugin
import org.jetbrains.research.deepbugs.python.ide.inspections.*
import java.io.File

abstract class DeepBugsTestBase : BasePlatformTestCase() {
    init {
        DeepBugsPlugin.setTestPlugin("DeepBugsPython")
    }

    override fun getTestDataPath(): String {
        return File("src/test/testData").canonicalPath
    }

    override fun setUp() {
        super.setUp()
        myFixture.enableInspections(*inspectionTools)
        (myFixture as? CodeInsightTestFixtureImpl)?.canChangeDocumentDuringHighlighting(true)
    }

    protected open fun runHighlightTestForFile(file: String) {
        myFixture.configureByFile(file)
        myFixture.checkHighlighting(true, false, false)
    }

    companion object {
        val inspectionTools by lazy {
            arrayOf(
                PyDeepBugsBinOperandInspection(),
                PyDeepBugsBinOperatorInspection(),
                PyDeepBugsSwappedArgsInspection()
            )
        }
    }
}
