package kr.or.ns.crawling.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kr.or.ns.crawling.vo.UdemyResponse;
import kr.or.ns.crawling.vo.UdemyUnit;

@RestController
@RequestMapping("/Crawling/")
public class CrawlingController {
	
	@Autowired
	@Qualifier("restTemplate")
	public RestTemplate restTemplate;

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

	
	// 유데미 크롤링하기
		@RequestMapping("CrawlingUdemy.do")
		public UdemyUnit CrawlingUdemy() {
			
			URI uri = UriComponentsBuilder.fromHttpUrl("https://www.udemy.com/api-2.0/discovery-units/all_courses/") 
					.queryParam("page_size", "16")
					.queryParam("subcategory_id", "8")
					.queryParam("source_page", "subcategory_page")
					.queryParam("locale", "ko_KR")
					.queryParam("currency", "krw")
					.queryParam("navigation_locale", "ko_KR")
					.queryParam("skip_price", "true")
					.queryParam("sos", "ps")
					.queryParam("fl", "scat")
					.build().toUri();

			UdemyResponse returnTypeData = restTemplate.getForObject(uri, UdemyResponse.class);
			System.out.println(returnTypeData);
			return returnTypeData.getUnit();
		}
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// 인프런 크롤링하는건데 위에거가 제목밖에 안나와서 다른거 뽑아보기 연습하기 - 연규
		@RequestMapping("CrawlingInflearn1.do")
		public Map<String, Object> CrawlingInflearn1() {

			// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
			String url = "https://www.inflearn.com/courses/it-programming/web-dev"; // 크롤링할 url지정
			Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

			List<String> titleList = new ArrayList<>();
			Map<String,Object> titleMap = new HashMap();
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
			Elements element = doc.select(".card.course.course_card_item");

			System.out.println(element);
			System.out.println("============================================================");

//			// Iterator을 사용하여 하나씩 값 가져오기
			Iterator<Element> titles = element.select(".course_title").iterator();
			Iterator<Element> images = element.select(".card-image").iterator();
			Iterator<Element> instructors = element.select(".instructor").iterator();
			
//			Iterator<Element> ie2 = element.select("strong.title").iterator();
			
	//
			while (titles.hasNext()) {
				String title = titles.next().text();
				titleMap.put("title", title);
//				String image = images.next().text();
//				titleMap.put("images", images);
				String instructor = instructors.next().text();
				titleMap.put("instructor", instructor);
				
			}
	//
//			System.out.println("============================================================");

			System.out.println("크롤링 페이지 이동");
			return titleMap;
		}
}
