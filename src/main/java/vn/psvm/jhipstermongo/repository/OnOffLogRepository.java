package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.OnOffLog;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the OnOffLog entity.
 */
@SuppressWarnings("unused")
public interface OnOffLogRepository extends MongoRepository<OnOffLog,String> {

}
