package Day0725.File;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetAddress;
import java.net.ServerSocket;

public class TCPServerSocket {

	public static final int PORT = 8887;
	public static final String IP = "127.0.0.1"; 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		try {
			//创建服务器端
			ServerSocket mServerSocket = new ServerSocket(8887);
			System.out.println("服务器启动成功，监听8887端口，正在等待被连接");
			
			//客户端连接服务器端
			Socket socket = mServerSocket.accept();
			System.out.println("成功连接客户端与服务器!");

			InetAddress myAddress=socket.getInetAddress();
			SocketAddress otherAddress=socket.getRemoteSocketAddress();
			System.out.println("我的地址"+myAddress);
			System.out.println("服务器的地址"+otherAddress);
			
			//连接成功，开始传输文件
			receiveFile(socket);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void receiveFile(Socket socket) {

		FileOutputStream fos = null;//文件读入流
		DataInputStream dis = null;//DataOutputStream数据输出流允许应用程序将基本Java数据类型写到基础输出流中

		//buffer起缓冲作用，一次读取或写入多个字节的数据
		byte[] buffer = new byte[1024];

		try {
			try {
				//这里使用DataInputStream，可以调用它的readUTF方法来读取要传输的文件名，客户端使用writeUTF方法将文件名先传输过来
				dis = new DataInputStream(socket.getInputStream());

				//首先读取文件名
				String oldFileName = dis.readUTF();
				//文件路径采用与客户端相同的路径，文件名重新命名
				String filePath = TCPClientSocket.fileDir + genereateFileName(oldFileName);
				System.out.println("receive filePath = " + filePath);

				//利用FileOutputStream来操作文件输出流
				fos = new FileOutputStream(new File(filePath));

				int length = 0;

				/*
				 * length = dis.read(buffer, 0, buffer.length)
				 * 一次读入1024个字节的内容到buffer中，length代表实际读入的字节数 fos.write(buffer, 0, length)
				 * 一次从buffer中的length个字节的内容写入到文件中 （注：文件大小超过1024B时，length一般为1024，最后一次读取可能小于1024）
				 */
				while ((length = dis.read(buffer, 0, buffer.length)) > 0) {
					fos.write(buffer, 0, length);
					fos.flush();
				}

			} finally {
				//使用完毕后，应关闭输入、输出流和socket
				if (dis != null)
					dis.close();
				if (fos != null)
					fos.close();
				if (socket != null)
					socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * example : oldName = "Java_TCPIP_Socket.pdf" newName =
	 * "Java_TCPIP_Socket-2.pdf"
	 */
	private static String genereateFileName(String oldName) {
		String newName = null;
		newName = oldName.substring(0, oldName.lastIndexOf(".")) + "-2" + oldName.substring(oldName.lastIndexOf("."));
		return newName;
	}

}
