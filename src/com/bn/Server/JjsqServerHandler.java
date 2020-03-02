package com.bn.Server;

import java.util.*;

import com.bn.Datebase.DBUtil;
import com.bn.util.Constant;
import com.bn.util.ConvertUtil;
import com.bn.util.IOUtil;
import com.bn.util.ImageUtil;
import com.bn.util.StrListChange;

import io.netty.buffer.*;
import io.netty.channel.*;

class myFutureListener implements ChannelFutureListener
{
	@Override
	public void operationComplete(ChannelFuture arg0) throws Exception{
		System.out.println("��������ͻ��˷������");
	}
}
public class JjsqServerHandler extends ChannelInboundHandlerAdapter {
	static myFutureListener mm=new myFutureListener();
	
	List<String[]> ls=null;//������ݲ�ѯ���
	byte[] datapic=null; //������Ҫ����Pc��ͼƬ
	public static byte[] Pcimg;//���pc������������
	
	//���ӳɹ�ʱ
	@Override
	public void channelActive(final ChannelHandlerContext ctx){
		System.out.println("�ͻ�������->"+ctx.channel().remoteAddress());
	}
	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg)
	{
		ByteBuf m=(ByteBuf)msg;
		String ss=null;
		String content="";//��ȡ��Ϣ�ַ��������Ч��Ϣ
		String path= content=Constant.allPath+"/";
		byte datapic[];
		String[] s;
		try {
			byte[] data=IOUtil.readBytes(msg);
			byte[] tb=Arrays.copyOfRange(data, 0, 4);
			int type=ConvertUtil.fromBytesToInt(tb);
			byte[] realData=Arrays.copyOfRange(data, 4, data.length);
			switch (type) {
			case 1:
				Pcimg=null;
				Pcimg=realData;
				ctx.close();
				break;
			case 0:
				
				String msgStr=ConvertUtil.fromBytesToString(realData);
				if(msgStr.startsWith(Constant.getAdministrator))//��ȡ��¼��������
				{
					content=msgStr.substring(20);
					ss=DBUtil.getAdministrator(content);
					IOUtil.writeString(ctx, ss, mm);
					ctx.close();
				}else if(msgStr.startsWith(Constant.GetManagerByID)){//ͨ������ԱID��ȡ����Ա
        			content=msgStr.substring(18);
        			s=StrListChange.StrToArray(content);
        			ss=StrListChange.ListToStr(DBUtil.GetManagerByID(s));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.GetAllManager)){//��ȡ���й���Ա
        		
        			ls=DBUtil.GetAllManager();
        			ss=StrListChange.ListToStr(ls);
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        	   }else if(msgStr.startsWith(Constant.UpdateManagerByButton)){//���¹���Ա
        		    content=msgStr.substring(25);
        			s=StrListChange.StrToArray(content);
        			DBUtil.UpdateManagerByButton(s);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.insertManager)){//��ӹ���Ա
        			content=msgStr.substring(17);
        			s=StrListChange.StrToArray(content);
        			DBUtil.insertManager(s);
                    ctx.close();
        		}else if(msgStr.startsWith(Constant.getdpName)){//�õ����е�������
        			ss=StrListChange.ListToStr(DBUtil.getdpName());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getAlldpXx)){//�õ����е�����Ϣ
        			ss=StrListChange.ListToStr(DBUtil.getAlldpXx());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getdpDetialXx)){//�õ��������̵���ϸ��Ϣ
        			content=msgStr.substring(17);
        			ss=StrListChange.ListToStr(DBUtil.getdpDetialXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiondpNameXx)){//ͨ���������õ���Ϣ
        		    content=msgStr.substring(22);
        			ss=StrListChange.ListToStr(DBUtil.getSectiondpNameXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatedpxx)){
        			content=msgStr.substring(14);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updatedpxx(s);;
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.insertdpXx)) {
					content=msgStr.substring(14);
					s=StrListChange.StrToArray(content);
					DBUtil.insertdpXx(s);
					ctx.close();
				}else if(msgStr.startsWith(Constant.getSectiondpXx)){
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectiondpXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatedpFlag)){
        		    content=msgStr.substring(16);
        			DBUtil.updatedpFlag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatedp1Flag)){
        		    content=msgStr.substring(17);
        			DBUtil.updatedp1Flag(content);
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.getMaxdpId)) {
        			ss=DBUtil.getMaxdpId();
        			IOUtil.writeString(ctx, ss, mm);
        			ctx.close();
					
				}else if(msgStr.startsWith(Constant.getSPName)){//�õ�������Ʒ����
        			ss=StrListChange.ListToStr(DBUtil.getSPName());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getALLSP)){//��ȡ������Ʒ�Ĳ�����Ϣ
        			ss=StrListChange.ListToStr(DBUtil.getALLSP());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionspXx)){////ͨ����־λ��ȡ��Ʒ������Ϣ
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectionspXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionspNameXx)){//ͨ�����ƻ�ȡ������Ϣ
        		    content=msgStr.substring(22);
        			ss=StrListChange.ListToStr(DBUtil.getSectionspNameXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionspNameXxs)){//ͨ��ģ�����ƻ�ȡ������Ϣ
        		    content=msgStr.substring(23);
        			ss=StrListChange.ListToStr(DBUtil.getSectionspNameXxs(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getspDetail)){//ͨ��ģ�����ƻ�ȡ������Ϣ
        		    content=msgStr.substring(15);
        			ss=StrListChange.ListToStr(DBUtil.getspDetail(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getPic)){
       			content = msgStr.substring(10);
   				datapic=ImageUtil.getImage(path+content);
       			IOUtil.writeByte(ctx, datapic, mm);
//       			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatespFlag)){//������Ʒ��־λ
        			content=msgStr.substring(16);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updatespFlag(s);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.insertpicname)){
	       			 content= msgStr.substring(17);
	       			 ImageUtil.savePic(Pcimg, path+content);
	       			 ctx.close();
	       		}else if(msgStr.startsWith(Constant.updatespXx)){
        			content=msgStr.substring(14);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updatespXx(s);;
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.getdpIDByName)) {
					content=msgStr.substring(17);
					System.out.println(content);
					ss=DBUtil.getdpIDByName(content);
					IOUtil.writeString(ctx, ss, mm);	
					ctx.close();
        		}else if (msgStr.startsWith(Constant.GetMaxspId)) {
        			ss=DBUtil.GetMaxspId();
        			IOUtil.writeString(ctx, ss, mm);
        			ctx.close();
					
				}else if (msgStr.startsWith(Constant.insertspXx)) {
					content=msgStr.substring(14);
					s=StrListChange.StrToArray(content);
					DBUtil.insertspXx(s);
					ctx.close();
				}else if(msgStr.startsWith(Constant.getAlluser)){
        			ss=StrListChange.ListToStr(DBUtil.getAlluser());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
                    ctx.close();
        		}else if(msgStr.startsWith(Constant.getUserForjcb)){
        			content=msgStr.substring(17);
        			String []array=StrListChange.StrToArray(content);
        			ss=StrListChange.ListToStr(DBUtil.getUserForjcb(array));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
                    ctx.close();
        		}else if(msgStr.startsWith(Constant.getuserForjtf)){
        			content=msgStr.substring(17);
        			ss=StrListChange.ListToStr(DBUtil.getuserForjtf(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
                    ctx.close();
        		}else if(msgStr.startsWith(Constant.getSingleUserXx)){
        			content=msgStr.substring(19);
        			ss=StrListChange.ListToStr(DBUtil.getSingleUserXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
                    ctx.close();
        		}else if(msgStr.startsWith(Constant.Updateuserflag)){//
        			content=msgStr.substring(18);
        			String[] array=StrListChange.StrToArray(content);
        			DBUtil.Updateuserflag(array);
        		}else if(msgStr.startsWith(Constant.updatePassword)){//
        			content=msgStr.substring(18);
        			String[] array=StrListChange.StrToArray(content);
        			DBUtil.updatePassword(array);
        		}else if(msgStr.startsWith(Constant.getADName)){
        			ss=StrListChange.ListToStr(DBUtil.getADName());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getAllADXx)){
        			ss=StrListChange.ListToStr(DBUtil.getAllADXx());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getADDetialXx)){
        			content=msgStr.substring(17);
        			//System.out.println("content:"+content);
        			ss=StrListChange.ListToStr(DBUtil.getADDetialXx(content));
        			//System.out.println("ss:"+ss);
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateADxx)){
        			content=msgStr.substring(14);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updateADxx(s);;
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.insertADXx)) {
					content=msgStr.substring(14);
					s=StrListChange.StrToArray(content);
					DBUtil.insertADXx(s);
					ctx.close();
				}else if(msgStr.startsWith(Constant.getSectionADXx)){
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectionADXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateADFlag)){
        		    content=msgStr.substring(16);
        			DBUtil.updateADFlag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateAD1Flag)){
        		    content=msgStr.substring(17);
        			DBUtil.updateAD1Flag(content);
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.getMaxADId)) {
        			ss=DBUtil.getMaxADId();
        			IOUtil.writeString(ctx, ss, mm);
        			ctx.close();
					
				}else if(msgStr.startsWith(Constant.getSectiontzXx)){
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectiontzXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatetzFlag)){
        		    content=msgStr.substring(16);
        			DBUtil.updatetzFlag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatetz1Flag)){
        		    content=msgStr.substring(17);
        			DBUtil.updatetz1Flag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.gettzDetailXx)){
        			content=msgStr.substring(17);
        			System.out.println("content:"+content);
        			ss=StrListChange.ListToStr(DBUtil.gettzDetailXx(content));
        			System.out.println("ss:"+ss);
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionplXx)){
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectionplXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateplFlag)){
        		    content=msgStr.substring(16);
        			DBUtil.updateplFlag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatepl1Flag)){
        		    content=msgStr.substring(17);
        			DBUtil.updatepl1Flag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionwtXx)){
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectionwtXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatewtFlag)){
        		    content=msgStr.substring(16);
        			DBUtil.updatewtFlag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatewt1Flag)){
        		    content=msgStr.substring(17);
        			DBUtil.updatewt1Flag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getwtDetail)){
        		    content=msgStr.substring(15);
        			ss=StrListChange.ListToStr(DBUtil.getwtDetail(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionhdXx)){
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectionhdXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatehdFlag)){
        		    content=msgStr.substring(16);
        			DBUtil.updatehdFlag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updatehd1Flag)){
        		    content=msgStr.substring(17);
        			DBUtil.updatehd1Flag(content);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.gethdDetail)){
        		    content=msgStr.substring(15);
        			ss=StrListChange.ListToStr(DBUtil.gethdDetail(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getGSName)){//�õ����й�˾����
        			ss=StrListChange.ListToStr(DBUtil.getGSName());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getALLGS)){//��ȡ���й�˾�Ĳ�����Ϣ
        			ss=StrListChange.ListToStr(DBUtil.getALLGS());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiongsXx)){////ͨ����־λ��ȡ��˾������Ϣ
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectiongsXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiongsNameXx)){//ͨ�����ƻ�ȡ������Ϣ
        		    content=msgStr.substring(22);
        			ss=StrListChange.ListToStr(DBUtil.getSectiongsNameXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiongsNameXxs)){//ͨ��ģ�����ƻ�ȡ������Ϣ
        		    content=msgStr.substring(23);
        			ss=StrListChange.ListToStr(DBUtil.getSectiongsNameXxs(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getgsDetail)){//��ȡ��˾����ϸ��Ϣ
        		    content=msgStr.substring(15);
        			ss=StrListChange.ListToStr(DBUtil.getgsDetail(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updategsFlag)){//���¹�˾��־λ
        			content=msgStr.substring(16);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updategsFlag(s);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updategsXx)){//���¹�˾��־λ
        			content=msgStr.substring(14);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updategsXx(s);
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.GetMaxgsId)) {//�õ����Ĺ�˾ID
        			ss=DBUtil.GetMaxgsId();
        			IOUtil.writeString(ctx, ss, mm);
        			ctx.close();
					
				}else if (msgStr.startsWith(Constant.insertgsXx)) {//��ӹ�˾��Ϣ
					content=msgStr.substring(14);
					s=StrListChange.StrToArray(content);
					DBUtil.insertgsXx(s);
					ctx.close();
				}
			else if(msgStr.startsWith(Constant.getCaseTitle)){//�õ����а�������
        			ss=StrListChange.ListToStr(DBUtil.getCaseTtile());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getALLCase)){//��ȡ���а����Ĳ�����Ϣ
        			ss=StrListChange.ListToStr(DBUtil.getALLCase());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionCaseXx)){////ͨ����־λ��ȡ����������Ϣ
        		    content=msgStr.substring(20);
        			ss=StrListChange.ListToStr(DBUtil.getSectionCaseXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionCaseTitleXx)){//ͨ�����ƻ�ȡ������Ϣ
        		    content=msgStr.substring(25);
        			ss=StrListChange.ListToStr(DBUtil.getSectionCaseTitleXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionCaseTitleXxs)){//ͨ��ģ�����ƻ�ȡ������Ϣ
        		    content=msgStr.substring(26);
        			ss=StrListChange.ListToStr(DBUtil.getSectionCaseTitleXxs(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getCaseDetail)){//��ȡ������ϸ��Ϣ
        		    content=msgStr.substring(17);
        			ss=StrListChange.ListToStr(DBUtil.getCaseDetail(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateCaseFlag)){//���°�����־λ
        			content=msgStr.substring(18);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updateCaseFlag(s);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateCaseXx)){//���°�����־λ
        			content=msgStr.substring(16);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updateCaseXx(s);
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.getdesignerNameByID)) {//ͨ��ID��ȡ���ʦ��
					content=msgStr.substring(23);
					System.out.println(content);
					ss=DBUtil.getdesignerNameByID(content);
					IOUtil.writeString(ctx, ss, mm);	
					ctx.close();
        		}else if (msgStr.startsWith(Constant.GetMaxCaseId)) {//��ȡ�������ID
        			ss=DBUtil.GetMaxCaseId();
        			IOUtil.writeString(ctx, ss, mm);
        			ctx.close();
					
			}else if (msgStr.startsWith(Constant.insertCaseXx)) {//���밸����Ϣ
					content=msgStr.substring(16);
					s=StrListChange.StrToArray(content);
					DBUtil.insertCaseXx(s);
					ctx.close();
			}else if(msgStr.startsWith(Constant.getDesignerName)){//�õ����е�������
    			ss=StrListChange.ListToStr(DBUtil.getDesignerName());
    			IOUtil.writeString(
    					ctx,
    					ss,
    					mm
    					);	
    			ctx.close();
    		}else if (msgStr.startsWith(Constant.getDesignerIDByName)) {
				content=msgStr.substring(23);
				ss=DBUtil.getDesignerIDByName(content);
				IOUtil.writeString(ctx, ss, mm);	
				ctx.close();
    		}else if(msgStr.startsWith(Constant.getALLDesigner)){//��ȡ������Ʒ�Ĳ�����Ϣ
    			ss=StrListChange.ListToStr(DBUtil.getALLDesigner());
    			IOUtil.writeString(
    					ctx,
    					ss,
    					mm
    					);	
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.getSectionDesignerXxs)){//ͨ��ģ�����ƻ�ȡ������Ϣ
    		    content=msgStr.substring(25);
    			ss=StrListChange.ListToStr(DBUtil.getSectionDesignerXxs(content));
    			IOUtil.writeString(
    					ctx,
    					ss,
    					mm
    					);	
    			ctx.close();
    		}else if (msgStr.startsWith(Constant.getcompanyNameByID)) {
				content=msgStr.substring(22);
				System.out.println(content);
				ss=DBUtil.getcompanyNameByID(content);
				IOUtil.writeString(ctx, ss, mm);	
				ctx.close();
    		}else if(msgStr.startsWith(Constant.getDesignerDetail)){
    		    content=msgStr.substring(21);
    			ss=StrListChange.ListToStr(DBUtil.getDesignerDetail(content));
    			IOUtil.writeString(
    					ctx,
    					ss,
    					mm
    					);	
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.updatedesignerXx)){//���¹�˾��־λ
    			content=msgStr.substring(20);
    			s=StrListChange.StrToArray(content);
    			DBUtil.updatedesignerXx(s);
    			ctx.close();
    		}else if (msgStr.startsWith(Constant.getCompanyIDByName)) {
				content=msgStr.substring(22);
				ss=DBUtil.getCompanyIDByName(content);
				IOUtil.writeString(ctx, ss, mm);	
				ctx.close();
    		}else if (msgStr.startsWith(Constant.GetMaxdesignerId)) {
    			ss=DBUtil.GetMaxdesignerId();
    			IOUtil.writeString(ctx, ss, mm);
    			ctx.close();
				
			}else if (msgStr.startsWith(Constant.insertDesignerXx)) {
				content=msgStr.substring(20);
				s=StrListChange.StrToArray(content);
				DBUtil.insertDesignerXx(s);
				ctx.close();
			}else if(msgStr.startsWith(Constant.updateDesignerFlag)){//���¹�˾��־λ
    			content=msgStr.substring(22);
    			s=StrListChange.StrToArray(content);
    			DBUtil.updateDesignerFlag(s);
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.getUserPassword)){//��ȡ�û�����
    			content=msgStr.substring(19);
    			ls=DBUtil.getUserpassword(content);
    			ss=StrListChange.ListToStr(ls);
    			
    			IOUtil.writeString(ctx, ss, mm);
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.insertUser)){//¼�����û�
    			
    			content =msgStr.substring(14);
    			DBUtil.insertUser(content);
    			System.out.println(ss);
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.GetPostListXx)){
    			ss=StrListChange.ListToStr(DBUtil.GetPostListXx());
				IOUtil.writeString(ctx, ss, mm);
    			ctx.close();
    		}
			}
					/*else if(msgStr.startsWith(Constant.getUserXx)){
    			content=msgStr.substring(13);
    			ss=StrListChange.ListToStr(DBUtil.getUserXx(content));
    			IOUtil.writeString(ctx, ss, mm);
    			ctx.close();
    		}*/
				
		

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			m.release();
		}
		
	}
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx ,Throwable cause)
	{
		System.out.println("�������쳣���˳�");
		cause.printStackTrace();
		ctx.close();
	}
}
