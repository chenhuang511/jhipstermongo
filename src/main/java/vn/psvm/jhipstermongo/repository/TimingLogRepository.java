package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.TimingLog;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TimingLog entity.
 */
@SuppressWarnings("unused")
public interface TimingLogRepository extends MongoRepository<TimingLog,String> {

}
