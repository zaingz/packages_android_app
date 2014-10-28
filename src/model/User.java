package model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
@Table(name = "UserInfo")
public class User extends Model {
	@SerializedName("id")
	@Column(name = "_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
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
	public static User getRandom() {
	    return new Select().from(User.class).orderBy("RANDOM()").executeSingle();
	}
	
	public User(){
		super();
		
	}
	 
	


}
