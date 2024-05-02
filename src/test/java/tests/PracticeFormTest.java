package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.*;

public class PracticeFormTest {

    private final ClassLoader cl = PracticeFormTest.class.getClassLoader();

    @Test
    void zipFileShouldContainFilesTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("downloads.zip")
        )) {
            ZipEntry entry;
            List<String> zipFilesActualList = new ArrayList<>();
            List<String> zipFilesExpectedList = new ArrayList<>();
            zipFilesExpectedList.add("file-sample_150kB.pdf");
            zipFilesExpectedList.add("file_example_CSV_5000.csv");
            zipFilesExpectedList.add("file_example_XLS_50.xls");

            while ((entry = zis.getNextEntry()) != null) {
                zipFilesActualList.add(entry.getName());
            }

            assertThat(zipFilesActualList).isEqualTo(zipFilesExpectedList);
        }
    }

    @Test
    void zipFileXlsxTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("downloads.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".xls")) {
                    XLS xls = new XLS(zis);
                    String actualFirstName = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    String actualLastName = xls.excel.getSheetAt(0).getRow(1).getCell(2).getStringCellValue();

                    assertThat(actualFirstName).isEqualTo("Dulce");
                    assertThat(actualLastName).isEqualTo("Abril");
                }
            }
        }
    }

    @Test
    void zipFileCsvTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("downloads.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    CSVReader csvr = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvr.readAll();

                    assertThat(new String[]{"", "First Name", "Last Name", "Gender", "Country", "Age", "Date", "Id"}).isEqualTo(data.get(0));
                    assertThat(new String[]{"1", "Dulce", "Abril", "Female", "United States", "32", "15/10/2017", "1562"}).isEqualTo(data.get(1));
                }
            }
        }
    }

    @Test
    void zipFilePdfTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("downloads.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    PDF pdf = new PDF(zis);

                    assertThat(pdf.text).contains("Lorem ipsum dolor sit amet, consectetur adipiscing");
                    assertThat(pdf.numberOfPages).isEqualTo(4);
                }
            }
        }
    }
}