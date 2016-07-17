package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDateSortableDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_FILE")
public class EzeeLeaseFile extends EzeeDateSortableDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 142235048568107528L;

	@Column(name = "FILENAME")
	private String filename;

	@Lob
	@Column(name = "FILE")
	private byte[] file;

	public EzeeLeaseFile() {
		super();
	}

	public EzeeLeaseFile(final int order, final String filedate, final String filename) {
		this(NULL_ID, order, filedate, filename);
	}

	public EzeeLeaseFile(final Long id, final int order, final String filedate, final String filename) {
		super(id, order, filedate);
		this.filename = filename;
	}

	public final byte[] getFile() {
		return file;
	}

	public void setFile(final byte[] file) {
		this.file = file;
	}

	public final String getFilename() {
		return filename;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EzeeLeaseFile other = (EzeeLeaseFile) obj;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		return true;
	}
}