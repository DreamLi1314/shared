package club.javafamily.utils;

import com.aspose.cells.*;
import com.aspose.words.*;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.*;

/**
 * @author Jack Li
 * @date 2023/5/23 下午1:55
 * @description
 */
public class FileConvertUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileConvertUtil.class);


    /**
     * Excel 转 PDF
     * @param in Excel stream
     * @return pdf stream
     */
    public static FastByteArrayOutputStream excelToPdf(InputStream in)
       throws Exception
    {
        // 导出PDF文件
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();

        excelToPdf(in, out);

        return out;
    }

    /**
     * Excel 转 PDF
     * @param in Excel stream
     * @param out pdf stream
     */
    public static boolean excelToPdf(InputStream in, OutputStream out)
       throws Exception
    {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getCellsLicense()) {
            return false;
        }

        long old = System.currentTimeMillis();

        Workbook doc = new Workbook(in);
        final Worksheet worksheet = doc.getWorksheets().get(0);
        worksheet.getPageSetup().customPaperSize(24, 15);
        // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,EPUB, XPS, SWF 相互转换
//        PdfSaveOptions saveOptions = new PdfSaveOptions();
//        saveOptions.setCompliance(PdfCompliance.PDF_A_1_B);
//
//        doc.save(out, saveOptions);
        doc.save(out, FileFormatType.PDF);
        long now = System.currentTimeMillis();

        // 转化用时
        logger.info("word转换pdf成功，共耗时：" + ((now - old) / 1000.0) + "秒");

        doc.dispose();

        return true;
    }

    /**
     * 获取 aspose 证书
     * @return boolean
     */
    private static boolean getCellsLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("license.xml");
            is = resource.getInputStream();
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 获取aspose证书
     * @return boolean
     */
    private static boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("license.xml");
            is = resource.getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * word转pdf静态方法
     * @param inPath word文件全路径含文件名
     * @param outPath pdf输出全路径含文件名
     * @return boolean
     */
    public static boolean docToPdf(String inPath, String outPath) throws Exception {
        try(FileInputStream in = new FileInputStream(inPath);
            FileOutputStream out = new FileOutputStream(outPath))
        {
            return docToPdf(in, out);
        }
    }

    /**
     * word转pdf静态方法
     * @param in word
     * @param out pdf
     * @return boolean
     */
    public static boolean docToPdf(InputStream in, OutputStream out) throws Exception {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return false;
        }

        long old = System.currentTimeMillis();

        Document doc = new Document(in);
        // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,EPUB, XPS, SWF 相互转换
        doc.save(out, SaveFormat.PDF);
        long now = System.currentTimeMillis();

        // 转化用时
        logger.info("word转换pdf成功，共耗时：" + ((now - old) / 1000.0) + "秒");

        return true;
    }
}
