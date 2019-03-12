/*
 * Copyright (c) 1996-2005, InetSoft Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to InetSoft Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package com.inetsoft.test.util;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.*;
import java.util.List;

/**
 * VSFaceUtil is a utility class for viewsheet face object.
 *
 * @version 8.5, 07/26/2006
 * @author InetSoft Technology Corp
 */
public class VSFaceUtil {

   public static BufferedImage getShadow(Dimension paneSize, int edge) {
      BufferedImage out = new BufferedImage(paneSize.width + edge,
            paneSize.height + edge,
            BufferedImage.TYPE_INT_ARGB);
      BufferedImage shadow = createDropShadowSVG(paneSize, edge / 3);

      Graphics g2 = out.getGraphics();
      g2.drawImage(shadow, 0, 0, null);
      g2.dispose();

      return out;
   }

   /**
    * Add drop shadow to the svg.
    */
   public static SVGGraphics2D addSVGShadow(SVGGraphics2D g, int edge) {
      int w = g.getSVGCanvasSize().width;
      int h = g.getSVGCanvasSize().height;
      Dimension dim = new Dimension(w, h);

      BufferedImage out = getShadow(dim, edge);

      g.drawImage(out, 0, 0, null);
      g.dispose();

      return g;
   }

   /**
    * Add drop shadow to the image.
    */
   public static BufferedImage addShadow(BufferedImage img, int edge) {
      int w = img.getWidth();
      int h = img.getHeight();

      BufferedImage out = new BufferedImage(w + edge, h + edge,
                                            BufferedImage.TYPE_INT_ARGB);
      BufferedImage shadow = createDropShadow(img, edge / 3);

      Graphics g = out.getGraphics();
      g.drawImage(shadow, 0, 0, null);
      g.drawImage(img, 0, 0, null);
      g.dispose();

      return out;
   }

   /**
    * Create a shadow of the svg.
    */
   public static BufferedImage createDropShadowSVG(Dimension imgSize, int size) {
      BufferedImage shadow = new BufferedImage(
            imgSize.width + 4 * size,
            imgSize.height + 4 * size,
            BufferedImage.TYPE_INT_ARGB);

      Graphics2D g2 = shadow.createGraphics();
//      g2.drawImage(image, size * 2, size * 2, null);

      g2.setComposite(AlphaComposite.SrcIn);
      g2.setColor(Color.GRAY);
      g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());
      g2.dispose();

      shadow = getGaussianBlurFilter(size, true).filter(shadow, null);
      shadow = getGaussianBlurFilter(size, false).filter(shadow, null);

      return shadow;
   }

   /**
    * Create a shadow of the image.
    */
   private static BufferedImage createDropShadow(BufferedImage image,
                                                 int size) 
   {
      BufferedImage shadow = new BufferedImage(
         image.getWidth() + 4 * size,
         image.getHeight() + 4 * size,
         BufferedImage.TYPE_INT_ARGB);
        
      Graphics2D g2 = shadow.createGraphics();
      g2.drawImage(image, size * 2, size * 2, null);
        
      g2.setComposite(AlphaComposite.SrcIn);
      g2.setColor(Color.GRAY);
      g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());       
      g2.dispose();

      shadow = getGaussianBlurFilter(size, true).filter(shadow, null);
      shadow = getGaussianBlurFilter(size, false).filter(shadow, null);
      
      return shadow;
   }
    
   private static ConvolveOp getGaussianBlurFilter(int radius,
                                                   boolean horizontal) 
   {
      if(radius < 1) {
         throw new IllegalArgumentException("Radius must be >= 1");
      }
      
      int size = radius * 2 + 1;
      float[] data = new float[size];
      
      float sigma = radius / 3.0f;
      float twoSigmaSquare = 2.0f * sigma * sigma;
      float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
      float total = 0.0f;
      
      for(int i = -radius; i <= radius; i++) {
         float distance = i * i;
         int index = i + radius;
         data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
         total += data[index];
      }
      
      for(int i = 0; i < data.length; i++) {
         data[i] /= total;
      }        
      
      Kernel kernel = null;

      if(horizontal) {
         kernel = new Kernel(size, 1, data);
      }
      else {
         kernel = new Kernel(1, size, data);
      }

      return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
   }
}
