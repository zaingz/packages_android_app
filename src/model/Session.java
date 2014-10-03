package model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;



@Table(name = "Session")
public class Session extends Model { 
	 @Column(name = "_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	    public int _id;
	@Column(name = "tokken")
    public String tokken;
	public Session(){
		super();
		}
	public Session(int _id,String tokken){
		this._id=_id;
		this.tokken=tokken;
		
	}
	
}