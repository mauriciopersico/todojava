package com.example.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/reports")
public class JasperController {
	@RequestMapping(value = "/files/{fileName}", method = RequestMethod.GET)
	public HttpEntity<byte[]> createPdf(
	                 @PathVariable("fileName") String fileName) throws IOException, JRException {
		//fileName no se si usarla. devolver un nombre estatico
		URL url = getClass().getResource("/reports/report1.jasper");
		String jasperFile =  url.getPath();
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,  new HashMap<String, Object>(), 
	    new JREmptyDataSource());
	    byte[] jasperBody = JasperExportManager.exportReportToPdf(jasperPrint);
		HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", "pdf"));
	    header.set("Content-Disposition",
	                   "attachment; filename=" + "report.pdf");
	    header.setContentLength(jasperBody.length);
		return new HttpEntity<byte[]>(jasperBody, header);
	  }

}
