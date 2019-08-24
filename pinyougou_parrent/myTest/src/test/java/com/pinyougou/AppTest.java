package com.pinyougou;

import static org.junit.Assert.assertTrue;

import com.pingyougou.mapper.TbBrandMapper;
import com.pingyougou.pojos.TbBrand;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(locations = "classpath:spring/applicationContext-dao.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Test
    public void shouldAnswerWithTrue() throws IOException, IllegalAccessException {
        Class<?> tb = TbBrand.class;
        Field[] fields = tb.getDeclaredFields();

        List<TbBrand> tbBrands = tbBrandMapper.selectAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet rows = workbook.createSheet();
        int rowCount = 0;
        for (TbBrand tbBrand : tbBrands) {
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
        FileOutputStream os = new FileOutputStream(new File("F:\\project_java\\mavenProject\\pinyougou_parrent\\myTest\\src\\excel\\text.xlsx"));
        workbook.write(os);
    }

    @Test
    public void importExcel() throws IOException, InvalidFormatException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> type = TbBrand.class;
        Field[] fields = type.getDeclaredFields();
        Method[] methods = type.getMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            if (method.getName().matches("set\\w+")){
                methodMap.put(method.getName().replace("set","" ).toLowerCase(), method);
            };
        }

        Workbook workbook;

        try {
            System.out.println("2003");
            workbook = new HSSFWorkbook(POIFSFileSystem.create(new File("F:\\project_java\\mavenProject\\pinyougou_parrent\\myTest\\src\\excel\\text.xlsx")));
        } catch (Exception e) {
            System.out.println("2007");
            workbook = new XSSFWorkbook(new File("F:\\project_java\\mavenProject\\pinyougou_parrent\\myTest\\src\\excel\\text.xlsx"));
        }
        Sheet sheet = workbook.getSheetAt(0);
        ArrayList<String> excelFiledList = new ArrayList<>();
        List<TbBrand> brands = new ArrayList<>();
        for (Row cells : sheet) {
            int cellNum = cells.getLastCellNum();
            if (cells.getRowNum() == 0){
                for (int i = 0; i < cellNum; i++) {
                    String value = cells.getCell(i).getStringCellValue();
                    excelFiledList.add(value);
                }
            }else{
                Object o = type.newInstance();
                for (int i = 0; i < cellNum; i++) {
                    String value = cells.getCell(i).getStringCellValue();
                    String filedName = excelFiledList.get(i);
                    System.out.println(filedName);
                    Method method = methodMap.get(filedName.toLowerCase());
                    Class<?> parameterType = method.getParameterTypes()[0];
                    method.invoke(o,parameterType.getConstructor(String.class).newInstance(value));
                }
                brands.add((TbBrand) o);
            }
        }
        System.out.println(brands);

    }


}
