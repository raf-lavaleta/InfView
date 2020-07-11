package infview.workspace;

public class Attribute {
	
	private String name;
	private String type;
	private int length;
	private boolean primaryKey;
	private boolean sort = false;
	private boolean asc = false;

	public Attribute(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	
	
	public Attribute(String name, String type, int length, boolean primaryKey) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
		this.primaryKey = primaryKey;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public String toString() {
		return name;
	}



	public boolean isSort() {
		return sort;
	}



	public void setSort(boolean sort) {
		this.sort = sort;
	}



	public boolean isAsc() {
		return asc;
	}



	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	
	
}
