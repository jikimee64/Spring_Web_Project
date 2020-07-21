package kr.or.ns.crawling.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UdemyPrice {
	 private double amount;
	 private String currency;
	 private String priceString;
}
