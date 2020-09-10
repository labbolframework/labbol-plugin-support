/**
 * 
 */
package com.labbol.core.filesc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.support.servlet.HttpServletUtils;
import org.yelong.support.spring.mvc.interceptor.AbstractHandlerInterceptor;

import com.labbol.core.platform.scip.model.SCIP;
import com.labbol.core.platform.scip.utils.SCIPUtils;
import com.labbol.core.service.LabbolModelService;
import com.labbol.core.utils.MultipartRequestUtilsE;

/**
 * 文件上传IP段验证。在涉密的IP段中无法上传文件
 * 
 * @author PF
 */
public class FileUploadSCIPInterceptor extends AbstractHandlerInterceptor {

	@Resource
	private LabbolModelService modelService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		MultipartRequest multipartRequest = MultipartRequestUtilsE.getMultipartRequest(request);
		if (null == multipartRequest) {
			return true;
		}
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		if (MapUtils.isEmpty(fileMap)) {
			return true;
		}
		//如果表单中存在文件，但是没有选择文件，Map中还是会存在记录的。但是文件没有名称，所有如果所有上传来的文件名称为空代表没有上传文件
		if (!fileMap.values().stream().allMatch(x -> !StringUtils.isBlank(x.getOriginalFilename()))) {
			return true;
		}
		List<SCIP> scips = modelService.collect(ModelCollectors.findBySingleColumnEQ(SCIP.class, "ipType", "01"));
		if (CollectionUtils.isEmpty(scips)) {
			return true;
		}
		String userIp = HttpServletUtils.getIpAddrByNginxReverseProxy(request);
		if (SCIPUtils.contains(scips, userIp)) {
			throw new FileUploadSCIPException("涉密网无法上传文件！");
		}
		return true;
	}

}
