package kr.or.ns.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kr.or.ns.crawling.vo.UdemyResponse;
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

	String array[] = new String[] { "java", "javascript", "html-css", "spring", "python", "vuejs", "react", "jquery",
			"jsp", "bootstrap", "java-persistence-api" };

	@RequestMapping("CrawlingInflearn9.do")
	public void CrawlingInflearn222() {
		List<Map<String, Object>> list = new ArrayList<>();
	
		System.out.println("크기" + array.length);

		for (int i = 0; i < array.length; i++) {

			// String url = "https://www.inflearn.com/courses/it-programming/web-dev"; //
			// 크롤링할 url지정
			String url = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=" + array[i]; // 크롤링할
																												// url지정

			Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

			List<Map<String, Object>> titleList = new ArrayList<>();

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
			System.out.println("endpage : " + endPage);
			if (endPage > 1) {
				for (int z = 1; z <= endPage; z++) {
					url = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=" + array[i]
							+ "page=" + z;
					System.out.println("z수 : " + z);

					Elements element = html.getElementsByClass("course_card_item");

					/* for (Element fileblock : fileblocks) { */
					for (int j = 0; j < element.size(); j++) {
						Map<String, Object> map = new HashMap();

						String key = element.get(j).attr("fxd-data");
						String key_insert = key.substring(13, 19);
						map.put("l_key", key_insert);

						Elements level = element.get(j).getElementsByClass("course_level");
						map.put("cate_level", level.text());

						// 가격자르기
						// Elements prices = element.get(j).getElementsByClass("price");
						Elements prices2 = element.get(j).getElementsByClass("price").tagName("span");
						Elements prices3 = element.get(j).getElementsByClass("pay_price");

						//System.out.println("prices2 : " + prices2);
						//System.out.println("prices3.text() : " + prices3.text());

						if (prices2.text().equals("무료")) {
							map.put("p_seq", 1);
						} else {
							if (prices3.text().equals("")) {
								//System.out.println(prices3.text());
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
								//System.out.println("price3 : " + prices3.text());
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
						//System.out.println(arr[1]);
						int end = arr[1].lastIndexOf("%");

						Double star_insert = Double.parseDouble(arr[1].substring(0, end));
						//System.out.println("별 ss: " + star_insert);

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
					}

				}

			} else {
				Elements element = html.getElementsByClass("course_card_item");

				/* for (Element fileblock : fileblocks) { */
				for (int j = 0; j < element.size(); j++) {
					Map<String, Object> map = new HashMap();

					String key = element.get(j).attr("fxd-data");
					String key_insert = key.substring(13, 19);
					map.put("l_key", key_insert);

					Elements level = element.get(j).getElementsByClass("course_level");
					map.put("cate_level", level.text());

					// 가격자르기
					// Elements prices = element.get(j).getElementsByClass("price");
					Elements prices2 = element.get(j).getElementsByClass("price").tagName("span");
					Elements prices3 = element.get(j).getElementsByClass("pay_price");

					System.out.println("prices2 : " + prices2);
					System.out.println("prices3.text() : " + prices3.text());

					if (prices2.text().equals("무료")) {
						map.put("p_seq", 1);
					} else {
						if (prices3.text().equals("")) {
							System.out.println(prices3.text());
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
							System.out.println("price3 : " + prices3.text());
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
					System.out.println(arr[1]);
					int end = arr[1].lastIndexOf("%");

					Double star_insert = Double.parseDouble(arr[1].substring(0, end));
					System.out.println("별 ss: " + star_insert);

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
				}
			}

			 int result = service.insertStudy(titleList);
			 System.out.println("온라인 삽입 결과 : " + result);
		}
	}

	// 인프런 크롤링하기
	@RequestMapping("CrawlingInflearn5.do")
	public List<String> CrawlingInflearn() {

		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
		// String url = "https://www.inflearn.com/courses/it-programming/web-dev"; //
		// 크롤링할 url지정

		// List<String> list2 = Arrays.asList(new
		// String[]{"html-css","javascript","spring","python","vuejs","react","jquery","jsp","bootstrap","java-persistence-api","java"});

		List<String> titleList = new ArrayList<>();

		String url = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=html-css%2Cjavascript%2Cspring%2Creact%2Cjquery%2Cjsp%2Cbootstrap%2Cjava-persistence-api%2Cjava%2Cpython%2Cvuejs"; // 크롤링할
																																																					// URLurl지정
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

		// for (int i = 1; i <= 1; i++) {

		String url2 = "https://www.inflearn.com/courses/it-programming/web-dev?order=seq&skill=html-css%2Cjavascript%2Cspring%2react%2Cjquery%2Cjsp%2Cbootstrap%2Cjava-persistence-api%2Cjava%2Cpython%2Cvuejs&page=2"; // 크롤링할

		try {
			doc2 = Jsoup.connect(url2).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements element2 = doc2.select(".card.course.course_card_item");

		// System.out.println(element);

//		// Iterator을 사용하여 하나씩 값 가져오기
		// Iterator<Element> imgs = element2.select(".swiper-lazy").iterator();
		Iterator<Element> titles = element2.select(".course_title").iterator();
		// Iterator<Element> authors = element2.select(".instructor").iterator();
		// Iterator<Element> prices = element2.select(".price").iterator();
//		Iterator<Element> ie2 = element.select("strong.title").iterator();

		/*
		 * while (imgs.hasNext()) { }
		 */

		while (titles.hasNext()) {
			String title = titles.next().text();
			System.out.println("타이틀 : " + title);
			titleList.add(title);
		}

		/*
		 * while (authors.hasNext()) { String author = authors.next().text();
		 * titleList.add(author); }
		 * 
		 * while (prices.hasNext()) { String price = prices.next().text();
		 * titleList.add(price); }
		 */
//
//		System.out.println("============================================================");
		System.out.println("크롤링 페이지 이동");
		// }
		return titleList;
	}

	   // 구름에듀 크롤링하기
	   @RequestMapping("CrawlingGoormEdu.do")
	   public List<Map<String, Object>> CrawlingGoormEdu() {

	      List languagelist = new ArrayList();
	      languagelist.add("자바");
	      languagelist.add("자바스크립트");
	      languagelist.add("html");
	      languagelist.add("css");
	      languagelist.add("spring");
	      languagelist.add("파이썬");
	      languagelist.add("vue.js");
	      languagelist.add("react");
	      languagelist.add("jQuery");
	      // JSP 없음
	      languagelist.add("bootstrap");
	      // JPA 없음

	      List<Map<String, Object>> titleList = new ArrayList<>();
	      String url = "";
	      for (int i = 0; i < languagelist.size(); i++) {
	         url = "https://edu.goorm.io/category/programming?page=1&sort=newest&classification=" + languagelist.get(i); // 크롤링할
	                                                                                          // url지정

	         // String url = "https://edu.goorm.io/category/programming/web-programming"; //
	         // 크롤링할 url지정

	         System.out.println("주소 : " + url);

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
	            System.out.println("ps : " + ps);
	            count++;
	         }
	         end = count - 2;

	         System.out.println("페이지네이션" + end);
	         // System.out.println("페이지 갯수" + pagination2);
	         Elements element = html.getElementsByClass("_1xnzzp");
	         if (end > 1) {
	            for (int j = 0; j < element.size(); j++) {

	               Map<String, Object> map = new HashMap();

	               // 이미지
	               Elements files = element.get(j).getElementsByTag("img");
	               String src = files.attr("data-src");
	               map.put("l_image", src);
	               System.out.println("받아온 이미지" + files);
	               System.out.println("받아온 이미지2 " + src);

	               // 키값
	               Elements originalKey = element.get(j).getElementsByClass("_1xnzzp");
	               String prekey = originalKey.attr("href");
	               System.out.println("가공 전 " + prekey);
	               String key = prekey.substring(9, 14);
	               System.out.println("가공 후 " + key);
	               map.put("l_key", key);

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
	               System.out.println("stars" + stars);
	               if (stars < 1.0) {
	                  map.put("l_star", "0.5");
	               } else if (stars < 1.1) {
	                  map.put("l_star", "1.0");
	               } else if (stars < 1.6) {
	                  map.put("l_star", "1.5");
	               } else if (stars < 2.1) {
	                  map.put("l_stars", "2.0");
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
	               System.out.println("별점" + stars);

	               // 리뷰 수
	               Elements review_cnt = element.get(j).getElementsByClass("_1kTxrO").tagName("span");
	               int l_review = Integer.parseInt(review_cnt.text());
	               map.put("l_review", l_review);

	               // 가격
	               Elements pricesData = element.get(j).getElementsByClass("_3vh60A");
	               String pricesData2 = pricesData.text();
	               System.out.println("가격 가공 전" + pricesData2);
	               // String prices1 = pricesData2.replaceAll(",", "");
	               // System.out.println("1단계 가공" + prices1);
	               // String prices22 = prices1.substring(1);
	               // System.out.println("2단계 가공" + prices22);

	               if (pricesData2.equals("무료") || pricesData2.equals("\0")) {
	                  map.put("p_seq", 1);
	               } else {
	                  String prices1 = pricesData2.replaceAll(",", "");
	                  System.out.println("가격1단계" + prices1);
	                  String prices22 = prices1.substring(1);
	                  System.out.println("가격2단계" + prices22);
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

	               // 상세보기용 주소
	               Elements course_card_back = element.get(j).getElementsByClass("_1xnzzp");
	               map.put("l_address", "https://edu.goorm.io" + course_card_back.attr("href"));

	               // 난이도
	               Elements level = element.get(j).getElementsByClass("_4lV4wq").eq(0).tagName("span");
	               map.put("cate_level", level.text());

	               // 사이트 정적으로 추가(구름에듀)
	               map.put("site_seq", 2);

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
	               titleList.add(map);
	            }

	         }
	      }
	      // 페이지 갯수 뽑기
	      System.out.println(titleList);
	      System.out.println("============================================================");
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
		Map<String, Object> titleMap = new HashMap();
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

	// 우철이가 인프런 뽑음
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

		/* for (int i = 0; i < 6; i++) { */

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

		/* for (Element fileblock : fileblocks) { */
		for (int j = 0; j < element.size(); j++) {
			Map<String, Object> map = new HashMap();
			Elements files = element.get(j).getElementsByTag("img");

			String src = files.attr("src");
			map.put("src", src);

//		// Iterator을 사용하여 하나씩 값 가져오기
			Elements titles = element.get(j).getElementsByClass("course_title");
			map.put("titles", titles.text());

			Elements instructors = element.get(j).getElementsByClass("instructor");
			map.put("instructors", instructors.text());

			Elements stars = element.get(j).getElementsByClass("star_solid");
			map.put("stars", stars.attr("style"));

			Elements review_cnt = element.get(j).getElementsByClass("review_cnt");
			map.put("review_cnt", review_cnt.text());

			Elements prices = element.get(j).getElementsByClass("price");
			map.put("prices", prices.text());

			Elements course_card_back = element.get(j).getElementsByClass("course_card_control");
			map.put("href", "https://www.inflearn.com" + course_card_back.prev().attr("href"));

			Elements level = element.get(j).getElementsByClass("course_level");
			map.put("level", level.text());

			Elements skills = element.get(j).getElementsByClass("course_skills");
			map.put("skills", skills.text());

			map.put("sites", "인프런");

			titleList.add(map);
			/*
			 * if(a == level.text()) {; }
			 */

		} // for문 끝

		return titleList;
	}
}
