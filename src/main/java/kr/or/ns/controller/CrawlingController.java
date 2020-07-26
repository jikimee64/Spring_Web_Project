package kr.or.ns.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ns.crawling.vo.UdemyCourses;
import kr.or.ns.crawling.vo.UdemyKey;
import kr.or.ns.crawling.vo.UdemyPage;
import kr.or.ns.crawling.vo.UdemyPrice;
import kr.or.ns.crawling.vo.UdemyResponse;
import kr.or.ns.crawling.vo.UdemyResponsePrice;
import kr.or.ns.crawling.vo.UdemyUnit;
import kr.or.ns.service.BoardService;
import kr.or.ns.service.CrawlingService;

@RestController
@RequestMapping("/Crawling/")
public class CrawlingController {
	@Autowired
	private CrawlingService service;

	@Autowired
	@Qualifier("restTemplate")
	public RestTemplate restTemplate;

	@RequestMapping("CrawlingInflearn.do")
	@Scheduled(cron = "0 45 22 * * * ")
	public void CrawlingInflearn() {

		String array[] = new String[] { "java", "javascript", "html-css", "spring", "python", "vuejs", "react",
				"jquery", "jsp", "bootstrap", "java-persistence-api" };

		List<Map<String, Object>> titleList = new ArrayList<>();

		for (int i = 0; i < array.length; i++) {

			// String url = "https://www.inflearn.com/courses/it-programming/web-dev"; //
			// 크롤링할 url지정
			String url = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=" + array[i]; // 크롤링할

			Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

			/* for (int i = 0; i < 6; i++) { */

			Connection conn = Jsoup.connect(url);

			Document html = null;
			try {
				html = conn.get();
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			int endPage = 0;
			Elements elements = doc.select(".pagination-list");
			Iterator<Element> pagesize = elements.select(".pagination-link").iterator();
			while (pagesize.hasNext()) {
				String ps = pagesize.next().text();
				endPage = Integer.parseInt(ps);
			}

			Elements element = html.getElementsByClass("course_card_item");

			if (endPage > 1) {
				for (int z = 1; z <= endPage; z++) {
					url = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=" + array[i]
							+ "&page=" + z;

					Connection conn2 = Jsoup.connect(url);

					Document html2 = null;
					try {
						html2 = conn2.get();
					} catch (IOException e) {
						e.printStackTrace();
					}

					element = html2.getElementsByClass("course_card_item");

					/* for (Element fileblock : fileblocks) { */
					for (int j = 0; j < element.size(); j++) {
						Map<String, Object> map = new HashMap();

						String key = element.get(j).attr("fxd-data");
						String arr_key[] = key.split(",");
						String key_insert = arr_key[0].substring(13);
						map.put("l_key", key_insert);

						Elements level = element.get(j).getElementsByClass("course_level");
						map.put("cate_level", level.text());

						// 가격자르기
						// Elements prices = element.get(j).getElementsByClass("price");
						Elements prices2 = element.get(j).getElementsByClass("price").tagName("span");
						Elements prices3 = element.get(j).getElementsByClass("pay_price");


						if (prices2.text().equals("무료")) {
							map.put("p_seq", 1);
						} else {
							if (prices3.text().equals("")) {
								int prices_insert = Integer.parseInt(prices2.text().replaceAll(",", "").substring(1));
								if (prices_insert < 30000) {
									map.put("p_seq", 2);
								} else if (prices_insert < 50000) {
									map.put("p_seq", 3);
								} else if (prices_insert < 100000) {
									map.put("p_seq", 4);
								} else {
									map.put("p_seq", 5);
								}
							} else {
								int prices_insert = Integer.parseInt(prices3.text().replaceAll(",", "").substring(1));
								if (prices_insert < 30000) {
									map.put("p_seq", 2);
								} else if (prices_insert < 50000) {
									map.put("p_seq", 3);
								} else if (prices_insert < 100000) {
									map.put("p_seq", 4);
								} else {
									map.put("p_seq", 5);
								}
							}

						}

						map.put("site_seq", 1); // 인프런

						if (i == 0) {
							map.put("lan_seq", 1);
						} else if (i == 1) {
							map.put("lan_seq", 2);
						} else if (i == 2) {
							map.put("lan_seq", 3);
						} else if (i == 3) {
							map.put("lan_seq", 4);
						} else if (i == 4) {
							map.put("lan_seq", 5);
						} else if (i == 5) {
							map.put("lan_seq", 6);
						} else if (i == 6) {
							map.put("lan_seq", 7);
						} else if (i == 7) {
							map.put("lan_seq", 8);
						} else if (i == 8) {
							map.put("lan_seq", 9);
						} else if (i == 9) {
							map.put("lan_seq", 10);
						} else if (i == 10) {
							map.put("lan_seq", 11);
						}

						Elements files = element.get(j).getElementsByTag("img");
						String src = files.attr("src");
						map.put("l_image", src);

						Elements titles = element.get(j).getElementsByClass("course_title");
						int titleSize = titles.text().length() / 2;
						map.put("l_title", titles.text().substring(0, titleSize));

						Elements instructors = element.get(j).getElementsByClass("instructor");
						map.put("l_writer", instructors.text());

						// List<Double> star2 = Arrays.asList
						// (new Double[]{0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0});
						// 자른후 ...?
						Elements stars = element.get(j).getElementsByClass("star_solid");
						int star_size = stars.attr("style").length();
						String arr[] = stars.attr("style").split(": ");
						int end = arr[1].lastIndexOf("%");

						Double star_insert = Double.parseDouble(arr[1].substring(0, end));

						if (star_insert == (double) 0.0) {
							map.put("l_star", "0");
						} else if (star_insert <= (double) 10.0) {
							map.put("l_star", "0.5");
						} else if (star_insert <= (double) 20.0) {
							map.put("l_star", "1.0");
						} else if (star_insert <= (double) 30.0) {
							map.put("l_star", "1.5");
						} else if (star_insert <= (double) 40.0) {
							map.put("l_star", "2.0");
						} else if (star_insert <= (double) 50.0) {
							map.put("l_star", "2.5");
						} else if (star_insert <= (double) 60.0) {
							map.put("l_star", "3.0");
						} else if (star_insert <= (double) 70.0) {
							map.put("l_star", "3.5");
						} else if (star_insert <= (double) 80.0) {
							map.put("l_star", "4.0");
						} else if (star_insert <= (double) 90.0) {
							map.put("l_star", "4.5");
						} else {
							map.put("l_star", "5.0");
						}

						// 괄호 자르기
						Elements review_cnt = element.get(j).getElementsByClass("review_cnt");
						String review_cnt_insert = review_cnt.text().replaceAll("\\)|\\(", "");
						map.put("l_review", review_cnt_insert);

						Elements course_card_back = element.get(j).getElementsByClass("course_card_control");
						map.put("l_address", "https://www.inflearn.com" + course_card_back.prev().attr("href"));
						titleList.add(map);

						// 보여줄 가격
						if (prices2.text().equals("무료")) {
							map.put("l_price", prices2.text());
						} else {
							if (prices3.text().equals("")) {
								String prices_insert = prices2.text().substring(1);
								map.put("l_price", prices_insert);
							} else {
								String prices_insert = prices3.text().substring(1);
								map.put("l_price", prices_insert);
								map.put("p_seq", 2);
							}
						}
					}
				}
			} else {

				/* for (Element fileblock : fileblocks) { */
				for (int j = 0; j < element.size(); j++) {
					Map<String, Object> map = new HashMap();

					String key = element.get(j).attr("fxd-data");
					String arr_key[] = key.split(",");
					String key_insert = arr_key[0].substring(13);
					map.put("l_key", key_insert);

					Elements level = element.get(j).getElementsByClass("course_level");
					map.put("cate_level", level.text());

					// 가격자르기
					// Elements prices = element.get(j).getElementsByClass("price");
					Elements prices2 = element.get(j).getElementsByClass("price").tagName("span");
					Elements prices3 = element.get(j).getElementsByClass("pay_price");


					if (prices2.text().equals("무료")) {
						map.put("p_seq", 1);
					} else {
						if (prices3.text().equals("")) {
							int prices_insert = Integer.parseInt(prices2.text().replaceAll(",", "").substring(1));
							if (prices_insert < 30000) {
								map.put("p_seq", 2);
							} else if (prices_insert < 50000) {
								map.put("p_seq", 3);
							} else if (prices_insert < 100000) {
								map.put("p_seq", 4);
							} else {
								map.put("p_seq", 5);
							}
						} else {
							int prices_insert = Integer.parseInt(prices3.text().replaceAll(",", "").substring(1));
							if (prices_insert < 30000) {
								map.put("p_seq", 2);
							} else if (prices_insert < 50000) {
								map.put("p_seq", 3);
							} else if (prices_insert < 100000) {
								map.put("p_seq", 4);
							} else {
								map.put("p_seq", 5);
							}
						}

					}

					map.put("site_seq", 1); // 인프런

					// 스킬문제...
					Elements skills = element.get(j).getElementsByClass("course_skills");
					if (i == 0) {
						map.put("lan_seq", 1);
					} else if (i == 1) {
						map.put("lan_seq", 2);
					} else if (i == 2) {
						map.put("lan_seq", 3);
					} else if (i == 3) {
						map.put("lan_seq", 4);
					} else if (i == 4) {
						map.put("lan_seq", 5);
					} else if (i == 5) {
						map.put("lan_seq", 6);
					} else if (i == 6) {
						map.put("lan_seq", 7);
					} else if (i == 7) {
						map.put("lan_seq", 8);
					} else if (i == 8) {
						map.put("lan_seq", 9);
					} else if (i == 9) {
						map.put("lan_seq", 10);
					} else if (i == 10) {
						map.put("lan_seq", 11);
					}

					Elements files = element.get(j).getElementsByTag("img");
					String src = files.attr("src");
					map.put("l_image", src);

					Elements titles = element.get(j).getElementsByClass("course_title");
					int titleSize = titles.text().length() / 2;
					map.put("l_title", titles.text().substring(0, titleSize));

					Elements instructors = element.get(j).getElementsByClass("instructor");
					map.put("l_writer", instructors.text());

					// List<Double> star2 = Arrays.asList
					// (new Double[]{0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0});
					// 자른후 ...?
					Elements stars = element.get(j).getElementsByClass("star_solid");
					int star_size = stars.attr("style").length();
					String arr[] = stars.attr("style").split(": ");
					int end = arr[1].lastIndexOf("%");

					Double star_insert = Double.parseDouble(arr[1].substring(0, end));

					if (star_insert == (double) 0.0) {
						map.put("l_star", "0");
					} else if (star_insert <= (double) 10.0) {
						map.put("l_star", "0.5");
					} else if (star_insert <= (double) 20.0) {
						map.put("l_star", "1.0");
					} else if (star_insert <= (double) 30.0) {
						map.put("l_star", "1.5");
					} else if (star_insert <= (double) 40.0) {
						map.put("l_star", "2.0");
					} else if (star_insert <= (double) 50.0) {
						map.put("l_star", "2.5");
					} else if (star_insert <= (double) 60.0) {
						map.put("l_star", "3.0");
					} else if (star_insert <= (double) 70.0) {
						map.put("l_star", "3.5");
					} else if (star_insert <= (double) 80.0) {
						map.put("l_star", "4.0");
					} else if (star_insert <= (double) 90.0) {
						map.put("l_star", "4.5");
					} else {
						map.put("l_star", "5.0");
					}

					// 괄호 자르기
					Elements review_cnt = element.get(j).getElementsByClass("review_cnt");
					String review_cnt_insert = review_cnt.text().replaceAll("\\)|\\(", "");
					map.put("l_review", review_cnt_insert);

					Elements course_card_back = element.get(j).getElementsByClass("course_card_control");
					map.put("l_address", "https://www.inflearn.com" + course_card_back.prev().attr("href"));
					titleList.add(map);

					// 보여줄 가격
					if (prices2.text().equals("무료")) {
						map.put("l_price", prices2.text());
					} else {
						if (prices3.text().equals("")) {
							String prices_insert = prices2.text().substring(1);
							map.put("l_price", prices_insert);
						} else {
							String prices_insert = prices3.text().substring(1);
							map.put("l_price", prices_insert);
							map.put("p_seq", 2);
						}
					}
				}
			}
		}

		int result = service.insertStudy(titleList);
	}

	@RequestMapping("CrawlingGoormEdu.do")
	@Scheduled(cron = "0 45 22 * * * ")
	public void CrawlingGoormEdu() {

		List languagelist = new ArrayList();
		languagelist.add("자바");
		languagelist.add("자바스크립트");
		languagelist.add("html");
		languagelist.add("css");
		languagelist.add("spring");
		languagelist.add("파이썬"); // 40개?
		languagelist.add("vue.js");
		languagelist.add("react");
		languagelist.add("jQuery");
		// JSP 없음
		languagelist.add("bootstrap");
		// JPA 없음

		// 크롤링할 url지정
		for (int i = 0; i < languagelist.size(); i++) {
			String url = "https://edu.goorm.io/category/programming?page=1&sort=newest&classification="
					+ languagelist.get(i);
			List<Map<String, Object>> titleList = new ArrayList<>();

			Connection conn = Jsoup.connect(url);

			Document html = null;
			Document doc = null;
			try {
				html = conn.get();
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 페이지 갯수 뽑기
			Elements elements = doc.select("._1VYQb4.pagination");
			Iterator<Element> pagesize = elements.select(".page-item").iterator();
			int count = 0;
			int end = 0;
			while (pagesize.hasNext()) {
				String ps = pagesize.next().text();
				count++;
			}
			end = count - 2;


			Elements element = html.getElementsByClass("_1xnzzp");

			if (end > 1) {
				for (int z = 1; z <= end; z++) {
					url = "https://edu.goorm.io/category/programming?page=" + z + "&sort=newest&classification="
							+ languagelist.get(i);


					Connection conn2 = Jsoup.connect(url);

					Document html2 = null;
					try {
						html2 = conn2.get();
					} catch (IOException e) {
						e.printStackTrace();
					}

					element = html2.getElementsByClass("_1xnzzp");

					for (int j = 0; j < element.size(); j++) {
						Map<String, Object> map = new HashMap();
						// 키값
						Elements originalKey = element.get(j).getElementsByClass("_1xnzzp");
						String prekey = originalKey.attr("href");
						String arr[] = prekey.split("/");
						map.put("l_key", arr[2]);

						// 난이도
						Elements level = element.get(j).getElementsByClass("_4lV4wq").eq(0).tagName("span");
						String cate = level.text();

						if (cate.equals("쉬움")) {
							map.put("cate_level", "입문");
						} else if (cate.equals("보통")) {
							map.put("cate_level", "초급");
						} else {
							map.put("cate_level", "중급이상");
						}

						// 가격
						Elements pricesData = element.get(j).getElementsByClass("_3vh60A");
						String pricesData2 = pricesData.text();
						// String prices1 = pricesData2.replaceAll(",", "");
						// String prices22 = prices1.substring(1);

						if (pricesData2.equals("무료") || pricesData2.equals("\0")) {
							map.put("p_seq", 1);
						} else {
							String prices1 = pricesData2.replaceAll(",", "");
							String prices22 = prices1.substring(1);
							int prices_insert = Integer.parseInt(prices22);
							if (prices_insert < 30000) {
								map.put("p_seq", 2);
							} else if (prices_insert < 50000) {
								map.put("p_seq", 3);
							} else if (prices_insert < 100000) {
								map.put("p_seq", 4);
							} else {
								map.put("p_seq", 5);
							}
						}

						// 사이트 정적으로 추가(구름에듀)
						map.put("site_seq", 2);

						// 언어
						switch (i) {
						case 0:
							map.put("lan_seq", 1);
							break;
						case 1:
							map.put("lan_seq", 2);
							break;
						case 2:
							map.put("lan_seq", 3);
							break;
						case 3:
							map.put("lan_seq", 3);
							break;
						case 4:
							map.put("lan_seq", 4);
							break;
						case 5:
							map.put("lan_seq", 5);
							break;
						case 6:
							map.put("lan_seq", 6);
							break;
						case 7:
							map.put("lan_seq", 7);
							break;
						case 8:
							map.put("lan_seq", 8);
							break;
						case 9:
							map.put("lan_seq", 10);
							break;

						}

						// 이미지
						Elements files = element.get(j).getElementsByTag("img");
						String src = files.attr("data-src");
						map.put("l_image", src);

						// 강의제목
						Elements title = element.get(j).getElementsByClass("kV2LiJ");
						map.put("l_title", title.text());

						// 강사
						Elements instructors = element.get(j).getElementsByClass("xaJHLa");
						map.put("l_writer", instructors.text());

						// 별점
						// Elements starsData = element.get(j).getElementsByClass("_2KWt9f _3SwFuE");
						Elements starsData = element.get(j).getElementsByClass("_3SwFuE");
						String starsText = starsData.text();
						double stars = Double.parseDouble(starsText);
					
						if(stars == 0.0) {
							map.put("l_star", "0");
						} else if (stars < 1.0) {
							map.put("l_star", "0.5");
						} else if (stars < 1.1) {
							map.put("l_star", "1.0");
						} else if (stars < 1.6) {
							map.put("l_star", "1.5");
						} else if (stars < 2.1) {
							map.put("l_star", "2.0");
						} else if (stars < 2.6) {
							map.put("l_star", "2.5");
						} else if (stars < 3.1) {
							map.put("l_star", "3.0");
						} else if (stars < 3.6) {
							map.put("l_star", "3.5");
						} else if (stars < 4.1) {
							map.put("l_star", "4.0");
						} else if (stars < 4.6) {
							map.put("l_star", "4.5");
						} else {
							map.put("l_star", "5.0");
						}

						// 리뷰 수
						Elements review_cnt = element.get(j).getElementsByClass("_1kTxrO").tagName("span");
						int l_review = Integer.parseInt(review_cnt.text());
						map.put("l_review", l_review);

						// 상세보기용 주소
						Elements course_card_back = element.get(j).getElementsByClass("_1xnzzp");
						map.put("l_address", "https://edu.goorm.io" + course_card_back.attr("href"));

						// 가격 보여주기
						if (pricesData2.equals("무료") || pricesData2.equals("\0")) {
							map.put("l_price", pricesData2);
						} else {
							String prices22 = pricesData2.substring(1);
							map.put("l_price", prices22);
						}
						titleList.add(map);
					}

				}

			} else {

				// Elements element = html.getElementsByClass("_1xnzzp");
				for (int j = 0; j < element.size(); j++) {
					Map<String, Object> map = new HashMap();
					// 키값
					Elements originalKey = element.get(j).getElementsByClass("_1xnzzp");
					String prekey = originalKey.attr("href");
					String arr[] = prekey.split("/");
					map.put("l_key", arr[2]);

					// 난이도
					Elements level = element.get(j).getElementsByClass("_4lV4wq").eq(0).tagName("span");
					String cate = level.text();

					if (cate.equals("쉬움")) {
						map.put("cate_level", "입문");
					} else if (cate.equals("보통")) {
						map.put("cate_level", "초급");
					} else {
						map.put("cate_level", "중급이상");
					}

					// 가격
					Elements pricesData = element.get(j).getElementsByClass("_3vh60A");
					String pricesData2 = pricesData.text();
					// String prices1 = pricesData2.replaceAll(",", "");
					// String prices22 = prices1.substring(1);

					if (pricesData2.equals("무료") || pricesData2.equals("\0")) {
						map.put("p_seq", 1);
					} else {
						String prices1 = pricesData2.replaceAll(",", "");
						String prices22 = prices1.substring(1);
						int prices_insert = Integer.parseInt(prices22);
						if (prices_insert < 30000) {
							map.put("p_seq", 2);
						} else if (prices_insert < 50000) {
							map.put("p_seq", 3);
						} else if (prices_insert < 100000) {
							map.put("p_seq", 4);
						} else {
							map.put("p_seq", 5);
						}
					}

					// 사이트 정적으로 추가(구름에듀)
					map.put("site_seq", 2);

					// 언어
					switch (i) {
					case 0:
						map.put("lan_seq", 1);
						break;
					case 1:
						map.put("lan_seq", 2);
						break;
					case 2:
						map.put("lan_seq", 3);
						break;
					case 3:
						map.put("lan_seq", 3);
						break;
					case 4:
						map.put("lan_seq", 4);
						break;
					case 5:
						map.put("lan_seq", 5);
						break;
					case 6:
						map.put("lan_seq", 6);
						break;
					case 7:
						map.put("lan_seq", 7);
						break;
					case 8:
						map.put("lan_seq", 8);
						break;
					case 9:
						map.put("lan_seq", 10);
						break;

					}

					// 이미지
					Elements files = element.get(j).getElementsByTag("img");
					String src = files.attr("data-src");
					map.put("l_image", src);

					// 강의제목
					Elements title = element.get(j).getElementsByClass("kV2LiJ");
					map.put("l_title", title.text());

					// 강사
					Elements instructors = element.get(j).getElementsByClass("xaJHLa");
					map.put("l_writer", instructors.text());

					// 별점
					Elements starsData = element.get(j).getElementsByClass("_2KWt9f _3SwFuE");
					String starsText = starsData.text();
					double stars = Double.parseDouble(starsText);
					if (stars < 1.0) {
						map.put("l_star", "0.5");
					} else if (stars < 1.1) {
						map.put("l_star", "1.0");
					} else if (stars < 1.6) {
						map.put("l_star", "1.5");
					} else if (stars < 2.1) {
						map.put("l_star", "2.0");
					} else if (stars < 2.6) {
						map.put("l_star", "2.5");
					} else if (stars < 3.1) {
						map.put("l_star", "3.0");
					} else if (stars < 3.6) {
						map.put("l_star", "3.5");
					} else if (stars < 4.1) {
						map.put("l_star", "4.0");
					} else if (stars < 4.6) {
						map.put("l_star", "4.5");
					} else {
						map.put("l_star", "5.0");
					}

					// 리뷰 수
					Elements review_cnt = element.get(j).getElementsByClass("_1kTxrO").tagName("span");
					int l_review = Integer.parseInt(review_cnt.text());
					map.put("l_review", l_review);

					// 상세보기용 주소
					Elements course_card_back = element.get(j).getElementsByClass("_1xnzzp");
					map.put("l_address", "https://edu.goorm.io" + course_card_back.attr("href"));

					// 가격 보여주기
					if (pricesData2.equals("무료") || pricesData2.equals("\0")) {
						map.put("l_price", pricesData2);
					} else {
						String prices22 = pricesData2.substring(1);
						map.put("l_price", prices22);
					}
					titleList.add(map);
				}

			}
			int result = service.insertStudy(titleList);
			// 페이지 갯수 뽑기
		}
	}

	// 유데미 크롤링하기
	@RequestMapping("CrawlingUdemy.do")
	@Scheduled(cron = "0 45 22 * * * ")
	public void CrawlingUdemy() throws JsonProcessingException {

		// 서비스에 넘길 리스트맵
		List<Map<String, Object>> listMap = new ArrayList();
		// 강의 아이디만 보관
		// List<String> idList = new ArrayList();
		// 강의정보 보관
		List<UdemyResponse> list = new ArrayList();

		String course[] = { "6148", "4820", "7380", "4308", "6368", "6404", "7450" };
		// html, 부트스트랩, 파이썬, vue.js, javascript, jquery, react,

		for (int i = 0; i < course.length; i++) {
			UdemyResponse returnTypeData = new UdemyResponse();
			URI uri = UriComponentsBuilder.fromHttpUrl("https://www.udemy.com/api-2.0/discovery-units/all_courses/")
					.queryParam("p", "1").queryParam("lang", "ko").queryParam("page_size", "16")
					.queryParam("subcategory_id", "8").queryParam("source_page", "subcategory_page")
					.queryParam("locale", "ko_KR").queryParam("currency", "krw")
					.queryParam("navigation_locale", "ko_KR").queryParam("course_label", course[i])
					.queryParam("sos", "ps").queryParam("fl", "scat").build().toUri();
			returnTypeData = restTemplate.getForObject(uri, UdemyResponse.class);
			int page = returnTypeData.getUnit().getPagination().getTotal_page();
			list.add(returnTypeData);
		}

		for (int j = 0; j < list.size(); j++) {
			for (int z = 0; z < list.get(j).getUnit().getItems().size(); z++) {
				Map<String, Object> map = new HashMap();

				String id = Integer.toString(list.get(j).getUnit().getItems().get(z).getId());
				// idList.add(id);
				map.put("l_key", id);
				String title = list.get(j).getUnit().getItems().get(z).getTitle();
				map.put("l_title", title);
				String url = list.get(j).getUnit().getItems().get(z).getUrl();
				String l_address = "https://www.udemy.com" + url;
				map.put("l_address", l_address);
				String l_image = list.get(j).getUnit().getItems().get(z).getImage_750x422();
				map.put("l_image", l_image);
				float stars = list.get(j).getUnit().getItems().get(z).getRating();

				if (stars < (float) 1.0) {
					map.put("l_star", "0.5");
				} else if (stars < (float) 1.1) {
					map.put("l_star", "1.0");
				} else if (stars < (float) 1.6) {
					map.put("l_star", "1.5");
				} else if (stars < (float) 2.1) {
					map.put("l_stars", "2.0");
				} else if (stars < (float) 2.6) {
					map.put("l_star", "2.5");
				} else if (stars < (float) 3.1) {
					map.put("l_star", "3.0");
				} else if (stars < (float) 3.6) {
					map.put("l_star", "3.5");
				} else if (stars < (float) 4.1) {
					map.put("l_star", "4.0");
				} else if (stars < (float) 4.6) {
					map.put("l_star", "4.5");
				} else {
					map.put("l_star", "5.0");
				}

				int num_reviews = list.get(j).getUnit().getItems().get(z).getNum_reviews();
				map.put("l_review", num_reviews);
				String instructional_level = list.get(j).getUnit().getItems().get(z).getInstructional_level_simple();

				if (instructional_level.equals("모든 수준")) {
					map.put("cate_level", "입문");
				} else if (instructional_level.equals("초급")) {
					map.put("cate_level", "초급");
				} else {
					map.put("cate_level", "중급이상");
				}

				String author = list.get(j).getUnit().getItems().get(z).getAuthor();
				map.put("l_writer", author);
				// String label_title =
				// list.get(j).getUnit().getItems().get(z).getContext_info().getLabel().getTitle();

				switch (j) {
				case 0:
					map.put("lan_seq", 3);
					break;
				case 1:
					map.put("lan_seq", 10);
					break;
				case 2:
					map.put("lan_seq", 5);
					break;
				case 3:
					map.put("lan_seq", 6);
					break;
				case 4:
					map.put("lan_seq", 2);
					break;
				case 5:
					map.put("lan_seq", 8);
					break;
				case 6:
					map.put("lan_seq", 7);
					break;
				}

				URI uri2 = UriComponentsBuilder.fromHttpUrl("https://www.udemy.com/api-2.0/pricing/")
						.queryParam("course_ids", id).queryParam("fields[pricing_result]",
								"price,discount_price,list_price,price_detail,price_serve_tracking_id")
						.build().toUri();

				HttpHeaders headers = new HttpHeaders();
				HttpEntity entity = new HttpEntity(headers);

				JSONObject rateResponse = restTemplate.getForObject(uri2, JSONObject.class);
				Map<String, Map> coursesResponse = (Map<String, Map>) rateResponse.get("courses");

				UdemyPrice udemyPrice = new UdemyPrice();
				coursesResponse.keySet().forEach(key -> {
					Map<String, Map<String, Object>> object = coursesResponse.get(key);
					Map<String, Object> price = object.get("price");

					udemyPrice.setAmount((double) price.get("amount"));
					udemyPrice.setCurrency((String) price.get("currency"));
					udemyPrice.setPriceString((String) price.get("price_string"));
				});

				String price_string = udemyPrice.getPriceString();
				Double double_price = udemyPrice.getAmount();

				if (price_string.equals("Free")) {
					map.put("l_price", "무료");
				} else {
					map.put("l_price", price_string.substring(1));
				}

				// 무료인것은 amount 0
				if (double_price == (double) 0) {
					map.put("p_seq", 1);
				} else if (double_price < (double) 30000) {
					map.put("p_seq", 2);
				} else if (double_price < (double) 50000) {
					map.put("p_seq", 3);
				} else if (double_price < (double) 100000) {
					map.put("p_seq", 4);
				} else {
					map.put("p_seq", 5);
				}

				map.put("site_seq", 3);

				listMap.add(map);
			}
		}
		int result = service.insertStudy(listMap);
	}
}
