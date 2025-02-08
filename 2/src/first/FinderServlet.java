package first;


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

	private List<Map<String, Object>> list = new ArrayList<>();
	private int page = 0;

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
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();


		if(request.getParameter("page") != null&& !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		writer.write("<form action=\"/first_war_exploded/find\">\n" +
				"        <label>\n" +
				"            <input type=\"text\" name=\"page\">\n" +
				"        </label>\n" +
				"        <input type=\"submit\" value=\"跳转\">\n" +"<br>"+

						"        <input type=\"submit\" name=\"shang\" value=\"上一页\">\n"+

						"        <input type=\"submit\" name=\"xia\" value=\"下一页\">\n"+
				"    </form>"
				);

		String value = request.getParameter("value");
		String shang=request.getParameter("shang");
		String xia=request.getParameter("xia");

		//String

		if(value == null) {//处于分页界面
			//进行跳转页操作
			if(request.getParameter("page") != null&& !request.getParameter("page").isEmpty()){
				page = Integer.parseInt(request.getParameter("page"));
				page--;
			}
			else if(shang!=null)//点击的是上一页
				page--;
			else  //点击的是下一页
				page++;
			int total = list.size() / 10;
			if(list.size() % 10 != 0) {
				total++;
			}

			if(page < 0 || page >= total) {
				writer.write("无效页码");
				return;
			}
			int maxIndex = Math.min(page * 10 + 10, list.size());
			for(int i = page * 10; i < maxIndex; i++) {
				Map<String, Object> map = list.get(i);
				if(map.isEmpty()) {
					break;
				}
				writer.write(map.toString());
				writer.write("<br>");
				System.out.println(map);
			}
			writer.write("第" + (page + 1) + "页");

			writer.write("，共" + total + "页<br>");
		} else {//在第一次的查询页面

			list.clear();
			contacts.forEach(map -> {
				if (map.get("id").toString().contains(value)) {
					list.add(map);
				} else if(map.get("name").toString().contains(value) ||
						map.get("name").toString().replace("*", "").contains(value)) {
					list.add(map);
				} else if(map.get("mobile").toString().contains(value)) {
					list.add(map);
				} else if(map.get("email").toString().contains(value)) {
					list.add(map);
				}
			});

			list.forEach(map -> {
				String temp = map.get("name").toString();
				if(temp.contains("*")) {
					map.put("gender", "女");
					map.put("name", temp.substring(0, temp.length() - 1));
				} else {
					map.put("gender", "男");
				}
			});

/*			list.forEach(map -> {
				writer.write(map.toString());
				System.out.println(map.toString());
				writer.write("<br>");
			});*/

			for (int i = 0; i < Math.min(list.size(), 10); i++) {
				writer.write(list.get(i).toString());
				System.out.println(list.get(i).toString());
				writer.write("<br>");
			}
			writer.write("第1页");
			int total = list.size() / 10;
			if(list.size() % 10 != 0) {
				total++;
			}
			writer.write("，共" + total + "页<br>");
		}

		writer.write("共" + list.size() + "条");
	}
}
