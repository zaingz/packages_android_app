package model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;
@Table(name = "UserInfo")
public class User extends Model {
	@SerializedName("id")
	@Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public  int id;
	@Column(name = "username")
	public String username;
	@Column(name = "email")
	public String email;
	@Column(name = "updated_at")
	public String updated_at;
	@Column(name = "lat")
	public String lat;
	@Column(name = "lon")
	public String lon;
	
	public User(){
		super();
		
	}
	 
	


}
