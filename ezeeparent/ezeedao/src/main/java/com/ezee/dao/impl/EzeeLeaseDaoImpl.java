package com.ezee.dao.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.transaction.annotation.Transactional;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.filter.lease.EzeeLeaseFilter;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseFile;

public class EzeeLeaseDaoImpl extends EzeeBaseDaoImpl<EzeeLease> implements EzeeLeaseDao {

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void delete(final EzeeLease entity) {
		deleteMappings(entity, "deleteCategoryMappingsSql");
		deleteMappings(entity, "deleteIncidentalMappingsSql");
		deleteMappings(entity, "deleteTenantMappingsSql");
		deleteMappings(entity, "deletePremisesMappingsSql");
		deleteMappings(entity, "deleteBondMappingsSql");
		deleteMappings(entity, "deleteMetaDataMappingsSql");
		deleteMappings(entity, "deleteNotesSql");
		deleteMappings(entity, "deleteFilesSql");
		entity.setCategory(null);
		entity.setTenant(null);
		entity.setPremises(null);
		super.save(entity);
		super.delete(entity);
	}

	@Override
	public EzeeLease get(long id) {
		return get(id, EzeeLease.class);
	}

	@Override
	public List<EzeeLease> get() {
		return get(EzeeLease.class);
	}

	@Override
	public List<EzeeLease> get(final EzeeLeaseFilter filter) {
		return get(filter, EzeeLease.class);
	}

	@Override
	public void save(final EzeeLease entity) {
		if (doPreProcessFiles(entity)) {
			preprocess(entity);
			merge(entity);
		} else {
			super.save(entity);
		}
	}

	/**
	 * Repopulate 'nulled' out binary files and account for files deleted on the
	 * client side.
	 */
	private void preprocess(final EzeeLease entity) {
		SortedSet<EzeeLeaseFile> persistedFiles = get(entity.getId()).getFiles();
		SortedSet<EzeeLeaseFile> entityFiles = entity.getFiles();
		SortedSet<EzeeLeaseFile> remainingFiles = new TreeSet<EzeeLeaseFile>();
		if (!isEmpty(persistedFiles)) {
			for (EzeeLeaseFile file : persistedFiles) {
				if (entityFiles.contains(file)) {
					remainingFiles.add(file);
				}
			}
			entity.setFiles(remainingFiles);
		}
	}

	/**
	 * Client versions do not download files and so the file contents are empty.
	 */
	private boolean doPreProcessFiles(final EzeeLease entity) {
		if (!isEmpty(entity.getFiles())) {
			return entity.getFiles().first().getFile() == null;
		}
		return false;
	}
}
