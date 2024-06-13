package pl.programisci.task_crud.file.reader;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.programisci.task_crud.model.Genre;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileReader {
        private static final String GENRE_EXCEL_FILE = "C:\\Users\\patryk.kawula\\IdeaProjects\\task\\gatunek.xlsx";
        private static final String GENRE_EXCEL_SHEET = "Arkusz1";

        public static List<Genre> readXLSXFile() {
            try (XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(GENRE_EXCEL_FILE));) {
                Sheet sheet = work.getSheet(GENRE_EXCEL_SHEET);
                List<Genre> types = new ArrayList<>();

                for (Row row : sheet) {
                    if (!checkIfRowIsEmpty(row)) {
                        String type = row.getCell(0).getStringCellValue();
                        types.add(new Genre(type));
                    }
                }
                System.out.println(types);
                return types;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        public static boolean checkIfRowIsEmpty(Row row) {
            if (row == null) {
                return true;
            }
            if (row.getLastCellNum() <= 0) {
                return true;
            }
            for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                    return false;
                }
            }
            return true;
        }
    }
