package com.robot.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Administrator on 2017/7/5.
  */
public class StringUtils {
	public static String LOCK = "lock";

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	public static boolean isEqual(String a, String b) {
		if (isEmpty(a) || isEmpty(b))
			return false;
		return a.equals(b);
	}

	public static boolean emptyEquals(String a1, String a2) {
		if (StringUtils.isEmpty(a1)) {
			if (StringUtils.isEmpty(a2)) {
				return true;
			}
			return false;
		} else {
			return a1.equals(a2);
		}

	}

	/**
	 * 描述: 替换所有空格（包含换行）
	 *
	 * @param str
	 * @return
	 * @auther 胡义振
	 * @date 2014-9-23
	 */
	public static String replaceAllBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s|\u3000|\t|\r\n|\r|\n|\n\r");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 获取文件后缀名
	 *
	 * @param fineName 文件全名
	 * @return String
	 */
	public static String getSuffixName(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception er) {
			return fileName;
		}
	}

	/**
	 * 获取去掉后缀的文件名
	 *
	 * @param fineName 文件全名
	 * @return String
	 */
	public static String getPrefixName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/**
	 * 检查字符串是否存在（如：allStr = "25,26,28,29"， ）
	 *
	 * @param allStr     所有字符串
	 * @param checkedStr 检测的字符串
	 * @return
	 */
	public static boolean checkIncludeString(String allStr, String checkedStr) {
		try {
			if (allStr == null || allStr.trim().length() < 1) {
				return false;
			} else {
				allStr = allStr.replaceAll(" ", "");
				checkedStr = checkedStr.replaceAll(" ", "");
				String astr[] = allStr.split(",");
				for (int jj = 0; jj < astr.length; jj++) {
					if (astr[jj].toLowerCase().equals(checkedStr.toLowerCase())) {
						astr = null;
						return true;
					}
				}
				return false;
			}
		} catch (Exception er) {
			return false;
		}
	}

	public static String MapToPathParam(Map map) {
		if (map == null)
			return "";
		StringBuilder pathParam = new StringBuilder("?");
		Iterator<Entry> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry next = iterator.next();
			pathParam.append(next.getKey() + "=" + next.getValue() + "&");
		}
		return pathParam.toString();
	}

	public static String[] split(String arr, String regex) {
		arr = arr + " ";
		String[] split = arr.split(regex);
		String last = split[split.length - 1];
		split[split.length - 1] = last.substring(0, last.length() - 1);
		return split;
	}

	public static List<String> splitList(String arr, String regex) {
		arr = arr + " ";
		String[] split = arr.split(regex);
		String last = split[split.length - 1];
		split[split.length - 1] = last.substring(0, last.length() - 1);
		List<String> list = new ArrayList<>();
		for (String s : split) {
			list.add(s);
		}
		return list;
	}

	public static String join(List<String> arr, String split) {
		if (arr == null || arr.isEmpty())
			return "";
		else {
			StringBuilder sb = new StringBuilder();
			for (String s : arr) {
				sb.append(s + split);
			}
			return sb.substring(0, sb.length() - split.length()).toString();
		}

	}

	public static String join(String[] arr, String split) {
		if (arr == null || arr.length == 0)
			return "";
		else {
			StringBuilder sb = new StringBuilder();
			for (String s : arr) {
				sb.append(s + split);
			}
			return sb.substring(0, sb.length() - split.length()).toString();
		}

	}

	public static byte[] gzipCompress(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 压缩
		GZIPOutputStream gos = new GZIPOutputStream(baos);
		gos.write(data, 0, data.length);
		gos.finish();
		baos.flush();
		baos.close();
		return baos.toByteArray();
	}

	public static int searchPosition(String target, String object, String split) {
		if (isEmpty(target) || isEmpty(object))
			return -1;
		String[] arr = split(target, split);
		for (int i = 0; i < arr.length; i++) {
			if (object.equals(arr[i]))
				return i;
		}
		return -1;
	}

	public static String valueOf(Object o) {
		if (o == null)
			return null;
		else
			return String.valueOf(o);
	}

	public static String convert(String str, int length) {
		if (str.length() < length) {
			String temp = "";
			for (int i = 0; i < length - str.length(); i++) {
				temp += "0";
			}
			return temp + str;
		}
		return str;
	}

	public static String toBinaryString(int a, int length) {
		return convert(Integer.toBinaryString(a), length);
	}

	public static String toHexString1(byte[] b) {
		return toHexString1(b, " ");
	}

	public static String toHexString1(byte[] b, String split) {
		StringBuffer buffer = new StringBuffer();
		int n = 0;
		for (int i = 0; i < b.length; ++i) {
			buffer.append(toHexString1(b[i]));
			if (i < b.length - 1)
				buffer.append(split);
		}
		return buffer.toString();
	}

	public static String toHexString1(byte b) {
		String s = Integer.toHexString(b & 0xFF);
		if (s.length() == 1) {
			return "0" + s;
		} else {
			return s;
		}
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 虚拟表（查询虚拟表）
	 *
	 * @param length   虚拟表行数
	 * @param lineName 虚拟表列名
	 * @return
	 * @throws ParseException
	 */
	public static String virtualTableSql(int length, String lineName) throws ParseException {
		String string = "select 1 as " + lineName;
		for (int i = 2; i <= length; i++) {
			string = string + " union all select " + i;
		}
		if (length < 1) {
			string = "";
		}
		return string;
	}

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 *
	 * @param inputString 原始字符串
	 * @param length      指定长度
	 * @return
	 */
	public static String[] getStrList(String inputString, int length) {
		int size = inputString.length() / length;
		if (inputString.length() % length != 0) {
			size += 1;
		}
		return getStrList(inputString, length, size);
	}

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 *
	 * @param inputString 原始字符串
	 * @param length      指定长度
	 * @param size        指定列表大小
	 * @return
	 */
	public static String[] getStrList(String inputString, int length,
									  int size) {
		String[] list = new String[size];
		for (int index = 0; index < size; index++) {
			String childStr = substring(inputString, index * length,
					(index + 1) * length);
			list[index] = childStr;
		}
		return list;
	}

	/**
	 * 分割字符串，如果开始位置大于字符串长度，返回空
	 *
	 * @param str 原始字符串
	 * @param f   开始位置
	 * @param t   结束位置
	 * @return
	 */
	public static String substring(String str, int f, int t) {
		if (f > str.length())
			return null;
		if (t > str.length()) {
			return str.substring(f, str.length());
		} else {
			return str.substring(f, t);
		}
	}

	public static String random4number() {
		String num = "";
		for (int i = 0; i < 4; i++) {
			int n = (int) (Math.random() * 10);
			num += n;
		}
		return num;
	}

	public static String randomNumber(Integer count) {
		String num = "";
		for (int i = 0; i < count; i++) {
			int n = (int) (Math.random() * 10);
			num += n;
		}
		return num;
	}

	public static String randomValue() {
		return yyyyMMddHHmmss.format(new Date()) + random4number();
	}
//    public static void main(String[] args) {
//    	System.out.println(random4number());
//	}


	private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * date转string
	 *
	 * @param data
	 * @param sdf
	 * @return
	 */
	public static String dateToString(Date date, SimpleDateFormat sdf) {
		String value = "";
		if (date != null) {
			if (sdf == null) {
				value = simpleDateFormat.format(date);
			} else {
				value = sdf.format(date);
			}
		}
		return value;
	}

	public static String dateToString(Date date, String string) {
		SimpleDateFormat sdf = new SimpleDateFormat(string);
		return dateToString(date, sdf);
	}

	public static <T> T ifnull(T t, T o) {
		if (t == null)
			return o;
		return t;
	}

	/**
	 * 判断字符串中是否包含中文
	 *
	 * @param str 待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static String MapToPath(Map map) {
		StringBuilder pathParam = new StringBuilder("?");
		Iterator<Entry> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry next = iterator.next();
			pathParam.append(next.getKey() + "={" + next.getKey() + "}&");
		}
		return pathParam.toString();
	}

	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		//49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			//grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			//convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			//convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}

		return sb.toString();
	}

	/*
	 * 删除末尾字符串
	 */
	public static String trimEnd(String inStr, String suffix) {
		while (inStr.endsWith(suffix)) {
			inStr = inStr.substring(0, inStr.length() - suffix.length());
		}
		return inStr;
	}

	/**
	 * * 判断一个对象是否为空
	 *
	 * @param object Object
	 * @return true：为空 false：非空
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * * 判断一个Collection是否为空， 包含List，Set，Queue
	 *
	 * @param coll 要判断的Collection
	 * @return true：为空 false：非空
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return isNull(coll) || coll.isEmpty();
	}

	public static boolean isEmpty(Date n) {
		return n == null;
	}

	public static boolean isEmpty(Integer n) {
		return n == null;
	}

	public static boolean isEmpty(Long n) {
		return n == null;
	}

	public static boolean isEmpty(Double n) {
		return n == null;
	}

	public static boolean isEmpty(BigDecimal n) {
		return n == null;
	}

	public static String intToBinary32(int i, int bitNum) {
		String binaryStr = Integer.toBinaryString(i);
		while (binaryStr.length() < bitNum) {
			binaryStr = "0" + binaryStr;
		}
		return binaryStr;
	}
}
