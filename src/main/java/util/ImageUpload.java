package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class ImageUpload {
	public Map<String,Object> upload(String picStr,String useid) throws IOException{
		picStr = picStr.replaceAll(" ","+");
		Map<String,Object> map = new HashMap<String,Object>();
		long nowTime = System.currentTimeMillis();
		OutputStream out = null;
		BASE64Decoder decoder = new BASE64Decoder();  
		if (picStr != null){
			try {
				// Base64解码
				byte[] b = decoder.decodeBuffer(picStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
				String imgFilePath ="D:\\"+useid+nowTime+".jpg";
//				String imgFilePath = "G:\\Program Files\\Tomcat\\webapps\\"+useid+nowTime+".jpg";
//				String imgFilePath = "/etc/apache-tomcat-8.0.32/webapps/solution/upload/"+useid+nowTime+".jpg";// 新生成的图片
				out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				ImageScale is=new ImageScale();
				BufferedImage img = ImageIO.read(new File(imgFilePath));
				int width = img.getWidth(null); // 得到源图宽
				int height = img.getHeight(null); // 得到源图长
				BufferedImage img_scale = is.imageZoomOut(img, width / 2, height / 2, true);
				String imgFilePath2 ="D:\\"+"mini"+useid+nowTime+".jpg";
//				String imgFilePath2 = "G:\\Program Files\\Tomcat\\webapps\\"+"mini"+ useid+nowTime+".jpg";
//				String imgFilePath2 = "/etc/apache-tomcat-8.0.32/webapps/solution/miniupload/"+"mini"+useid+nowTime+".jpg";// 新生成的图片
				FileOutputStream out2 = new FileOutputStream(imgFilePath2);
				ImageIO.write(img_scale,"jpg",out2); 
				out2.flush();
				out2.close();
				String imgURL = "http://210.13.199.111/upload/"+useid+nowTime+".jpg";
				String minimgURL = "http://210.13.199.111/solution/miniupload/"+"mini"+useid+nowTime+".jpg";
				map.put("MSG", "SUCCESS");
				map.put("URL", imgURL);
				map.put("miniURL", minimgURL);
			} catch (Exception e) {
				System.out.println("============================");
				System.out.println(e.toString());
				map.put("MSG", "FALSE");
			}finally{
				out.close();
			}
		}else{
			map.put("MSG", "no pic");
		}
		return map;
	}
	
}
