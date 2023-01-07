package org.example.yiji.dolphin.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @Description:
 * @Author wanglin
 * @Date 2021/1/20 16:17
 */
public class VerifyUtil {

    // 验证码字符集
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    // 验证码字符集
    private static final char[] numberChars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    // 字符数量
    private static final int SIZE = 4;
    // 数字数量
    private static final int NUMBER_SIZE = 6;
    // 干扰线数量
    private static final int LINES = 5;
    // 宽度
    private static final int WIDTH = 120;
    // 高度
    private static final int HEIGHT = 40;
    // 字体大小
    private static final int FONT_SIZE = 30;

    /**
     * 生成随机验证码及图片
     * Object[0]：验证码字符串；
     * Object[1]：验证码图片。
     */
    public static Object[] createImage() {
        return createImage(SIZE);
    }
    public static Object[] createImage(int size) {
        StringBuffer sb = new StringBuffer();
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics graphic = image.getGraphics();
        // 3.设置画笔颜色
        graphic.setColor(Color.LIGHT_GRAY);
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        // 5.画随机字符
        Random ran = new Random();
        for (int i = 0; i <size; i++) {
            // 取随机字符索引
            int n = ran.nextInt(chars.length);
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 设置字体大小
            graphic.setFont(new Font(
                    null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            // 画字符
            graphic.drawString(
                    chars[n] + "", i * WIDTH / SIZE, HEIGHT*2/3);
            // 记录字符
            sb.append(chars[n]);
        }
        // 6.画干扰线
        for (int i = 0; i < LINES; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),
                    ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }
        // 7.返回验证码和图片base64
        return new Object[]{sb.toString(), BufferedImageToBase64(image)};
    }

    /**
     * 生成随机验证码
     */
    public static String createCode() {
        return createCode(SIZE);
    }
    public static String createCode(int size) {
        StringBuffer sb = new StringBuffer();
        Random ran = new Random();
        for (int i = 0; i <size; i++) {
            // 取随机字符索引
            int n = ran.nextInt(chars.length);
            // 记录字符
            sb.append(chars[n]);
        }
        // 7.返回验证码和图片base64
        return sb.toString();
    }

    /**
     * 生成随机数字验证码
     */
    public static String createNumber() {
        return createNumber(NUMBER_SIZE);
    }
    public static String createNumber(int size) {
        StringBuffer sb = new StringBuffer();
        Random ran = new Random();
        for (int i = 0; i <size; i++) {
            // 取随机字符索引
            int n = ran.nextInt(numberChars.length);
            // 记录字符
            sb.append(numberChars[n]);
        }
        // 7.返回验证码和图片base64
        return sb.toString();
    }

    /**
     * 随机取色
     */
    public static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
        return color;
    }


    /**
     * BufferedImage 编码转换为 base64
     * @param bufferedImage
     * @return
     */
    private static String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
//        System.out.println("值为：" + "data:image/jpg;base64," + png_base64);
        return "data:image/jpg;base64," + png_base64;
    }


}
