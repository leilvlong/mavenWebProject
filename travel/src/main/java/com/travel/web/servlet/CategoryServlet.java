package com.travel.web.servlet;

import com.travel.domain.Category;
import com.travel.domain.ResultInfo;
import com.travel.factory.BeanFactory;
import com.travel.service.ICategoryService;
import com.travel.web.handler.CategoryHanderl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    private ICategoryService cs = (ICategoryService) BeanFactory.getBean("categoryServiceImpl");

    private ICategoryService cs2 = (ICategoryService) Proxy.newProxyInstance(CategoryHanderl.class.getClassLoader(),
            new Class[]{ICategoryService.class},
            new CategoryHanderl((ICategoryService) BeanFactory.getBean("categoryServiceImpl")));


    private Object findIndexCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException {
        ResultInfo info = new ResultInfo();
        Class<?> tb = Category.class;
        Field[] fields = tb.getDeclaredFields();

        List<Category> categories = cs.selectAll();

        Workbook workbook ;
        String fileName;
        try {
            // 2007
            workbook = new XSSFWorkbook();
            fileName = "test.xlsx";
        } catch (Exception e) {
            //2003
            workbook = new HSSFWorkbook();
            fileName = "test.xls";
        }

        Sheet rows = workbook.createSheet();
        int rowCount = 0;
        for (Category tbBrand : categories) {
            int cellCount = 0;
            if (rowCount == 0){
                Row indexRow = rows.createRow(rowCount++);
                for (int i = 0; i < fields.length; i++) {
                    indexRow.createCell(cellCount).setCellValue(fields[cellCount++].getName());
                }
            }else{
                Row row = rows.createRow(rowCount++);
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object o = field.get(tbBrand);
                    row.createCell(cellCount++).setCellValue(String.valueOf(o));
                }
            }

        }
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=\""+new String(fileName.getBytes("gb2312"),"ISO8859-1"));

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        return info;

    }
}
