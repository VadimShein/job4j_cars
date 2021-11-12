package ru.job4j.cars.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.cars.model.Item;
import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UploadServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UploadServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("c:\\images\\");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    name = item.getFieldName();
                    File file = new File(folder + File.separator + item.getFieldName() + ".JPG");
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            LOG.error(e.getMessage(), e);
        }
        Item item = PsqlStore.instOf().findItemById(Integer.parseInt(name));
        item.setPhoto(name + ".JPG");
        PsqlStore.instOf().updateItem(item);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}