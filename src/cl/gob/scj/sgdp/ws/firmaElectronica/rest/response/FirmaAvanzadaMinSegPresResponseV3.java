package cl.gob.scj.sgdp.ws.firmaElectronica.rest.response;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Recepcion de documentos firmados
*
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class FirmaAvanzadaMinSegPresResponseV3 {
	
	@JsonProperty("files")
	public List<FileV3> files;
	@JsonProperty("metadata")
    public MetadataV3 metadata;
	@JsonProperty("idSolicitud")
    public long idSolicitud;
	
	public List<FileV3> getFiles() {
		return files;
	}
	public void setFiles(List<FileV3> files) {
		this.files = files;
	}
	public MetadataV3 getMetadata() {
		return metadata;
	}
	public void setMetadata(MetadataV3 metadata) {
		this.metadata = metadata;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
}