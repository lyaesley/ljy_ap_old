<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ include file = "callback.jsp" %>
<%@ page import="java.security.MessageDigest" %>

<%
//이 페이지는 수정하지 마십시요. 수정시 html태그나 자바스크립트가 들어가는 경우 동작을 보장할 수 없습니다

//hash데이타값이 맞는 지 확인 하는 루틴은 세틀뱅크에서 받은 데이타가 맞는지 확인하는 것이므로 꼭 사용하셔야 합니다
//정상적인 결제 건임에도 불구하고 노티 페이지의 오류나 네트웍 문제 등으로 인한 hash 값의 오류가 발생할 수도 있습니다.
//그러므로 hash 오류건에 대해서는 오류 발생시 원인을 파악하여 즉시 수정 및 대처해 주셔야 합니다.
//그리고 정상적으로 data를 처리한 경우에도 세틀뱅크에서 응답을 받지 못한 경우는 결제결과가 중복해서 나갈 수 있으므로 관련한 처리도 고려되어야 합니다.  (PTrno 가 PAuthDt의 일자(8자리)에 대해 unique 하니 PTrno로 체크 해주세요)

String P_STATUS, P_TR_NO, P_AUTH_DT, P_TYPE, P_MID, P_OID, P_FN_CD1, P_FN_CD2, P_FN_NM, P_UNAME, P_AMT, P_NOTI, P_RMESG1, P_RMESG2, P_AUTH_NO;
String P_HASH, PG_AUTH_KEY;
boolean resp = false;

//세틀뱅크 noti server에서 받은 value
P_STATUS    = request.getParameter("PStateCd") + "";   // 거래상태 : 0021(성공), 0031(실패), 0051(입금대기중)
P_TR_NO     = request.getParameter("PTrno") + "";    // 거래번호
P_AUTH_DT   = request.getParameter("PAuthDt") + "";  // 승인시간
P_AUTH_NO   = request.getParameter("PAuthNo") + "";  // 승인번호 
P_TYPE      = request.getParameter("PType") + "";     // 거래종류 (CARD, BANK)
P_MID       = request.getParameter("PMid") + "";      // 회원사아이디
P_OID       = request.getParameter("POid") + "";      // 주문번호
P_FN_CD1    = request.getParameter("PFnCd1") + "";   // 금융사코드 (은행코드, 카드코드)
P_FN_CD2    = request.getParameter("PFnCd2") + "";  // 금융사코드 (은행코드, 카드코드)
P_FN_NM     = request.getParameter("PFnNm") + "";     // 금융사명 (은행명, 카드사명)
P_UNAME     = request.getParameter("PUname") + "";    // 주문자명
P_AMT       = request.getParameter("PAmt") + "";      // 거래금액
P_NOTI      = request.getParameter("PNoti") + "";     // 주문정보
P_RMESG1    = request.getParameter("PRmesg1") + "";   // 메시지1
P_RMESG2    = request.getParameter("PRmesg2") + "";   // 메시지2
P_HASH      = request.getParameter("PHash") + "";     // NOTI HASH 코드값

//mid가 mid_test인 경우에 사용합니다
//해당 회원사 id당 하나의 auth_key가 발급됩니다
//발급받으신 auth_key를 설정하셔야 합니다 
PG_AUTH_KEY = "ST1009281328226982205";

StringBuffer sbNoti = new StringBuffer();
sbNoti.append(P_STATUS);
sbNoti.append(P_TR_NO);
sbNoti.append(P_AUTH_DT);
sbNoti.append(P_TYPE);
sbNoti.append(P_MID);
sbNoti.append(P_OID);
sbNoti.append(P_AMT);
sbNoti.append(PG_AUTH_KEY);


String sNoti = sbNoti.toString();
byte[] bNoti = sNoti.getBytes();

MessageDigest md = MessageDigest.getInstance("MD5");
byte[] digest = md.digest(bNoti);

StringBuffer strBuf = new StringBuffer();

for (int i = 0; i < digest.length; i++) {
    int c = digest[i] & 0xff;
    if (c <= 15)
        strBuf.append("0");
    strBuf.append(Integer.toHexString(c));
}

String HashedData = strBuf.toString();
String[] noti = {P_STATUS, P_TR_NO, P_AUTH_DT, P_TYPE, P_MID, P_OID, P_FN_CD1, P_FN_CD2, P_FN_NM, P_UNAME, P_AMT, P_NOTI, P_RMESG1, P_RMESG2, P_AUTH_NO, P_HASH, HashedData };


//관련 db처리는 callback.asp의 noti_success(),noti_failure(),noti_hash_err()에서 관련 루틴을 추가하시면 됩니다
//각 함수 호출시 값은 배열로 전달되도록 되어 있으므로 처리시 주의하시기 바랍니다.
//위의 각 함수에는 현재 결제에 관한 log남기게 됩니다. 회원사서버에서 남기실 절대경로로 맞게 수정하여 주세요
if (HashedData.trim().equals(P_HASH)) {    
    if (P_STATUS.trim().equals("0021"))
        resp = noti_success(noti);
    else if (P_STATUS.trim().equals("0031"))
        resp = noti_failure(noti);
    else if (P_STATUS.trim().equals("0051"))
        resp = noti_waiting_pay(noti);
    else
        resp = false;
}
else {
    noti_hash_err(noti);
}

//세틀뱅크로 전송되어야 하는 값이므로 삭제하지 마세요.
if (resp)
    out.println("OK");
else
    out.println("FAIL");

%>



