package com.jeeplus.modules.gen.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.FreeMarkers;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.mapper.JaxbMapper;
import com.jeeplus.modules.gen.entity.GenCategory;
import com.jeeplus.modules.gen.entity.GenConfig;
import com.jeeplus.modules.gen.entity.GenDict;
import com.jeeplus.modules.gen.entity.GenScheme;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.entity.GenTableColumn;
import com.jeeplus.modules.gen.entity.GenTemplate;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class a {
	private static Logger logger = LoggerFactory.getLogger(a.class);
	public static String a1 = "1";
	public static String a2 = "1.0";
	public static String a3 = "65537";
	public static String a4 = "7895676554377612126675053673296433964718538182351200665675913115728361847036411842194972769116298015398953574664037947420586168168159630635833853468128323";
	private static final String LL = "http://www.jeeplus.org";
	public static String f1 = null;

	public static String a1() {
		String a5;
		try {
			InetAddress a1 = InetAddress.getLocalHost();
			byte[] a2 = NetworkInterface.getByInetAddress(a1).getHardwareAddress();

			StringBuffer a4 = new StringBuffer();

			for (int i = 0; i < a2.length; i++) {
				if (i != 0) {
					a4.append("D");
				}

				String a3 = Integer.toHexString(a2[i] & 0xFF);
				a4.append(a3.length() == 1 ? new StringBuilder().append(0).append(a3).toString() : a3);
			}

			return a4.toString().toUpperCase();
		} catch (Exception e) {
			a5 = new StringBuilder().append(q15()).append(a16()).toString();
			if ("".equals(a5))
				return "BDBEFHSLKOMQA";
		}
		return a5;
	}

	public static String b1(String b3) {
		RSAPublicKey a1 = b.a2(a4, a3);
		String a2 = "";
		try {
			a2 = b.a6(b3, a1);
		} catch (Exception e) {
			a2 = "ERROR";
		}

		return a2;
	}

	private static int a2(String s) {
		int i = 0;
		char[] ac = s.toCharArray();
		int j = 0;
		int k = ac.length;
		while (j < k) {
			i = 31 * i + ac[j];
			j++;
		}
		return Math.abs(i);
	}

	private static String a3(String s) {
		byte[] abyte0 = s.getBytes();
		char[] ac = new char[s.length()];
		int i = 0;
		int k = abyte0.length;
		while (i < k) {
			int j = abyte0[i];
			if ((j >= 48) && (j <= 57))
				j = (j - 48 + 5) % 10 + 48;
			else if ((j >= 65) && (j <= 90))
				j = (j - 65 + 13) % 26 + 65;
			else if ((j >= 97) && (j <= 122)) {
				j = (j - 97 + 13) % 26 + 97;
			}
			ac[i] = (char) j;
			i++;
		}
		return String.valueOf(ac);
	}


	public static void a4(GenTable a1) {
		Iterator var1 = a1.getColumnList().iterator();

		while (true) {
			while (true) {
				GenTableColumn b1;
				do {
					if (!var1.hasNext()) {
						return;
					}

					b1 = (GenTableColumn) var1.next();
				} while (StringUtils.isNotBlank(b1.getId()));

				if (StringUtils.isBlank(b1.getComments())) {
					b1.setComments(b1.getName());
				}

				if (!StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "CHAR") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "VARCHAR") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "NARCHAR")) {
					if (!StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "DATETIME") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "DATE") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "TIMESTAMP")) {
						if (StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "BIGINT") || StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "NUMBER")) {
							String[] ss = StringUtils.split(StringUtils.substringBetween(b1.getJdbcType(), "(", ")"), ",");
							if (ss != null && ss.length == 2 && Integer.parseInt(ss[1]) > 0) {
								b1.setJavaType("Double");
							} else if (ss != null && ss.length == 1 && Integer.parseInt(ss[0]) <= 10) {
								b1.setJavaType("Integer");
							} else {
								b1.setJavaType("Long");
							}
						}
					} else {
						b1.setJavaType("java.util.Date");
						b1.setShowType("dateselect");
					}
				} else {
					b1.setJavaType("String");
				}

				b1.setJavaField(StringUtils.toCamelCase(b1.getName()));
				b1.setIsPk(a1.getPkList().contains(b1.getName()) ? "1" : "0");
				b1.setIsInsert("1");
				if (!StringUtils.equalsIgnoreCase(b1.getName(), "id") && !StringUtils.equalsIgnoreCase(b1.getName(), "create_by") && !StringUtils.equalsIgnoreCase(b1.getName(), "create_date") && !StringUtils.equalsIgnoreCase(b1.getName(), "del_flag")) {
					b1.setIsEdit("1");
				} else {
					b1.setIsEdit("0");
				}

				if (!StringUtils.equalsIgnoreCase(b1.getName(), "name") && !StringUtils.equalsIgnoreCase(b1.getName(), "title") && !StringUtils.equalsIgnoreCase(b1.getName(), "remarks") && !StringUtils.equalsIgnoreCase(b1.getName(), "update_date")) {
					b1.setIsList("0");
				} else {
					b1.setIsList("1");
				}

				if (!StringUtils.equalsIgnoreCase(b1.getName(), "name") && !StringUtils.equalsIgnoreCase(b1.getName(), "title")) {
					b1.setIsQuery("0");
				} else {
					b1.setIsQuery("1");
				}

				if (!StringUtils.equalsIgnoreCase(b1.getName(), "name") && !StringUtils.equalsIgnoreCase(b1.getName(), "title")) {
					b1.setQueryType("=");
				} else {
					b1.setQueryType("like");
				}

				if (StringUtils.startsWithIgnoreCase(b1.getName(), "user_id")) {
					b1.setJavaType(User.class.getName());
					b1.setJavaField(b1.getJavaField().replaceAll("Id", ".id|name"));
					b1.setShowType("userselect");
				} else if (StringUtils.startsWithIgnoreCase(b1.getName(), "office_id")) {
					b1.setJavaType(Office.class.getName());
					b1.setJavaField(b1.getJavaField().replaceAll("Id", ".id|name"));
					b1.setShowType("officeselect");
				} else if (StringUtils.startsWithIgnoreCase(b1.getName(), "area_id")) {
					b1.setJavaType(Area.class.getName());
					b1.setJavaField(b1.getJavaField().replaceAll("Id", ".id|name"));
					b1.setShowType("areaselect");
				} else if (!StringUtils.startsWithIgnoreCase(b1.getName(), "create_by") && !StringUtils.startsWithIgnoreCase(b1.getName(), "update_by")) {
					if (!StringUtils.startsWithIgnoreCase(b1.getName(), "create_date") && !StringUtils.startsWithIgnoreCase(b1.getName(), "update_date")) {
						if (!StringUtils.equalsIgnoreCase(b1.getName(), "remarks") && !StringUtils.equalsIgnoreCase(b1.getName(), "content")) {
							if (StringUtils.equalsIgnoreCase(b1.getName(), "parent_id")) {
								b1.setJavaType("This");
								b1.setJavaField("parent.id|name");
								b1.setShowType("treeselect");
							} else if (StringUtils.equalsIgnoreCase(b1.getName(), "parent_ids")) {
								b1.setShowType("input");
								b1.setQueryType("like");
							} else if (StringUtils.equalsIgnoreCase(b1.getName(), "del_flag")) {
								b1.setShowType("radiobox");
								b1.setDictType("del_flag");
							} else {
								b1.setShowType("input");
							}
						} else {
							b1.setShowType("textarea");
						}
					} else {
						b1.setShowType("dateselect");
					}
				} else {
					b1.setJavaType(User.class.getName());
					b1.setJavaField(b1.getJavaField() + ".id");
					b1.setShowType("input");
				}
			}
		}
	}

	public static String a5() {
		try {
			File a1 = new DefaultResourceLoader().getResource("").getFile();
			if (a1 != null) {
				return new StringBuilder().append(a1.getAbsolutePath()).append(File.separator)
						.append(StringUtils.replaceEach(a.class
								.getName(), new String[]{new StringBuilder().append("util.")
								.append(a.class
										.getSimpleName()).toString(), "."}, new String[]{"template", File.separator})).toString();
			}

		} catch (Exception e) {
			logger.error("{}", e);
		}

		return "";
	}

	/**
	 * xml文件转换为对象
	 * @param a1
	 * @param a2
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T a6(String a1, String a2, Class<?> clazz) {
		try {
			String a3 = "";
			if ("1".equals(a1))
				a3 = new StringBuilder().append("com/jeeplus/modules/gen/templates/form-style/modules/gen/").append(a2).toString();
			else {
				a3 = new StringBuilder().append("com/jeeplus/modules/gen/templates/dialog-style/modules/gen/").append(a2).toString();
			}
			Resource a4 = new ClassPathResource(a3);
			InputStream a5 = a4.getInputStream();
			BufferedReader a8 = new BufferedReader(new InputStreamReader(a5, "UTF-8"));

			StringBuilder a6 = new StringBuilder();
			while (true) {
				String a7 = a8.readLine();
				if (a7 == null) {
					break;
				}
				a6.append(a7).append("\r\n");
			}
			if (a5 != null) {
				a5.close();
			}
			if (a8 != null) {
				a8.close();
			}

			return (T)JaxbMapper.fromXml(a6.toString(), clazz);
		} catch (IOException e) {
			logger.warn("Error file convert: {}", e.getMessage());
		}
		return null;
	}

	public static GenConfig a7() {
		try {
			String a1 = "com/jeeplus/modules/gen/templates/config.xml";
			Resource a2 = new ClassPathResource(a1);
			InputStream a3 = a2.getInputStream();
			BufferedReader a4 = new BufferedReader(new InputStreamReader(a3, "UTF-8"));

			StringBuilder a5 = new StringBuilder();
			while (true) {
				String a6 = a4.readLine();
				if (a6 == null) {
					break;
				}
				a5.append(a6).append("\r\n");
			}
			if (a3 != null) {
				a3.close();
			}
			if (a4 != null) {
				a4.close();
			}

			return (GenConfig) JaxbMapper.fromXml(a5.toString(), GenConfig.class);
		} catch (IOException e) {
			logger.warn("Error file convert: {}", e.getMessage());
		}
		return null;
	}

	public static List<GenTemplate> a8(GenConfig a1, String a2, boolean a3, String a4) {
		List<GenTemplate> b1 = Lists.newArrayList();
		if ((a1 != null) && (a1.getCategoryList() != null) && (a2 != null)) {
			for (GenCategory e : a1.getCategoryList()) {
				if (a2.equals(e.getValue())) {
					List<String> c1 = null;
					if (!a3)
						c1 = e.getTemplate();
					else {
						c1 = e.getChildTableTemplate();
					}
					if (c1 == null) break;
					for (String s : c1) {
						if (StringUtils.startsWith(s, GenCategory.CATEGORY_REF)) {
							b1.addAll(a8(a1,
									StringUtils.replace(s, GenCategory.CATEGORY_REF, ""),
									false, a4));
						} else {
							GenTemplate a11 = (GenTemplate) a6(a4, s, GenTemplate.class);

							if (a11 != null)
								b1.add(a11);
						}
					}
					break;
				}
			}

		}

		return b1;
	}

	public static Map<String, Object> a9(GenScheme a1) {
		Map a2 = Maps.newHashMap();

		a2.put("packageName", StringUtils.lowerCase(a1
				.getPackageName()));
		a2.put("lastPackageName", StringUtils.substringAfterLast(
				(String) a2
						.get("packageName"),
				"."));
		a2.put("moduleName",
				StringUtils.lowerCase(a1
						.getModuleName()));
		a2.put("subModuleName", StringUtils.lowerCase(a1
				.getSubModuleName()));
		a2.put("className", StringUtils.uncapitalize(a1.getGenTable()
				.getClassName()));
		a2.put("ClassName", StringUtils.capitalize(a1.getGenTable()
				.getClassName()));

		a2.put("functionName", a1.getFunctionName());
		a2.put("functionNameSimple", a1.getFunctionNameSimple());
		a2.put("functionAuthor", StringUtils.isNotBlank(a1
				.getFunctionAuthor()) ? a1
				.getFunctionAuthor() :
				UserUtils.getUser().getName());
		a2.put("functionVersion", DateUtils.getDate());

		a2.put("urlPrefix", new StringBuilder().append(a2.get("moduleName"))
				.append(StringUtils.isNotBlank(a1
						.getSubModuleName()) ? new StringBuilder().append("/")
						.append(StringUtils.lowerCase(a1
								.getSubModuleName())).toString() : "").append("/")
				.append(a2
						.get("className"))
				.toString());
		a2.put("viewPrefix", a2
				.get("urlPrefix"));

		a2.put("permissionPrefix", new StringBuilder().append(a2.get("moduleName"))
				.append(StringUtils.isNotBlank(a1
						.getSubModuleName()) ? new StringBuilder().append(":")
						.append(StringUtils.lowerCase(a1
								.getSubModuleName())).toString() : "").append(":")
				.append(a2
						.get("className"))
				.toString());

		a2.put("dbType", Global.getConfig("jdbc.type"));

		a2.put("table", a1.getGenTable());

		return a2;
	}

	public static String a10(GenTemplate a1, Map<String, Object> a2, boolean a3, String a4)
			throws Exception {
		String a5 = new StringBuilder().append(a4).append(File.separator)
				.append(StringUtils.replaceEach(FreeMarkers.renderString(new StringBuilder()
						.append(a1
								.getFilePath()).append("/").toString(), a2), new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator}))
				.append(FreeMarkers.renderString(a1
						.getFileName(), a2)).toString();
		logger.debug(new StringBuilder().append(" fileName === ").append(a5).toString());

		String a6 = FreeMarkers.renderString(StringUtils.trimToEmpty(a1
				.getContent()), a2);

		logger.debug(new StringBuilder().append(" content === \r\n").append(a6).toString());

		if (a3) {
			FileUtils.deleteFile(a5);
		}

		if (FileUtils.createFile(a5)) {
			FileUtils.writeToFile(a5, a6, true);
			logger.debug(new StringBuilder().append(" file create === ").append(a5).toString());
			return new StringBuilder().append("生成成功：").append(a5).append("<br/>").toString();
		}
		logger.debug(new StringBuilder().append(" file extents === ").append(a5).toString());
		return new StringBuilder().append("文件已存在：").append(a5).append("<br/>").toString();
	}

	public static String a11(Date a2, String a1) {
		return new SimpleDateFormat(a1).format(a2);
	}

	public static synchronized String a12() {
		try {
			Locale a1 = Locale.CHINA;

			String a2 = "yyyy-MM-dd";
			SimpleDateFormat a4 = new SimpleDateFormat(a2, a1);

			Date a6 = new Date();
			a4.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			URL url = new URL("http://open.baidu.com/special/time/");

			HttpURLConnection a3 = (HttpURLConnection) url
					.openConnection();
			a3.setReadTimeout(2000);
			a3.setConnectTimeout(2000);
			a3.connect();
			long a5 = a3.getDate();
			a6 = new Date(a5);
			return a4.format(a6);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a11(new Date(), "yyyy-MM-dd");
	}

	public static String a13() {
		String b1 = "";
		try {
			File a1 = File.createTempFile("realhowto", ".vbs");
			a1.deleteOnExit();
			FileWriter a2 = new FileWriter(a1);

			String f = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";

			a2.write(f);
			a2.close();
			Process d = Runtime.getRuntime().exec(new StringBuilder().append("cscript //NoLogo ")
					.append(a1
							.getPath()).toString());

			BufferedReader a3 = new BufferedReader(new InputStreamReader(d
					.getInputStream()));
			String a4;
			while ((a4 = a3.readLine()) != null) {
				b1 = new StringBuilder().append(b1).append(a4).toString();
			}
			a3.close();
		} catch (Exception localException) {
		}
		return b1.trim();
	}

	public static String a14(String a1) {
		String a2 = "";
		try {
			File a3 = File.createTempFile("realhowto", ".vbs");
			a3.deleteOnExit();
			FileWriter a4 = new FileWriter(a3);

			String a5 = new StringBuilder().append("Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"").append(a1).append("\")\nWscript.Echo objDrive.SerialNumber").toString();

			a4.write(a5);
			a4.close();
			Process p = Runtime.getRuntime().exec(new StringBuilder().append("cscript //NoLogo ")
					.append(a3
							.getPath()).toString());

			BufferedReader input = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			String a6;
			while ((a6 = input.readLine()) != null) {
				a2 = new StringBuilder().append(a2).append(a6).toString();
			}
			input.close();
		} catch (Exception localException) {
		}
		return a2.trim();
	}

	public static String q15() {
		String a8 = "";
		try {
			//创建临时vbs脚本文件
			File a1 = File.createTempFile("tmp", ".vbs");
			a1.deleteOnExit();
			FileWriter a2 = new FileWriter(a1);
			String a3 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";

			a2.write(a3);
			a2.close();
			Process p = Runtime.getRuntime().exec(new StringBuilder().append("cscript //NoLogo ")
					.append(a1.getPath()).toString());

			BufferedReader a7 = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = a7.readLine()) != null) {
				a8 = new StringBuilder().append(a8).append(line).toString();
			}
			a7.close();
			a1.delete();
			System.out.println("a8:" + a8);
		} catch (Exception localException) {
		}
		return a8.trim();
	}

	public static String a16() {
		String result = "";
		try {
			Process a5 = Runtime.getRuntime().exec("ipconfig /all");

			InputStreamReader e = new InputStreamReader(a5
					.getInputStream());

			LineNumberReader a4 = new LineNumberReader(e);
			String b;
			while ((b = a4.readLine()) != null) {
				if (b.indexOf("Physical Address") <= 0)
					continue;
				String d = b.substring(b.indexOf("-") - 2);

				result = d;
			}

		} catch (IOException localIOException) {
		}

		return result;
	}

	public static String a18() {
		String a1 = Global.getConfig("jdbc.type");
		GenConfig a2 = a7();
		StringBuffer a3 = new StringBuffer();
		List<GenDict> a4 = a2.getMysqlFieldType();
		if (a1.equals("mysql"))
			a4 = a2.getMysqlFieldType();
		else if (a1.equals("oracle")) {
			a4 = a2.getOracleFieldType();
		}

		for (GenDict a5 : a4) {
			a3.append(new StringBuilder().append("'").append(a5.getValue()).append("',").toString());
		}
		return a3.substring(0, a3.length() - 1).toString();
	}

	static {
		f1 = a1();
	}
}