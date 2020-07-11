package infview.actions;

public class ActionManager {
	private OpenResourceAction openResourceAction;
	private OtvoriMSEAction mseAction;
	private ModifyAttributeAction modifyAttributeAction;
	private ModifyEntityAction modifyEntityAction;
	private ModifyRelationAction modifyRelationAction;
	private SaveMSEAction saveMSEAction;
	private RestoreAction readSerilaAction;
	private OpenDBSchemaAction openDBSchemaAction;
	
	public OpenDBSchemaAction getOpenDBSchemaAction() {
		return openDBSchemaAction;
	}

	public void setOpenDBSchemaAction(OpenDBSchemaAction openDBSchemaAction) {
		this.openDBSchemaAction = openDBSchemaAction;
	}

	public ActionManager() {
		// TODO Auto-generated constructor stub
		openResourceAction = new OpenResourceAction();
		mseAction = new OtvoriMSEAction();
		
		openDBSchemaAction = new OpenDBSchemaAction();
		
		modifyAttributeAction = new ModifyAttributeAction();
		modifyEntityAction = new ModifyEntityAction();
		modifyRelationAction = new ModifyRelationAction();
		
		saveMSEAction = new SaveMSEAction();
		
		readSerilaAction = new RestoreAction();
	}

	public RestoreAction getReadSerilaAction() {
		return readSerilaAction;
	}
	
	public void setReadSerilaAction(RestoreAction readSerilaAction) {
		this.readSerilaAction = readSerilaAction;
	}
	
	public ModifyAttributeAction getModifyAttributeAction() {
		return modifyAttributeAction;
	}

	public void setModifyAttributeAction(ModifyAttributeAction modifyAttributeAction) {
		this.modifyAttributeAction = modifyAttributeAction;
	}

	public ModifyEntityAction getModifyEntityAction() {
		return modifyEntityAction;
	}

	public void setModifyEntityAction(ModifyEntityAction modifyEntityAction) {
		this.modifyEntityAction = modifyEntityAction;
	}

	public ModifyRelationAction getModifyRelationAction() {
		return modifyRelationAction;
	}

	public void setModifyRelationAction(ModifyRelationAction modifyRelationAction) {
		this.modifyRelationAction = modifyRelationAction;
	}

	public OpenResourceAction getOpenResourceAction() {
		return openResourceAction;
	}	

	public void setOpenResourceAction(OpenResourceAction openRescourceAction) {
		this.openResourceAction = openRescourceAction;
	}
	
	public OtvoriMSEAction getMseAction() {
		return mseAction;
	}
	
	public void setMseAction(OtvoriMSEAction mseAction) {
		this.mseAction = mseAction;
	}

	public SaveMSEAction getSaveMSEAction() {
		return saveMSEAction;
	}

	public void setSaveMSEAction(SaveMSEAction saveMSEAction) {
		this.saveMSEAction = saveMSEAction;
	}
	
	
	
}
