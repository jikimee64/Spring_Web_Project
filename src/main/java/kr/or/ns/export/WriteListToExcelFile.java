package kr.or.ns.export;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.or.ns.vo.Users;


public class WriteListToExcelFile {
	public static void writeMemberListToFile(String fileName, List<Users> memberList) throws Exception {
		
	Workbook workbook = null;
	
	if(fileName.endsWith("xlsx")) {
		workbook = new XSSFWorkbook();
	
	}else if(fileName.endsWith("xls")) {
		workbook = new HSSFWorkbook();
	
	}else {
		throw new Exception("유효하지 않은 파일명입니다. xls나 xlsx만 가능합니다.");
	}
		
	
	Sheet sheet = workbook.createSheet("cordova");
	
	Iterator<Users> iterator = memberList.iterator();
	
	int rowIndex = 0;
	int excelName = 0; // 처음에는 고정값을 넣기 위해 사용한 변수
	
	do {
		Users users = iterator.next();
		Row row = sheet.createRow(rowIndex++);
		
		if(excelName==0) { //처음에 고정값
			
			Cell cell0 = row.createCell(0);
			cell0.setCellValue("아이디");
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue("이메일");
			
			Cell cell2 = row.createCell(2);
			cell2.setCellValue("닉네임");
			
			Cell cell3 = row.createCell(3);
			cell3.setCellValue("신고횟수");
	
		}else { //다음부터는 순차적으로 값이 들어감
			
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(users.getUser_id());
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(users.getUser_email());
			
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(users.getNickname());
			
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(users.getBlame_count());
			
		}
		
		
	}while(iterator.hasNext());  //다음값이 없을때까지 뽑음
		
	//excel데이터를 file로 만듦
	FileOutputStream fos = new FileOutputStream(fileName);
	workbook.write(fos); //쓴다
	fos.close();
	
	System.out.println(fileName+"파일쓰기 성공!");
	
	}

}
