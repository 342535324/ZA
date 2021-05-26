package com.rs.core.za.testUtil.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * HTTP请求相关
 */
public class ZAHTTPUtil {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送POST请求
	 * 
	 * @param path
	 *            完整路径
	 * @param params
	 *            参数集合 key,value
	 * @param encoding
	 *            编码格式
	 * @param SessionID
	 *            作为用户标识的sessionId 可为空
	 * @return 服务器返回得字符串
	 * @throws IOException
	 */
	public static String sendPOSTRequest(String path, Map<String, Object> params, String encoding, String SessionID)
			throws IOException {
		OutputStream out = null;
		StringBuilder sb = new StringBuilder();
		InputStream in = null;
		StringBuilder data = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				data.append(entry.getKey()).append("=");
				data.append(URLEncoder.encode(entry.getValue().toString(), encoding));
				data.append("&");
			}
			data.deleteCharAt(data.length() - 1);
		}
		byte[] entity = data.toString().getBytes("utf-8");
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(path).openConnection();

			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");

			if (SessionID != null) {
				conn.setRequestProperty("cookie", SessionID);
			}
			conn.setDoOutput(true);// 允许对外输出数据
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			conn.setRequestProperty("Content-Length", String.valueOf(entity.length));

			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
			conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

			out = conn.getOutputStream();
			out.write(entity);

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(in);
				byte[] b = new byte[4096];
				int len = 0;
				while ((len = bis.read(b)) != -1) {
					sb.append((new String(b, 0, len)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(path);
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return sb.toString();
	}

	public static InputStream getHttpImgIO(String path, String SessionID) throws IOException {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(path).openConnection();
			if (SessionID != null) {
				conn.setRequestProperty("cookie", SessionID);
			}
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为5秒
			conn.setConnectTimeout(5000);

			conn.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");

			return conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File loadHttpImg(String path, String SessionID, File f) throws IOException {
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		InputStream in = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			in = getHttpImgIO(path, SessionID);
			bis = new BufferedInputStream(in);
			fos = new FileOutputStream(f);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fos.close();
			in.close();
		}
		return f;
	}

}