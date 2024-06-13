package pl.programisci.task_crud.file.writer;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.FullGenreDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class GenreExcelWriter {

    private void createSheet(Workbook workbook, List<FullGenreDto> genresDto, String sheetname) {
        Sheet sheet = workbook.createSheet(sheetname);
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 10000);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 4000);

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
        headerCell.setCellValue("genre_id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("type");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("db_create_time");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("db_modify_time");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("version");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < genresDto.size(); i++) {
            Row row = sheet.createRow(i + 1);
            FullGenreDto genreDto = genresDto.get(i);

            Cell cell = row.createCell(0);
            cell.setCellValue(genreDto.genreId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(genreDto.type());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(genreDto.dbCreationDate().toString());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(genreDto.dbUpdateDate().toString());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(genreDto.version());
            cell.setCellStyle(style);
            }
        }

        private void clearSheet(Workbook workBook, String sheetname) {
            Sheet sheet = workBook.getSheet(sheetname);
            Iterator<Row> rowIte =  sheet.iterator();
            while(rowIte.hasNext()){
                rowIte.next();
                rowIte.remove();
            }
        }

    public void saveExcelFile(Workbook workbook, List<FullGenreDto> genresDto, String sheetname) {
        createSheet(workbook, genresDto, sheetname);
        clearSheet(workbook, sheetname);
//        String fileLocation = "C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\temp.xlsx";
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream(fileLocation);
//            workbook.write(outputStream);
//            workbook.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
