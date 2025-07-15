package de.winkler.splitthebills.controller.ui.vaadin.views

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.SvgIcon
import com.vaadin.flow.component.orderedlayout.Scroller
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.sidenav.SideNav
import com.vaadin.flow.component.sidenav.SideNavItem
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Layout
import com.vaadin.flow.router.Location
import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vaadin.flow.server.menu.MenuConfiguration
import com.vaadin.flow.server.menu.MenuEntry
import com.vaadin.flow.spring.security.AuthenticationContext
import com.vaadin.flow.theme.lumo.LumoUtility
import org.springframework.boot.actuate.endpoint.web.Link
import org.w3c.dom.Text
import java.util.function.Consumer
import kotlin.math.log

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
class MainLayout(private val authContent: AuthenticationContext) : AppLayout() {
    private var viewTitle: H1? = null

    init {
        primarySection = Section.DRAWER
        addDrawerContent()
        addHeaderContent()
    }

    private fun addHeaderContent() {
        val toggle = DrawerToggle()
        toggle.setAriaLabel("Menu toggle")

        viewTitle = H1()
        viewTitle!!.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE)

        addToNavbar(true, toggle, viewTitle)
    }

    private fun addDrawerContent() {
        val appName = Span("Split the bills")
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE)
        val header = Header(appName)

        val scroller = Scroller(createNavigation())

        addToDrawer(header, scroller, createFooter())
    }

    private fun createNavigation(): SideNav {
        val nav = SideNav()

        val menuEntries = MenuConfiguration.getMenuEntries()
        menuEntries.forEach(Consumer { entry: MenuEntry ->
            if (entry.icon() != null) {
                nav.addItem(SideNavItem(entry.title(), entry.path(), SvgIcon(entry.icon())))
            } else {
                nav.addItem(SideNavItem(entry.title(), entry.path()))
            }
        })

        return nav
    }

    private fun createFooter(): Footer {
        val footer = Footer()
        val layout = VerticalLayout()
        footer.add(layout)
        if (authContent.principalName.isPresent) {
            val name = Paragraph(authContent.principalName.get())
            val logout = Button("Logout")
            logout.addClickListener {
                    ui.get().page.setLocation("/logout")
            }
            layout.add(name)
            layout.add(logout)
            layout.add(Anchor("https://www.google.com", "Google"))
        }
        return footer
    }

    override fun afterNavigation() {
        super.afterNavigation()
        viewTitle!!.text = currentPageTitle
    }

    private val currentPageTitle: String
        get() = MenuConfiguration.getPageHeader(content).orElse("")
}