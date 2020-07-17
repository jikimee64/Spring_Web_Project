package kr.or.ns.crawling;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
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

import kr.or.ns.crawlingvo.UdemyResponse;
import kr.or.ns.crawlingvo.UdemyUnit;

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
		// String url = "https://www.inflearn.com/courses/it-programming/web-dev"; //
		// 크롤링할 url지정

		// List<String> list2 = Arrays.asList(new
		// String[]{"html-css","javascript","spring","python","vuejs","react","jquery","jsp","bootstrap","java-persistence-api","java"});

		List<String> titleList = new ArrayList<>();

		String url = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=html-css%2Cjavascript%2Cspring%2"
				+ "Creact%2Cjquery%2Cjsp%2Cbootstrap%2Cjava-persistence-api%2Cjava%2Cpython%2Cvuejs"; // 크롤링할 URLurl지정
		Document doc = null; // Document에는 페이지의 전체 소스가 저장된다
		int endPage = 0;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.

		Elements element = doc.select(".pagination-list");
		Iterator<Element> pagesize = element.select(".pagination-link").iterator();
		while (pagesize.hasNext()) {
			String ps = pagesize.next().text();
			endPage = Integer.parseInt(ps);
		}

		// ==========================================

		Document doc2 = null; // Document에는 페이지의 전체 소스가 저장된다

		for (int i = 1; i <= endPage; i++) {

			String url2 = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=html-css%2Cjavascript%2Cspring%2"
					+ "Creact%2Cjquery%2Cjsp%2Cbootstrap%2Cjava-persistence-api%2Cjava%2Cpython%2Cvuejs&page=" + i; // 크롤링할

			try {
				doc2 = Jsoup.connect(url2).get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Elements element2 = doc2.select(".card.course.course_card_item");

			// System.out.println(element);

//		// Iterator을 사용하여 하나씩 값 가져오기
			Iterator<Element> imgs = element2.select(".swiper-lazy").iterator();
			Iterator<Element> titles = element2.select(".course_title").iterator();
			Iterator<Element> authors = element2.select(".instructor").iterator();
			Iterator<Element> prices = element2.select(".price").iterator();
//		Iterator<Element> ie2 = element.select("strong.title").iterator();

			while (imgs.hasNext()) {
			}

			while (titles.hasNext()) {
				String title = titles.next().text();
				titleList.add(title);
			}

			while (authors.hasNext()) {
				String author = authors.next().text();
				titleList.add(author);
			}

			while (prices.hasNext()) {
				String price = prices.next().text();
				titleList.add(price);
			}
//
//		System.out.println("============================================================");
			System.out.println("크롤링 페이지 이동");
		}
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
				.queryParam("page_size", "16").queryParam("subcategory_id", "8")
				.queryParam("source_page", "subcategory_page").queryParam("locale", "ko_KR")
				.queryParam("currency", "krw").queryParam("navigation_locale", "ko_KR").queryParam("skip_price", "true")
				.queryParam("sos", "ps").queryParam("fl", "scat").build().toUri();

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

//				// Iterator을 사용하여 하나씩 값 가져오기
				Iterator<Element> titles = element.select(".course_title").iterator();
				Iterator<Element> images = element.select(".card-image").iterator();
				Iterator<Element> instructors = element.select(".instructor").iterator();
				
//				Iterator<Element> ie2 = element.select("strong.title").iterator();
				
		//
				while (titles.hasNext()) {
					String title = titles.next().text();
					titleMap.put("title", title);
//					String image = images.next().text();
//					titleMap.put("images", images);
					String instructor = instructors.next().text();
					titleMap.put("instructor", instructor);
					
				}
		//
//				System.out.println("============================================================");

				System.out.println("크롤링 페이지 이동");
				return titleMap;
			}
	
	// 다뽑아보기 우철
	@RequestMapping("CrawlingInflearn2.do")
	public List<Map<String, Object>> CrawlingInflearn2() {

		String url = "https://www.inflearn.com/courses/it-programming/web-dev"; // 크롤링할 url지정
		Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

		List<Map<String, Object>> titleList = new ArrayList<>();
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

	/*	for (int i = 0; i < 6; i++) {*/

			// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
			// Elements element = doc.select(".card.course.course_card_item");

			// 사진 시작
			Connection conn = Jsoup.connect(url);

			// 3. HTML 파싱.
			Document html = null;
			try {
				html = conn.get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Elements element = html.getElementsByClass("course_card_item");
			
		/*	for (Element fileblock : fileblocks) {*/
			for (int j = 0; j < element.size(); j++) {
				Map<String, Object> map = new HashMap();
				Elements files = element.get(j).getElementsByTag("img");
				
				String href = files.attr("src");
				System.out.println("아오 : " + href);
				map.put("href", href);

//		// Iterator을 사용하여 하나씩 값 가져오기
				Elements titles = element.get(j).getElementsByClass("course_title");
				System.out.println("titles : " + titles.text());
				map.put("titles", titles.text());
				Elements instructors = element.get(j).getElementsByClass("instructor");
				System.out.println("instructors : " + instructors.text());
				map.put("instructors", instructors.text());
				Elements prices =element.get(j).getElementsByClass("price");
				System.out.println("prices : " + prices.text());
				map.put("prices", prices.text());
				titleList.add(map);
			}

		//} // for문 끝
		return titleList;
	}
}
