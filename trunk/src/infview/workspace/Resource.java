package infview.workspace;

import java.util.ArrayList;

public class Resource extends WorkNode{
	
		private String name;
		private String description;
		private ArrayList<Relation> relacije;
		
		public Resource(String name, String description) {
			// TODO Auto-generated method stub
			this.name = name;
			this.description = description;
			relacije = new ArrayList<>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public ArrayList<Relation> getRelacije() {
			return relacije;
		}

		public void setRelacije(ArrayList<Relation> relacije) {
			this.relacije = relacije;
		}
		
		public void addEntity(Entity entity) {
			this.getDeca().add(entity);
		}
		
		public void addRelation(Relation relation) {
			this.relacije.add(relation);
		}
		
		@Override
		public String toString() {
		// TODO Auto-generated method stub
		return name;
		}
		
		public Entity getEntity(String name) {
			for (WorkNode n : getDeca()) {
				Entity tmp = (Entity)n;
				if ((tmp.getName()).equals(name)) return tmp;
			}
			return null;
		}
		
		public boolean containsRelation(Relation rel) {
			for (Relation r: relacije) {
				if ((rel.getName()).equals(r.getName())) return true;
			}
			return false;
		}
}
