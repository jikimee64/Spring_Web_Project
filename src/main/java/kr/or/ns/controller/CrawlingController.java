package kr.or.ns.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Crawling/")
public class CrawlingController {

	@RequestMapping("CrawlingInflearn.do")
	public List<String> CrawlingInflearn() {

		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
		String url = "https://www.inflearn.com/courses/it-programming/web-dev"; // 크롤링할 url지정
		Document doc = null; // Document에는 페이지의 전체 소스가 저장된다
		
		List<String> titleList = new ArrayList<>();

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
		Elements element = doc.select(".card.course.course_card_item");

		System.out.println(element);
		System.out.println("============================================================");

//		// Iterator을 사용하여 하나씩 값 가져오기
		Iterator<Element> titles = element.select(".course_title").iterator();
//		Iterator<Element> ie2 = element.select("strong.title").iterator();
//
		while (titles.hasNext()) {
			String title = titles.next().text();
			titleList.add(title);
		}
//
//		System.out.println("============================================================");

		System.out.println("크롤링 페이지 이동");
		return titleList;
	}
	@RequestMapping(value= "CrawlingUdemy.do", produces="text/plain;charset=UTF-8")
	public String CrawlingUdemy() {
		
		return "헬로유데미" ;
	}
	

}
