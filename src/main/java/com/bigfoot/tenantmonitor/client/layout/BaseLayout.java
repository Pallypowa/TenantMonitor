package com.bigfoot.tenantmonitor.client.layout;

import com.bigfoot.tenantmonitor.client.NavBar;
import com.bigfoot.tenantmonitor.client.pages.LoginCard;
import com.bigfoot.tenantmonitor.client.pages.SignUpCard;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.RouterLayout;

public class BaseLayout extends AppLayout implements RouterLayout {
    private final LoginCard loginCard;
    private final SignUpCard signUpCard;

    public BaseLayout(LoginCard loginCard, SignUpCard signUpCard) {
        this.loginCard = loginCard;
        this.signUpCard = signUpCard;
        NavBar navBar = new NavBar(loginCard, signUpCard);
        addToNavbar(navBar);
    }
}
