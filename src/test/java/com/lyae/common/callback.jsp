<%@ page contentType="text/html;charset=EUC-KR" %>
<%@ page import="java.io.*" %>

<%!

boolean noti_success(String noti[]) throws IOException
{
    //결제에 관한 log남기게 됩니다. log path수정 및 db처리루틴이 추가하여 주십시요.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_success.log", noti);
    return true;
}

boolean noti_failure(String noti[]) throws IOException
{
    //결제에 관한 log남기게 됩니다. log path수정 및 db처리루틴이 추가하여 주십시요.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_failure.log", noti);
    return true;
}

boolean noti_hash_err(String noti[]) throws IOException
{
    //결제에 관한 log남기게 됩니다. log path수정 및 db처리루틴이 추가하여 주십시요.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_hash_err.log", noti);
    return true;
}

boolean noti_waiting_pay(String noti[]) throws IOException
{
    //결제에 관한 log남기게 됩니다. log path수정 및 db처리루틴이 추가하여 주십시요.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_waiting_pay.log", noti);
    return true;
}

void noti_write(String file, String noti[]) throws IOException
{

    StringBuffer strBuf = new StringBuffer();

    strBuf.append("거래상태:" + noti[0] + "::");
    strBuf.append("거래번호:" + noti[1] + "::");
    strBuf.append("승인날짜:" + noti[2] + "::");
    strBuf.append("거래종류:" + noti[3] + "::");
    strBuf.append("회원사ID:" + noti[4] + "::");
    strBuf.append("주문번호:" + noti[5] + "::");
    strBuf.append("금융사코드:" + noti[6] + "::");
    strBuf.append("금융사코드:" + noti[7] + "::");
    strBuf.append("금융사명:" + noti[8] + "::");
    strBuf.append("주문자명:" + noti[9] + "::");
    strBuf.append("거래금액:" + noti[10] + "::");
    strBuf.append("주문정보:" + noti[11] + "::");
    strBuf.append("메세지1:" + noti[12] + "::");
    strBuf.append("메세지2:" + noti[13] + "::");
    strBuf.append("승인번호:" + noti[14] + "::");
    strBuf.append("P_HASH:" + noti[15] + "::");        
    strBuf.append("HashedData:" + noti[16]);
    strBuf.append("\n");

    byte b[] = strBuf.toString().getBytes("EUC-KR");
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file, true));
    stream.write(b);
    stream.close();
}

%>



