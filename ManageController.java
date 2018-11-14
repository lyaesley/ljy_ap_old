package com.tsp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.util.ConvUtil;
import com.tsp.common.Common;
import com.tsp.common.CspResult;
import com.tsp.common.ExcelSimpleUtil;
import com.tsp.common.ExcelServletWriter;
import com.tsp.common.aspect.IdmsLog;
import com.tsp.common.idms.IdmsView;
import com.tsp.service.CspService;
import com.tsp.service.ManageService;
import com.tsp.service.StatService;
import com.tsp.user.TempAssignRateInfo;
import com.tsp.user.Userinfo;
import com.tsp.util.AdPaging;




@Controller
public class ManageController {
	
	protected Common common = new Common();
	
	
	private final Logger logger = Logger.getLogger(getClass());

	
	//서비스 하나 생성 할 것.
	@Autowired
	protected ManageService manageService;
	
	@Autowired
	protected StatService statService;
	
	@Autowired CspService cspService;

	 /**
     * @Method Name : assignManageReg
     * @작성일 : 2012. 2. 6.
     * @작성자 : 황신희
     * @변경이력 :
     * @Method 설명 : 배정관리
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    
    @RequestMapping("/Manage/assignRegist.do")
    public String assignManageReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String[] assignVal = request.getParameterValues("assignVal");
    	String[] storeTyp = request.getParameterValues("storeTyp");
    	
    	HashMap param = new HashMap();
    	
    	  System.out.println("assignVal.length-------"+assignVal.length);  	
		for(int i=0;i<assignVal.length;i++){
				
			
			param.put("assn_rt", assignVal[i]);
			param.put("store_typ", storeTyp[i]);
			boolean resultVal = manageService.setUpAssignManage(param);
			
			
		}
    	    	
		return "asg_manage/new_assing_upAssignManage_succ";
        
    }
    
    @RequestMapping("/Manage/assignManage.do")
    public String assignManage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	logger.info("assignManage.do 진입.");
    	
    	String nowyear = request.getParameter("nowyear") == null ?     "" : request.getParameter("nowyear");
        String nowmonth = request.getParameter("nowmonth") == null ?   "" : request.getParameter("nowmonth");
        String storetype = request.getParameter("storetype") == null ? "" : request.getParameter("storetype");
        String scDate = nowyear+nowmonth;	
        String pageNo = request.getParameter("pageNo") == null ? "1" : request.getParameter("pageNo");
        
        int perPage = 10;
        int startidx = (Integer.parseInt(pageNo) - 1) * perPage;
        int endidx = Integer.parseInt(pageNo) * perPage;
                
    	List list_month = manageService.getScMonth();        
        List list_year = manageService.getScYear();  
        
        HashMap param = new HashMap();
        param.put("startidx", startidx);
        param.put("endidx", endidx);
        param.put("scDate", scDate);
        
        List manageHiList = manageService.getAssignManageHiList(param);        
        List manageList = manageService.getAssignManageList();
       
        int totalcount = manageHiList.size();
        int listPage = 10;
        String paging = AdPaging.makePaging(pageNo, totalcount, perPage, listPage);
        
        
        model.addAttribute("manageHiList", manageHiList);
        model.addAttribute("manageList", manageList);
        model.addAttribute("map_month", list_month);
        model.addAttribute("map_year", list_year);
        model.addAttribute("scDate", scDate);
        model.addAttribute("paging", paging);
        
    	return "asg_manage/new_assign_manager";
    }
    
    
    
    @RequestMapping("/Manage/dataManage.do")
    public String DataManage(){
    	
    	logger.info("통계메뉴 진입");
    	
    	
    	return "asg_manage/data_mamage";
    }
    
    
    /*
     * 2013.02.22 통합상담이력 메뉴 결합  by Teddy84
     */
    @RequestMapping("/Manage/historyManage.do")
    public String HistoryManage(){
    	logger.info("개선된 통합상담이력 메뉴 진입");
    	
    	return "asg_manage/history_manage";
    }
    
    
    
    
    
    @RequestMapping("/Manage/assign_data_xls.do")
    public String assignlistXls(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.info("배정 데이터 통계 추출 Controller");
    	
    	String getDate = request.getParameter("getDate")==null ? "" : request.getParameter("getDate");
    	getDate = common.htmlToText(getDate);

    	
    	
    	HashMap param = new HashMap();
        param.put("getDate", getDate);
        
        List list = manageService.getAssignData(getDate);
        model.addAttribute("list", list);
    	
        return "asg_manage/assign_data_xls";
    }
    
    
    @RequestMapping("/Manage/reward_data_xls.do")
    public String rewardlistXls(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	logger.info("보상 데이터 통계 추출 Controller");
    	
    	String getDate = request.getParameter("getDate")==null ? "" : request.getParameter("getDate");
    	getDate = common.htmlToText(getDate);
    	
    	HashMap param = new HashMap();
        param.put("getDate", getDate);
        
        List list = manageService.getRewardData(getDate);
        model.addAttribute("list", list);
    	
    	
    	return "asg_manage/reward_data_xls";
    }
    
    
    @RequestMapping("/Manage/error_data_xls.do")
    public String errorlistXls(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	logger.info("신규가입 오류 데이터 추출 Controller");
    	
    	String getDate = request.getParameter("getDate")==null ? "" : request.getParameter("getDate");
    	getDate = common.htmlToText(getDate);
    	
    	HashMap param = new HashMap();
        param.put("getDate", getDate);
        
        List list = manageService.getErrorData(getDate);
        model.addAttribute("list",list);
        
        
    	return "asg_manage/error_data_xls";
    }
    
    // Daily Data 추출
    @RequestMapping("/Manage/total_data_xls.do")
    public String totallistXls(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	logger.info("통계데이터 추출  Controller");
    	
    	String getDate = request.getParameter("getDate")==null ? "" : request.getParameter("getDate");
    	getDate = common.htmlToText(getDate);
    	
    	String fileName = "ML01.SKMC."+getDate+"_01";
    	logger.info("통계데이터 추출 파일 명 : "+fileName);
    	
    	HashMap param = new HashMap();
        param.put("fileName", fileName);
    	
        List list = manageService.getTotalData(fileName);
        List asPackList = manageService.getASPackTotalData(getDate);
        
        
        list.addAll(asPackList);
        model.addAttribute("list",list);
        
        
    	return "asg_manage/total_data_xls";
    }
    
    
    
    // Daily 가입 Data 추출
    @RequestMapping("/Manage/member_count_xls.do")
    public String countlistXls(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	logger.info("가입자 수 데이터 추출 Controller");
    	
    	String getDate = request.getParameter("getDate")==null ? "" : request.getParameter("getDate");
    	getDate = common.htmlToText(getDate);
    	
    	HashMap param = new HashMap();
        param.put("getDate", getDate);
        
        List list = manageService.getCountData(getDate);
        model.addAttribute("list",list);
    	
    	return "asg_manage/count_data_xls";
    }
    
    @RequestMapping({"/Manage/member_count_SS_xls.do"})
    public String countlistSsXls(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
    {
      this.logger.info("삼성화재 가입자 수 데이터 추출 Controller");

      String getDate = request.getParameter("getDate") == null ? "" : request.getParameter("getDate");
      getDate = Common.htmlToText(getDate);

      HashMap param = new HashMap();
      param.put("getDate", getDate);

      List list = this.manageService.getCountSsData(getDate);
      model.addAttribute("list", list);

      return "asg_manage/count_data_xls";
    }

    @RequestMapping({"/Manage/member_count_ME_xls.do"})
    public String countlistMeXls(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
    {
      this.logger.info("메리츠 가입자 수 데이터 추출 Controller");

      String getDate = request.getParameter("getDate") == null ? "" : request.getParameter("getDate");
      getDate = Common.htmlToText(getDate);

      HashMap param = new HashMap();
      param.put("getDate", getDate);

      List list = this.manageService.getCountMeData(getDate);
      model.addAttribute("list", list);

      return "asg_manage/count_data_xls";
    }
    
    @RequestMapping("/Manage/storeChgManage.do")
    public String storeChg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	logger.info("storeChgManage.do 진입.");
    	
    	String store_id = request.getParameter("chg_store_id") == null ?   "" : request.getParameter("chg_store_id");
        String store_yn = request.getParameter("chg_store_state") == null ?   "" : request.getParameter("chg_store_state");
        
        
        HashMap param = new HashMap();
        param.put("store_id", store_id);
        param.put("store_yn", store_yn);

        manageService.chgStoreState(param);
        
    	
    	return "asg_manage/new_store_state_update";
    }
    
    
    
    @RequestMapping("/Manage/mobileManage.do")
    public String mobileManage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    
    	logger.info("모바일 인증관리 mobileManage.do 진입.");
    	
    	String getDate = request.getParameter("getDate") == null ?     "" : request.getParameter("getDate");
        String getSvc_num = request.getParameter("getSvc_num") == null ?   "" : request.getParameter("getSvc_num");
    	
        
        HashMap param = new HashMap();
        param.put("getDate", getDate);
        param.put("svc_num", getSvc_num);
        
        List errorInfoList = manageService.getErrorInfoList(param);
        List sendSmsList = manageService.getSendSmsList(param);
        int sendSmsListCnt = manageService.getSendSmsListCnt(param);
        List successSmsList = manageService.getSuccessSmsList(param);
        List pageAgreeList = manageService.getPageAgreeList(param);
        List finalJoinList = manageService.getFinalJoinList(param);

        model.addAttribute("errorInfoList", errorInfoList);
        model.addAttribute("sendSmsList", sendSmsList);
        model.addAttribute("sendSmsListCnt", sendSmsListCnt);
        model.addAttribute("successSmsList", successSmsList);
        model.addAttribute("pageAgreeList", pageAgreeList);
        model.addAttribute("finalJoinList", finalJoinList);

        
    	return "asg_manage/mobile_manage";
    }
    
    @RequestMapping("/Manage/mobileMstManage.do")
    public String mobileTwoManage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
      this.logger.info("모바일 인증관리 mobileMstManage.do 진입.");

      String startDate = request.getParameter("startDate") == null ? "" : request.getParameter("startDate");
      String endDate = request.getParameter("endDate") == null ? "" : request.getParameter("endDate");
      String getSvc_num = request.getParameter("getSvc_num") == null ? "" : request.getParameter("getSvc_num");
      
      HashMap param = new HashMap();
      param.put("startDate", startDate);
      param.put("endDate", endDate);
      param.put("svc_num", getSvc_num);

      List errorInfoList = manageService.getErrorInfoList3(param);
      List errorInfoList_three = manageService.getErrorInfoList4(param);
      
      List finalJoinList = manageService.getFinalJoinList3(param);
      List finalJoinList_three = manageService.getFinalJoinList4(param);
      List sendSmsList_three = manageService.getSendSmsList3(param);
      
      List ocbCmplList = manageService.getOcbCmplList4(param);
      List jobTryList = manageService.getJobTryList(param);
      List pageAgreeList = manageService.getPageAgreeList4(param);
      
     /* List sendSmsList = manageService.getSendSmsList2(param);
      List cnfCnplList = manageService.getCnfCnplList(param);
      List jobTryList = manageService.getJobTryList(param);
      List ocbCmplList = manageService.getOcbCmplList(param);
      List pageAgreeList = manageService.getPageAgreeList2(param);*/
      
/*      int sendSmsListCnt = manageService.getSendSmsListCnt2(param);
      int cnfCnplListCnt = manageService.getCnfCnplListCnt(param);
*/   
      
      model.addAttribute("startDate", startDate);
      model.addAttribute("endDate", endDate);
      model.addAttribute("svcNum", getSvc_num);
      
      model.addAttribute("t_errorInfoList", errorInfoList);
      model.addAttribute("t_finalJoinList", finalJoinList);
      model.addAttribute("p3_errorInfoList", errorInfoList_three);
      model.addAttribute("p3_finalJoinList", finalJoinList_three);
      model.addAttribute("p3_sendSmsList", sendSmsList_three);
      
      model.addAttribute("jobTryList", jobTryList);
      model.addAttribute("ocbCmplList", ocbCmplList);
      model.addAttribute("pageAgreeList", pageAgreeList);      
      
      /*model.addAttribute("sendSmsList", sendSmsList);
      model.addAttribute("sendSmsListCnt", sendSmsListCnt);
      model.addAttribute("cnfCnplList", cnfCnplList);
      model.addAttribute("cnfCnplListCnt", cnfCnplListCnt);
      model.addAttribute("jobTryList", jobTryList);
      model.addAttribute("ocbCmplList", ocbCmplList);
      model.addAttribute("pageAgreeList", pageAgreeList);*/
      
      //수정사항
      return "asg_manage/mobileMst_manage";
    }
    
    
    @RequestMapping({"/Manage/mobileTwoManage.do"})
    public String mobileMstManage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
      this.logger.info("모바일 인증관리 mobileTwoManage.do 진입.");

      String getDate = request.getParameter("startDate") == null ? "" : request.getParameter("startDate");
      String getSvc_num = request.getParameter("getSvc_num") == null ? "" : request.getParameter("getSvc_num");

      HashMap param = new HashMap();
      param.put("getDate", getDate);
      param.put("svc_num", getSvc_num);

      List errorInfoList = manageService.getErrorInfoList2(param);
      List sendSmsList = manageService.getSendSmsList2(param);
      List finalJoinList = manageService.getFinalJoinList2(param);
      List cnfCnplList = manageService.getCnfCnplList(param);
      List jobTryList = manageService.getJobTryList(param);
      List ocbCmplList = manageService.getOcbCmplList(param);
      List pageAgreeList = manageService.getPageAgreeList2(param);
      
      int sendSmsListCnt = manageService.getSendSmsListCnt2(param);
      int cnfCnplListCnt = manageService.getCnfCnplListCnt(param);

      model.addAttribute("errorInfoList", errorInfoList);
      model.addAttribute("finalJoinList", finalJoinList);
      model.addAttribute("sendSmsList", sendSmsList);
      model.addAttribute("sendSmsListCnt", sendSmsListCnt);
      model.addAttribute("cnfCnplList", cnfCnplList);
      model.addAttribute("cnfCnplListCnt", cnfCnplListCnt);
      model.addAttribute("jobTryList", jobTryList);
      model.addAttribute("ocbCmplList", ocbCmplList);
      model.addAttribute("pageAgreeList", pageAgreeList);
      
      //수정사항
      return "asg_manage/mobileTwo_manage";
    }
    
    // Daily 입출고 Data 추출
    @RequestMapping("/Manage/inven_data_xls.do")
    public void invenData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	logger.info("입출고 관리 대장 Controller");
    	
    	String getDate = request.getParameter("getDate")==null ? "" : request.getParameter("getDate");
    	getDate = common.htmlToText(getDate);
    	
    	HashMap param = new HashMap();
        param.put("getDate", getDate);        
        
        List<LinkedHashMap<String,String>> invenDataList = manageService.getInvenData(getDate);        
        List<LinkedHashMap<String,String>> notCompInvenDataList =  manageService.getNotCompInvenData(invenDataList);
        
        ObjectMapper mapper = new ObjectMapper();
        CspResult cspResult = new CspResult();
        List<LinkedHashMap<String, Object>> resultList = new ArrayList<>();
        
        for(int i=0; i<invenDataList.size(); i++){
         	int row = i+2;         	
         	
         	String releaseDy = invenDataList.get(i).get("RELEASE_DY").replaceFirst("([\\d]{4})([\\d]{2})([\\d]{2})", "$1-$2-$3");
         	String cspResData = manageService.getCspData(invenDataList.get(i).get("SVC_MGMT_NUM"));
         	cspResult = cspResData != null ? mapper.readValue(cspResData, CspResult.class) : new CspResult();
         	
         	// MIN(가입단말기 출고가, 보상잔여한도, 본상단말기) - 상품의 자기부담금
         	String insuPrice = "MIN(J"+ row +", L" + row + ", P" + row + ") - O" + row;
         	
         	LinkedHashMap<String, Object> map = new LinkedHashMap<>();			
 			map.put("index", i+1);
 			map.put("releaseDy", releaseDy);
 			map.put("dealType", invenDataList.get(i).get("DEAL_TYPE"));
 			map.put("releaseYn", invenDataList.get(i).get("RELEASE_YN"));    			
 			map.put("switcherNm", invenDataList.get(i).get("SWITCHER_NM"));
 			map.put("switcher", invenDataList.get(i).get("SWITCHER"));
 			map.put("svcMgmtNum", invenDataList.get(i).get("SVC_MGMT_NUM"));
 			map.put("joinProdNm", invenDataList.get(i).get("JOIN_PROD_NM"));
 			map.put("joinMdl", invenDataList.get(i).get("JOIN_MDL"));
 			map.put("eqpOutAmt", cspResult.getEqpOutAmt() == null ? "" :Integer.parseInt(cspResult.getEqpOutAmt()));
 			map.put("compMdlNm", invenDataList.get(i).get("COMP_MDL_NM"));
 			map.put("compMdlAmt", invenDataList.get(i).get("COMP_MDL_PRICE"));
 			map.put("color", invenDataList.get(i).get("COLOR"));
 			map.put("diffPrc", Integer.parseInt(invenDataList.get(i).get("DIFF_PRC")));
 			map.put("sharePrc", Integer.parseInt(invenDataList.get(i).get("SHARE_PRC")));
 			map.put("totRemAmt", cspResult.getTotPrtCmpRemAmt() == null ? "" :Integer.parseInt(cspResult.getTotPrtCmpRemAmt()) 
 					+cspResult.getTotLossCmpRemAmt() == null ? "" : Integer.parseInt(cspResult.getTotLossCmpRemAmt())
 					+cspResult.getPrtLossCmpRemAmt() == null ? "" : Integer.parseInt(cspResult.getPrtLossCmpRemAmt()));
 			map.put("usim", Integer.parseInt(invenDataList.get(i).get("USIM")));
 			map.put("totPrc", Integer.parseInt(invenDataList.get(i).get("TOT_PRC")));
 			map.put("insuPrice", insuPrice);
 			map.put("appType", invenDataList.get(i).get("APP_TYPE"));
 			map.put("invoiceNum", invenDataList.get(i).get("INVOICE_NUM"));
 			map.put("reJoinYn", invenDataList.get(i).get("REJOIN_YN"));
 			map.put("insuCoCd", invenDataList.get(i).get("INSU_CO_CD"));
 			
 			if("A0037".equals(invenDataList.get(i).get("ETC1"))){
 				Boolean notCompInvenFlag = false;
 				
				for(int j=0; j<notCompInvenDataList.size(); j++) {
					if( invenDataList.get(i).get("CLAIM_ID").equals(notCompInvenDataList.get(j).get("CLAIM_ID")) 
							&& invenDataList.get(i).get("SVC_MGMT_NUM").equals(notCompInvenDataList.get(j).get("SVC_MGMT_NUM"))
							&& invenDataList.get(i).get("SVC_NUM").equals(notCompInvenDataList.get(j).get("SVC_NUM"))
							&& invenDataList.get(i).get("CTZ_CORP_NUM_BACK").equals(notCompInvenDataList.get(j).get("CTZ_CORP_NUM_BACK")) ){
						
						notCompInvenFlag = true;
						map.put("notEtc2", notCompInvenDataList.get(j).get("ETC2"));
					}
				}	
 				
 				if(!notCompInvenFlag){
 					map.put("notEtc2", "매장처리완료설정일 없음");
 				} 				
 			}
 			
 			if("A0038".equals(invenDataList.get(i).get("ETC1"))){
 				map.put("notEtc2", invenDataList.get(i).get("ETC2"));
 			}
 			
 			map.put("procSts", invenDataList.get(i).get("PROC_STS"));
 			map.put("etc2", invenDataList.get(i).get("ETC2"));
 			resultList.add(map);
         }
        
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
    	header.put("index", "순번");
		header.put("releaseDy", "출고날짜");
		header.put("dealType", "구분");			
		header.put("releaseYn", "임대");
		header.put("switcherNm", "최종기변자");
		header.put("switcher", "최종기변자ID");
		header.put("svcMgmtNum", "서비스관리번호");
		header.put("joinProdNm", "가입상품");
		header.put("joinMdl", "가입단말기명");
		header.put("eqpOutAmt", "출고가");		
		header.put("compMdlNm", "보상단말기명");
		header.put("compMdlAmt", "출고가");
		header.put("color", "보상단말기색상");
		header.put("diffPrc", "기기차액");
		header.put("sharePrc", "자부담금");
		header.put("totRemAmt", "보상한도");
		header.put("usim", "유심");
		header.put("totPrc", "합계");
		header.put("insuPrc", "보험사지급액");
		header.put("appType", "카/현");
		header.put("invoiceNum", "송장번호");
		header.put("reJoinYn", "5.0 재가입유무");
		header.put("insuCoCd", "보험사");
		header.put("notEtc2", "매장처리완료최종설정일");		
		header.put("procSts", "최종상태");
		header.put("etc2", "최종상태변경일");

        try ( ExcelServletWriter ew = new ExcelServletWriter(response, ConvUtil.toYMD(new Date())) ){
        	int j = 0;        	
        	int k = 0;
        	int l = 1;
        	
        	ew.moveRow(0);        	
        	for(Map.Entry<String, String> h : header.entrySet()){        		
        		ew.moveCell(j);
        		ew.setCellValue(h.getValue());        		
        		j++;
        	}
        	
        	for(Map<String, Object> map : resultList) {        		        		
        		k = 0;
        		ew.moveRow(l);
        		
        		for(Map.Entry<String, Object> body : map.entrySet()) {
        			ew.moveCell(k);        			
        			Object a = body.getValue();
        			
        			if (a == null) { k++; continue; }
        			if (a.getClass().getName().indexOf("Integer") > -1) {
        				ew.setCellValue((int)body.getValue());
        			}
        			else {
        				if ("insuPrice".equals(body.getKey())) {
        					ew.setCellFormula(body.getValue().toString());
        				}
        				else {
        					ew.setCellValue(body.getValue().toString());        					
        				}
        			}        			
            		k++;
            	}        		
        		l++;
        	}
        	
        }
    }
    
    // Daily 택배 Data 추출
    @IdmsLog
    @RequestMapping("/Manage/parcel_data_xls.do")
    public String parcelData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	this.logger.info("택배송장 Controller");

        String getDate = request.getParameter("getDate") == null ? "" : request.getParameter("getDate");
        getDate = Common.htmlToText(getDate);

        HashMap param = new HashMap();
        param.put("getDate", getDate);

        List parcelDataList = this.manageService.getParcelData(getDate);

        if ((parcelDataList != null) && (parcelDataList.size() != 0)) {
          List ukeySetNameParcelDataList = this.manageService.getUkeySetNameParcelDataList(parcelDataList);
          Userinfo ui = this.common.getCookie(request);
          HashMap idms_param = new HashMap();
          idms_param = (HashMap)parcelDataList.get(0);
          IdmsView idmsview = new IdmsView();
          idmsview.setAdmin_login_id(ui.getUserId());
          idmsview.setMember_login_id(((String)idms_param.get("SVC_MGMT_NUM")).toString());
          idmsview.setMember_name(((String)idms_param.get("CUST_NM")).toString());
          idmsview.setAdmin_view_id("PSA0000007");
          idmsview.setFunction_id("10005");
          idmsview.setRegr(ui.getUserId());
          idmsview.setList_count(parcelDataList.size());
          request.setAttribute("idms_type", "view");
          request.setAttribute("idms_model", idmsview);

          model.addAttribute("parcelDataList", ukeySetNameParcelDataList);
        }

        return "asg_manage/parcel_data_xls";
    }
    
    @ResponseBody
    @RequestMapping("/Manage/getUntreatedRate_xls.do")
    public void getUntreatedRate_xls(HttpServletRequest request, HttpServletResponse response, 
    		@ModelAttribute("TempAssignRateInfo") TempAssignRateInfo tempAssignRateInfo) throws Exception {
    	logger.info("미/처리율 통계 init !!");
    	try{
	    	String startDate_Str = request.getParameter("startDate") == null ? "" : request.getParameter("startDate");
	    	String endDate_Str = request.getParameter("endDate") == null ? "" : request.getParameter("endDate");
	    	
	    	startDate_Str = startDate_Str+"01";
	    	endDate_Str = endDate_Str+"01";
	    	   	
	    	SimpleDateFormat dateFormatYMD = new SimpleDateFormat("yyyyMMdd");
	    	Date startDate_Date = dateFormatYMD.parse(startDate_Str);
	    	Date endDate_Date = dateFormatYMD.parse(endDate_Str);
	    	
	    	List<Map<String, Object>> resultList = new ArrayList<>();
	    	int day = 3;
	    	int gmd = getMonthsDifference(startDate_Date, endDate_Date);
	    	List<String> storeTypeList = new ArrayList<String>();
	    	storeTypeList.add("SKMC");
	    	storeTypeList.add("PSNM");
	    	
	    	for (int st=0; st<storeTypeList.size(); st++){
	    		HashMap<String, String> param = new HashMap<String, String>();
	    		param.put("storeType", storeTypeList.get(st));
	    		
	    		Calendar cal = Calendar.getInstance();
	    		cal.setTime(dateFormatYMD.parse(startDate_Str));
	    		
	    		for(int i=0; i<gmd; i++){
	    			for(int j=0,k=0,l=9; j<day; j++,k=1){
	    				cal.add(Calendar.DATE, k); //1일 증가
	    				
	    				String startDate = dateFormatYMD.format(cal.getTime()); //1일,11일,21일
	    				String endDate;
	    				String endDay;
	    				
	    				if(j==2){	    					
	    					endDay =  Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	    					endDate = startDate.substring(0, 6) + endDay;
	    					
	    					cal.add(Calendar.MONTH, k); //1달씩 증가
	    					cal.set(Calendar.DATE, 1); // 1일로 셋팅
	    					
	    				} else {
	    					cal.add(Calendar.DATE, l);
	    					endDate = dateFormatYMD.format(cal.getTime()); //10일,20일
	    				}
	    				
	    				param.put("startDate", startDate);
	    				param.put("endDate", endDate);
	    				
	    				//String totalCnt = manageService.getAssignTotalCount(param);
	    				String unAssignCnt = manageService.getUnAssignCount(param); //처리 완료 외 모든 상태 값 카운트
	    				logger.info("unAssignCnt :: "+unAssignCnt);
	    				String assignCnt = manageService.getAssignCount(param); //처리 완료 상태 값 카운트
	    				logger.info("assignCnt :: "+assignCnt);
	    				int totalCnt = Integer.parseInt(assignCnt) + Integer.parseInt(unAssignCnt);
	    				logger.info("totalCnt :: "+totalCnt);
	    				String assignRate = String.format("%.2f", Double.parseDouble(assignCnt) / totalCnt * 100);
	    				logger.info("assignRate % :: "+assignRate);
	    				String unAssignRate = String.format("%.2f", Double.parseDouble(unAssignCnt) / totalCnt * 100);
	    				logger.info("unAssignRate % :: "+unAssignRate);
	    				String assignAverageDate = manageService.getAssignAverageDate(param);
	    				
	    				if (assignAverageDate == null){
	    					assignAverageDate = "0";
	    				} else {
	    					assignAverageDate = String.format("%.2f", Double.parseDouble(assignAverageDate));	    					
	    				}
	    				logger.info("assignAverageDate % :: "+assignAverageDate);
	    				
	    				Map<String, Object> map = new HashMap<>();
	    				map.put("startDate", startDate);
	    				map.put("endDate", endDate);
	    				map.put("storeType", storeTypeList.get(st));
	    				map.put("assignAverageDate", assignAverageDate);    			
	    				map.put("totalCnt", totalCnt);
	    				map.put("assignCnt", Integer.parseInt(assignCnt));
	    				map.put("assignRate", assignRate);
	    				map.put("unAssignCnt", Integer.parseInt(unAssignCnt));
	    				map.put("unAssignRate", unAssignRate);
	    				resultList.add(map);
	    			}
	    		}
	    		
	    		resultList.add(new HashMap<>());
	    	}
	    	
	    	LinkedHashMap<String, String> header = new LinkedHashMap<>();
	    	header.put("storeType", "매장 유형");
			header.put("startDate", "시작 기간");
			header.put("endDate", "끝 기간");			
			header.put("assignAverageDate", "평균소요일수");
			header.put("totalCnt", "접수건수");
			header.put("assignCnt", "처리건수");
			header.put("assignRate", "처리율");
			header.put("unAssignCnt", "미처리건수");
			header.put("unAssignRate", "미처리율");
			
			ExcelSimpleUtil.writeResponse(response, "excel", resultList, header);
	    } catch (Exception e) {
	    	logger.info("getUntreatedRate_xls() Exception !!!!!!!!!!!!");
	    	logger.info(ConvUtil.toString(e));
		}
    }
    
    @RequestMapping("/Manage/getAssignData_xls.do")
    public void getAssignData_xls(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("배정 통계 init !!");
    	
    	try{
    		String startDate_Str = request.getParameter("startDate") == null ? "" : request.getParameter("startDate");
	    	String endDate_Str = request.getParameter("endDate") == null ? "" : request.getParameter("endDate");
	    	String startDay_Str = request.getParameter("startDay") == null ? "" : request.getParameter("startDay");
	    	String endDay_Str = request.getParameter("endDay") == null ? "" : request.getParameter("endDay");
	    	String dateFormat = request.getParameter("dateFormat") == null ? "" : request.getParameter("dateFormat");	    	
	    	
	    	HashMap<String, String> param = new HashMap<String, String>();
	    	String[] monthList = {"STORE_TYP","년도","1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
	    	String[] dayList = {"STORE_TYP","년도","월","1일","2일","3일","4일","5일","6일","7일","8일","9일","10일","11일","12일"
	    			,"13일","14일","15일","16일","17일","18일","19일","20일","21일","22일","23일","24일","25일","26일","27일","28일"
	    			,"29일","30일","31일"};
	    	String[] dateList;
	    	List<Map<String, String>> dataCnt;
	    	
	    	if("month".equals(dateFormat)){
	    		param.put("startDate", startDate_Str);
	    		param.put("endDate", endDate_Str);
	    		
	    		dataCnt = manageService.selectAssignCnt(param);	    	
	    		dateList = monthList;
	    	} else {
	    		param.put("startDate", startDay_Str);
	    		param.put("endDate", endDay_Str);
	    		
	    		dataCnt = manageService.selectAssignDayCnt(param);	    	
	    		dateList = dayList;
	    	}
	    	
	    	List<Map<String, Object>> resultList = new ArrayList<>();
	    	
	    	for( Map<String,String> node : dataCnt){
	    		if(node.get("STORE_TYP") != null){
	    			Map<String, Object> map = new HashMap<>();
	    			for(String date : dateList){
	    				map.put(date, node.get(date));
	    			}
	    			resultList.add(map);
	    		}
	    	}
	    	
	    	LinkedHashMap<String, String> header = new LinkedHashMap<>();
	    	for(String date : dateList){
	    		header.put(date, date);
			}	    	
			
			ExcelSimpleUtil.writeResponse(response, "excel", resultList, header);
	    	
    	} catch (Exception e) {
	    	logger.info("getAssignData_xls() Exception !!!!!!!!!!!!");
	    	logger.info(ConvUtil.toString(e));
		}
    }
    
    public static final int getMonthsDifference(Date startDate, Date endDate) {
        int startMonth = startDate.getYear() * 12 + startDate.getMonth();
        int endMonth = endDate.getYear() * 12 + endDate.getMonth();
        return endMonth - startMonth + 1;
    }
    
    @RequestMapping("/Manage/getChangeAssignData_xls.do")
    public void getChangeAssignData_xls(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String startYM = request.getParameter("startDate") == null ? ConvUtil.toYMD(new Date()).substring(0, 6) :  common.htmlToText(request.getParameter("startDate"));
    	String endYM = request.getParameter("endDate") == null ? ConvUtil.toYMD(new Date()).substring(0, 6) :  common.htmlToText(request.getParameter("endDate"));
		
		StringBuffer sbSumMonth = new StringBuffer();		
		StringBuffer sbSelectMonth = new StringBuffer();
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("startYM", startYM);
		param.put("schYM", endYM);
		
		LinkedHashMap<String, String> header = new LinkedHashMap<>();
		header.put("storeType", "매장 유형");
		
		//조회할 월 목록 조회
		List<Map<String, String>> monthList = statService.getMonthList(param);
		for(int i = 0 ; i < monthList.size() ; i++){
			sbSumMonth.append("SUM(DECODE(REG_DT,"+monthList.get(i).get("DATE_YM")+", RNUM))");			
			sbSumMonth.append(" AS "+'"'+monthList.get(i).get("DATE_YM")+'"');
			
			if(i != monthList.size()-1){				
				sbSumMonth.append(", ");
			}
			header.put(monthList.get(i).get("DATE_YM"), monthList.get(i).get("DATE_YM"));
		}
		
		param.put("monthSumParam", sbSumMonth);
		
		List<Map<String, String>> assignStoreStats = manageService.selectAssignStoreStats(param);
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		for(int i = 0 ; i < assignStoreStats.size() ; i++){
			
			String monthData = "";
			Map<String, Object> map = new HashMap<>();			
			map.put("storeType", assignStoreStats.get(i).get("STORE_TYP"));
			
			for(int m = 0 ; m < monthList.size() ; m++){				
				monthData += assignStoreStats.get(i).get(monthList.get(m).get("DATE_YM")) == null ? " " : String.valueOf(assignStoreStats.get(i).get(monthList.get(m).get("DATE_YM")));
				
				if(m != monthList.size() - 1){
					monthData += ",";
				}
				
				map.put(monthList.get(m).get("DATE_YM"), assignStoreStats.get(i).get(monthList.get(m).get("DATE_YM")));
			}
			
			assignStoreStats.get(i).put("monthData", monthData);			
			resultList.add(map);			
		}
    	
		
		ExcelSimpleUtil.writeResponse(response, "excel", resultList, header);
		
    }
    
    @RequestMapping("/Manage/custInsuView.do")
    public String custInsuView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {    	
    	if (request.getParameter("custData") == null){
    		return "asg_manage/custInsuView";    		
    	}
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("custType", request.getParameter("custType"));
    	param.put("custData", request.getParameter("custData"));
    	
    	List<Map<String, String>> custInsuList =manageService.selectCustInsu(param);
    	
    	model.addAttribute("custData", request.getParameter("custType"));
    	model.addAttribute("custData", request.getParameter("custData"));
    	model.addAttribute("custInsuList", custInsuList);
    	
    	return "asg_manage/custInsuView";    	
    }
    
    
}
