package ie.curiositysoftware.jobengine.settings;

public class JobSettingParameter {
    private String nameField;

    private String typeField;

    private String valueField;

    public JobSettingParameter()
    {

    }

    public String getNameField() {
        return nameField;
    }

    public String getTypeField() {
        return typeField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public void setTypeField(String typeField) {
        this.typeField = typeField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }
}
