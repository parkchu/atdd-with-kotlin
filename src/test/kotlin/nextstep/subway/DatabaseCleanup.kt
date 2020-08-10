package nextstep.subway

import com.google.common.base.CaseFormat
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.metamodel.EntityType

@Component
@ActiveProfiles("test")
class DatabaseCleanup: InitializingBean {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    var tableNames: List<String>? = null

    override fun afterPropertiesSet() {
        tableNames = entityManager!!.metamodel.entities
                .filter { e: EntityType<*> -> e.javaType.getAnnotation(Entity::class.java) != null }
                .map { e: EntityType<*> -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.name) }
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        for (tableName in tableNames!!) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $tableName ALTER COLUMN ID RESTART WITH 1").executeUpdate()
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
