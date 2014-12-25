package com.sbb.tanxin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.text.TextUtils;

public class StringUtils {
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		String regEx = "[^0-9]";
		// 清除掉所有特殊字�?
		// String regEx = "[ (+]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断给定字符串是否空白串�?br> 空白串是指由空格、制表符、回车符、换行符组成的字符串<br>
	 * 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isBlank(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static String cutEmail(String str) {
		if ("".equals(str)) {
			return str;
		}
		int index = str.indexOf("@");
		return str.substring(0, 1) + "****"
				+ str.substring(index, str.length());

	}

	/**
	 * 字符串拼�?
	 * 
	 * @param str
	 * @return
	 */
	public static String JoinString(String str, String joinStr) {
		if (str == null || str.equals("")) {
			return "";
		}
		int point = str.lastIndexOf('.');
		return str.substring(0, point) + joinStr + str.substring(point);
	}

	/**
	 * 返回str中最后一个separator子串后面的字符串 当str == null || str == "" || separator == ""
	 * 时返回str�?当separator==null || 在str中不存在子串separator 时返�?""
	 * 
	 * @param str
	 *            源串
	 * @param separator
	 *            子串
	 * @return
	 */
	public static String substringAfterLast(String str, String separator) {
		if (TextUtils.isEmpty(str) || "".equals(separator)) {
			return str;
		}

		if (separator == null) {
			return "";
		}
		int idx = str.lastIndexOf(separator);
		if (idx < 0) {
			return str;
		}

		return str.substring(idx + separator.length());
	}

	/**
	 * 去除字符串头部字�?比如 +86
	 * 
	 * @param srcStr
	 * @param head
	 * @return
	 */
	public static String cutHead(String srcStr, String head) {
		if (TextUtils.isEmpty(srcStr))
			return srcStr;
		if (srcStr.startsWith(head))
			return substringAfter(srcStr, head);
		return srcStr;
	}

	/**
	 * 返回str中separator子串后面的字符串 当str == null || str == "" || separator == ""
	 * 时返回str�?当separator==null || 在str中不存在子串separator 时返�?""
	 * 
	 * @param str
	 *            源串
	 * @param separator
	 *            子串
	 * @return
	 */
	public static String substringAfter(String str, String separator) {
		if (TextUtils.isEmpty(str) || "".equals(separator)) {
			return str;
		}

		if (separator == null) {
			return "";
		}
		int idx = str.indexOf(separator);
		if (idx < 0) {
			return "";
		}

		return str.substring(idx + separator.length());
	}

	/***
	 * 全角转半�?
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 倒叙输出�?��字符�?
	 * 
	 * @param str
	 * @return
	 */
	public static String reverseSort(String str) {
		String str2 = "";
		for (int i = str.length() - 1; i > -1; i--) {
			str2 += String.valueOf(str.charAt(i));
		}

		return str2;
	}

	/**
	 * 表情删除时使�?获取标签"�?的位�?
	 * 
	 * @param str
	 * @return
	 */
	public static int getPositionEmoj(String str) {
		String[] arr = new String[str.length()];
		for (int i = str.length() - 2; i >= 0; i--) {
			arr[i] = str.substring(i, (i + 1));
			if (":".equals(arr[i])) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * �?***替换手机号的中间四位
	 * 
	 * @param num
	 * @return
	 */
	public static String replaceNum(String num) {
		if (num.length() == 0) {
			return num;
		}
		return num.substring(0, 3) + "****"
				+ num.substring(num.length() - 4, num.length());
	}

	/**
	 * 计算位数
	 * 
	 * @param str
	 * @return
	 */
	public static double calculatePlaces(String s) {
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度�?，否则为1
		for (int i = 0; i < s.length(); i++) {
			// 获取�?��字符
			String temp = s.substring(i, i + 1);
			// 判断是否为中文字�?
			if (temp.matches(chinese)) {
				// 中文字符长度�?
				valueLength += 1;
			} else {
				// 其他字符长度�?.5
				valueLength += 0.5;
			}
		}
		// 进位取整
		return Math.ceil(valueLength);
	}

	/**
	 * 截取8位字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String cutEight(String s) {
		String string = "";
		int a = 0;
		char arr[] = s.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if ((c >= 0x0391 && c <= 0xFFE5)) // 中文字符
			{
				a = a + 2;
				string = string + c;
			} else if ((c >= 0x0000 && c <= 0x00FF)) // 英文字符
			{
				a = a + 1;
				string = string + c;
			}
			if (a == 15 || a == 16) {
				return string;
			}
		}
		return s;
	}

}