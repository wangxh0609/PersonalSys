package test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.docMgr.infodb.domain.DocMarInfo;
import com.hust.docMgr.infodb.service.InfodbService;

public class TestCrm {

	@Test
	public void test(){
		String xmlpath="classpath:spring/applicationContext.xml";
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext(xmlpath);
		InfodbService staffService=(InfodbService)applicationContext.getBean("infodbService");
		DocMarInfo doc=new DocMarInfo();
		doc.setAccountNum("test123");
		staffService.addInfo(doc);
		
	}
}
