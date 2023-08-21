package com.example.application.ui;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@AnonymousAllowed
@Route(value = "DashNews")
public class DashNews  extends VerticalLayout {

    private NoticiaService noticiaService;

    private TextField title = new TextField("Title", "News Title");
    private DatePicker date = new DatePicker("Creation Date");

    private TextArea body = new TextArea("News Description");


    public DashNews(@Autowired NoticiaService noticiaService) {
        this.noticiaService = noticiaService;


        add(
                new H1("Dashboard news"),
                new FormLayout(title, date, body),
                new Button("save")
        );


    }
}


