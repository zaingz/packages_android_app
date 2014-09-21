package model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;



@Table(name = "Categories")
public class Session extends Model { 
	 @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	    public int id;
	@Column(name = "tokken")
    public String tokken;
	public Session(){
		super();
		}
	public Session(int id,String tokken){
		this.id=id;
		this.tokken=tokken;
		
	}
}