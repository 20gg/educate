﻿package com.hysm.wecat.wxdb;

import java.util.UUID;


public class Base64
{
    /** Base64编码表。*/
    private static char Base64Code[] =
    {
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
        'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
        'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
        'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/',
    };

    /** Base64解码表。*/
    private static byte Base64Decode[] =
    {
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, //注意两个63，为兼容SMP，
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,63,-1,63, //“/”和“-”都翻译成63。
        52,53,54,55,56,57,58,59,60,61,-1,-1,-1, 0,-1,-1,
        -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14, //注意两个0：
        15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,-1, //“A”和“=”都翻译成0。
        -1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,
        41,42,43,44,45,46,47,48,49,50,51,-1,-1,-1,-1,-1,
    };

    /**
     * 构造方法私有化，防止实例化。
     */
    private Base64() {}

    /**
     * Base64编码。将字节数组中字节3个一组编码成4个可见字符。
     * @param b 需要被编码的字节数据。
     * @return 编码后的Base64字符串。
     */

    public static String encode(String str)
    {
        try
        {
            return encode(str.getBytes("GBK"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public static String encode(byte[] b)
    {
        int code = 0;

        //按实际编码后长度开辟内存，加快速度
        StringBuffer sb =new StringBuffer(((b.length-1)/3)<<2+4);

        //进行编码
        for (int i=0;i<b.length;i++)
        {
            code|=(b[i]<<(16-i%3*8)) & (0xff<<(16-i%3*8));
            if (i%3==2 || i==b.length-1)
            {
                sb.append(Base64Code[(code & 0xfc0000) >>> 18 ]);
                sb.append(Base64Code[(code & 0x3f000)  >>> 12 ]);
                sb.append(Base64Code[(code & 0xfc0)    >>> 6  ]);
                sb.append(Base64Code[ code & 0x3f             ]);
                code=0;
            }
        }

        //对于长度非3的整数倍的字节数组，编码前先补0，编码后结尾处编码用=代替，
        //=的个数和短缺的长度一致，以此来标识出数据实际长度
        if (b.length%3>0)
        {
            sb.setCharAt(sb.length()-1,'=');
        }
        if (b.length%3==1)
        {
            sb.setCharAt(sb.length()-2,'=');
        }
        return sb.toString();
    }

    /**
     * Base64解码。
     * @param code 用Base64编码的ASCII字符串
     * @return 解码后的字节数据
     */

    public static String decodeGBK(String code)
    {

        try
        {
            return new String(decode(code),"GBK");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public static byte[] decode(String code)
    {

        //检查参数合法性
        if (code==null)
        {
            return null;
        }
        int len = code.length();
        if (len%4!=0)
        {
            throw new IllegalArgumentException(
                  "Base64 string length must be 4*n");
        }
        if (code.length()==0)
        {
            return new byte[0];
        }

        //统计填充的等号个数
        int pad = 0;
        if (code.charAt(len-1)=='=')
        {
            pad++;
        }
        if (code.charAt(len-2)=='=')
        {
           pad++;
        }

        //根据填充等号的个数来计算实际数据长度
        int retLen = len/4*3 - pad;

        //分配字节数组空间
        byte[] ret = new byte [retLen];

        //查表解码
        char ch1,ch2,ch3,ch4;
        int i;
        for (i=0;i<len;i+=4)
        {
            int j=i/4*3;
            ch1 = code.charAt(i);
            ch2 = code.charAt(i+1);
            ch3 = code.charAt(i+2);
            ch4 = code.charAt(i+3);
            int tmp = (Base64Decode[ch1]<<18)|(Base64Decode[ch2]<<12)
                    |(Base64Decode[ch3]<<6)|(Base64Decode[ch4]);
            ret[j] = (byte) ((tmp&0xff0000) >> 16);
            if (i<len-4)
            {
                ret[j+1] = (byte) ((tmp&0x00ff00) >> 8);
                ret[j+2] =(byte) ((tmp&0x0000ff));
            }
            else
            {
                if(j+1<retLen)
                {
                    ret[j+1] = (byte) ((tmp&0x00ff00) >> 8);
                }
                if(j+2<retLen)
                {
                    ret[j+2] = (byte) ((tmp&0x0000ff));
                }
            }
        }
        return ret;
    }

      public static void main(String[] args)
      {   
    	  //System.out.println(new String(Base64.decode("xPq6w6Oh08q8/tLRytWhos7Su+HU2jI00KHKscTau9i4tMT6tcTTyrz+o6zH69ei0uKy6crVo6Egoaq6ztTzu6o=")));
          //System.out.println(new String(Base64.decode("dXNlcm5hbWU6")));
          //System.out.println(new String(Base64.decode("YWFyZW4=")));
          //System.out.println(new String(Base64.decode("UGFzc3dvcmQ6")));
          //System.out.println(new String(Base64.decode("bmo0NDMxMTIzbmp1Y3Q=")));
         System.out.println("**********");
         System.out.println(Base64.decodeGBK("MjM5NDI1NTA4MA=="));
         
         
         byte[] temp = {(byte)0x7c,(byte)0xd1,(byte)0xc3,(byte)0xf8,(byte)0x84,(byte)0x1f,(byte)0x00,(byte)0x17,(byte)0x4c,(byte)0x00,(byte)0xdc,(byte)0x40,(byte)0x08,(byte)0x00,(byte)0x45,(byte)0x00,
        		 (byte)0x05,(byte)0xdc,(byte)0x11,(byte)0xb1,(byte)0x00,(byte)0x00,(byte)0x40,(byte)0x06,(byte)0xdd,(byte)0x4e,(byte)0xc0,(byte)0xa8,(byte)0x02,(byte)0x65,(byte)0xc0,(byte)0xa8,
        		 (byte)0x02,(byte)0x67,(byte)0x00,(byte)0x50,(byte)0xc7,(byte)0xb1,(byte)0x9f,(byte)0x04,(byte)0x90,(byte)0xf3,(byte)0x84,(byte)0xa8,(byte)0x9d,(byte)0x97,(byte)0x50,(byte)0x18,
        		 (byte)0x0d,(byte)0xce,(byte)0x55,(byte)0xa9,(byte)0x00,(byte)0x00,(byte)0xd7,(byte)0xdf,(byte)0x5d,(byte)0xf8,(byte)0x8f,(byte)0xef,(byte)0xba,(byte)0x9f,(byte)0xc5,(byte)0x3f,
        		 (byte)0x96,(byte)0x3f,(byte)0xfe,(byte)0xde,(byte)0x3d,(byte)0x98,(byte)0x87,(byte)0x3f,(byte)0xd1,(byte)0x30,(byte)0x2a,(byte)0xa0,(byte)0xcf,(byte)0x9d,(byte)0x0c,(byte)0x6b,
        		 (byte)0x1e,(byte)0x96,(byte)0x6d,(byte)0x1c,(byte)0xfa,(byte)0xb4,(byte)0x08,(byte)0xee,(byte)0x18,(byte)0x3c,(byte)0x4e,(byte)0x12,(byte)0xdd,(byte)0x30,(byte)0x9e,(byte)0xd9,
        		 (byte)0x50,(byte)0xfd,(byte)0x36,(byte)0x29,(byte)0x96,(byte)0x9e,(byte)0x13,(byte)0xe1,(byte)0xce,(byte)0xef,(byte)0xfb,(byte)0xe0,(byte)0xc4,(byte)0xde,(byte)0x4a,(byte)0x98,
        		 (byte)0x8e,(byte)0x27,(byte)0xe2,(byte)0x82,(byte)0xc7,(byte)0x2b,(byte)0x4a,(byte)0x9e,(byte)0xff,(byte)0x8d,(byte)0x9f,(byte)0xf8,(byte)0xce,(byte)0x9f,(byte)0x3f,(byte)0xfa,
        		 (byte)0xd3,(byte)0x9b,(byte)0x07,(byte)0xe0,(byte)0x9f,(byte)0x1f,(byte)0x0f,(byte)0xda,(byte)0x3f,(byte)0xfc,(byte)0xfb,(byte)0xef,(byte)0xff,(byte)0xf5,(byte)0xeb,(byte)0xf9,
        		 (byte)0xe7,(byte)0x3f,(byte)0xfc,(byte)0xc7,(byte)0x1f,(byte)0xff,(byte)0x04,(byte)0xe9,(byte)0x51,(byte)0xfc,(byte)0xf7,(byte)0xff,(byte)0xf8,(byte)0xf3,(byte)0x7f,(byte)0xce,
        		 (byte)0xe3,(byte)0x7e,(byte)0x6d,(byte)0x65,(byte)0xe0,(byte)0x7a,(byte)0x6a,(byte)0xab,(byte)0x2b,(byte)0xd6,(byte)0xd4,(byte)0xb9,(byte)0xba,(byte)0xfa,(byte)0x27,(byte)0xfc,
        		 (byte)0xa7,(byte)0x9f,(byte)0x1f,(byte)0x0b,(byte)0x6b,(byte)0xfe,(byte)0x70,(byte)0x5d,(byte)0xfd,(byte)0x7f,(byte)0x79,(byte)0x55,(byte)0xfd,(byte)0x66,(byte)0x60,(byte)0xee,
        		 (byte)0x71,(byte)0xae,(byte)0xd1,(byte)0x02,(byte)0x61,(byte)0x1f,(byte)0xea,(byte)0x21,(byte)0x06,(byte)0x52,(byte)0x61,(byte)0x09,(byte)0xed,(byte)0xa1,(byte)0x8f,(byte)0x74,
        		 (byte)0x64,(byte)0x8d,(byte)0x61,(byte)0xec,(byte)0xd1,(byte)0x5d,(byte)0x58,(byte)0xa2,(byte)0x0c,(byte)0x50,(byte)0xd4,(byte)0x1e,(byte)0xcf,(byte)0xa0,(byte)0xd0,(byte)0x2a,
        		 (byte)0x3f,(byte)0xa1,(byte)0xab,(byte)0xce,(byte)0xda,(byte)0x4f,(byte)0xa3,(byte)0x2d,(byte)0x35,(byte)0xc3,(byte)0x45,(byte)0x18,(byte)0xc4,(byte)0x77,(byte)0x8f,(byte)0xcf,
        		 (byte)0xeb,(byte)0xeb,(byte)0x87,(byte)0xba,(byte)0x2b,(byte)0xc1,(byte)0x00,(byte)0xe8,(byte)0x25,(byte)0x6c,(byte)0xff,(byte)0x61,(byte)0xd6,(byte)0x9e,(byte)0xc2,(byte)0xaa,
        		 (byte)0x77,(byte)0x48,(byte)0x92,(byte)0x20,(byte)0x9e,(byte)0x62,(byte)0x35,(byte)0x0b,(byte)0x50,(byte)0xbe,(byte)0xcf,(byte)0x50,(byte)0xa3,(byte)0x8f,(byte)0x5c,(byte)0x16,
        		 (byte)0xde,(byte)0x3b,(byte)0x06,(byte)0x3d,(byte)0xdd,(byte)0x8c,(byte)0xd3,(byte)0xf8,(byte)0xbc,(byte)0xa0,(byte)0x23,(byte)0x52,(byte)0xb8,(byte)0x38,(byte)0xd3,(byte)0x07,
        		 (byte)0x1b,(byte)0x09,(byte)0x0a,(byte)0x22,(byte)0x95,(byte)0x03,(byte)0xb0,(byte)0x07,(byte)0x60,(byte)0xaf,(byte)0x2e,(byte)0x46,(byte)0x45,(byte)0x54,(byte)0x32,(byte)0xc3,
        		 (byte)0x3c,(byte)0x77,(byte)0x48,(byte)0xd5,(byte)0x18,(byte)0x99,(byte)0x1d,(byte)0x35,(byte)0xa8,(byte)0x86,(byte)0xf1,(byte)0xd6,(byte)0x01,(byte)0x00,(byte)0x54,(byte)0xa1,
        		 (byte)0x2c,(byte)0xa0,(byte)0x44,(byte)0x52,(byte)0x8c,(byte)0xba,(byte)0xd9,(byte)0x71,(byte)0x33,(byte)0x8b,(byte)0xa1,(byte)0x4e,(byte)0x2f,(byte)0xe3,(byte)0xf3,(byte)0xf2,
        		 (byte)0x3a,(byte)0x4a,(byte)0xe1,(byte)0x94,(byte)0xd2,(byte)0x60,(byte)0x71,(byte)0x0e,(byte)0xca,(byte)0x15,(byte)0x12,(byte)0xeb,(byte)0x8e,(byte)0x0c,(byte)0xc4,(byte)0xe0,
        		 (byte)0xec,(byte)0x3a,(byte)0x6b,(byte)0x1e,(byte)0x6b,(byte)0x74,(byte)0xfa,(byte)0xd7,(byte)0x10,(byte)0xf6,(byte)0x12,(byte)0x7c,(byte)0xce,(byte)0x4a,(byte)0x42,(byte)0x01,
        		 (byte)0x12,(byte)0x95,(byte)0xc0,(byte)0x41,(byte)0xe2,(byte)0xaa,(byte)0xa5,(byte)0x38,(byte)0xd2,(byte)0xae,(byte)0xe3,(byte)0xf3,(byte)0xfa,(byte)0x3a,(byte)0x6a,(byte)0x08,
        		 (byte)0x2c,(byte)0x26,(byte)0xcf,(byte)0x66,(byte)0x56,(byte)0xb5,(byte)0xd2,(byte)0x5d,(byte)0x47,(byte)0x63,(byte)0x23,(byte)0x0a,(byte)0x09,(byte)0x2c,(byte)0xcd,(byte)0x13,
        		 (byte)0x78,(byte)0xf1,(byte)0x62,(byte)0x2c,(byte)0x13,(byte)0x71,(byte)0xbd,(byte)0x1e,(byte)0xbb,(byte)0xc6,(byte)0x55,(byte)0xb8,(byte)0xa5,(byte)0x39,(byte)0xce,(byte)0xd6,
        		 (byte)0x21,(byte)0xaf,(byte)0x65,(byte)0x51,(byte)0xb8,(byte)0x22,(byte)0xf1,(byte)0xc7,(byte)0x28,(byte)0x69,(byte)0x99,(byte)0x96,(byte)0x60,(byte)0x94,(byte)0x46,(byte)0x4f,
        		 (byte)0x35,(byte)0xc4,(byte)0x48,(byte)0x11,(byte)0xdf,(byte)0xca,(byte)0xc6,(byte)0xe7,(byte)0xf5,(byte)0x75,(byte)0x34,(byte)0x44,(byte)0x58,(byte)0xbe,(byte)0xd6,(byte)0x98,
        		 (byte)0xcd,(byte)0x11,(byte)0x8e,(byte)0xe3,(byte)0x72,(byte)0x8e,(byte)0x8d,(byte)0xc9,(byte)0x50,(byte)0x0c,(byte)0xac,(byte)0xc8,(byte)0xd0,(byte)0xb2,(byte)0x77,(byte)0xa8,
        		 (byte)0x12,(byte)0xe3,(byte)0x67,(byte)0x86,(byte)0x46,(byte)0x1f,(byte)0x4c,(byte)0xdc,(byte)0x36,(byte)0xbe,(byte)0x33,(byte)0x71,(byte)0x9f,(byte)0x68,(byte)0x91,(byte)0xd2,
        		 (byte)0xe8,(byte)0xfa,(byte)0xb3,(byte)0x72,(byte)0x8d,(byte)0xac,(byte)0x8e,(byte)0x66,(byte)0x74,(byte)0x38,(byte)0xf6,(byte)0xd8,(byte)0x28,(byte)0x1e,(byte)0x4c,(byte)0x69,
        		 (byte)0xb2,(byte)0x4d,(byte)0xc0,(byte)0x7a,(byte)0x6a,(byte)0xed,(byte)0x90,(byte)0x03,(byte)0xbc,(byte)0x75,(byte)0xe2,(byte)0x2f,(byte)0xe3,(byte)0xbb,(byte)0x39,(byte)0xc9,
        		 (byte)0xc7,(byte)0xb9,(byte)0x65,(byte)0xa8,(byte)0xfe,(byte)0x9c,(byte)0x39,(byte)0x66,(byte)0x25,(byte)0xf9,(byte)0xce,(byte)0x9e,(byte)0x20,(byte)0xa8,(byte)0xfb,(byte)0xa7,
        		 (byte)0x1b,(byte)0x03,(byte)0x4d,(byte)0x6a,(byte)0xb5,(byte)0xf0,(byte)0xb0,(byte)0xfd,(byte)0xe3,(byte)0xf3,(byte)0x71,(byte)0xd1,(byte)0x8b,(byte)0x4d,(byte)0x4e,(byte)0x76,
        		 (byte)0x81,(byte)0xaf,(byte)0x93,(byte)0x6e,(byte)0xd7,(byte)0x97,(byte)0x10,(byte)0xb3,(byte)0x61,(byte)0x60,(byte)0xb3,(byte)0x51,(byte)0xd7,(byte)0xb3,(byte)0x39,(byte)0xbb,
        		 (byte)0x37,(byte)0x53,(byte)0x03,(byte)0xbd,(byte)0x9c,(byte)0xeb,(byte)0x1a,(byte)0x7f,(byte)0x74,(byte)0x4b,(byte)0xbe,(byte)0x23,(byte)0xc7,(byte)0xfc,(byte)0x44,(byte)0x3a,
        		 (byte)0x77,(byte)0x5e,(byte)0x8d,(byte)0x4f,(byte)0x2b,(byte)0xc7,(byte)0x47,(byte)0xd4,(byte)0xc5,(byte)0xe7,(byte)0x29,(byte)0x9c,(byte)0x7b,(byte)0x88,(byte)0xbc,(byte)0x14,
        		 (byte)0x59,(byte)0x1c,(byte)0x8d,(byte)0x6b,(byte)0x91,(byte)0xb8,(byte)0x34,(byte)0x75,(byte)0x7a,(byte)0xf8,(byte)0x6a,(byte)0x65,(byte)0x9b,(byte)0x15,(byte)0x35,(byte)0x5d,
        		 (byte)0x1b,(byte)0x9e,(byte)0xfe,(byte)0x55,(byte)0x90,(byte)0x70,(byte)0xc8,(byte)0x7c,(byte)0x66,(byte)0x7b,(byte)0x3d,(byte)0x4a,(byte)0xd0,(byte)0x7c,(byte)0x56,(byte)0xc3,
        		 (byte)0x04,(byte)0x84,(byte)0x9b,(byte)0x6d,(byte)0xc3,(byte)0x35,(byte)0x38,(byte)0xd7,(byte)0x2b,(byte)0xa3,(byte)0x31,(byte)0x13,(byte)0x2b,(byte)0x04,(byte)0x7d,(byte)0xcd,
        		 (byte)0xc7,(byte)0xec,(byte)0x04,(byte)0x9e,(byte)0x05,(byte)0x15,(byte)0x6f,(byte)0xc2,(byte)0x20,(byte)0x80,(byte)0x63,(byte)0x64,(byte)0xaf,(byte)0x73,(byte)0xca,(byte)0xa2,
        		 (byte)0x30,(byte)0x72,(byte)0x9e,(byte)0x0d,(byte)0x5a,(byte)0x18,(byte)0x88,(byte)0xcf,(byte)0x58,(byte)0xae,(byte)0x02,(byte)0x15,(byte)0x33,(byte)0xf3,(byte)0xdc,(byte)0xf9,
        		 (byte)0xca,(byte)0x60,(byte)0xce,(byte)0x0a,(byte)0xac,(byte)0x65,(byte)0x9f,(byte)0xb8,(byte)0x2a,(byte)0x94,(byte)0x54,(byte)0x4e,(byte)0xda,(byte)0x7f,(byte)0x03,(byte)0xd8,
        		 (byte)0xd3,(byte)0xcc,(byte)0xec,(byte)0x36,(byte)0xbb,(byte)0xe1,(byte)0xe9,(byte)0x5f,(byte)0x45,(byte)0xed,(byte)0x83,(byte)0x43,(byte)0x66,(byte)0xe4,(byte)0xb3,(byte)0x8e,
        		 (byte)0xd9,(byte)0xc3,(byte)0x57,(byte)0xc3,(byte)0xfd,(byte)0xf4,(byte)0xa8,(byte)0x44,(byte)0x5a,(byte)0x55,(byte)0x86,(byte)0xb9,(byte)0x1c,(byte)0x75,(byte)0xd9,(byte)0xda,
        		 (byte)0xce,(byte)0x6a,(byte)0x44,(byte)0x57,(byte)0x5f,(byte)0x9b,(byte)0xb6,(byte)0xf6,(byte)0x93,(byte)0xb1,(byte)0x71,(byte)0xf6,(byte)0x26,(byte)0x61,(byte)0x88,(byte)0x5e,
        		 (byte)0x17,(byte)0xf0,(byte)0x98,(byte)0xd0,(byte)0xae,(byte)0x89,(byte)0xc6,(byte)0x3e,(byte)0xfb,(byte)0xd0,(byte)0xab,(byte)0xe1,(byte)0xd6,(byte)0xc7,(byte)0x42,(byte)0x15,
        		 (byte)0x8c,(byte)0x43,(byte)0xc8,(byte)0xe1,(byte)0x7a,(byte)0x0e,(byte)0xee,(byte)0x55,(byte)0xd9,(byte)0x0a,(byte)0x23,(byte)0x20,(byte)0xf6,(byte)0x44,(byte)0xd1,(byte)0xf2,
        		 (byte)0xe2,(byte)0x49,(byte)0x48,(byte)0x43,(byte)0xda,(byte)0x6a,(byte)0x2e,(byte)0x53,(byte)0xb5,(byte)0x48,(byte)0x45,(byte)0x9c,(byte)0x14,(byte)0x4e,(byte)0x39,(byte)0x28,
        		 (byte)0x0f,(byte)0x21,(byte)0x22,(byte)0xe3,(byte)0x7e,(byte)0xdf,(byte)0xcc,(byte)0x83,(byte)0x89,(byte)0xef,(byte)0x62,(byte)0xd9,(byte)0x8b,(byte)0x85,(byte)0x27,(byte)0xc4,
        		 (byte)0x69,(byte)0xc3,(byte)0x38,(byte)0xc4,(byte)0xf5,(byte)0x1c,(byte)0xa6,(byte)0x47,(byte)0xd1,(byte)0x6f,(byte)0x04,(byte)0x46,(byte)0x80,(byte)0x43,(byte)0x35,(byte)0x4c,
        		 (byte)0x1b,(byte)0x24,(byte)0xb1,(byte)0x6f,(byte)0xcc,(byte)0xa2,(byte)0x3d,(byte)0xc3,(byte)0xdd,(byte)0x43,(byte)0xc8,(byte)0xe0,(byte)0x2c,(byte)0xe6,(byte)0x92,(byte)0x81,
        		 (byte)0x2e,(byte)0xcb,(byte)0x2e,(byte)0xb3,(byte)0x09,(byte)0x56,(byte)0x19,(byte)0x55,(byte)0xbf,(byte)0x0a,(byte)0xe6,(byte)0xfd,(byte)0x28,(byte)0x88,(byte)0x65,(byte)0xbb,
        		 (byte)0xfc,(byte)0x73,(byte)0x98,(byte)0x68,(byte)0xe5,(byte)0x61,(byte)0x82,(byte)0x98,(byte)0x8b,(byte)0x24,(byte)0x10,(byte)0xc3,(byte)0xe6,(byte)0xf9,(byte)0xc4,(byte)0x40,
        		 (byte)0x21,(byte)0x06,(byte)0xf7,(byte)0x7a,(byte)0xdf,(byte)0xad,(byte)0x8f,(byte)0x63,(byte)0x18,(byte)0x89,(byte)0xf9,(byte)0x34,(byte)0xa5,(byte)0xc1,(byte)0xb0,(byte)0xd6,
        		 (byte)0x5f,(byte)0x93,(byte)0xd4,(byte)0x5c,(byte)0xa1,(byte)0xcd,(byte)0xb9,(byte)0x39,(byte)0x83,(byte)0x6d,(byte)0xf9,(byte)0x8b,(byte)0x1a,(byte)0x22,(byte)0x50,(byte)0x8a,
        		 (byte)0xb1,(byte)0x8b,(byte)0xd2,(byte)0x6c,(byte)0xf0,(byte)0xb2,(byte)0xea,(byte)0xfe,(byte)0x99,(byte)0x8c,(byte)0x00,(byte)0x76,(byte)0x0c,(byte)0x09,(byte)0x97,(byte)0x58,
        		 (byte)0x10,(byte)0xa3,(byte)0x3a,(byte)0x9a,(byte)0x0d,(byte)0xef,(byte)0x6a,(byte)0x40,(byte)0x4e,(byte)0xd9,(byte)0x0d,(byte)0x5b,(byte)0xe6,(byte)0x0e,(byte)0x2c,(byte)0x6d,
        		 (byte)0xb3,(byte)0x45,(byte)0x3c,(byte)0x2f,(byte)0xe3,(byte)0x1c,(byte)0x06,(byte)0x93,(byte)0x81,(byte)0x39,(byte)0xdd,(byte)0x18,(byte)0xdd,(byte)0x15,(byte)0xea,(byte)0xde,
        		 (byte)0x45,(byte)0xc3,(byte)0x4b,(byte)0xd1,(byte)0xd7,(byte)0x9d,(byte)0x55,(byte)0x21,(byte)0xf6,(byte)0xb4,(byte)0xaa,(byte)0x75,(byte)0x2d,(byte)0xb3,(byte)0x1f,(byte)0x9f,
        		 (byte)0xd7,(byte)0xd7,(byte)0xab,(byte)0x34,(byte)0x75,(byte)0x86,(byte)0x11,(byte)0x49,(byte)0x32,(byte)0x3a,(byte)0xd8,(byte)0xd4,(byte)0xd8,(byte)0xcb,(byte)0xc6,(byte)0x4d,
        		 (byte)0x60,(byte)0x94,(byte)0x96,(byte)0x30,(byte)0x16,(byte)0xbd,(byte)0x0e,(byte)0xc7,(byte)0x53,(byte)0x4d,(byte)0x59,(byte)0x87,(byte)0x1d,(byte)0x1e,(byte)0x76,(byte)0x0f,
        		 (byte)0xab,(byte)0xb1,(byte)0x13,(byte)0x12,(byte)0xbe,(byte)0x67,(byte)0x31,(byte)0x15,(byte)0x48,(byte)0x03,(byte)0x02,(byte)0x08,(byte)0xd9,(byte)0x8d,(byte)0xcf,(byte)0xeb,
        		 (byte)0xeb,(byte)0xa4,(byte)0x47,(byte)0x11,(byte)0x8f,(byte)0x8f,(byte)0x31,(byte)0x45,(byte)0xdf,(byte)0x34,(byte)0x8e,(byte)0xa2,(byte)0x0a,(byte)0x45,(byte)0xf7,(byte)0x4f,
        		 (byte)0xb2,(byte)0x2d,(byte)0xc6,(byte)0xa9,(byte)0xce,(byte)0x33,(byte)0xcf,(byte)0x48,(byte)0x85,(byte)0xbe,(byte)0xae,(byte)0x73,(byte)0x64,(byte)0x3f,(byte)0xaa,(byte)0x9f,
        		 (byte)0x62,(byte)0x61,(byte)0xb0,(byte)0xc4,(byte)0x5d,(byte)0x09,(byte)0x8c,(byte)0x4b,(byte)0xea,(byte)0x9e,(byte)0x53,(byte)0x33,(byte)0x26,(byte)0x9c,(byte)0xea,(byte)0xa4,
        		 (byte)0xb3,(byte)0xa3,(byte)0x5c,(byte)0x33,(byte)0x29,(byte)0x41,(byte)0x7a,(byte)0x0f,(byte)0x19,(byte)0x00,(byte)0x7c,(byte)0x4f,(byte)0xc6,(byte)0x98,(byte)0xf1,(byte)0xa8,
        		 (byte)0x89,(byte)0x3a,(byte)0xa9,(byte)0x63,(byte)0xdd,(byte)0x8d,(byte)0x5b,(byte)0x90,(byte)0x50,(byte)0x37,(byte)0x24,(byte)0xf4,(byte)0x3a,(byte)0x01,(byte)0x29,(byte)0x10,
        		 (byte)0x8c,(byte)0x20,(byte)0xd6,(byte)0x01,(byte)0x23,(byte)0x24,(byte)0x1b,(byte)0x4f,(byte)0xa8,(byte)0x98,(byte)0x43,(byte)0x42,(byte)0x50,(byte)0xc9,(byte)0x8f,(byte)0xcf,
        		 (byte)0xcb,(byte)0xeb,(byte)0x28,(byte)0x73,(byte)0x46,(byte)0xea,(byte)0x92,(byte)0xa8,(byte)0x5d,(byte)0x63,(byte)0xb0,(byte)0xce,(byte)0xbc,(byte)0xa4,(byte)0xb9,(byte)0x58,
        		 (byte)0x8f,(byte)0x0b,(byte)0xc2,(byte)0x26,(byte)0x69,(byte)0x7c,(byte)0xbe,(byte)0x7d,(byte)0x9d,(byte)0x1c,(byte)0x88,(byte)0xbe,(byte)0xc6,(byte)0x54,(byte)0xba,(byte)0x2e,
        		 (byte)0x52,(byte)0xe8,(byte)0x33,(byte)0x35,(byte)0x10,(byte)0x28,(byte)0xf7,(byte)0x19,(byte)0x4b,(byte)0xa3,(byte)0x98,(byte)0x34,(byte)0x51,(byte)0x5d,(byte)0xaa,(byte)0x83,
        		 (byte)0xac,(byte)0x02,(byte)0x46,(byte)0x49,(byte)0x64,(byte)0x20,(byte)0xec,(byte)0x5a,(byte)0x21,(byte)0x98,(byte)0xe9,(byte)0xc9,(byte)0x64,(byte)0x63,(byte)0x55,(byte)0xc4,
        		 (byte)0x2e,(byte)0x50,(byte)0xca,(byte)0xc2,(byte)0xec,(byte)0xf0,(byte)0x75,(byte)0x86,(byte)0xb4,(byte)0xbc,(byte)0x07,(byte)0xa1,(byte)0x6c,(byte)0xc4,(byte)0x9c,(byte)0x34,
        		 (byte)0x05,(byte)0xa3,(byte)0xc8,(byte)0x9c,(byte)0xd0,(byte)0x37,(byte)0x5c,(byte)0x1c,(byte)0xef,(byte)0x79,(byte)0x04,(byte)0xf5,(byte)0xb4,(byte)0x71,(byte)0x5b,(byte)0x1a,
        		 (byte)0x44,(byte)0x3a,(byte)0x7a,(byte)0xe2,(byte)0x80,(byte)0x4a,(byte)0x16,(byte)0xc2,(byte)0x3a,(byte)0x52,(byte)0x5f,(byte)0x50,(byte)0xc8,(byte)0x6b,(byte)0xb1,(byte)0x39,
        		 (byte)0x10,(byte)0xf5,(byte)0xc7,(byte)0x95,(byte)0x37,(byte)0xb0,(byte)0x4d,(byte)0x46,(byte)0x8c,(byte)0x4a,(byte)0x07,(byte)0xe6,(byte)0x65,(byte)0xf8,(byte)0xf1,(byte)0x5d,
        		 (byte)0xe1,(byte)0x5e,(byte)0x38,(byte)0xd4,(byte)0xc3,(byte)0xd4,(byte)0x58,(byte)0x34,(byte)0xc4,(byte)0x28,(byte)0x7a,(byte)0x33,(byte)0xdb,(byte)0x34,(byte)0x73,(byte)0x26,
        		 (byte)0x8d,(byte)0x21,(byte)0xa5,(byte)0xb7,(byte)0x7c,(byte)0x03,(byte)0x7e,(byte)0x2e,(byte)0x22,(byte)0xf6,(byte)0xc1,(byte)0xaa,(byte)0x3f,(byte)0x41,(byte)0x54,(byte)0xe1,
        		 (byte)0xc4,(byte)0x3d,(byte)0x8d,(byte)0xe8,(byte)0x60,(byte)0xf7,(byte)0xb0,(byte)0x74,(byte)0xd9,(byte)0xf6,(byte)0xf0,(byte)0x2e,(byte)0xb9,(byte)0xb6,(byte)0x61,(byte)0x91,
        		 (byte)0x8b,(byte)0xc6,(byte)0x37,(byte)0xc3,(byte)0xa2,(byte)0x8d,(byte)0xa5,(byte)0x70,(byte)0xa5,(byte)0x32,(byte)0x44,(byte)0xb6,(byte)0x57,(byte)0x29,(byte)0x66,(byte)0x17,
        		 (byte)0xc5,(byte)0xb3,(byte)0x59,(byte)0xe3,(byte)0xfb,(byte)0x0e,(byte)0x03,(byte)0x7e,(byte)0x54,(byte)0x17,(byte)0x6b,(byte)0x6f,(byte)0x83,(byte)0x93,(byte)0xfa,(byte)0x44,
        		 (byte)0x99,(byte)0x3a,(byte)0x7a,(byte)0xab,(byte)0xa0,(byte)0xfa,(byte)0xa2,(byte)0x3e,(byte)0xfb,(byte)0xa1,(byte)0x6e,(byte)0x51,(byte)0x3b,(byte)0xd7,(byte)0xe8,(byte)0x46,
        		 (byte)0xfa,(byte)0x1d,(byte)0x33,(byte)0x31,(byte)0x3c,(byte)0x70,(byte)0x0c,(byte)0x4b,(byte)0x30,(byte)0x9c,(byte)0xf3,(byte)0x3b,(byte)0x25,(byte)0x85,(byte)0x23,(byte)0x8c,
        		 (byte)0x27,(byte)0x6b,(byte)0x3e,(byte)0xd9,(byte)0xc0,(byte)0xae,(byte)0xe9,(byte)0x46,(byte)0x92,(byte)0x26,(byte)0x02,(byte)0x74,(byte)0xa6,(byte)0x87,(byte)0xe4,(byte)0x54,
        		 (byte)0xd8,(byte)0x9a,(byte)0xc1,(byte)0xe5,(byte)0x89,(byte)0xc3,(byte)0x64,(byte)0x54,(byte)0xc2,(byte)0xb3,(byte)0x55,(byte)0x99,(byte)0xd8,(byte)0x17,(byte)0x42,(byte)0xd6,
        		 (byte)0x03,(byte)0xc4,(byte)0x36,(byte)0xf7,(byte)0xf0,(byte)0xf4,(byte)0xaf,(byte)0x06,(byte)0x81,(byte)0x43,(byte)0xe3,(byte)0x8d,(byte)0xa8,(byte)0x3d,(byte)0xa8,(byte)0x58,
        		 (byte)0xb1,(byte)0x3d,(byte)0x0d,(byte)0xa8,(byte)0x65,(byte)0x2f,(byte)0x8b,(byte)0xc0,(byte)0xd3,(byte)0xa1,(byte)0x96,(byte)0x76,(byte)0x02,(byte)0x2e,(byte)0x2f,(byte)0x32,
        		 (byte)0x85,(byte)0x11,(byte)0xca,(byte)0xf2,(byte)0x4d,(byte)0xcf,(byte)0xf1,(byte)0x5c,(byte)0xd1,(byte)0x7b,(byte)0x5b,(byte)0xf9,(byte)0x14,(byte)0x78,(byte)0x59,(byte)0x80,(byte)0xde,(byte)0x8b,(byte)0x7c,(byte)0x75,(byte)0x0e,(byte)0xeb,(byte)0xd0,(byte)0x77,(byte)0xeb,(byte)0x85,(byte)0x68,(byte)0xf7,(byte)0x17,(byte)0x12,(byte)0xe0,(byte)0x1c,(byte)0x49,(byte)0xd1,(byte)0xd7,(byte)0x50,(byte)0x37,(byte)0x07,(byte)0x64,(byte)0xf0,(byte)0xb1,(byte)0x34,(byte)0x99,(byte)0xa4,(byte)0xbf,(byte)0x82,(byte)0xf4,(byte)0x86,(byte)0x1b,(byte)0x7c,(byte)0x54,(byte)0xff,(byte)0x0e,(byte)0x7d,(byte)0x97,(byte)0x53,(byte)0x9b,(byte)0xbe,(byte)0x50,(byte)0xb5,(byte)0x86,(byte)0xa4,(byte)0x1a,(byte)0xa6,(byte)0x23,(byte)0x21,(byte)0xec,(byte)0x25,(byte)0xc9,(byte)0xd2,(byte)0x04,(byte)0x43,(byte)0x72,(byte)0x7b,(byte)0xef,(byte)0xb2,(byte)0xff,(byte)0x2c,(byte)0xa7,(byte)0x86,(byte)0x85,(byte)0x90,(byte)0x4d,(byte)0x53,(byte)0x1b,(byte)0x23,(byte)0x38,(byte)0x4f,(byte)0x7b,(byte)0x7b,(byte)0xd0,(byte)0xfe,(byte)0x6f,(byte)0xea,(byte)0xaa,(byte)0xd6,(byte)0x12,(byte)0x6b,(byte)0xda,(byte)0x27,(byte)0xa3,(byte)0x88,(byte)0x09,(byte)0xe2
};
         
         System.out.println(Base64.decode(new String(temp)));
         //System.out.println(UUID.randomUUID());
      }




}


