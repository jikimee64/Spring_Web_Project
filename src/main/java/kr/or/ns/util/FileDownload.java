package kr.or.ns.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownload extends AbstractView {

	public FileDownload() {
		setContentType("application/download; charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		File file = (File) model.get("downloadFile");

		response.setContentType(getContentType()); //다운로드를 위한 콘텐츠 타입 설정
		response.setContentLength((int) file.length()); //다운로드되는 파일의 크기 설정

		String userAgent = request.getHeader("User-Agent"); //브라우저 정보 가져오기
									//여기의 Header는 개발자도구-Network의 Header인 듯하다
		boolean ie = userAgent.indexOf("MSIE") > -1; //MSIE라는 문자열의 위치가 존재한다면 -1보단 큰 값이기 때문에 true 반환
													//MSIE = Microsoft Internet Explorer
													//다른 브라우저는 "CHROME" 이런 식으로 입력하면 됨
		String fileName = null;
		if (ie) {
			fileName = URLEncoder.encode(file.getName(), "utf-8");
		} else {
			fileName = new String(file.getName().getBytes("utf-8"),
					"iso-8859-1"); //라틴-1. HTML 문서의 기본 인코딩
		}
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\";"); //Content-Disposition 헤더를 써서 전송되는 파일의 이름을 명시한다
		response.setHeader("Content-Transfer-Encoding", "binary"); //전송되는 파일의 인코딩이 바이너리 타입이라는 것을 명시
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out); //FileCopyUtils = 스프링의 유틸리티 클래스
											//FileInputStream으로부터 데이터를 읽어와 response의 OutputStream을 통해 출력
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException ex) {
				}
		}
		out.flush();
	}

}
