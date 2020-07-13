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

	// 인프런 크롤링하기
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

	// 유데미 크롤링하기
	@RequestMapping("CrawlingUdemy.do")
	public List<String> CrawlingUdemy() {
		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
				String url = "https://www.udemy.com/topic/web-development"; // 크롤링할 url지정
				Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

				
				List<String> titleList = new ArrayList<>();

				try {
					doc = Jsoup.connect(url).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
				Elements element = doc.select(".topic-onboarding--background-container--3QIiJ.advertising-banner--container--2nUhw.advertising-banner--dark--KftMn");

				System.out.println(element);
				System.out.println("============================================================");

//				// Iterator을 사용하여 하나씩 값 가져오기
				Iterator<Element> titles = element.select(".udlite-heading-xl.advertising-banner--title--2D50e").iterator();
//				Iterator<Element> ie2 = element.select("strong.title").iterator();
		//
				while (titles.hasNext()) {
					String title = titles.next().text();
					titleList.add(title);
				}
		//
//				System.out.println("============================================================");

				System.out.println("크롤링 페이지 이동");
				return titleList;
	}

	
	
	// 구름에듀 크롤링하기
	@RequestMapping("CrawlingGoormEdu.do")
	public List<String> CrawlingGoormEdu() {


		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
		String url = "https://edu.goorm.io/category/programming/web-programming"; // 크롤링할 url지정
		Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

		List<String> titleList = new ArrayList<>();

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
		Elements element = doc.select("._2tXzr4._1P2ks5._2hZilU._2BQKrJ.card");

		System.out.println(element);
		System.out.println("============================================================");

//		// Iterator을 사용하여 하나씩 값 가져오기
		Iterator<Element> titles = element.select(".kV2LiJ").iterator();
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

}
