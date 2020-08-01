package Day0726;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/*
 * 网络基本概念：IP端口
 * 协议：ISO  7层结构==>TCP/IP＝＝>socket
 * 
 * URL：用户访问远程服务资源==服务器自愿访问==Tomcat服务器
 * Socket：计算机互相通讯==QQ
 * 
 * 多线程：+URL==多线程下载（迅雷下载）
 * 
 * 实现过程
 * 1.单线程下载
 * 2.单线程分块下载
 * 		1.获取文件总大小
 * 		2.每块的大小
 * 		3.时间的统计
 * 3.多线程下载
 * 	1.进度统计（顺序被打乱）
 * 	2.合并的十季，要等到所有的块下载完成才会这样
 * 	3.时间的统计
 * 
 * 隐患：对块数的限制12M/2M=6
 * 1024M/2M=512个子线程
 * 千万别下大文件
 * 必须要进行快数的限制  dowmNums 不能超过10
 * 
 * */
public class Demo01 {
	// 当前正在下载块
	private int downNums = 0;

	public static void main(String[] args) throws IOException, InterruptedException {
		new Demo01().download();
	}

	public void download() throws IOException, InterruptedException {
		// 忽略SSL验证
		// SslUtils.ignoreSsl();
		URL url = new URL(
				"https://mirror.bit.edu.cn/apache/tomcat/tomcat-10/v10.0.0-M7/bin/apache-tomcat-10.0.0-M7-windows-x64.zip");
		String filename = "D:/apache-tomcat-10.0.0-M7-windows-x64.zip";
		long time = System.currentTimeMillis();

		URLConnection conn = url.openConnection();
		// 获取文件总大小
		int filesize = conn.getContentLength();
		// 每块的大小(自定义2)
		int blocksize = 2 * 1024 * 1024;
		// 计算块数
		int blocknums = filesize / blocksize;
		if (filesize % blocksize != 0) {
			blocknums++;
		}

		System.out.println("开始下载");

		for (int i = 0; i < blocknums; i++) {
			/* 开启线程下载 */
			downNums++;
			
			synchronized(this) {
				//如果当前下载的块数大于0将继续等待
				System.out.println("当前下载块数达到10");
				while(downNums>10) {
					wait();
				}
			}
			int index = i;
			new Thread() {
				public void run() {
					try {
						System.out.println("第" + (index + 1) + "块开始下载");
						/* 在每次循环中获取一个连接对象 */
						URLConnection conn= url.openConnection();
						
						InputStream in= conn.getInputStream();

						FileOutputStream out= new FileOutputStream(filename + index);

						// 开始的字节数
						int beginBytes = index * blocksize;
						// 结束的字节数
						int endBytes = beginBytes + blocksize;
						// 结束的字节数不能超过文件的大小
						if (endBytes > filesize) {
							endBytes = filesize;
						}
						// 跳过开始的字节数
						in.skip(beginBytes);
						// 当前下载到的位置
						int position = beginBytes;

						byte[] buffer = new byte[1024];
						int count;
						while ((count = in.read(buffer)) > 0) {
							if (position + count > endBytes) {
								// 计算超出部分
								int a = position + count - endBytes;
								// 减去超出部分
								count = count - a;
							}

							out.write(buffer, 0, count);
							// 更新下载位置（向前推进）
							position += count;
							// 如果下载位置已经到达该块结束的位置
							if (position >= endBytes) {
								break;
							}
						}
						in.close();
						out.close();
						System.out.println("第" + (index + 1) + "块结束下载");
						//匿名类中访问外部类对象的方法是Dmo01.this
						synchronized(Demo01.this) {
							Demo01.this.downNums--;
							Demo01.this.notify();
						}
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}.start();
			
			//
			//file.delete();

		}
		/*再次等待*/
		synchronized(this) {
			//如果当前下载的块数大于0将继续等待
			while(downNums>0) {
				wait();
			}
		}
		// 合并文件
		marge(filename, blocknums);
		System.out.println("共花了" + (System.currentTimeMillis() - time) / 1000 + "秒");
		
		Delete(filename, blocknums);

		System.out.println("下载完成");
	}
	public static void Delete(String path, int filenums) {
		for (int i = 0; i < filenums; i++) {
			File file=new File("D:","apache-tomcat-10.0.0-M7-windows-x64.zip"+i);
			file.delete();
		}
	}
	
	public static void marge(String path, int filenums) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		for (int i = 0; i < filenums; i++) {
			FileInputStream fis = new FileInputStream(path + i);
			byte[] buffer = new byte[1024];
			int count;
			while ((count = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fis.close();

		}
		fos.close();
	}
}
