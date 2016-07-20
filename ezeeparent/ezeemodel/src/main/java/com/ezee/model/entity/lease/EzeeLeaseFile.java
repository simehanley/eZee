package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ezee.model.entity.EzeeDateSortableDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_FILE")
public class EzeeLeaseFile extends EzeeDateSortableDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 142235048568107528L;

	private static final int ID_INDEX = 0;
	private static final int ORDER_INDEX = 1;
	private static final int DATE_INDEX = 2;
	private static final int FILENAME_INDEX = 3;

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

	@Transient
	public final String getFileDescrptor() {
		return getId() + "," + order + "," + date + "," + filename;
	}

	@Transient
	public static final EzeeLeaseFile getFile(final String descriptor) {
		String[] fields = descriptor.split(",");
		return new EzeeLeaseFile(Long.valueOf(fields[ID_INDEX]), Integer.valueOf(fields[ORDER_INDEX]),
				fields[DATE_INDEX], fields[FILENAME_INDEX]);
	}
}