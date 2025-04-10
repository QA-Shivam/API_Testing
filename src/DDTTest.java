import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DDTTest {
    ArrayList<Object> arr = new ArrayList<Object>();
	
	public ArrayList<Object> getData(String testCaseName) throws IOException {
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\satya\\eclipse-workspace\\API_Testing\\src\\payload\\DDTTest.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
		int noofsheet = xssfWorkbook.getNumberOfSheets();
		for (int i = 0; i < noofsheet; i++) {
			if (xssfWorkbook.getSheetName(i).equalsIgnoreCase("testData")) {
				XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
				Iterator<Row> row = sheet.iterator();
				Row firstrow = row.next();
				Iterator<Cell> cell = firstrow.cellIterator();
				int column = 0;
				int k = 0;
				while (cell.hasNext()) {
					Cell value = cell.next();
//					System.out.println(value);
					if (value.getStringCellValue().equalsIgnoreCase("TestCase")) {
						column = k;
					}
					k++;
				}
//				System.out.println(column);

				while (row.hasNext()) {
					Row r = row.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						Iterator<Cell> c = r.cellIterator();
						while (c.hasNext()) {
							Cell cellvalue= c.next();
							if(cellvalue.getCellType()==CellType.STRING)
							{
							arr.add(cellvalue.getStringCellValue());

							}
							else {
								arr.add(cellvalue.getNumericCellValue());
							}
						}
					}
				}
			}
		}
		return arr;
	}
	
	public class test{
		public static void main(String[] args) throws IOException {
			DDTTest dt= new DDTTest();
			ArrayList a=dt.getData("Login");
			a.forEach(item->System.out.println(item));
		}
		
	}
}
