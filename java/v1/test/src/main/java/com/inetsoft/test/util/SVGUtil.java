package com.inetsoft.test.util;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.*;
import org.w3c.dom.*;

public class SVGUtil {
   /**
    * Create and return an SVG document
    */
   public static SVGGraphics2D getSVGGraphics2D() {
      DOMImplementation impl = GenericDOMImplementation.getDOMImplementation();
      String svgNS = "http://www.w3.org/2000/svg";
      Document myFactory = impl.createDocument(svgNS, "svg", null);
      SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(myFactory);
      return new SVGGraphics2D(ctx, false);
   }
}
