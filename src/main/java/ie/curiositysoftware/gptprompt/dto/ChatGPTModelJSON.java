package ie.curiositysoftware.gptprompt.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

@JsonIgnoreProperties
public class ChatGPTModelJSON {
    @JsonIgnoreProperties
    public static class ChatGPTModelJSONNode {

        private String id;

        private String label;

        private String xpath;

        private String type;

        private String description;

        private String expectedResults;

        private String action;

        private String loc;

        private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getExpectedResults() {
            return expectedResults;
        }

        public void setExpectedResults(String expectedResults) {
            this.expectedResults = expectedResults;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getXpath() {
            return xpath;
        }

        public void setXpath(String xpath) {
            this.xpath = xpath;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }

    @JsonIgnoreProperties
    public static class ChatGPTModelJSONEdge {
        private String from;

        private String to;

        private String label;

        private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }

    @JsonIgnoreProperties
    public static class ChatGPTModelJSONEFlowchart {
        private List<ChatGPTModelJSONNode> nodes;

        private List<ChatGPTModelJSONEdge> edges;

        public List<ChatGPTModelJSONNode> getNodes() {
            return nodes;
        }

        public void setNodes(List<ChatGPTModelJSONNode> nodes) {
            this.nodes = nodes;
        }

        public List<ChatGPTModelJSONEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<ChatGPTModelJSONEdge> edges) {
            this.edges = edges;
        }
    }

    private String modelName;

    private String modelDescription;

    private String url;

    private ChatGPTModelJSONEFlowchart flowchart;

    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ChatGPTModelJSONEFlowchart getFlowchart() {
        return flowchart;
    }

    public void setFlowchart(ChatGPTModelJSONEFlowchart flowchart) {
        this.flowchart = flowchart;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        if (modelName == null || modelName.isEmpty()) {
            return "Generated Model";
        }

        return modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
