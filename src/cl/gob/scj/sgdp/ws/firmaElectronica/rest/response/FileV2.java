package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "content", "checksum_original", "checksum", "description", "status" })
@Generated("jsonschema2pojo")
public class FileV2 {

	@JsonProperty("type")
	private FileV2.Type type;
	@JsonProperty("content")
	private String content;
	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("checksum_original")
	private String checksumOriginal;
	@JsonProperty("checksum")
	private String checksum;
	@JsonProperty("description")
	private String description;
	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("status")
	private String status;

	@JsonProperty("type")
	public FileV2.Type getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(FileV2.Type type) {
		this.type = type;
	}

	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	@JsonProperty("content")
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("checksum_original")
	public String getChecksumOriginal() {
		return checksumOriginal;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("checksum_original")
	public void setChecksumOriginal(String checksumOriginal) {
		this.checksumOriginal = checksumOriginal;
	}

	@JsonProperty("checksum")
	public String getChecksum() {
		return checksum;
	}

	@JsonProperty("checksum")
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@Generated("jsonschema2pojo")
	public enum Type {

		PDF("PDF"), JSON("JSON"), XML("XML");

		private final String value;
		private final static Map<String, FileV2.Type> CONSTANTS = new HashMap<String, FileV2.Type>();

		static {
			for (FileV2.Type c : values()) {
				CONSTANTS.put(c.value, c);
			}
		}

		Type(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}

		@JsonCreator
		public static FileV2.Type fromValue(String value) {
			FileV2.Type constant = CONSTANTS.get(value);
			if (constant == null) {
				throw new IllegalArgumentException(value);
			} else {
				return constant;
			}
		}

	}

}