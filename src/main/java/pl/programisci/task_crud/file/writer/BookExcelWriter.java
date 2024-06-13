package pl.programisci.task_crud.file.writer;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.FullBookDto;
import pl.programisci.task_crud.dto.FullGenreDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class BookExcelWriter {
    private void createSheet(Workbook workbook, List<FullBookDto> booksDto, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 10000);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 10000);
        sheet.setColumnWidth(6, 10000);
        sheet.setColumnWidth(7, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("book_id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("isbn");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("author_id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("genre_id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("db_create_time");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("db|_modify_time");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("version");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < booksDto.size(); i++) {
            Row row = sheet.createRow(i + 1);
            FullBookDto bookDto = booksDto.get(i);

            Cell cell = row.createCell(0);
            cell.setCellValue(bookDto.bookId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(bookDto.name());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(bookDto.isbn());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(bookDto.authorId());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(bookDto.genreId());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(bookDto.dbCreateTime());
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(bookDto.dbUpdateTime());
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(bookDto.version());
            cell.setCellStyle(style);
        }
    }

    private void clearSheet(Workbook workBook, String sheetName) {
        Sheet sheet = workBook.getSheet(sheetName);
        Iterator<Row> rowIte = sheet.iterator();
        while (rowIte.hasNext()) {
            rowIte.next();
            rowIte.remove();
        }
    }

    public void saveExcelFile(Workbook workbook, List<FullBookDto> booksDto, String sheetName) {
        createSheet(workbook, booksDto, sheetName);
        clearSheet(workbook, sheetName);
//        String fileLocation = "C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\temp.xlsx";
//        try {
//            FileOutputStream outputStream = new FileOutputStream(fileLocation);
//            workbook.write(outputStream);
//            workbook.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
