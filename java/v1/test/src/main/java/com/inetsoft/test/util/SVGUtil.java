package com.inetsoft.test.util;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.*;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.*;
import org.w3c.dom.svg.SVGDocument;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class SVGUtil {
   public static String svgNS = "http://www.w3.org/2000/svg";

   /**
    * Create and return an SVG document
    */
   public static SVGGraphics2D getSVGGraphics2D() {
      DOMImplementation impl = GenericDOMImplementation.getDOMImplementation();

      Document myFactory = impl.createDocument(svgNS, "svg", null);
      SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(myFactory);

      return new SVGGraphics2D(ctx, false);
   }


   public static SVGGraphics2D getSVGGraphics2DByUri(String uri) {
      try {
         String parser = XMLResourceDescriptor.getXMLParserClassName();
         SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);

         File file = new File(uri);
         InputStream in = new FileInputStream(file);

         SVGDocument doc = (SVGDocument) f.createDocument(parser, in);
         Element elSVG = doc.getRootElement();
         String width = elSVG.getAttribute("width");
         String height = elSVG.getAttribute("height");

         NodeList nodes = elSVG.getElementsByTagName("svg");
         Node node = nodes.item(0);
         Element el = null;

         if(Node.ELEMENT_NODE == node.getNodeType()) {
            el = (Element)node;
         }

         SVGGraphics2D g = new SVGGraphics2D(doc);
         g.setTopLevelGroup(el);

         g.setSVGCanvasSize(new Dimension(Integer.parseInt(width), Integer.parseInt(height)));

//         DOMImplementation impl = GenericDOMImplementation.getDOMImplementation();
//         Document doc = impl.createDocument(uri, "svg", null);

//         SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(doc);


//         SVGGraphics2D g = getSVGGraphics2D();

//         NodeList childNodes = doc.getElementsByTagName("svg").item(0).getChildNodes();
//
//         System.out.println("========childNodes=====" + childNodes);
//
//         for (int i = 0; i < childNodes.getLength(); i++) {
//            Node node = childNodes.item(i);
//
////            g.getDOMFactory().getElementsByTagName("svg").item(0).appendChild(node);
//            System.out.println("======node=====" + node.getNodeName() + "===g.getRoot()==" + g.getRoot().getNodeName());
//         }

         return g;

      } catch (IOException ex) {
         ex.printStackTrace();
      }

      return getSVGGraphics2D();
   }

   public static byte[] transcodeToSVG(Document doc) throws TranscoderException {

      try {
         //Determine output type:
         SVGTranscoder t = new SVGTranscoder();

         //Set transcoder input/output
         TranscoderInput input = new TranscoderInput(doc);
         ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
         OutputStreamWriter ostream = new OutputStreamWriter(bytestream, StandardCharsets.UTF_8);
         TranscoderOutput output = new TranscoderOutput(ostream);

         //Perform transcoding
         t.transcode(input, output);
         ostream.flush();

         return bytestream.toByteArray();

      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return null;
   }
}
