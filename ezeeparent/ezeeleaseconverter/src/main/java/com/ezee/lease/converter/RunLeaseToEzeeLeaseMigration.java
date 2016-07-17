package com.ezee.lease.converter;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;
import com.hg.leases.dao.LeaseDao;
import com.hg.leases.model.Lease;

public class RunLeaseToEzeeLeaseMigration {

	private LeaseDao leaseDao;

	private EzeeLeaseDao ezeeLeaseDao;

	private final LeaseToEzeeLeaseConverter converter = new LeaseToEzeeLeaseConverter();

	public RunLeaseToEzeeLeaseMigration(final LeaseDao leaseDao, final EzeeLeaseDao ezeeLeaseDao) {
		this.leaseDao = leaseDao;
		this.ezeeLeaseDao = ezeeLeaseDao;
	}

	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"ezeelease-migration-context.xml")) {
			RunLeaseToEzeeLeaseMigration migration = new RunLeaseToEzeeLeaseMigration(ctx.getBean(LeaseDao.class),
					ctx.getBean(EzeeLeaseDao.class));
			migration.migrate();
		}
	}

	public void migrate() {
		Set<Lease> leases = leaseDao.get();
		if (!isEmpty(leases)) {
			for (Lease lease : leases) {
				EzeeLease ezeeLease = converter.convert(lease);
				ezeeLeaseDao.save(ezeeLease);
				converter.getCategoriesCache().put(lease.getCategory().getCategory(), ezeeLease.getCategory());
				converter.getPremisesCache().put(lease.getPremises().getAddressLineOne(), ezeeLease.getPremises());
				converter.getTenantCache().put(lease.getTenant().getName(), ezeeLease.getTenant());
			}
		}
	}
}