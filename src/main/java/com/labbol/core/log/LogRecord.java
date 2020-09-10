/**
 * 
 */
package com.labbol.core.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
/**
 * 记录日志 <br/>
 * 
 * 标注此注解的Controller或者处理器将根据设置进行记录日志
 * 
 * @author PengFei
 */
public @interface LogRecord {

	/**
	 * 是否记录日志
	 * 
	 * @return <code>true</code> 记录日志
	 */
	boolean isRecordLog() default true;

	/**
	 * @return 操作的模块
	 */
	String operModule() default "";

	/**
	 * @return 操作人员姓名
	 */
	String userName() default "";

	/**
	 * @return 日志描述
	 */
	String logDesc() default "";

	/**
	 * @return 事件类型
	 */
	String eventType() default "";
}
