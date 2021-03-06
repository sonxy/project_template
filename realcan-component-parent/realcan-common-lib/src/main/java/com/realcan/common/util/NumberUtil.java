package com.realcan.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数字操作功能类
 * 
 * @Filename: ConvertUtil.java
 * @Version: 1.0
 * @Author: jian.mei
 *
 */
public class NumberUtil {

	/**
	 * 检查字串是否全部是数字字符
	 * 
	 * @param input 输入字符串
	 * @return 如果包含非数字字符则返回false
	 */
	public static boolean isDigital(String input) {
		if (RoUtil.isEmpty(input)) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 在数字前面补零.
	 * <p/>
	 * 在数值前面补零，整个字符串达到固定长度，主要用于银行帐号，单据编号等
	 * 
	 * @param num    转换的数值
	 * @param length 使整个串达到的长度
	 * @return 数值前面补零的固定长度的字符串
	 */
	public static String appendZero(int num, int length) {
		String pattern = "";
		for (int i = 0; i < length; i++) {
			pattern += "0";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(num);
	}

	/**
	 * 格式化数值
	 * 
	 * @param num     待格式化实型数值
	 * @param pattern 格式样式
	 * @return 符合格式的字符串
	 */
	public static String formatNumber(Number num, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(num);
	}

	/**
	 * 格式化为百分数
	 * 
	 * @param num
	 * @param digit
	 * @return String
	 */
	public static String formatPercent(double num, int digit) {
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMaximumFractionDigits(digit);
		return format.format(num);
	}

	/**
	 * 判断奇数
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isOdd(int input) {
		return input % 2 != 0;
	}

	/**
	 * 判断偶数
	 * 
	 * @param input
	 * @return isEven
	 */
	public static boolean isEven(int input) {
		return input % 2 == 0;
	}

	/**
	 * 将阿拉伯数字型的金额转换成大写金额，如211234567890.23转换后为：贰仟壹佰壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元贰角叁分
	 * <p>
	 * <b>注意：</b>最大支持小数点前12位数字，即千亿元；小数点后只保留2位，后面的位数截断。
	 * </p>
	 * 
	 * @param lower 要转换的阿拉伯数字型的金额，最大支持小数点前12位数字。
	 * @return
	 */
	public static String formatMoneyCN(double lower) {
		if (lower == 0) {
			return "零";
		}
		if (Math.abs(lower) > 1000000000000d) {
			return "金额太大！";
		}
		String money = ""; // 转换后的字符串
		boolean isNegative = false;
		if (lower < 0) {
			lower = Math.abs(lower);
			isNegative = true;
		}
		String num = "零壹贰叁肆伍陆柒捌玖";
		String[] unit = { "元", "拾", "佰", "仟", "万", "拾万", "佰万", "仟万", "亿", "拾亿", "佰亿", "仟亿" };
		String s = String.valueOf(lower); // 将金额转换为字符串
		int a = s.indexOf("+"); // 判断s是否包含'+',如1.67E+4
		int e = s.indexOf("E"); // 判断s是否包含'E',如1.67E+4
		// 如果包含'E'(该金额是以科学记数法表示,则转换成普通表示法)
		if (e != -1) {
			int index = 0; // 指数值
			if (a == -1) {
				index = Integer.parseInt(s.substring(e + 1)); // 取得指数值
			} else {
				index = Integer.parseInt(s.substring(a + 1)); // 取得指数值
			}
			String sub1 = s.substring(0, e); // 取得尾数值
			int dot = sub1.indexOf("."); // 尾数的小数点位置
			// 如果不含有小数点,则在后面补index个'0'
			if (dot == -1) {
				for (int i = 1; i <= index; i++) {
					s = sub1 + "0";
				}
			} else { // 如果含有小数点,则向后移动小数点index位
				String sub11 = sub1.substring(0, dot); // 小数点前面的字串
				String sub12 = sub1.substring(dot + 1); // 小数点后面的字串
				if (index >= sub12.length()) {
					int j = index - sub12.length();
					for (int i = 1; i <= j; i++) {
						sub12 = sub12 + "0";
					}
				} else {
					sub12 = sub12.substring(0, index) + "." + sub12.substring(index);
				}
				s = sub11 + sub12;
			}
		}
		int sdot = s.indexOf("."); // s中小数点的位置
		String beforeDot = ""; // 小数点前面的字串
		String afterDot = ""; // 小数点后面的字串
		// 如果包含小数点
		if (sdot != -1) {
			beforeDot = s.substring(0, sdot);
			afterDot = s.substring(sdot + 1);
		} else { // 不包含小数点
			beforeDot = s;
		}
		int bl = beforeDot.length();
		boolean zero = false; // 数字是否为零
		int z = 0; // '0'的个数
		// 逐位取数字
		for (int j = 0, i = bl - 1; j <= bl - 1; j++, i--) {
			int number = Integer.parseInt(String.valueOf(beforeDot.charAt(j)));
			if (number == 0) {
				zero = true;
				z++;
			} else {
				zero = false;
				z = 0;
			}
			if (zero && z == 1) {
				money += "零";
			} else if (zero) {
			} else if (!zero) {
				money += num.substring(number, number + 1) + unit[i];
			}
		}
		// 删去多余的'万'和'亿'
		for (int i = 1; i <= 2; i++) {
			String ss = "";
			if (i == 1) {
				ss = "万";
			} else {
				ss = "亿";
			}
			int last = money.lastIndexOf(ss);
			if (last != -1) {
				String moneySub1 = money.substring(0, last);
				String moneySub2 = money.substring(last, money.length());
				int last2 = moneySub1.indexOf(ss);
				while (last2 != -1) {
					moneySub1 = moneySub1.substring(0, last2) + moneySub1.substring(last2 + 1, moneySub1.length());
					last2 = moneySub1.indexOf(ss);
				}
				money = moneySub1 + moneySub2;
			}
		}
		// money中是否包含'元'
		int yuan = money.indexOf("元");
		// 如果不包含'元'
		if (yuan == -1) {
			int zi = money.lastIndexOf("零");
			// 如果最后一位字符为'零',则删除它
			if (zi == money.length() - 1) {
				money = money.substring(0, money.length() - 1) + "元"; // 在money最后加上'元'
			}
		}
		// 如果小数点后面的字串不为空,则处理'角','分'
		if (!afterDot.equals("")) {
			int al = afterDot.length();
			if (al > 2) { // 如果字串长度大于2,则截断
				afterDot = afterDot.substring(0, 2);
				al = afterDot.length();
			}
			// 如果字符串不为'0'或'00',则处理,否则不进行处理
			if (!afterDot.equals("0") && !afterDot.equals("00")) {
				// 逐位取得字符
				for (int i = 0; i < al; i++) {
					int number = Integer.parseInt(String.valueOf(afterDot.charAt(i)));
					if (number != 0 && i == 0) {
						money += num.substring(number, number + 1) + "角";
					} else if (number != 0 && i == 1) {
						money += num.substring(number, number + 1) + "分";
					} else if (number == 0 && i == 0) {
						money += "零";
					}
				}
			}
		}
		// 如果不包含'角','分'则在最后加上'整'字
		if (money.indexOf("角") == -1 && money.indexOf("分") == -1) {
			money += "整";
		}

		// 处理小于1的小数
		if (Math.abs(lower) < 1) {
			if (money.startsWith("元")) {
				money = money.substring(1);
			}
			if (money.startsWith("零")) {
				money = money.substring(1);
			}
		}
		// 处理负数
		if (isNegative) {
			money = "负" + money;
		}
		return money;
	}

	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		return b1.add(b2);
	}

	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		return b1.subtract(b2);
	}

	public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {
		return b1.multiply(b2);
	}

	public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
		return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

}
