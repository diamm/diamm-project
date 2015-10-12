package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.diamm.model.DiammUser;
import uk.ac.kcl.cch.diamm.model.NoteVisibility.NoteVisibilityEnum;
import uk.ac.kcl.cch.diamm.model.Notetype.NoteTypeEnum;
import uk.ac.kcl.cch.util.StringUtil;

public class NoteDTO {
    private String op;
	private Integer noteKey;
    private String noteText;
    private NoteTypeEnum noteType;
    private NoteVisibilityEnum noteVisibility;
    private DiammUser user;
    private Integer imageKey;
    private Integer sourceKey;
    
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Integer getNoteKey() {
		return noteKey;
	}
	
	public void setNoteKey(Integer noteKey) {
		this.noteKey = noteKey;
	}
	
	public String getNoteText() {
		return noteText;
	}
	public void setNoteText(String noteText) {
		this.noteText = StringUtil.squeeze(noteText);
	}
	
	public NoteTypeEnum getNoteType() {
		return noteType;
	}
	public void setNoteType(NoteTypeEnum noteType) {
		this.noteType = noteType;
	}
	
	public NoteVisibilityEnum getNoteVisibility() {
		return noteVisibility;
	}
	
	public void setNoteVisibility(NoteVisibilityEnum noteVisibility) {
		this.noteVisibility = noteVisibility;
	}
	
	public DiammUser getUser() {
		return user;
	}
	
	public void setUser(DiammUser user) {
		this.user = user;
	}

	public Integer getImageKey() {
		return imageKey;
	}

	public void setImageKey(Integer imageKey) {
		this.imageKey = imageKey;
	}

	public Integer getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(Integer sourceKey) {
		this.sourceKey = sourceKey;
	}
}
