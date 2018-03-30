package com.fable.industry.api.common.helper;

import com.fable.industry.api.common.sysinit.SystemParamInit;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

//import com.fable.insightview.platform.common.bpm.activiti.mgr.EnumActivitiRestType;

public class RestHepler {

	private final static Logger LOGGER = LoggerFactory.getLogger(RestHepler.class);

	public static HttpHeaders createHeaders(final String username, final String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}
	public static HttpHeaders createHeadersContenType(final String username, final String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
				set("Content-Type", "application/json; charset=utf-8");
			}
		};
	}
	/*private HttpHeaders createHeaderscontentype(final String username,
			final String password) {
		return new HttpHeaders() {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);
		headers.set("Content-Type", "application/json; charset=utf-8");
		}
		return headers;
	}*/
	/**
	 * rest 接口请求
	 * 
	 * @param url
	 *            rest请求地址
	 * @param param
	 *            请求参数
	 * @param uriVariables
	 *            url地址中参数
	 * @return 响应结果
	 */
	public static String exchange(String url, Object param, Object... uriVariables) {
		// 获取用户名和密码
		String username = SystemParamInit.getValueByKey("rest.username");
		String password = SystemParamInit.getValueByKey("rest.password");

		try {

			HttpHeaders requestHeaders = createHeaders(username, password);

			HttpEntity<Object> requestEntity = new HttpEntity<Object>(param, requestHeaders);
			RestTemplate rest = new RestTemplate();

			ResponseEntity<String> rssResponse = rest.exchange(url, HttpMethod.POST, requestEntity, String.class, uriVariables);

			if (null != rssResponse) {
				return rssResponse.getBody();
			}

		} catch (RestClientException e) {

			LOGGER.error("The request failed，msg={}", e);

		} catch (Exception e) {
			LOGGER.error("The request failed，msg={}", e);
		}

		return null;
	}
	public static String exchangeContenType(String url, Object param, Object... uriVariables) {
		// 获取用户名和密码
		String username = SystemParamInit.getValueByKey("rest.username");
		String password = SystemParamInit.getValueByKey("rest.password");

		try {

			HttpHeaders requestHeaders = createHeadersContenType(username, password);

			HttpEntity<Object> requestEntity = new HttpEntity<Object>(param, requestHeaders);
			RestTemplate rest = new RestTemplate();

			ResponseEntity<String> rssResponse = rest.exchange(url, HttpMethod.POST, requestEntity, String.class, uriVariables);

			if (null != rssResponse) {
				return rssResponse.getBody();
			}

		} catch (RestClientException e) {

			LOGGER.error("The request failed，msg={}", e);

		} catch (Exception e) {
			LOGGER.error("The request failed，msg={}", e);
		}

		return null;
	}

	/**
	 * rest 接口请求
	 * 
	 * @param url
	 *            rest请求地址
	 * @param param
	 *            请求参数
	 * @param uriVariables
	 *            url地址中参数
	 * @return 响应结果
	 */
	public static <T> T exchangeEX(String url, Class<T> resultType, Object param, Object... uriVariables) {
		// 获取用户名和密码
		String username = SystemParamInit.getValueByKey("rest.username");
		String password = SystemParamInit.getValueByKey("rest.password");
		try {
			HttpHeaders requestHeaders = createHeaders(username, password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(param, requestHeaders);
			RestTemplate rest = new RestTemplate();
			ResponseEntity<T> rssResponse = rest.exchange(url, HttpMethod.POST, requestEntity, resultType, uriVariables);
			if (null != rssResponse) {
				return rssResponse.getBody();
			}
		} catch (RestClientException e) {
			LOGGER.error("The request failed，msg={}", e);
		} catch (Exception e) {
			LOGGER.error("The request failed，msg={}", e);
		}
		return null;
	}

	public static <T> T exchange(String url, Class<T> resultType, Object params, HttpMethod method) {
		// 获取用户名和密码
		String username = SystemParamInit.getValueByKey("rest.username");
		String password = SystemParamInit.getValueByKey("rest.password");
		try {
			HttpHeaders requestHeaders = createHeaders(username, password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, requestHeaders);
			RestTemplate rest = new RestTemplate();
			ResponseEntity<T> rssResponse = rest.exchange(url, method, requestEntity, resultType);
			if (null != rssResponse) {
				return rssResponse.getBody();
			}
		} catch (RestClientException e) {
			LOGGER.error("The request failed，msg={}", e);
		} catch (Exception e) {
			LOGGER.error("The request failed，msg={}", e);
		}
		return null;
	}
}
