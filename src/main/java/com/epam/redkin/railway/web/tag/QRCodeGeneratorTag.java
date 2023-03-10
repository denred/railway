package com.epam.redkin.railway.web.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRCodeGeneratorTag extends TagSupport {
    private String data;
    private int width;
    private int height;

    public void setData(String data) {
        this.data = data;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            ByteArrayOutputStream out = QRCode.from(data)
                    .withSize(width, height)
                    .to(ImageType.PNG)
                    .stream();

            // Get the application root path
            String appRoot = pageContext.getServletContext().getRealPath("/");
            String filePath = appRoot + "qrcode/" + data + ".png";
            System.out.println(filePath);

            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            File tempFile = new File(filePath);

            tempFile.deleteOnExit();
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(out.toByteArray())), "png", tempFile);


            // Set the file path as an attribute of the request
            pageContext.setAttribute("qrcodeFilePath", "qrcode/" + tempFile.getName());

            return EVAL_BODY_INCLUDE;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }


    @Override
    public int doEndTag() {
        pageContext.getRequest().removeAttribute("qrcodeFile");
        return EVAL_PAGE;
    }
}
