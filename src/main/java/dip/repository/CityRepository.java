package dip.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import dip.model.City;

/**
 * <p>Description: </p>
 * @author JiaSonglin
 * @version V1.0,2017年2月17日 下午1:10:39
 */
public interface CityRepository extends Repository<City, Integer>{

	List<City> findAll();
}
