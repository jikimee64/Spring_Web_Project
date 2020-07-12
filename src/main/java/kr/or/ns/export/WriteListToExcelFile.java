package kr.or.ns.export;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.or.ns.vo.Users;


public class WriteListToExcelFile {
   public static void writeMemberListToFile(String fileName, List<Users> memberList, HttpServletRequest request) throws Exception {
      
   Workbook workbook = null;
   
   if(fileName.endsWith("xlsx")) {
      workbook = new XSSFWorkbook();
   
   }else if(fileName.endsWith("xls")) {
      workbook = new HSSFWorkbook();
   
   }else {
      throw new Exception("유효하지 않은 파일명입니다. xls나 xlsx만 가능합니다.");
   }
   
   Sheet sheet = workbook.createSheet("회원관리");
   
   Iterator<Users> iterator = memberList.iterator();
   
   int rowIndex = 0;
   int excelName = 0; // 처음에는 고정값을 넣기 위해 사용한 변수
   
   do {
      Row row = sheet.createRow(rowIndex++);
      if(excelName==0) { //처음에 고정값
    	  System.out.println("칼럼입니다.");
         Cell cell0 = row.createCell(0);
         cell0.setCellValue("아이디");
         
         Cell cell1 = row.createCell(1);
         cell1.setCellValue("이메일");
         
         Cell cell2 = row.createCell(2);
         cell2.setCellValue("닉네임");
         
         Cell cell3 = row.createCell(3);
         cell3.setCellValue("신고횟수");
         
         excelName++;
      }else { //다음부터는 순차적으로 값이 들어감
         Users users = iterator.next();
         System.out.println("나는 유저다 : " + users);
         System.out.println("안녕하십니까!!22");
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
 	String path = request.getServletContext().getRealPath("/manager/");
    File xlsFile = new File(path+fileName); //저장경로 설정
 	FileOutputStream fos = new FileOutputStream(xlsFile);
 	workbook.write(fos); //쓴다
 	fos.close();
   
   System.out.println(fileName+"파일쓰기 성공!");
   
   }

}