package demo;

import javax.persistence.OrderBy;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface DummyRepository extends CrudRepository< DummyEntity, Long >
{
    @Override
    @OrderBy ( "dummy_name ASC" )
    @Cacheable("dummy_table")
    Iterable< DummyEntity > findAll ( );
}
