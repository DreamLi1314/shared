package com.inetsoft.test.controller;

import com.inetsoft.test.util.SVGTransformer;
import com.inetsoft.test.util.SVGUtil;
import com.inetsoft.test.util.VSFaceUtil;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.util.XMLResourceDescriptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

@Controller
public class SVGController {

   @GetMapping("/png")
   void testPNG(HttpServletRequest request, HttpServletResponse response) throws Exception {
      // set header
      response.setContentType("image/png");
      response.setDateHeader("Expires", -1);
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Pragma", "no-cache");

      int edge = 6;
      BufferedImage out = VSFaceUtil.getShadow(contentSize, edge);

      ImageIO.write(out, "jpg", response.getOutputStream());
   }

   @GetMapping("/shadow")
   void testShadow(HttpServletRequest request, HttpServletResponse response) throws Exception {
      // set header
      response.setContentType("image/svg+xml");
      response.setDateHeader("Expires", -1);
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Pragma", "no-cache");

      // create graphics
      SVGGraphics2D g = SVGUtil.getSVGGraphics2D();
      setRenderingStratergy(g);
      g.setSVGCanvasSize(contentSize);

      // draw shadow
      int edge = 6;
      BufferedImage out = VSFaceUtil.getShadow(contentSize, edge);

      g.drawImage(out, 0, 0, null);
      g.setColor(Color.GRAY);
      g.fillRect(0, 0, out.getWidth(), out.getHeight());

      g.dispose();

      // save svg to byte[]
      byte[] buf = saveSVGToBytes(g);

      // write svg response
      writeSvgResponse(request, response, buf);
   }


   @GetMapping("/svg")
   void drawSVG(HttpServletRequest request, HttpServletResponse response) throws Exception {
      // set header
      response.setContentType("image/svg+xml");
      response.setDateHeader("Expires", -1);
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Pragma", "no-cache");

      // draw guage
      byte[] buf = drawGuage();

      // write svg response
      writeSvgResponse(request, response, buf);
   }

   private void writeSvgResponse(HttpServletRequest request, HttpServletResponse response, byte[] buf) throws IOException {
      final String encodingTypes = request.getHeader("Accept-Encoding");
      final ServletOutputStream outputStream = response.getOutputStream();

      try(final GZIPOutputStream out = new GZIPOutputStream(outputStream)) {
         response.setContentType("image/svg+xml");

         response.addHeader("Content-Encoding", "gzip");
         out.write(buf);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   private byte[] drawGuage() throws Exception {
      // create graphics
      SVGGraphics2D g = SVGUtil.getSVGGraphics2D();
      g.setSVGCanvasSize(contentSize);

      // get image
      Image img = createPanelImage();
      setRenderingStratergy(g);

      // draw pane image
      g.drawImage(img, 0, 0, null);
      g.dispose();

      // draw shadow
//      g = VSFaceUtil.addSVGShadow(g, 6);

      // save svg to byte[]
      byte[] buf = saveSVGToBytes(g);

      return buf;
   }

   private byte[] saveSVGToBytes(SVGGraphics2D g) throws SVGGraphics2DIOException {
      g.dispose();
      final ByteArrayOutputStream out2 = new ByteArrayOutputStream();
      final OutputStreamWriter writer = new OutputStreamWriter(out2, StandardCharsets.UTF_8);
      g.stream(writer, true);

      return out2.toByteArray();
   }

   protected void setRenderingStratergy(Graphics2D g) {
      // set rendering hints
      // commenting setRenderingStratergy will cause new bug
      RenderingHints hints = new RenderingHints(null);
      hints.put(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      hints.put(RenderingHints.KEY_DITHERING,
            RenderingHints.VALUE_DITHER_ENABLE);
      g.setRenderingHints(hints);
   }

   protected BufferedImage createPanelImage() throws Exception {
      String panelPath = "/static/svg/02_panel.svg";
      AffineTransform transform =
            AffineTransform.getScaleInstance(1, 1);
      BufferedImage image = getImageByURI(panelPath, transform);

      return image;
   }


   protected BufferedImage getImageByURI(String uri, AffineTransform transform) {
      if(transform == null) {
         transform = AffineTransform.getScaleInstance(1, 1);
      }

      try {
         SVGTransformer transformer = createTransformer(uri);

         synchronized(transformer) {
            transformer.setSize(contentSize);
            transformer.setTransform(transform);
            return transformer.getImage();
         }
      }
      catch(Exception ex) {
         throw new RuntimeException("Unable to find or load image: " + uri, ex);
      }
   }

   private SVGTransformer createTransformer(String uri) throws Exception {
      String parser = XMLResourceDescriptor.getXMLParserClassName();
      SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
      URL url = SVGController.class.getResource(uri);
      Document doc = factory.createDocument(getConvertURLString(url));
      return new SVGTransformer(doc);
   }

   protected static String getConvertURLString(URL url) {
      String urlString = url + "";
      int index = urlString.indexOf("zip:");

      // fix weblogic url
      if(index == 0) {
         urlString = "jar:file" + urlString.substring(3);
      }

      // fix websphere url
      if(urlString.startsWith("wsjar:")) {
         urlString = urlString.substring(2);
      }

      return urlString;
   }

   private Dimension contentSize = new Dimension(500, 500);

}
