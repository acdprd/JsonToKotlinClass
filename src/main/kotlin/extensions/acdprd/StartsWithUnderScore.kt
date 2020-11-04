package extensions.acdprd

import com.intellij.ui.layout.panel
import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import javax.swing.JCheckBox
import javax.swing.JPanel

//test
object StartsWithUnderScore : Extension() {
    const val CONFIG_KEY = "acdprd.starts_with_underscore"
    const val UI_NAME = "Fields starts with underscore"

    override fun createUI(): JPanel {
        val checkBox = JCheckBox(UI_NAME).apply {
            isSelected = getConfig(CONFIG_KEY).toBoolean()
            addActionListener {
                setConfig(CONFIG_KEY, isSelected.toString())
            }
        }

        return panel { row { checkBox() } }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (getConfig(CONFIG_KEY).toBoolean() && kotlinClass is DataClass) {
            //make all properties name to be all upper case
            val newProperties = kotlinClass.properties.map { it.copy(name = "_${it.name}") }
            kotlinClass.copy(properties = newProperties)
        } else {
            kotlinClass
        }
    }

}