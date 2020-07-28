package com.rabbitmq.dmo.demorabbitmq;

import java.sql.ResultSet;

import javax.sql.DataSource;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.DeleteRoute;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Param;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PathParam;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.annotation.PutRoute;
import com.rabbitmq.dmo.demorabbitmq.config.PropertyFileReaderConfig;
import com.rabbitmq.dmo.demorabbitmq.model.Employee;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Path
public class EmployeeController {

	@Inject
	PropertyFileReaderConfig config;

	@Inject
	RabbitMQSender rabbitMQSender;

	private JdbcTemplate jdbcTemplate;


	@GetRoute("/producer/:empName/:empId")
	@JSON
	public String producer(@PathParam String empName, @PathParam String empId) {

		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setName(empName);
		String query = "select";
		rabbitMQSender.send(emp, query);
		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}

	@PostRoute("/addUser")
	@JSON
	public String addUser(@Param Employee employee) {
		
		String url = "Insert into Employee(name, empId) values(?,?)";
		int addedUser = 0;
		setJdbcTemplate();
		try{
          addedUser = getJdbcTemplate().update(url, new Object[]{employee.getName(), employee.getEmpId()});
		}

		catch(DataAccessException e){
           e.printStackTrace();
		}

		String queryType = "insert";

		if(addedUser > 0){
			rabbitMQSender.send(employee, queryType);
		}
		return "Employee created successfully";
	}

	@PutRoute("/updateUser/:empId")
	@JSON
	public String updateUser(@PathParam String empId, @Param String name) {
		Employee employee = new Employee();
		
		String url = "Update employee set name = ? where empId = ?";
		int updateUser = 0;
		setJdbcTemplate();
		try{
			updateUser = getJdbcTemplate().update(url, new Object[]{name, empId});
		}

		catch(DataAccessException e){
           e.printStackTrace();
		}

		String queryType = "update";

		if(updateUser > 0){
			employee.setEmpId(empId);employee.setName(name);
			rabbitMQSender.send(employee, queryType);
		}
		return "Employee updated successfully";
	}

	// @DeleteRoute("/deleteUser/:empId")
	// @JSON
	// public String deleteUser(@PathParam String empId) {
	// 	Employee employee = new Employee();
		
	// 	String url = "Update employee set name = ? where empId = ?";
	// 	String selectUrl = "Select name, empId from employee where empId = ?";
	// 	int updateUser = 0;
	// 	setJdbcTemplate();
	// 	try{

	// 		getJdbcTemplate().query(selectUrl, new Object[]{empId}, new RowMapper(){
	// 			Employee employee = new Employee();
				
				
	// 			employee.setEmpId()
				
	// 		});

	// 		updateUser = getJdbcTemplate().update(url, new Object[]{empId});
	// 	}

	// 	catch(DataAccessException e){
    //        e.printStackTrace();
	// 	}

	// 	String queryType = "update";

	// 	if(updateUser > 0){
	// 		employee.setEmpId(empId);employee.setName(name);
	// 		rabbitMQSender.send(employee, queryType);
	// 	}
	// 	return "Employee updated successfully";
	// }
	

    /**
     * @return DriverManagerDataSource return the dmSource
     */
    

    /**
     * @param dmSource the dmSource to set
     */
    

    /**
     * @return JdbcTemplate return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate() {
        this.jdbcTemplate = new JdbcTemplate(config.getDmSource());
    }


    /**
     * @return DataSource return the dataSource
     */
    

}