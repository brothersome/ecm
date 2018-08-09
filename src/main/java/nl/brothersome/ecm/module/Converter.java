package nl.brothersome.ecm.module;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class Converter {
	private static String conversion32 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public String toString32(long i) {
		String result = "" + conversion32.charAt((int) (i & 0x11));
		i = i >> 5;
		while (i>0) {
			result = conversion32.charAt((int) (i & 0x11)) + result;
			i = i >> 5;
		}
		return result;
	}

	public String getFilenameWithPath(long i) {
		String result = "/" + conversion32.charAt((int) (i & 0x11));
		i = i >> 5;
		int j = 1;
		while (i>0) {
			result = "/" + conversion32.charAt((int) (i & 0x11)) + result;
			j++;
			i = i >> 5;
		}
		while (j<14) {
			// 13 is the max
			result = "/0" + result;
		}
		return result;
	}

	public ByteBuffer unzip(String input) {
		try {
			InputStream is = new ByteArrayInputStream(input.getBytes());
			BufferedInputStream bis = new BufferedInputStream(is);
			ZipInputStream zis = new ZipInputStream(bis);
			byte[] tempBuffer = new byte[input.length()];
			zis.read(tempBuffer);
			return ByteBuffer.wrap(tempBuffer);
		} catch (IOException e) {
			return null;
		}
	}

	public ByteBuffer zip(String input) {
		try {
			OutputStream os = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			ZipOutputStream zos = new ZipOutputStream(bos);
			byte[] tempBuffer = new byte[input.length() * 20];
			zos.write(tempBuffer);
			return ByteBuffer.wrap(tempBuffer);
		} catch (IOException e) {
			return null;
		}
	}

}
