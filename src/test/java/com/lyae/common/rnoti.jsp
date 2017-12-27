<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ include file = "callback.jsp" %>
<%@ page import="java.security.MessageDigest" %>

<%
//�� �������� �������� ���ʽÿ�. ������ html�±׳� �ڹٽ�ũ��Ʈ�� ���� ��� ������ ������ �� �����ϴ�

//hash����Ÿ���� �´� �� Ȯ�� �ϴ� ��ƾ�� ��Ʋ��ũ���� ���� ����Ÿ�� �´��� Ȯ���ϴ� ���̹Ƿ� �� ����ϼž� �մϴ�
//�������� ���� ���ӿ��� �ұ��ϰ� ��Ƽ �������� ������ ��Ʈ�� ���� ������ ���� hash ���� ������ �߻��� ���� �ֽ��ϴ�.
//�׷��Ƿ� hash �����ǿ� ���ؼ��� ���� �߻��� ������ �ľ��Ͽ� ��� ���� �� ��ó�� �ּž� �մϴ�.
//�׸��� ���������� data�� ó���� ��쿡�� ��Ʋ��ũ���� ������ ���� ���� ���� ��������� �ߺ��ؼ� ���� �� �����Ƿ� ������ ó���� ����Ǿ�� �մϴ�.  (PTrno �� PAuthDt�� ����(8�ڸ�)�� ���� unique �ϴ� PTrno�� üũ ���ּ���)

String P_STATUS, P_TR_NO, P_AUTH_DT, P_TYPE, P_MID, P_OID, P_FN_CD1, P_FN_CD2, P_FN_NM, P_UNAME, P_AMT, P_NOTI, P_RMESG1, P_RMESG2, P_AUTH_NO;
String P_HASH, PG_AUTH_KEY;
boolean resp = false;

//��Ʋ��ũ noti server���� ���� value
P_STATUS    = request.getParameter("PStateCd") + "";   // �ŷ����� : 0021(����), 0031(����), 0051(�Աݴ����)
P_TR_NO     = request.getParameter("PTrno") + "";    // �ŷ���ȣ
P_AUTH_DT   = request.getParameter("PAuthDt") + "";  // ���νð�
P_AUTH_NO   = request.getParameter("PAuthNo") + "";  // ���ι�ȣ 
P_TYPE      = request.getParameter("PType") + "";     // �ŷ����� (CARD, BANK)
P_MID       = request.getParameter("PMid") + "";      // ȸ������̵�
P_OID       = request.getParameter("POid") + "";      // �ֹ���ȣ
P_FN_CD1    = request.getParameter("PFnCd1") + "";   // �������ڵ� (�����ڵ�, ī���ڵ�)
P_FN_CD2    = request.getParameter("PFnCd2") + "";  // �������ڵ� (�����ڵ�, ī���ڵ�)
P_FN_NM     = request.getParameter("PFnNm") + "";     // ������� (�����, ī����)
P_UNAME     = request.getParameter("PUname") + "";    // �ֹ��ڸ�
P_AMT       = request.getParameter("PAmt") + "";      // �ŷ��ݾ�
P_NOTI      = request.getParameter("PNoti") + "";     // �ֹ�����
P_RMESG1    = request.getParameter("PRmesg1") + "";   // �޽���1
P_RMESG2    = request.getParameter("PRmesg2") + "";   // �޽���2
P_HASH      = request.getParameter("PHash") + "";     // NOTI HASH �ڵ尪

//mid�� mid_test�� ��쿡 ����մϴ�
//�ش� ȸ���� id�� �ϳ��� auth_key�� �߱޵˴ϴ�
//�߱޹����� auth_key�� �����ϼž� �մϴ� 
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


//���� dbó���� callback.asp�� noti_success(),noti_failure(),noti_hash_err()���� ���� ��ƾ�� �߰��Ͻø� �˴ϴ�
//�� �Լ� ȣ��� ���� �迭�� ���޵ǵ��� �Ǿ� �����Ƿ� ó���� �����Ͻñ� �ٶ��ϴ�.
//���� �� �Լ����� ���� ������ ���� log����� �˴ϴ�. ȸ���缭������ ����� �����η� �°� �����Ͽ� �ּ���
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

//��Ʋ��ũ�� ���۵Ǿ�� �ϴ� ���̹Ƿ� �������� ������.
if (resp)
    out.println("OK");
else
    out.println("FAIL");

%>



