package infview.workspace;

public class Relation {
		
		private Entity fromEntity;
		private Entity toEntity;
		private Attribute fromAttribute;
		private Attribute toAttribute;
		private String type; //isto moze enum da ne bude string
		private String name;
		
		public Relation(Entity fromEntity, Entity toEntity, Attribute fromAttribute, Attribute toAttribute,
				String type, String name) {
			super();
			this.fromEntity = fromEntity;
			this.toEntity = toEntity;
			this.fromAttribute = fromAttribute;
			this.toAttribute = toAttribute;
			this.type = type;
			this.name = name;
		}

		public Entity getFromEntity() {
			return fromEntity;
		}

		public void setFromEntity(Entity fromEntity) {
			this.fromEntity = fromEntity;
		}

		public Entity getToEntity() {
			return toEntity;
		}

		public void setToEntity(Entity toEntity) {
			this.toEntity = toEntity;
		}

		public Attribute getFromAttribute() {
			return fromAttribute;
		}

		public void setFromAttribute(Attribute fromAttribute) {
			this.fromAttribute = fromAttribute;
		}

		public Attribute getToAttribute() {
			return toAttribute;
		}

		public void setToAttribute(Attribute toAttribute) {
			this.toAttribute = toAttribute;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
		
}
