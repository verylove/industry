package com.fable.industry.api.exception;

import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author fangang 异常处理基类
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String BUSSINESS_EXCEPTION_FILE = "/msg/rescodes.properties";

	protected static Properties exceptionLocaleProperties = new Properties();

	private Integer code;

	private Object[] args;

	private String message;

	private static Logger logger = LoggerFactory.getLogger(BaseException.class);

	static {
		// 装载业务异常配置文件
		try {
			Resource resource = new ClassPathResource(BUSSINESS_EXCEPTION_FILE);
			exceptionLocaleProperties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			logger.error("装载业务异常配置文件失败，请检查业务异常配置文件exception.properties是否存在。");
		}
	}

	/**
	 * 默认构造函数
	 */
	public BaseException() {
		super();
	}
	/**
	 * @param message 错误消息
	 * @param cause 异常原因
	 */
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * @param message 错误消息
	 */
	public BaseException(String message) {
		super(message);
	}
	/**
	 * @param cause 异常根原因
	 */
	public BaseException(Throwable cause) {
		super(cause);
	}
	/**
	 *
	 * @param code 错误编码
	 * @param message 错误消息
	 * @param cause 异常原因
	 */
	public BaseException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	/**
	 *
	 * @param code 错误编码
	 * @param message 错误消息
	 */
	public BaseException(int code, String message) {
		super(message);
		this.code = code;
	}
	/**
	 *
	 * @param code 错误编码
	 * @param cause 异常原因
	 */
	public BaseException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	/**
	 * 取得错误编码
	 * @return
	 */
	public int getCode() {
		return code;
	}
	/**
	 * 设置错误编码
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		if (getExceptionMessage() != null) {
			// return getCode() + ":" + getExceptionMessage();
			return getExceptionMessage();
		}

		// return getCode() + ":" + getLocalizedMessage();
		return getLocalizedMessage();
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return
	 */
	public Object[] getArgs() {
		return this.args;
	}

	/**
	 * @return
	 */
	public String getExceptionMessage() {
		return this.message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	public String getLocalizedMessage() {
		if (getCode() == 0) {
			logger.error("异常码为空，请检查抛出该异常的构造函数是否正确。");
		}

		if (("000000").equals(this.code)) {
			return MessageFormat.format(this.getCause().getMessage(), getArgs());
		}

		String message = exceptionLocaleProperties.getProperty(getMessage());
		if (message == null) {
			logger.error("异常码[" + getCode() + "]没有定义，请检查抛出该异常的构造函数是否正确。\r\n" + this.getCause().getMessage());
		}

		return MessageFormat.format(message, getArgs());
	}
}
