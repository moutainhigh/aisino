package com.aisino.test;

import static org.joda.time.DateTime.now;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.aisino.protocol.bean.RESPONSE_COMMON_FPKJ;
import com.aisino.protocol.bean.TaxcodeResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TestzctaxcodeResponse {
    public static void main(String[] args) {
//        String a = "<?xml version=\"1.0\" encoding=\"gbk\"?><business  id=\"FPKJ\" comment=\"发票开具\"><RESPONSE_COMMON_FPKJ  class=\"RESPONSE_COMMON_FPKJ\"><FPQQLSH>发票请求流水号</FPQQLSH><JQBH>税控设备编号</JQBH><FP_DM>发票代码</FP_DM><FP_HM>发票号码</FP_HM><KPRQ>开票日期</KPRQ><FP_MW>发票密文</FP_MW><JYM>校验码</JYM><EWM>二维码</EWM><RETURNCODE>返回代码</RETURNCODE><RETURNMSG>返回信息</RETURNMSG></RESPONSE_COMMON_FPKJ></business>";

        String a = "<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><business  id=\\\"FPKJ\\\" comment=\\\"发票开具\\\"><RESPONSE_COMMON_FPKJ  class=\\\"RESPONSE_COMMON_FPKJ\\\"><FPQQLSH>2</FPQQLSH><JQBH>1231412541</JQBH><FP_DM>111001571071</FP_DM><FP_HM>00000001</FP_HM><KPRQ>20150731013420</KPRQ><FP_MW><[CDATA[7766<1++--/877+>679>278476*387>-*97/+45864<+2/589111<9-59107*8<04/+11/>>9>2>95218476*31<>-*97/+45864<+2/39/1]]></FP_MW><JYM>63890371221214214477</JYM><EWM><[CDATA[iVBORw0KGgoAAAANSUhEUgAAAIsAAACLCAIAAADee/2oAAADqUlEQVR42u3b0XHDMAwEUfffdFJBYvFuAcnj1acztig+jgACzOvH69nXyyn4DKFXd/13gwvfuvKnK7e48kk2nnIYzawqpJBCrNDZKzL61hXF7PmzER6tj6M1hMyqQgopNCR09L4+etpNBip+ZMsUmVWFFFLoU4TKl/vcislgqFsopJBCHyqEVxnKHXsWP6jahEIKKfRAoaymkL3Bs5JlWS/Ac+vPqPoopNDXC5X9IT95egfPTxT6ViHsYMpYDkpVRctoWobe/KyPQgop1Akt7NjLuuTRaqBGuHnWRyGFFBoVKkeJDAWcPrx3vtmMf5NtK6SQQmNCc8dcqEnHe1HUImgeRyGFFEKE8F5LSb7QZ8Kb1kMhSiGFFEKEMphsQjfvRZ1PurHMqpBCCiFC+OzjJYm56aOaUmXarZBCCo0KUSEhe1oqYS3HXBY6h+oXCimkECtEhYTNqcGP3VBzjWT/CimkECJUBhuqC051pqk66VyOfv3uCimkECJUJpHUO71cH1RAoiIKMj8KKaQQIkS1hKniwsIw5qIXe3RJIYUUQoRuafA0J2BOE/Fs+vAxA1UfhRRSiK7LUY+NVwfwAIBXGZCti0IKKYQIzb1nNxPfuQBZhtVm86CQQgqxQgtbbry6milSg6cKEHlNQSGFFOpyOaoGSvXX5+IiTo5EOIUUUggRmtszL3Sd8brtZpX27S0UUkghVmhoYzy35e4nHU/f2W2JQgopNHQqmGrnZFn7ZlcJrxGzS0chhRRChBYOx+C/nAWSLPsvaxPkWR+FFFKoE5rr/VA5MVUexbtBWWn47Q8qpJBC00L4vpqaLOo8EF4dYGslCimk0JoQXm4oFwHVvMHjEBVEFVJIIVConBq8ZU4VKfAywVwzPj+NpZBCCkFCc6nwXLN5M0dnw49CCim0JkT1P7Iu+NxqYOubP2P/CaOQQgqxQnNvcOqIT9mGyRbBjZdCCinECmUb44XWEQ5cPjJeNDmuKSikkELdSZKFlg81+3PF2YV1lnfwFFJIoajHihcE8Vf5LXHxaPBZZg/UFBRSSKG/hfA5yiqeeH8IyXcnyqN5pqCQQgrR2XYWdcomEJKngn1xqjQc9M8UUkihISGq/If3xal668LZRKQQrJBCCj1TaK61XB7WKbNkfIORZ9sKKaTQrUJls5k6M4QHEmoRXP8dhRRSaEioPEmz6Vr+4C0bg9sqpwop9PVC+AGaskmM92zKwJb1s5thKKSQQoiQ12MvhZ5+/QJCuAiBGmE26AAAAABJRU5ErkJggg==]]></EWM><RETURNCODE>0000</RETURNCODE><RETURNMSG>返回信息</RETURNMSG></RESPONSE_COMMON_FPKJ></business>";
        
        String b = "iVBORw0KGgoAAAANSUhEUgAAAIsAAACLCAIAAADee/2oAAADqUlEQVR42u3b0XHDMAwEUfffdFJBYvFuAcnj1acztig+jgACzOvH69nXyyn4DKFXd/13gwvfuvKnK7e48kk2nnIYzawqpJBCrNDZKzL6 1hXF7PmzER6tj6M1hMyqQgopNCR09L4+etpNBip+ZMsUmVWFFFLoU4TKl/vcislgqFsopJBCHyqE VxnKHXsWP6jahEIKKfRAoaymkL3Bs5JlWS/Ac+vPqPoopNDXC5X9IT95egfPTxT6ViHsYMpYDkpVRctoWobe/KyPQgop1Akt7NjLuuTRaqBGuHnWRyGFFBoVKkeJDAWcPrx3vtmMf5NtK6SQQmNCc8dc qEnHe1HUImgeRyGFFEKE8F5LSb7QZ8Kb1kMhSiGFFEKEMphsQjfvRZ1PurHMqpBCCiFC+OzjJYm56aOaUmXarZBCCo0KUSEhe1oqYS3HXBY6h+oXCimkECtEhYTNqcGP3VBzjWT/CimkECJUBhuqC051 pqk66VyOfv3uCimkECJUJpHUO71cH1RAoiIKMj8KKaQQIkS1hKniwsIw5qIXe3RJIYUUQoRuafA0 J2BOE/Fs+vAxA1UfhRRSiK7LUY+NVwfwAIBXGZCti0IKKYQIzb1nNxPfuQBZhtVm86CQQgqxQgtb bry6milSg6cKEHlNQSGFFOpyOaoGSvXX5+IiTo5EOIUUUggRmtszL3Sd8brtZpX27S0UUkghVmho Yzy35e4nHU/f2W2JQgopNHQqmGrnZFn7ZlcJrxGzS0chhRRChBYOx+C/nAWSLPsvaxPkWR+FFFKo E5rr/VA5MVUexbtBWWn47Q8qpJBC00L4vpqaLOo8EF4dYGslCimk0JoQXm4oFwHVvMHjEBVEFVJI IVConBq8ZU4VKfAywVwzPj+NpZBCCkFCc6nwXLN5M0dnw49CCim0JkT1P7Iu+NxqYOubP2P/CaOQ QgqxQnNvcOqIT9mGyRbBjZdCCinECmUb44XWEQ5cPjJeNDmuKSikkELdSZKFlg81+3PF2YV1lnfw FFJIoajHihcE8Vf5LXHxaPBZZg/UFBRSSKG/hfA5yiqeeH8IyXcnyqN5pqCQQgrR2XYWdcomEJKn gn1xqjQc9M8UUkihISGq/If3xal668LZRKQQrJBCCj1TaK61XB7WKbNkfIORZ9sKKaTQrUJls5k6 M4QHEmoRXP8dhRRSaEioPEmz6Vr+4C0bg9sqpwop9PVC+AGaskmM92zKwJb1s5thKKSQQoiQ12Mv hZ5+/QJCuAiBGmE26AAAAABJRU5ErkJggg==".trim();
        final RESPONSE_COMMON_FPKJ returnInfo;
        XStream stream = new XStream(new DomDriver());
        stream.alias("business", TaxcodeResponse.class);
//        stream.alias("RESPONSE_COMMON_FPKJ", RESPONSE_COMMON_FPKJ.class);
        TaxcodeResponse res = (TaxcodeResponse)stream.fromXML(a.replace("class", "class1"));
        System.out.println(res);
        // if
        // (XmlPar.RESPONSE_BALANCE_SUCCESS.equals(returnStateInfo.getReturnCode()))
        // {// balance返回成功
//        returnInfo = (RESPONSE_COMMON_FPKJ) EIProtocolFactory.getDataRoot(responseMessage).get(0);
        
        final DateTime begin1 = now();
        
        final Long millSeconds1 = new Duration(begin1, now()).getStandardMinutes();
        
        
        
    }
}
