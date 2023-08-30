package com.example.application.ui;

import com.vaadin.collaborationengine.CollaborationMessage;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = "chat")
@AnonymousAllowed
public class ChatView extends VerticalLayout {

    public ChatView() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        var userInfo = new UserInfo(username, username);

        var messageList = new CollaborationMessageList(userInfo, "chat-room");
        var messageInput = new CollaborationMessageInput(messageList);

        add(
                new H1("Chat Room"),
                messageList,
                messageInput
        );

    }
}
