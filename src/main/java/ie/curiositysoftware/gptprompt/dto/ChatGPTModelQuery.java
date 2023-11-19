package ie.curiositysoftware.gptprompt.dto;


import java.util.List;

public class ChatGPTModelQuery {
    private String query;

    private String html;

    private String elementDescription;

    private Long promptId;

    private List<ChatGPTImageFile> images;

    private ChatGPTModelJSON model;

    private Long connectorId;

    public ChatGPTModelQuery() {

    }

    public String getQuery() {
        return query;
    }

    public void setModel(ChatGPTModelJSON model) {
        this.model = model;
    }

    public ChatGPTModelJSON getModel() {
        return model;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Long getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(Long connectorId) {
        this.connectorId = connectorId;
    }

    public Long getPromptId() {
        return promptId;
    }

    public void setPromptId(Long promptId) {
        this.promptId = promptId;
    }

    public List<ChatGPTImageFile> getImages() {
        return images;
    }

    public void setImages(List<ChatGPTImageFile> images) {
        this.images = images;
    }

    public String getElementDescription() {
        return elementDescription;
    }

    public String getHtml() {
        return html;
    }

    public void setElementDescription(String elementDescription) {
        this.elementDescription = elementDescription;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
