package com.example.application.ui;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.example.application.backend.service.UploadFileServiceImpl;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Route("upload-image-to-database")
public class UploadImageToDatabase extends VerticalLayout {

    private byte[] uploadedImageData;
    private String originalFileName;
    private String mimeType;

    UploadImageToDatabase() {
        Upload upload = new Upload(this::receiveUpload);
        Div output = new Div(new Text("(no image file uploaded yet)"));
        add(upload, output);

        // Configure upload component
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> {
            output.removeAll();
            output.add(new Text("Uploaded: " + originalFileName + "Type: " + mimeType));
            output.add(new Image(new StreamResource(this.originalFileName, this::loadImage), "Uploaded image"));
        });
        upload.addFailedListener(event -> {
            output.removeAll();
            output.add(new Text("Upload failed: " + event.getReason()));
        });
    }

    public InputStream loadImage() {
        if (uploadedImageData != null) {
            return new ByteArrayInputStream(uploadedImageData);
        }
        return null;
    }

    public OutputStream receiveUpload(String originalFileName, String MIMEType) {
        this.originalFileName = originalFileName;
        this.mimeType = MIMEType;
        this.uploadedImageData = new byte[0]; // Initialize the image data array

        return new ByteArrayOutputStream() {
            @Override
            public void close() throws IOException {
                super.close();
                uploadedImageData = toByteArray();

                // You can now save the `uploadedImageData` to your database entity
                // and perform any necessary database operations
            }
        };
    }
}
