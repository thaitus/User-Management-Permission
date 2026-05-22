package UserManagementPermission.model;

public class PermissionDTO {
    private int screenID;
    private String screenName;
    private boolean canView;
    private boolean canEdit;

    public PermissionDTO(int screenID, String screenName, boolean canView, boolean canEdit) {
        this.screenID = screenID;
        this.screenName = screenName;
        this.canView = canView;
        this.canEdit = canEdit;
    }

    public int getScreenID() { return screenID; }
    public void setScreenID(int screenID) { this.screenID = screenID; }

    public String getScreenName() { return screenName; }
    public void setScreenName(String screenName) { this.screenName = screenName; }

    public boolean isCanView() { return canView; }
    public void setCanView(boolean canView) { this.canView = canView; }

    public boolean isCanEdit() { return canEdit; }
    public void setCanEdit(boolean canEdit) { this.canEdit = canEdit; }
}