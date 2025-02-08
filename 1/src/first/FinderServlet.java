package first;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// contact table
	private List<Map<String, Object>> contacts = new ArrayList<Map<String, Object>>();

	public void init() throws ServletException {
		try {
			String files = getInitParameter("contacts");

			files = files.trim();
			files = files.replace('，', ',');
			String[] file_name_array = files.split(",");

			for (int i = 0; i < file_name_array.length; i++) {
				String file_name = file_name_array[i];
				file_name = file_name.trim();
				if (file_name.length() == 0) {
					continue;
				}

				File file = new File(getServletContext().getRealPath("/WEB-INF/contacts/" + file_name));

				FileInputStream fis = new FileInputStream(file);

				Workbook book = null;
				try {
					book = new XSSFWorkbook(fis);
				} catch (Exception ex) {
					book = new HSSFWorkbook(fis);
				}

				Sheet sheetAt = book.getSheetAt(0);

				for (Row row : sheetAt) {
					if (row.getRowNum() == 0) {
						continue;
					}

					if (row == null) {
						break;
					}

					Cell cell = row.getCell(0);

					if (cell == null) {
						break;
					}

					double no = row.getCell(0).getNumericCellValue();
					String id = row.getCell(1).getStringCellValue();
					String name = row.getCell(2).getStringCellValue();
					String strClass = "";
					String mobile = "";
					String email = "";

					cell = row.getCell(3);
					if (cell != null) {
						strClass = cell.getStringCellValue();
					}

					cell = row.getCell(4);
					if (cell != null) {
						cell.setCellType(CellType.STRING);
						mobile = cell.getStringCellValue();
					}

					cell = row.getCell(5);
					if (cell != null) {
						cell.setCellType(CellType.STRING);
						email = cell.getStringCellValue();
					}

					Map<String, Object> record = new HashMap<String, Object>();
					record.put("id", id);
					record.put("name", name);
					record.put("gender", null);
					record.put("class", strClass);
					record.put("mobile", mobile);
					record.put("email", email);
					contacts.add(record);
				}

				book.close();
				fis.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("value");
		System.out.println(key);
		boolean success=false;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		for (Map<String, Object> contact : contacts) {
			if(contact.get("name").equals(key)||contact.get("id").equals(key)
			    		||contact.get("name").toString().substring(0,contact.get("name").toString().length()-1).equals(key)||contact.get("mobile").equals(key)||contact.get("email").equals(key)) {

				int len=contact.get("name").toString().length();
				if(contact.get("name").toString().charAt(len-1)=='*'){
					contact.put("name",contact.get("name").toString().substring(0,len-1));
					contact.put("gender","女");
				}else{
					contact.put("gender","男");
				}
				writer.write(contact.toString());
				writer.write("<br></br>");
				success=true;
			}
		}
		if(!success)
			writer.write("error,找不到这样的学生");



	}

}
