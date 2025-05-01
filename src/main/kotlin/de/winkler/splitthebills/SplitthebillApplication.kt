package de.winkler.splitthebills

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.theme.Theme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Theme(value = "my-app")
class SplitthebillApplication : AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<SplitthebillApplication>(*args)
}
