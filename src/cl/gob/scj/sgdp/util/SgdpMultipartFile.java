package cl.gob.scj.sgdp.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

public class SgdpMultipartFile implements MultipartFile {
	
	private String name;
	private String originalFilename;
	private String contentType;
	private byte[] bytes;
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	@Override
	public String getOriginalFilename() {
		return this.originalFilename;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public boolean isEmpty() {
		if (this.bytes == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long getSize() {
		return this.bytes.length;
	}

	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return this.bytes;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(this.bytes);
		return bis;
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		FileOutputStream stream = new FileOutputStream(dest);
		try {
		    stream.write(bytes);
		} finally {
		    stream.close();
		}

	}

}
