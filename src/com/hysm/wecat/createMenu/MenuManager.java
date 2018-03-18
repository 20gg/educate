package com.hysm.wecat.createMenu;

 
import com.hysm.wecat.GetToken;
import com.hysm.wecat.menu.Button;
import com.hysm.wecat.menu.ClickButton;
import com.hysm.wecat.menu.ComplexButton;
import com.hysm.wecat.menu.Menu;
import com.hysm.wecat.menu.ViewButton;
import com.hysm.wecat.menu.ViewButton;
import com.hysm.wecat.util.Token;
import com.hysm.wecat.util.CommonUtil;
import com.hysm.wecat.util.MenuUtil;

public class MenuManager {
	//private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	
	//public static String  service_url ="http://www.dofei.net/jam/";
	
	public static String  service_url ="http://www.daodaketang.com/educate/";
	
	public static Menu getMenu(){
		
		ViewButton v0=new ViewButton();
		v0.setName("2017母亲节");
		v0.setType("view");
		v0.setUrl(service_url+"jsp/wx/mother/mother_share.html");
		
		/*ViewButton v1=new ViewButton();
		v1.setName("团队服务");
		v1.setType("view");
		v1.setUrl(service_url+"jsp/wx/team_service.html");
		
		ViewButton v2=new ViewButton();
		v2.setName("互联网+服务");
		v2.setType("view");
		v2.setUrl(service_url+"jsp/wx/web_service.html");*/
		
		ViewButton v3=new ViewButton();
		v3.setName("联系方式");
		v3.setType("view");
		//v3.setUrl(service_url+"jsp/wx/chengnuo.html");
		v3.setUrl("http://mp.weixin.qq.com/s/56ZG-_N0Q_1lsv6gBF_LYg");
		
		/*ViewButton v4=new ViewButton();
		v4.setName("培训与认证");
		v4.setType("view");
		v4.setUrl(service_url+"jsp/wx/train_approve.html");*/
		
		ViewButton v5=new ViewButton();
		v5.setName("服务流程");
		v5.setType("view");
		//v5.setUrl(service_url+"jsp/wx/service_flow.html");
		v5.setUrl("http://mp.weixin.qq.com/s/cejXCPg3Y3wLvLaTMfyFSA");
		
		ViewButton v7=new ViewButton();
		v7.setName("三大特色");
		v7.setType("view");
		v7.setUrl("http://mp.weixin.qq.com/s/EJyEcE50y-JTZOXRZ_JROA");
		
		ViewButton v6=new ViewButton();
		v6.setName("公司简介");
		v6.setType("view");
		//v6.setUrl(service_url+"jsp/wx/introduce.html");
		v6.setUrl("http://mp.weixin.qq.com/s/usC4ISCBWJuPEeIsDTeheQ");
		//http://mp.weixin.qq.com/s/usC4ISCBWJuPEeIsDTeheQ
		
		Button[] bb=new Button[]{(Button)v0,(Button)v3,(Button)v5,(Button)v7,(Button)v6};
		
		
		ViewButton v12=new ViewButton();
		v12.setName("客户评价");
		v12.setType("view");
		v12.setUrl(service_url+"jsp/wx/mami_baobei/kehu_pingjia.html");
		ViewButton v13=new ViewButton();
		v13.setName("宝宝留影");
		v13.setType("view");
		v13.setUrl(service_url+"jsp/wx/mami_baobei/baby_photo.html");
		ViewButton v15=new ViewButton();
		v15.setName("幸福快车");
		v15.setType("view");
		v15.setUrl(service_url+"jsp/wx/mami_baobei/news.html");
		Button[] bb2=new Button[]{(Button)v12,(Button)v13,(Button)v15};
		
		
		/*ViewButton v30=new ViewButton();
		v30.setName("客户登记");
		v30.setType("view");
		v30.setUrl(service_url+"jsp/wx/kehudengji.html");*/
		
		ViewButton v30=new ViewButton();
		v30.setName("好货商城");
		v30.setType("view");
		v30.setUrl("http://www.dofei.net/daigou/?from=groupmessage&isappinstalled=0");
		
		/*ViewButton v31=new ViewButton();
		v31.setName("关联手机号");
		v31.setType("view");
		v31.setUrl(service_url+"jsp/wx/ys/index.html");*/
		
		ViewButton v32=new ViewButton();
		v32.setName("登记预约");
		v32.setType("view");
		v32.setUrl(service_url+"/jsp/wx/appoint/my_appoint.html");
		
		ViewButton v33=new ViewButton();
		v33.setName("我的服务");
		v33.setType("view");
		v33.setUrl(service_url+"jsp/wx/my_service/service.html");
		
		ViewButton v34=new ViewButton();
		v34.setName("近期活动");
		v34.setType("view");
		v34.setUrl(service_url+"jsp/wx/huodong.html");
		 
		 
		Button[] bb3=new Button[]{(Button)v30,(Button)v32,(Button)v33,(Button)v34};
		
		
		//一级菜单
		ComplexButton b1=new ComplexButton();
		b1.setName("佳安美");
		b1.setSub_button(bb);
		ComplexButton b2=new ComplexButton();
		b2.setName("妈咪宝贝");
		b2.setSub_button(bb2);
		ComplexButton b3=new ComplexButton();
		b3.setName("我的服务"); 
	
		b3.setSub_button(bb3);
		
		Menu menu=new Menu();
		Button[] bbb=new Button[]{(Button)b1,(Button)b2,(Button)b3};
		
		
		menu.setButton(bbb);
		return menu;
	}
	
	public static Menu getMenu2(){
		
		//一级菜单
		ViewButton b1=new ViewButton();
		b1.setName("我想上课");
		b1.setType("view");
		b1.setUrl(service_url+"jsp/wx/Guyang/dinggou.html");
		 
		ViewButton b2=new ViewButton();
		b2.setName("到答课堂");
		
		b2.setType("view");
		b2.setUrl(service_url+"/jsp/pt/a_edit/show/1495714795.html");
		 
		ViewButton b3=new ViewButton();
		b3.setName("访问网站"); 
			
		b3.setType("view");
		b3.setUrl(service_url+"/jsp/pt/a_edit/show/1495714795.html");	
		
		Menu menu=new Menu();
		Button[] bbb=new Button[]{(Button)b1,(Button)b2,(Button)b3};
		
		
		menu.setButton(bbb);
		
		return menu;
	}

	public static void main(String[] args) {
		

		// 调用接口获取凭证

		if (null != GetToken.gettoken()) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu2(), GetToken.gettoken());

			// 判断菜单创建结果
			if (result){
				System.out.println("菜单创建成功！");
			}else{
				System.out.println("菜单创建失败！");
			}
		}
	}
}
