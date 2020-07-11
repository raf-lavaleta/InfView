package infview.workspace;

import java.util.ArrayList;

public class Entity extends WorkNode{
		private String name;
		private ArrayList<Attribute> attributes;
		private ArrayList<Relation> relations;
		
		
		
		
		public Entity() {
			super();
		}

		public Entity(String name) {
			// TODO Auto-generated constructor stub
			this.name = name;
			attributes = new ArrayList<>();
			relations = new ArrayList<>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ArrayList<Attribute> getAttributes() {
			return attributes;
		}

		public void setAttributes(ArrayList<Attribute> attributes) {
			this.attributes = attributes;
		}

		public ArrayList<Relation> getRelations() {
			return relations;
		}

		public void setRelations(ArrayList<Relation> relations) {
			this.relations = relations;
		}
		
		public void addRelation(Relation r) {
			relations.add(r);
		}
		
		public void addAttribute(Attribute a) {
			attributes.add(a);
		}
		
		public String toString() {
			return name;
		}
		
		public boolean isLeaf() {
			return true;
		}
		
		@Override
		public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
		}
		
		public Attribute getAttribute(String name) {
			for (Attribute a : getAttributes()) {
				if ((a.getName()).equals(name)) return a;
			}
			return null;
		}
}
