package dip.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import dip.model.City;
import dip.services.CityService;

/**
 * <p>Description: </p>
 * @author JiaSonglin
 * @version V1.0,2017年2月9日 下午2:31:37
 */
@Service("cityService")
public class CityServiceImpl implements CityService{

	private static final String WAITE_TRANSFER = "select * from city where state='1' limit 4";
	
	private static final String DEAIL_SUCCESS = "UPDATE city SET state='2' where id = ?";
	
	private static final String WAITE_NO_TRANSFER_WITH_MOD = "SELECT * FROM city WHERE state='1' and id%? = ? ";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<City> findCitys(int modSize,int modItem) {
		List<City> citys = jdbcTemplate.query(WAITE_NO_TRANSFER_WITH_MOD, new Object[] {modSize,modItem},new RowMapper<City>() {

			@Override
			public City mapRow(ResultSet rs, int rowNum) throws SQLException {
				City city = new City();
				city.setId(rs.getInt("id"));
				city.setName(rs.getString("name"));
				city.setCountry(rs.getString("country"));
				city.setMap(rs.getString("map"));
				city.setState(rs.getString("state"));
				return city;
			}
		});
		return citys;
	}
	
	@Override
	public List<City> findWaitDealCitys() {
		List<City> citys = jdbcTemplate.query(WAITE_TRANSFER,new RowMapper<City>() {
			
			@Override
			public City mapRow(ResultSet rs, int rowNum) throws SQLException {
				City city = new City();
				city.setId(rs.getInt("id"));
				city.setName(rs.getString("name"));
				city.setCountry(rs.getString("country"));
				city.setMap(rs.getString("map"));
				city.setState(rs.getString("state"));
				return city;
			}
		});
		return citys;
	}
	
	public static void main(String[] args) {
		String str = "75";
		System.out.println(Strings.commonPrefix(str, "725fds"));
	}

	@Override
	public void dealSuccess(String cityId) {
		jdbcTemplate.update(DEAIL_SUCCESS, new Object[]{cityId});
	}

}
