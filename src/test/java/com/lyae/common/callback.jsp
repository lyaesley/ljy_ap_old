<%@ page contentType="text/html;charset=EUC-KR" %>
<%@ page import="java.io.*" %>

<%!

boolean noti_success(String noti[]) throws IOException
{
    //������ ���� log����� �˴ϴ�. log path���� �� dbó����ƾ�� �߰��Ͽ� �ֽʽÿ�.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_success.log", noti);
    return true;
}

boolean noti_failure(String noti[]) throws IOException
{
    //������ ���� log����� �˴ϴ�. log path���� �� dbó����ƾ�� �߰��Ͽ� �ֽʽÿ�.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_failure.log", noti);
    return true;
}

boolean noti_hash_err(String noti[]) throws IOException
{
    //������ ���� log����� �˴ϴ�. log path���� �� dbó����ƾ�� �߰��Ͽ� �ֽʽÿ�.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_hash_err.log", noti);
    return true;
}

boolean noti_waiting_pay(String noti[]) throws IOException
{
    //������ ���� log����� �˴ϴ�. log path���� �� dbó����ƾ�� �߰��Ͽ� �ֽʽÿ�.
    noti_write("C:\\Inetpub\\wwwroot\\newpg3\\jsp\\noti_waiting_pay.log", noti);
    return true;
}

void noti_write(String file, String noti[]) throws IOException
{

    StringBuffer strBuf = new StringBuffer();

    strBuf.append("�ŷ�����:" + noti[0] + "::");
    strBuf.append("�ŷ���ȣ:" + noti[1] + "::");
    strBuf.append("���γ�¥:" + noti[2] + "::");
    strBuf.append("�ŷ�����:" + noti[3] + "::");
    strBuf.append("ȸ����ID:" + noti[4] + "::");
    strBuf.append("�ֹ���ȣ:" + noti[5] + "::");
    strBuf.append("�������ڵ�:" + noti[6] + "::");
    strBuf.append("�������ڵ�:" + noti[7] + "::");
    strBuf.append("�������:" + noti[8] + "::");
    strBuf.append("�ֹ��ڸ�:" + noti[9] + "::");
    strBuf.append("�ŷ��ݾ�:" + noti[10] + "::");
    strBuf.append("�ֹ�����:" + noti[11] + "::");
    strBuf.append("�޼���1:" + noti[12] + "::");
    strBuf.append("�޼���2:" + noti[13] + "::");
    strBuf.append("���ι�ȣ:" + noti[14] + "::");
    strBuf.append("P_HASH:" + noti[15] + "::");        
    strBuf.append("HashedData:" + noti[16]);
    strBuf.append("\n");

    byte b[] = strBuf.toString().getBytes("EUC-KR");
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file, true));
    stream.write(b);
    stream.close();
}

%>



