package com.example.application.UI.Features;

import com.example.application.backend.entity.Case;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.CaseService;
import com.example.application.backend.service.UserService;
import com.example.application.UI.Principals.MainView;
import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@PageTitle("CHAT ROOM | Land Lawyers")
@Route(value = "chat" , layout = MainView.class)
@PermitAll
public class ChatView extends VerticalLayout {
    private final UserService userService;
    private final CaseService caseService;
    private UserInfo userInfo;

    public ChatView(UserService userService, CaseService caseService) {
        this.userService = userService;
        this.caseService = caseService;


        Image backgroundImage = new Image("images/Lawertracking.jpg", "Background Image");
        backgroundImage.setHeight("300px");
        backgroundImage.getStyle().set("margin-bottom", "30px");
        // backgroundImage.setWidth("1425px");
        //backgroundImage.getStyle().set("margin-top", "800px");
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("margin-bottom", "60px");

        backgroundImage.setWidth("100%");
        backgroundImage.getElement().getStyle().set("border", "none");
        backgroundImage.addClassName(LumoUtility.Margin.NONE);


        add(backgroundImage

        );
        setMargin(false);
        setPadding(false);
        // Adjusting spacing and alignment
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setWidthFull();


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        var userInfo = new UserInfo(username, username);

        User userLoggedIn = userService.findUserByUsername(username);
        Case userCase = caseService.findCaseByUser(userLoggedIn);
        CollaborationMessageList messageList = createChatLayout(userInfo);

        //var messageList = new CollaborationMessageList(userInfo, "chat-room");
        var messageInput = new CollaborationMessageInput(messageList);
        messageInput.getStyle().set("margin-top", "100px");
        messageInput.setWidthFull();
        CollaborationAvatarGroup avatarGroup = createAvatarGroup(userInfo);
        avatarGroup.getStyle().set("margin-top","8px") ;
        HorizontalLayout titleAndAvatarLayout = new HorizontalLayout();
        titleAndAvatarLayout.setAlignItems(Alignment.START);
        titleAndAvatarLayout.setJustifyContentMode(JustifyContentMode.START);// Align items vertically in the center
        titleAndAvatarLayout.setSpacing(false); // Add spacing between title and avatars
        H1 title = new H1("ChatRoom");
        title.getStyle().set("margin-right", "20px");
        title.setWidthFull();
        titleAndAvatarLayout.getStyle().set("margin-right","auto");
        titleAndAvatarLayout.add(
                 title,
                avatarGroup //  avatar group
        );

        add(
                titleAndAvatarLayout, // title and avatars in the same line
                messageList,
                messageInput
        );


    }

    public CollaborationMessageList createChatLayout(UserInfo userInfo) {
        CollaborationAvatarGroup avatars = new CollaborationAvatarGroup(
                userInfo, "chat");

        CollaborationMessageList list = new CollaborationMessageList(userInfo,
                "chat");


        VerticalLayout layout = new VerticalLayout();
        layout.add(avatars, list);
        layout.expand(list);
        layout.setSizeFull();
        return list;
    }

    public CollaborationAvatarGroup createAvatarGroup(UserInfo userInfo) {
        CollaborationAvatarGroup avatars = new CollaborationAvatarGroup(
                userInfo, "chat");
        return avatars;

    }
}