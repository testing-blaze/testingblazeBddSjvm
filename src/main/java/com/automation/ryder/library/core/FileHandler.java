package com.automation.ryder.library.core;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.reports.LogLevel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public final class FileHandler {
    private ExcelReader excelReader;
    private JsonReader jsonReader;
    private AdobeReader adobeReader;
    private Docs docs;
    private WebDriver driver;

    public FileHandler(BrowsingDeviceBucket device) {
        this.excelReader = new ExcelReader();
        this.jsonReader = new JsonReader();
        this.adobeReader = new AdobeReader();
        this.docs = new Docs();
        this.driver = device.getDriver();
    }

    /**
     * perform various functions on Excel files
     *
     * @return
     * @author nauman.shahid
     */
    public ExcelReader forExcelAnd() {
        return this.excelReader;
    }

    /**
     * perform various functions on Doc files
     *
     * @return
     * @author nauman.shahid
     */
    public Docs forDocumentsAnd() {
        return this.docs;
    }

    /**
     * perform various functions on Json files
     *
     * @return
     * @author nauman.shahid
     */
    public JsonReader forJsonAnd() {
        return this.jsonReader;
    }

    /**
     * Perform various operations onn adob files
     *
     * @return
     * @author nauman.shahid
     */
    public AdobeReader forAdobeReaderAnd() {
        return this.adobeReader;
    }

    /**
     * Provide complete file path with fileName and extension. send as absolute path
     *
     * @param filePathWithFileName
     * @return String Array list of lines
     * @author nauman.shahid
     */
    public List<String> toReadAnyFile(String filePathWithFileName) {
        List<String> fileData = null;
        try {
            fileData = Files.readAllLines(Paths.get(filePathWithFileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileData;
    }

    /**
     * get list of all files from a local directory
     *
     * @param path
     * @return directory
     * @author nauman.shahid
     */
    public File[] toGetCompleteFilesListOnLocalDirectory(String path) {
        File directory = new File(path);
        return directory.listFiles();
    }

    /**
     * rename any downloaded file with .tmp extension
     *
     * @param file       directory path
     * @param fileName   new name of file
     * @param toFileType new extension of file
     * @author nauman.shahid
     */
    public void toRenameDownloadedTmpFile(File file, String fileName, String toFileType) {
        toRenameFileWithSpecificExtension(file, fileName, "tmp", toFileType);
    }

    /**
     * rename any downloaded file with .tmp extension
     *
     * @param file     directory path
     * @param fileName new name of file
     * @param fileType new extension of file
     * @author nauman.shahid
     */

    /**
     * rename a specific file with an extension
     *
     * @param filePath
     * @param fileName     name of file without extension
     * @param fromFileType
     * @param toFileType
     * @author nauman.shahid
     */
    public void toRenameFileWithSpecificExtension(File filePath, String fileName, String fromFileType, String toFileType) {
        File[] directory = toGetCompleteFilesListOnLocalDirectory(filePath.getAbsolutePath());
        for (File files : directory) {
            if (files.getName().endsWith("." + fromFileType)) {
                new File(files.getAbsolutePath())
                        .renameTo(new File(filePath.getAbsolutePath() + File.separator + fileName + "." + toFileType));
                break;
            }
        }
    }

    /**
     * rename a specific file in a folder
     *
     * @param filePath
     * @param fromFileName with extension
     * @param toFileName   with extension
     * @author nauman.shahid
     */
    public void toRenameFile(File filePath, String fromFileName, String toFileName) {
        File[] directory = toGetCompleteFilesListOnLocalDirectory(filePath.getAbsolutePath());
        for (File files : directory) {
            if (files.getName().equalsIgnoreCase((fromFileName))) {
                new File(files.getAbsolutePath())
                        .renameTo(new File(filePath.getAbsolutePath() + File.pathSeparatorChar + toFileName));
                break;
            }
        }
    }

    /**
     * create any file
     *
     * @param pathWithFileName path with filename and extension /abc/file.txt
     * @author nauman.shahid
     */
    public void toCreateFile(String pathWithFileName) {
        try {
            if (Files.notExists(Paths.get(pathWithFileName))) ;
            Files.createFile(Paths.get(pathWithFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write to an existing or new file
     *
     * @param pathWithFileName path with filename and extension /abc/file.txt
     * @param dataToWrite
     * @author nauman.shahid
     */
    public void toWriteInFile(String pathWithFileName, String dataToWrite) {
        try {
            if (Files.notExists(Paths.get(pathWithFileName)))
                Files.write(Paths.get(pathWithFileName), dataToWrite.getBytes());
            else
                Files.write(Paths.get(pathWithFileName), dataToWrite.getBytes(), StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * create a new folder
     *
     * @param pathOfDirectoryName path with filename and extension /abc/folderName
     * @author nauman.shahid
     */
    public void toCreateDirectory(String pathOfDirectoryName) {
        if (Files.notExists(Paths.get(pathOfDirectoryName))) {
            try {
                Files.createDirectory(Paths.get(pathOfDirectoryName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Delete any file in provide directory
     *
     * @param fileNameWithExtensionAndPath
     * @author jitendra.pisal
     */
    public void toDeleteFile(String fileNameWithExtensionAndPath) {
        File file = new File(fileNameWithExtensionAndPath);
        file.delete();
    }

    /**
     * get the input stream of file
     *
     * @param fileName Name of the file Or path of the file
     * @return InputStream
     * @author jitendra.pisal
     */
    public InputStream getResourceAsStream(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    /**
     * Handles all methods related to Doc files
     *
     * @author nauman.shahid
     */
    public final class Docs {

        /**
         * return content of docx file in string format.
         *
         * @param fileName
         * @return
         */
        private String getContentFromDocxFile(String fileName) {
            InputStream is = getResourceAsStream(fileName);
            HWPFDocument doc = null;
            WordExtractor wordExtractor = null;
            try {
                doc = new HWPFDocument(is);
                wordExtractor = new WordExtractor(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wordExtractor.getText();
        }

        /**
         * Enter Filename with extension and sheet name. Place file in resources folder
         * of project.
         *
         * @param fileName
         * @return String array of data
         * @author nauman.shahid
         */
        public List<XWPFParagraph> readDocFile(String fileName) {
            InputStream is = getResourceAsStream(fileName);
            XWPFDocument doc = null;
            try {
                doc = new XWPFDocument(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc.getParagraphs();
        }

        /**
         * This method returns the content from docx file in string format.
         *
         * @param fileName
         * @return content in docx file.
         * @author jitendra.pisal
         */
        public String getContentFromDocFile(String fileName) {
            InputStream is = getResourceAsStream(fileName);
            XWPFDocument doc = null;
            XWPFWordExtractor wordExtractor = null;
            try {
                doc = new XWPFDocument(is);
                wordExtractor = new XWPFWordExtractor(doc);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OLE2NotOfficeXmlFileException oel) {
                return getContentFromDocxFile(fileName);
            }
            return wordExtractor.getText();
        }

        /**
         * Enter Filename with extension and sheet name. Place file in resources folder
         * of project.
         *
         * @param fileName
         * @return String array of data
         * @author nauman.shahid
         */
        public XWPFDocument getAllDocControls(String fileName) {
            InputStream is = getResourceAsStream(fileName);
            XWPFDocument doc = null;
            try {
                doc = new XWPFDocument(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }

        /**
         * Enter Filename with extension and sheet name.
         * of project.
         *
         * @param fileName
         * @param filePath
         * @return String array of data
         * @author nauman.shahid
         */
        public List<XWPFParagraph> readDocFile(String filePath, String fileName) {
            InputStream is = null;
            try {
                is = new FileInputStream(filePath + "/" + fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            XWPFDocument doc = null;
            try {
                doc = new XWPFDocument(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc.getParagraphs();
        }
    }

    /**
     * Handles all methods related to excel files
     *
     * @author nauman.shahid
     */
    public final class ExcelReader {

        private XSSFWorkbook workbook;
        private XSSFSheet sheet;
        private XSSFRow row;

        /**
         * Create a new Excel file and write data to it.
         * @param fileName
         * @param sheetName
         * @param filePath path where you want to create new file
         * @param rowData A treemap with row number and data set -Example Data::rowData.put("1", new Object[] { "132", "Gopal","2nd year" });
         * @throws IOException
         */
        public void writeExcelFile(String fileName, String sheetName, String filePath, TreeMap<String, Object[]> rowData) throws IOException {
            InputStream is = new FileInputStream(filePath + "/" + fileName);
            workbook = new XSSFWorkbook(is);
            sheet = workbook.createSheet(sheetName);

            Set<String> keyid = rowData.keySet();

            int rowid = 0;

            // writing the data into the sheets...

            for (String key : keyid) {

                row = sheet.createRow(rowid++);
                Object[] objectArr = rowData.get(key);
                int cellid = 0;

                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
            }
            FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);

            workbook.write(out);
            out.close();
        }

        /**
         * Enter Filename with extension and sheet name. Place file in resources folder
         * of project.
         *
         * @param fileName
         * @param sheetName
         * @return String array of data
         * @author nauman.shahid
         */
        public String[][] readExcelFile(String fileName, String sheetName) {
            try {
                InputStream is = getClass().getResourceAsStream("/" + fileName);
                workbook = new XSSFWorkbook(is);
            } catch (Exception e) {

            }
            return getDataFromSheet(sheetName);
        }

        /**
         * Read downloaded Excel file at default location /target
         *
         * @param fileName  name of file with extension
         * @param sheetName
         * @return string[][]
         * @author nauman.shahid
         */
        public String[][] readFromDownloadedFile(String fileName, String sheetName) {
            File file = new File(System.getProperty("user.dir") + File.pathSeparatorChar + "target");
            toRenameDownloadedTmpFile(file, fileName, "xlsx");
            return readExcelFile(fileName, sheetName,
                    file.getAbsolutePath() + File.pathSeparatorChar + fileName);
        }

        /**
         * Enter file name, sheet name and custom path as well to read file.
         *
         * @param fileName
         * @param sheetName
         * @param filePath
         * @return string array.
         * @author nauman.shahid
         */
        public String[][] readExcelFile(String fileName, String sheetName, String filePath) {
            try {
                InputStream is = new FileInputStream(filePath + "/" + fileName);
                workbook = new XSSFWorkbook(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getDataFromSheet(sheetName);
        }

        private String[][] getDataFromSheet(String sheetName) {
            String[][] dataSets = null;
            try {
                XSSFSheet sheet = workbook.getSheet(sheetName);
                int totalRow = sheet.getLastRowNum() + 1;
                int totalColumn = sheet.getRow(0).getLastCellNum();
                dataSets = new String[totalRow - 1][totalColumn];
                for (int i = 1; i < totalRow; i++) {
                    XSSFRow rows = sheet.getRow(i);
                    for (int j = 0; j < totalColumn; j++) {
                        XSSFCell cell = rows.getCell(j);
                        if (cell.getCellTypeEnum() == CellType.STRING)
                            dataSets[i - 1][j] = cell.getStringCellValue();
                        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            long cellText = Math.round(cell.getNumericCellValue());
                            String cellTextString = Long.toString(cellText);
                            dataSets[i - 1][j] = cellTextString;
                        } else
                            dataSets[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                    }
                }

            } catch (Exception e) {
                //Console log Exception in reading Xlxs file" + e.getMessage());
            }
            return dataSets;
        }

        /**
         * Parse any file to an excel file
         *
         * @param fileToBeParsedPath           full path of the file with file name and
         *                                     extension
         * @param afterParsingFilePathWithName full path of the new file with name and
         *                                     extension
         * @author nauman.shahid
         */

        public void parseAnyFileAsExcel(String fileToBeParsedPath, String afterParsingFilePathWithName) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(new File(fileToBeParsedPath)));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            XSSFWorkbook xwork = new XSSFWorkbook();
            XSSFSheet xsheet = xwork.createSheet("testSheet");
            XSSFRow xrow = null;

            int rowid = 0;
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    String[] split = line.split("<br>");
                    Cell cell;
                    for (int i = 0; i < split.length; i++) {
                        xrow = xsheet.createRow(rowid);
                        cell = xrow.createCell(2);
                        cell.setCellValue(split[i]);
                        String[] columnSplit = split[i].split("\\W+");
                        int columnCount = 3;
                        for (int j = 0; j < columnSplit.length; j++) {

                            cell = xrow.createCell(columnCount++);
                            cell.setCellValue(columnSplit[j]);
                        }

                        rowid++;
                    }

                    // Create file system using specific name
                    FileOutputStream fout = new FileOutputStream(new File(afterParsingFilePathWithName));
                    xwork.write(fout);
                    fout.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //Console log  File Parsing process completed");
        }
    }

    /**
     * Handle various json related functions
     *
     * @author nauman.shahid
     */
    public final class JsonReader {
        InputStream is;
        Reader reader;
        JsonParser jsonParser;

        /**
         * read data with title
         *
         * @param fileName
         * @param childObject
         * @param key
         * @return
         */
        public String getDataFromJson(String fileName, String childObject, String key) {
            setUpReadStream(fileName);
            // getting the root element - the whole set of json data
            JsonElement rootElement = jsonParser.parse(reader);
            // covert it to the json object to access different child objects and arrays
            JsonObject theObject = rootElement.getAsJsonObject();
            return theObject.getAsJsonObject(childObject).get(key).getAsString();
        }

        /**
         * getJsonData
         *
         * @param fileName
         * @return
         */
        public JsonElement getDataFromJson(String fileName) {
            setUpReadStream(fileName);
            // getting the root element - the whole set of json data
            JsonElement rootElement = jsonParser.parse(reader);
            // covert it to the json object to access different child objects and arrays
            return rootElement.getAsJsonObject();
        }

        /**
         * read data with multiple titles
         *
         * @param fileName
         * @param firstChildObject
         * @param secondChildObject
         * @param key
         * @return
         */
        public String getDataFromJson(String fileName, String firstChildObject, String secondChildObject, String key) {
            setUpReadStream(fileName);
            // getting the root element - the whole set of json data
            JsonElement rootElement = jsonParser.parse(reader);
            // covert it to the json object to access different child objects and arrays
            JsonObject theObject = rootElement.getAsJsonObject();
            JsonObject childObject = theObject.getAsJsonObject(firstChildObject).getAsJsonObject(secondChildObject);
            return childObject.get(key).toString();
        }

        /**
         * read data from array
         *
         * @param fileName
         * @param firstChildObject
         * @param secondChildObject
         * @param arrayName
         * @param key
         * @return
         */
        public String getDataFromJson(String fileName, String firstChildObject, String secondChildObject,
                                      String arrayName, String key) {
            setUpReadStream(fileName);
            // getting the root element - the whole set of json data
            JsonElement rootElement = jsonParser.parse(reader);
            // covert it to the json object to access different child objects and arrays
            JsonObject theObject = rootElement.getAsJsonObject();
            JsonObject childObject = theObject.getAsJsonObject(firstChildObject).getAsJsonObject(secondChildObject);
            return childObject.getAsJsonArray(arrayName).getAsJsonObject().get(key).toString();
        }

        /**
         * get jsonObject of string json
         *
         * @param jsonInString
         * @return
         */
        public JsonObject getJsonObject(String jsonInString) {
            getJsonParserInstance();
            return jsonParser.parse(jsonInString).getAsJsonObject();
        }

        /**
         * get object array
         *
         * @param fileName
         * @param firstChildObject
         * @param secondChildObject
         * @param arrayName
         * @param key
         * @return
         */
        public JsonObject getDataArrayFromJson(String fileName, String firstChildObject, String secondChildObject,
                                               String arrayName, String key) {
            setUpReadStream(fileName);
            // getting the root element - the whole set of json data
            JsonElement rootElement = jsonParser.parse(reader);
            // covert it to the json object to access different child objects and arrays
            JsonObject theObject = rootElement.getAsJsonObject();
            JsonObject childObject = theObject.getAsJsonObject(firstChildObject).getAsJsonObject(secondChildObject);
            return childObject.getAsJsonArray(arrayName).getAsJsonObject();
        }

        /**
         * maange instance creation and input stream
         *
         * @param fileName
         */
        private void setUpReadStream(String fileName) {
            is = getClass().getClassLoader().getResourceAsStream(fileName);
            reader = new InputStreamReader(is);
            getJsonParserInstance();
        }

        /**
         * manage instance for json parser
         */
        private void getJsonParserInstance() {
            if (jsonParser == null) {
                jsonParser = new JsonParser();
            }
        }
    }


    /**
     * Read Adobe files
     *
     * @author nauman.shahid
     */
    public final class AdobeReader {
        InputStream is;
        Reader reader;

        /**
         * Read downloaded pdf file with extension at default location /target
         *
         * @param fileName   name of file
         * @param pageNumber
         * @return string
         * @author nauman.shahid
         */
        public String readFromDownloadedFile(String fileName, int pageNumber) {
            File file = new File(System.getProperty("user.dir") + File.pathSeparatorChar + "target");
            toRenameDownloadedTmpFile(file, fileName, "pdf");
            return readFromAdobeFileOnLocalAtUserDirectory(
                    file.getAbsolutePath() + File.pathSeparatorChar + fileName, pageNumber);
        }


        /**
         * read a file from src/main/resources
         *
         * @param fileName
         * @param pageNumber
         * @return string after readinf file
         * @author nauman.shahid
         */
        public String readFromAdobeFileFromResources(String fileName, int pageNumber) {
            return adobeReader(fileName, pageNumber);
        }

        /**
         * read file from url
         *
         * @param url
         * @param pageNumber
         * @return string after reading file
         * @throws MalformedURLException
         * @throws FileNotFoundException
         * @author nauman.shahid
         */
        public String readFromAdobeFileAfterDownloading(String url, int pageNumber)
                throws MalformedURLException, FileNotFoundException {
            return adobeReaderFromURL(url, pageNumber);
        }

        /**
         * read file from a specific location
         *
         * @param fileName
         * @param pageNumber
         * @return file after reading in string
         * @author nauman.shahid
         */
        public String readFromAdobeFileOnLocalAtUserDirectory(String fileName, int pageNumber) {
            return adobeReaderAtSpecificLocation(fileName, pageNumber);
        }

        /**
         * manage instance creation
         *
         * @param fileName
         * @return
         * @author nauman.shahid
         */
        private InputStream setUpReadStream(String fileName) {
            return is = getClass().getClassLoader().getResourceAsStream("/" + fileName);
        }

        /**
         * @param fileName
         * @param pageNumber
         * @return
         * @author nauman.shahid
         */
        private String adobeReader(String fileName, int pageNumber) {
            String page = null;
            try {
                PdfReader reader = new PdfReader(setUpReadStream(fileName));
                //Console log This PDF has " + reader.getNumberOfPages() + " pages.");
                page = PdfTextExtractor.getTextFromPage(reader, pageNumber);
                //Console log Page Content:\n\n" + page + "\n\n");
                //Console log Is this document tampered: " + reader.isTampered());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return page;
        }

        /**
         * @param path
         * @param pageNumber
         * @return
         * @author nauman.shahid
         */
        private String adobeReaderAtSpecificLocation(String path, int pageNumber) {
            String page = null;
            try {
                PdfReader reader = new PdfReader(path);
                //Console log This PDF has " + reader.getNumberOfPages() + " pages.");
                page = PdfTextExtractor.getTextFromPage(reader, pageNumber);
                //Console log Page Content:\n\n" + page + "\n\n");
                //Console log  Is this document tampered: " + reader.isTampered());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return page;
        }

        /**
         * @param url
         * @param pageNumber
         * @return
         * @throws MalformedURLException
         * @throws FileNotFoundException
         * @author nauman.shahid
         */
        private String adobeReaderFromURL(String url, int pageNumber)
                throws MalformedURLException, FileNotFoundException {
            String page = null;
            try {
                PdfReader reader = new PdfReader(setUpReadStream(downloadURLBasePDF(url)));
                //Console log This PDF has " + reader.getNumberOfPages() + " pages.");
                page = PdfTextExtractor.getTextFromPage(reader, pageNumber);
                //Console log Page Content:\n\n" + page + "\n\n");
                //Console log Is this document tampered: " + reader.isTampered());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return page;
        }

        /**
         * @param url
         * @return
         * @throws MalformedURLException
         * @throws FileNotFoundException
         * @author nauman.shahid
         */
        private String downloadURLBasePDF(String url) throws MalformedURLException, FileNotFoundException {
            Random random = new Random(500);
            String newFileName = "newPdfFile" + random + ".pdf";
            URL url1 = new URL(url);
            File file = new File(System.getProperty("user.dir") + File.separator + "target", newFileName);
            byte[] ba1 = new byte[1024];
            int baLength;
            FileOutputStream fos1 = new FileOutputStream(file);

            try {
                // Contacting the URL
                URLConnection urlConn = url1.openConnection();

                // Checking whether the URL contains a PDF
                if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
                    //Console log FAILED.\n[Sorry. This is not a PDF.]");
                } else {
                    try {

                        // Read the PDF from the URL and save to a local file
                        InputStream is1 = url1.openStream();
                        while ((baLength = is1.read(ba1)) != -1) {
                            fos1.write(ba1, 0, baLength);
                        }
                        fos1.flush();
                        fos1.close();
                        is1.close();
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {

            }
            return newFileName;
        }

    }

}
