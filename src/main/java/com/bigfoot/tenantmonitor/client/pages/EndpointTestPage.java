package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.BackendService;
import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Route(value = "endpointTest", layout = BaseLayout.class)
public class EndpointTestPage extends VerticalLayout {

    private final BackendService backendService;

    public EndpointTestPage(BackendService backendService){

        var ref = new Object() {
            String propertyId;
        };
        this.backendService = backendService;
        H1 title = new H1("Endpoint test buttons");
        VerticalLayout propertiesList = new VerticalLayout();
        Button getProperties = new Button("Get properties");
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload singleFileUpload = new Upload(memoryBuffer);
        singleFileUpload.addSucceededListener(event -> {
            InputStream fileData = memoryBuffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
            Dialog dialog = new Dialog();
            dialog.setModal(true);
            backendService.uploadFile(fileData, fileName, contentLength, mimeType, ref.propertyId);
        });


        getProperties.addClickListener(e -> {
            try {
                propertiesList.removeAll();
                List<PropertyDTO> properties = backendService.getProperties();
                properties.forEach(p -> {
                    H2 h2 = new H2();
                    h2.setText(p.getId().toString());
                    Button uploadFileButton = new Button("Upload file");
                    uploadFileButton.addClickListener(buttonClickEvent -> {
                        Dialog dialog = new Dialog();
                        dialog.setModal(true);
                        dialog.add(singleFileUpload);
                        ref.propertyId = getPropertyId(propertiesList, buttonClickEvent.getSource());
                        dialog.open();
                    });

                    Button showFileButton = new Button("Show files");

                    showFileButton.addClickListener(buttonClickEvent -> {//show files...
                        VerticalLayout verticalImageMetadatas = new VerticalLayout();
                        String clickedPropertyId = getPropertyId(propertiesList, buttonClickEvent.getSource());
                        Optional<PropertyDTO> firstProperty = properties.stream().filter(property -> property.getId().equals(UUID.fromString(clickedPropertyId))).findFirst();
                        firstProperty.get().getFiles().forEach(file -> {
                            HorizontalLayout horizonalImageMetadatas = new HorizontalLayout();
                            Button deleteButton = new Button();
                            deleteButton.setIcon(new Icon(VaadinIcon.FILE_REMOVE));
                            deleteButton.addSingleClickListener(event -> {
                                HorizontalLayout source =  (HorizontalLayout) event.getSource().getParent().get();
                                H3 fileId = (H3) source.getComponentAt(2);
                                RestClient.ResponseSpec response = backendService.deleteFile(fileId.getText());
                                if(response.toBodilessEntity().getStatusCode().is2xxSuccessful()){
                                    Notification.show("File has been deleted!");
                                }
                            });

                            Button showButton = new Button();
                            showButton.setIcon(new Icon(VaadinIcon.FILE_PRESENTATION));
                            showButton.addSingleClickListener(event -> {
                                HorizontalLayout source =  (HorizontalLayout) event.getSource().getParent().get();
                                H3 fileId = (H3) source.getComponentAt(2);
                                ResponseEntity<Resource> fileResponse = backendService.getFile(fileId.getText());
                                if(fileResponse.getStatusCode().is2xxSuccessful()){
                                    Notification.show("Yeey");
                                    IFrame iframe= new IFrame();
                                    iframe.setSizeFull();
                                    StreamResource resource = new StreamResource(fileResponse.getHeaders().getFirst("file-name"), () -> {
                                        try {
                                            return new BufferedInputStream(fileResponse.getBody().getInputStream());
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    });
                                    StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
                                    iframe.setSrc(registration.getResourceUri().toString());
                                    Dialog dialog = new Dialog();
                                    dialog.setHeight("calc(100vh - (2*var(--lumo-space-m)))");
                                    dialog.setWidth("calc(100vw - (4*var(--lumo-space-m)))");
                                    dialog.add(iframe);
                                    dialog.open();
                                }
                            });

                            horizonalImageMetadatas.add(new H3(file.getFile_name()),
                                    new H3(file.getFile_type()),
                                    new H3(file.getFile_id().toString()),
                                    new H3(file.getFile_size().toString()),
                                    showButton,
                                    deleteButton);
                            verticalImageMetadatas.add(horizonalImageMetadatas);
                        });
                        Dialog dialog = new Dialog();
                        dialog.setModal(true);
                        dialog.add(verticalImageMetadatas);
                        dialog.open();
                    });
                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    horizontalLayout.add(h2, uploadFileButton, showFileButton);
                    propertiesList.add(horizontalLayout);
                });
            } catch (Exception exc){
                add(new H1(Arrays.toString(exc.getStackTrace())));
            }

        });


        add(title, getProperties, propertiesList);
    }

    private String getPropertyId(VerticalLayout propertiesList, Button source){
        HorizontalLayout horizontalLayout = (HorizontalLayout) source.getParent().get();
        int index = propertiesList.indexOf(horizontalLayout);
        HorizontalLayout componentAt = (HorizontalLayout)propertiesList.getComponentAt(index);
        H2 idText = (H2) componentAt.getComponentAt(0);
        return idText.getText();
    }
}
