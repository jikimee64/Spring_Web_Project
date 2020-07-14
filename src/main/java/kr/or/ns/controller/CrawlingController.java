package kr.or.ns.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	

	@RequestMapping("CrawlingUdemy1.do")
	public String Udemy() {

        HashMap<String, Object> result = new HashMap<String, Object>();
 
        String jsonInString = "";
 
        try {
 
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate restTemplate = new RestTemplate(factory);
 
            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
 
            String url = "https://www.udemy.com/api-2.0/discovery-units/all_courses/";
 
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"page_size=16&subcategory=&instructional_level=&lang=&price=&duration=&closed_captions=&subcategory_id=8&source_page=subcategory_page&locale=ko_KR&currency=krw&navigation_locale=en_US&skip_price=true&sos=ps&fl=scat").build();
 
            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCode()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인
 
            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(resultMap.getBody());
 
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println("dfdfdfdf");
            System.out.println(e.toString());
 
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }
 
        return jsonInString;
	}
}
