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
		System.out.println("鏈嶅姟鍣ㄥ悜瀹㈡埛绔彂閫佸畬姣�");
	}
}
public class JjsqServerHandler extends ChannelInboundHandlerAdapter {
	static myFutureListener mm=new myFutureListener();
	
	List<String[]> ls=null;//瀛樻斁鏁版嵁鏌ヨ缁撴灉
	byte[] datapic=null; //鏈嶅姟鍣ㄨ浼犵粰Pc鐨勫浘鐗�
	public static byte[] Pcimg;//瀛樻斁pc浼犲埌鏈嶅姟鍣ㄧ殑
	
	//杩炴帴鎴愬姛鏃�
	@Override
	public void channelActive(final ChannelHandlerContext ctx){
		System.out.println("瀹㈡埛绔繛鎺�->"+ctx.channel().remoteAddress());
	}
	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg)
	{
		ByteBuf m=(ByteBuf)msg;
		String ss=null;
		String content="";//鎴彇娑堟伅瀛楃涓插悗鐨勬湁鏁堜俊鎭�
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
				if(msgStr.startsWith(Constant.getAdministrator))//鑾峰彇鐧诲綍鍚嶏紝瀵嗙爜
				{
					content=msgStr.substring(20);
					ss=DBUtil.getAdministrator(content);
					IOUtil.writeString(ctx, ss, mm);
					ctx.close();
				}else if(msgStr.startsWith(Constant.GetManagerByID)){//閫氳繃绠＄悊鍛業D鑾峰彇绠＄悊鍛�
        			content=msgStr.substring(18);
        			s=StrListChange.StrToArray(content);
        			ss=StrListChange.ListToStr(DBUtil.GetManagerByID(s));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.GetAllManager)){//鑾峰彇鎵�鏈夌鐞嗗憳
        		
        			ls=DBUtil.GetAllManager();
        			ss=StrListChange.ListToStr(ls);
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        	   }else if(msgStr.startsWith(Constant.UpdateManagerByButton)){//鏇存柊绠＄悊鍛�
        		    content=msgStr.substring(25);
        			s=StrListChange.StrToArray(content);
        			DBUtil.UpdateManagerByButton(s);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.insertManager)){//娣诲姞绠＄悊鍛�
        			content=msgStr.substring(17);
        			s=StrListChange.StrToArray(content);
        			DBUtil.insertManager(s);
                    ctx.close();
        		}else if(msgStr.startsWith(Constant.getdpName)){//寰楀埌鎵�鏈夊簵閾哄悕瀛�
        			ss=StrListChange.ListToStr(DBUtil.getdpName());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getAlldpXx)){//寰楀埌鎵�鏈夊簵閾轰俊鎭�
        			ss=StrListChange.ListToStr(DBUtil.getAlldpXx());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getdpDetialXx)){//寰楀埌鍗曚釜搴楅摵鐨勮缁嗕俊鎭�
        			content=msgStr.substring(17);
        			ss=StrListChange.ListToStr(DBUtil.getdpDetialXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiondpNameXx)){//閫氳繃搴楅摵鍚嶅緱鍒颁俊鎭�
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
					
				}else if(msgStr.startsWith(Constant.getSPName)){//寰楀埌鎵�鏈夊晢鍝佸悕瀛�
        			ss=StrListChange.ListToStr(DBUtil.getSPName());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getALLSP)){//鑾峰彇鎵�鏈夊晢鍝佺殑閮ㄥ垎淇℃伅
        			ss=StrListChange.ListToStr(DBUtil.getALLSP());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionspXx)){////閫氳繃鏍囧織浣嶈幏鍙栧晢鍝侀儴鍒嗕俊鎭�
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectionspXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionspNameXx)){//閫氳繃鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
        		    content=msgStr.substring(22);
        			ss=StrListChange.ListToStr(DBUtil.getSectionspNameXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionspNameXxs)){//閫氳繃妯＄硦鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
        		    content=msgStr.substring(23);
        			ss=StrListChange.ListToStr(DBUtil.getSectionspNameXxs(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getspDetail)){//閫氳繃妯＄硦鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
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
        		}else if(msgStr.startsWith(Constant.updatespFlag)){//鏇存柊鍟嗗搧鏍囧織浣�
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
        		}else if(msgStr.startsWith(Constant.getGSName)){//寰楀埌鎵�鏈夊叕鍙稿悕瀛�
        			ss=StrListChange.ListToStr(DBUtil.getGSName());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getALLGS)){//鑾峰彇鎵�鏈夊叕鍙哥殑閮ㄥ垎淇℃伅
        			ss=StrListChange.ListToStr(DBUtil.getALLGS());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiongsXx)){////閫氳繃鏍囧織浣嶈幏鍙栧叕鍙搁儴鍒嗕俊鎭�
        		    content=msgStr.substring(18);
        			ss=StrListChange.ListToStr(DBUtil.getSectiongsXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiongsNameXx)){//閫氳繃鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
        		    content=msgStr.substring(22);
        			ss=StrListChange.ListToStr(DBUtil.getSectiongsNameXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectiongsNameXxs)){//閫氳繃妯＄硦鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
        		    content=msgStr.substring(23);
        			ss=StrListChange.ListToStr(DBUtil.getSectiongsNameXxs(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getgsDetail)){//鑾峰彇鍏徃鐨勮缁嗕俊鎭�
        		    content=msgStr.substring(15);
        			ss=StrListChange.ListToStr(DBUtil.getgsDetail(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updategsFlag)){//鏇存柊鍏徃鏍囧織浣�
        			content=msgStr.substring(16);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updategsFlag(s);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updategsXx)){//鏇存柊鍏徃鏍囧織浣�
        			content=msgStr.substring(14);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updategsXx(s);
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.GetMaxgsId)) {//寰楀埌鏈�澶х殑鍏徃ID
        			ss=DBUtil.GetMaxgsId();
        			IOUtil.writeString(ctx, ss, mm);
        			ctx.close();
					
				}else if (msgStr.startsWith(Constant.insertgsXx)) {//娣诲姞鍏徃淇℃伅
					content=msgStr.substring(14);
					s=StrListChange.StrToArray(content);
					DBUtil.insertgsXx(s);
					ctx.close();
				}
			else if(msgStr.startsWith(Constant.getCaseTitle)){//寰楀埌鎵�鏈夋渚嬪悕瀛�
        			ss=StrListChange.ListToStr(DBUtil.getCaseTtile());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getALLCase)){//鑾峰彇鎵�鏈夋渚嬬殑閮ㄥ垎淇℃伅
        			ss=StrListChange.ListToStr(DBUtil.getALLCase());
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionCaseXx)){////閫氳繃鏍囧織浣嶈幏鍙栨渚嬮儴鍒嗕俊鎭�
        		    content=msgStr.substring(20);
        			ss=StrListChange.ListToStr(DBUtil.getSectionCaseXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionCaseTitleXx)){//閫氳繃鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
        		    content=msgStr.substring(25);
        			ss=StrListChange.ListToStr(DBUtil.getSectionCaseTitleXx(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.getSectionCaseTitleXxs)){//閫氳繃妯＄硦鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
        		    content=msgStr.substring(26);
        			ss=StrListChange.ListToStr(DBUtil.getSectionCaseTitleXxs(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}
        		else if(msgStr.startsWith(Constant.getCaseDetail)){//鑾峰彇妗堜緥璇︾粏淇℃伅
        		    content=msgStr.substring(17);
        			ss=StrListChange.ListToStr(DBUtil.getCaseDetail(content));
        			IOUtil.writeString(
        					ctx,
        					ss,
        					mm
        					);	
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateCaseFlag)){//鏇存柊妗堜緥鏍囧織浣�
        			content=msgStr.substring(18);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updateCaseFlag(s);
        			ctx.close();
        		}else if(msgStr.startsWith(Constant.updateCaseXx)){//鏇存柊妗堜緥鏍囧織浣�
        			content=msgStr.substring(16);
        			s=StrListChange.StrToArray(content);
        			DBUtil.updateCaseXx(s);
        			ctx.close();
        		}else if (msgStr.startsWith(Constant.getdesignerNameByID)) {//閫氳繃ID鑾峰彇璁捐甯堝悕
					content=msgStr.substring(23);
					System.out.println(content);
					ss=DBUtil.getdesignerNameByID(content);
					IOUtil.writeString(ctx, ss, mm);	
					ctx.close();
        		}else if (msgStr.startsWith(Constant.GetMaxCaseId)) {//鑾峰彇鏈�澶ф渚嬬殑ID
        			ss=DBUtil.GetMaxCaseId();
        			IOUtil.writeString(ctx, ss, mm);
        			ctx.close();
					
			}else if (msgStr.startsWith(Constant.insertCaseXx)) {//鎻掑叆妗堜緥淇℃伅
					content=msgStr.substring(16);
					s=StrListChange.StrToArray(content);
					DBUtil.insertCaseXx(s);
					ctx.close();
			}else if(msgStr.startsWith(Constant.getDesignerName)){//寰楀埌鎵�鏈夊簵閾哄悕瀛�
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
    		}else if(msgStr.startsWith(Constant.getALLDesigner)){//鑾峰彇鎵�鏈夊晢鍝佺殑閮ㄥ垎淇℃伅
    			ss=StrListChange.ListToStr(DBUtil.getALLDesigner());
    			IOUtil.writeString(
    					ctx,
    					ss,
    					mm
    					);	
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.getSectionDesignerXxs)){//閫氳繃妯＄硦鍚嶇О鑾峰彇閮ㄥ垎淇℃伅
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
    		}else if(msgStr.startsWith(Constant.updatedesignerXx)){//鏇存柊鍏徃鏍囧織浣�
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
			}else if(msgStr.startsWith(Constant.updateDesignerFlag)){//鏇存柊鍏徃鏍囧織浣�
    			content=msgStr.substring(22);
    			s=StrListChange.StrToArray(content);
    			DBUtil.updateDesignerFlag(s);
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.getUserPassword)){//鑾峰彇鐢ㄦ埛瀵嗙爜
    			content=msgStr.substring(19);
    			ls=DBUtil.getUserpassword(content);
    			ss=StrListChange.ListToStr(ls);
    			
    			IOUtil.writeString(ctx, ss, mm);
    			ctx.close();
    		}else if(msgStr.startsWith(Constant.insertUser)){//褰曞叆鏂扮敤鎴�
    			
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
    			kjskjkd
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
		System.out.println("鏈嶅姟鍣ㄥ紓甯稿凡閫�鍑�");
		cause.printStackTrace();
		ctx.close();
	}
}
