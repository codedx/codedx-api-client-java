/*
 * Code Dx API
 * Code Dx provides a variety of REST APIs, allowing external applications and scripts to interface with core functionality. This guide documents the various REST resources provided by Code Dx.  ## Authentication  Authentication is a requirement when accessing API endpoints. There are two methods by which authentication may be performed.  ### API Keys  The primary method for authentication is passing an `API-Key` header containing a valid API key with all requests.  For example&#58;  `API-Key: 550e8400-e29b-41d4-a716-44665544000`  API keys may be generated by Code Dx admins. Once they are generated, in most cases, they behave like regular users. They will need to be assigned user roles for any projects they will be used with. Although it is possible to assign the *admin* [role](UserGuide.html#UserRolesConfiguration) to an API key, the recommendation is to avoid doing so unless absolutely necessary.  See the user guide for an overview about how to create and manage [API keys](UserGuide.html#APIKeysAdministration).  ### HTTP Basic Authentication  HTTP Basic authentication may be used to authenticate with the API as a regular user. This is accomplished by including an `Authorization` header containing a typical authorization credential.  ## Error Handling  ### Bad Requests  For API calls that accept input, invalid values will trigger an HTTP 400 Bad Request status code.  ### Server Errors  For any API call, if an unexpected error occurs, an HTTP 500 Internal Server Error status code will be returned. If an error message is available, the response will include a basic message body describing the error&#58;  ` {   \"error\": \"error message\" } `  The error property will contain a string message indicating the nature of the error.  ### Errors with Third-Party Applications  Sometimes Code Dx must communicate with third-party applications like JIRA, Git, and certain enterprise tools. Some users may have in-house versions of these with self-signed certificates which may not be \"trusted\". In these cases, the API will respond with an HTTP 502 BAD GATEWAY status. If this happens, refer to [Trusting Self-Signed Certificates](InstallGuide.html#TrustingSelfSignedCertificates) in the install guide.  ### API Unavailable  In special circumstances, particularly during the installation and update phases, the API will be unavailable. When the API is unavailable, all calls will return an HTTP 503 Service Unavailable status, and no actions or side effects will occur as a result of the calls.  ## Examples  Code Dx's API uses REST over HTTP. As such, you can use any language/utility that supports making HTTP requests to interact with the API. The examples below use <a href=\"https://curl.haxx.se/\" target=\"_blank\">curl</a>, a popular command-line utility, to do so.  First, you'll need to [generate an API Key](UserGuide.html#APIKeysAdministration). Second, while you can create projects through the API, these examples will assume that you've already created one. You'll need to know its project id number, which you can find by looking at the URL for the [Findings Page](UserGuide.html#Findings), which will end in a number.  **Note:** Many API endpoints require a JSON body in the request. Most JSON will contain double-quotes (`\"`) and spaces, which have special meaning when used on the command line. In order to ensure your JSON body is interpreted as a single argument, you must <a href=\"https://en.wikipedia.org/wiki/Escape_character\" target=\"_blank\">escape</a> it properly. For example, if you wanted to `POST` the following JSON body&#58;  ``` { \"name\": \"John Doe\" } ```  You would put a backslash (`\\`) before each double-quote (`\"`), and surround the whole thing with double-quotes&#58;  ``` \"{ \\\"name\\\": \\\"John Doe\\\" }\" ```  The outermost double-quotes tell the command-line interpreter that everything within them is to be treated as a single argument (as opposed to the usual space-separated behavior). The backslash before each inner double-quote tells the command-line interpreter that you mean the literal double-quote character, and not the end of the quoted argument.  In many *non-Windows* operating systems, you can also use a single-quote (`'`) to surround the argument, and skip the backslashes&#58;  ``` '{ \"name\": \"John Doe\" }' ```  The examples below will use the double-quotes and backslashes style, as it works on most (if not all) operating systems.  ### Running an Analysis  To start an analysis, you can run  ``` curl -F file1=@src.zip -H \"API-Key: 942d16d4-fb3f-4653-9cb3-a9da2e28e574\" https://<yourcodedxserver>/codedx/api/projects/<project id>/analysis ```  Make sure you use your own values for the `src.zip` file, the API Key, the hostname for your Code Dx server, and the project id.  **Note:** This endpoint is not listed below due to a swagger limitation.  ### Retrieving Finding Data  There is a wide variety of data available for the findings of a project after running an analysis. Two examples are provided here.  ### Findings Table Data  You can retrieve the data that's used to populate the [findings table](UserGuide.html#FindingsTable)&#58;  ``` curl -H \"Content-Type: application/json\" -X POST -d \"{\\\"filter\\\":{},\\\"sort\\\":{\\\"by\\\":\\\"id\\\",\\\"direction\\\":\\\"ascending\\\"},\\\"pagination\\\":{\\\"page\\\":1,\\\"perPage\\\":10}}\" -H \"API-Key: 942d16d4-fb3f-4653-9cb3-a9da2e28e574\" https://<yourcodedxserver>/codedx/api/projects/<project id>/findings/table ```  Additional information about how to construct more useful filter and sort requests can be found in the documentation.  ### Finding Metadata  You can retrieve metadata for a finding, if you know the finding id. You can take the ID for a finding from the Findings Table&#58;  ``` curl -H \"API-Key: 942d16d4-fb3f-4653-9cb3-a9da2e28e574\" https://<yourcodedxserver>/codedx/api/findings/<finding id> ```  ### Generating a Report  You can use the API to generate a PDF [report](UserGuide.html#GenerateReport).  ``` curl -H \"Content-Type: application/json\" -X POST -d \"{\\\"filter\\\":{},\\\"config\\\":{\\\"summaryMode\\\":\\\"simple\\\",\\\"detailsMode\\\":\\\"simple\\\",\\\"includeResultDetails\\\":true,\\\"includeComments\\\":false}}\" -H \"API-Key: 942d16d4-fb3f-4653-9cb3-a9da2e28e574\" https://<yourcodedxserver>/codedx/api/projects/<project id>/report/pdf ```  ## Generating a Client SDK  If you require a client SDK for Code Dx, you can generate one using [Swagger Code Generator](https://github.com/swagger-api/swagger-codegen). Detailed instructions are available on the github page and our swagger spec can be found [here](swagger/swagger.json). 
 *
 * OpenAPI spec version: 3.5.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.codedx.client.model;

import java.util.Objects;
import com.codedx.client.model.Cwe;
import com.codedx.client.model.Descriptions;
import com.codedx.client.model.DetectionMethod;
import com.codedx.client.model.FindingDescriptor;
import com.codedx.client.model.Severity;
import com.codedx.client.model.ToolResultLocation;
import com.codedx.client.model.Variant;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ToolResult
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-12-07T19:30:21.793Z")
public class ToolResult {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("isManual")
  private Boolean isManual = null;

  @SerializedName("detectionMethod")
  private DetectionMethod detectionMethod = null;

  @SerializedName("tool")
  private String tool = null;

  @SerializedName("severity")
  private Severity severity = null;

  @SerializedName("contextualSeverity")
  private Severity contextualSeverity = null;

  @SerializedName("cwe")
  private Cwe cwe = null;

  @SerializedName("contextualCwe")
  private Cwe contextualCwe = null;

  @SerializedName("toolHierarchy")
  private List<String> toolHierarchy = null;

  @SerializedName("descriptor")
  private FindingDescriptor descriptor = null;

  @SerializedName("location")
  private ToolResultLocation location = null;

  @SerializedName("metadata")
  private Map<String, Object> metadata = null;

  @SerializedName("descriptions")
  private Descriptions descriptions = null;

  @SerializedName("variants")
  private List<Variant> variants = null;

  public ToolResult id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * hello
   * @return id
  **/
  @ApiModelProperty(value = "hello")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ToolResult isManual(Boolean isManual) {
    this.isManual = isManual;
    return this;
  }

   /**
   * Get isManual
   * @return isManual
  **/
  @ApiModelProperty(value = "")
  public Boolean isIsManual() {
    return isManual;
  }

  public void setIsManual(Boolean isManual) {
    this.isManual = isManual;
  }

  public ToolResult detectionMethod(DetectionMethod detectionMethod) {
    this.detectionMethod = detectionMethod;
    return this;
  }

   /**
   * Get detectionMethod
   * @return detectionMethod
  **/
  @ApiModelProperty(value = "")
  public DetectionMethod getDetectionMethod() {
    return detectionMethod;
  }

  public void setDetectionMethod(DetectionMethod detectionMethod) {
    this.detectionMethod = detectionMethod;
  }

  public ToolResult tool(String tool) {
    this.tool = tool;
    return this;
  }

   /**
   * Get tool
   * @return tool
  **/
  @ApiModelProperty(value = "")
  public String getTool() {
    return tool;
  }

  public void setTool(String tool) {
    this.tool = tool;
  }

  public ToolResult severity(Severity severity) {
    this.severity = severity;
    return this;
  }

   /**
   * Get severity
   * @return severity
  **/
  @ApiModelProperty(value = "")
  public Severity getSeverity() {
    return severity;
  }

  public void setSeverity(Severity severity) {
    this.severity = severity;
  }

  public ToolResult contextualSeverity(Severity contextualSeverity) {
    this.contextualSeverity = contextualSeverity;
    return this;
  }

   /**
   * Get contextualSeverity
   * @return contextualSeverity
  **/
  @ApiModelProperty(value = "")
  public Severity getContextualSeverity() {
    return contextualSeverity;
  }

  public void setContextualSeverity(Severity contextualSeverity) {
    this.contextualSeverity = contextualSeverity;
  }

  public ToolResult cwe(Cwe cwe) {
    this.cwe = cwe;
    return this;
  }

   /**
   * Get cwe
   * @return cwe
  **/
  @ApiModelProperty(value = "")
  public Cwe getCwe() {
    return cwe;
  }

  public void setCwe(Cwe cwe) {
    this.cwe = cwe;
  }

  public ToolResult contextualCwe(Cwe contextualCwe) {
    this.contextualCwe = contextualCwe;
    return this;
  }

   /**
   * Get contextualCwe
   * @return contextualCwe
  **/
  @ApiModelProperty(value = "")
  public Cwe getContextualCwe() {
    return contextualCwe;
  }

  public void setContextualCwe(Cwe contextualCwe) {
    this.contextualCwe = contextualCwe;
  }

  public ToolResult toolHierarchy(List<String> toolHierarchy) {
    this.toolHierarchy = toolHierarchy;
    return this;
  }

  public ToolResult addToolHierarchyItem(String toolHierarchyItem) {
    if (this.toolHierarchy == null) {
      this.toolHierarchy = new ArrayList<String>();
    }
    this.toolHierarchy.add(toolHierarchyItem);
    return this;
  }

   /**
   * Get toolHierarchy
   * @return toolHierarchy
  **/
  @ApiModelProperty(value = "")
  public List<String> getToolHierarchy() {
    return toolHierarchy;
  }

  public void setToolHierarchy(List<String> toolHierarchy) {
    this.toolHierarchy = toolHierarchy;
  }

  public ToolResult descriptor(FindingDescriptor descriptor) {
    this.descriptor = descriptor;
    return this;
  }

   /**
   * Get descriptor
   * @return descriptor
  **/
  @ApiModelProperty(value = "")
  public FindingDescriptor getDescriptor() {
    return descriptor;
  }

  public void setDescriptor(FindingDescriptor descriptor) {
    this.descriptor = descriptor;
  }

  public ToolResult location(ToolResultLocation location) {
    this.location = location;
    return this;
  }

   /**
   * Get location
   * @return location
  **/
  @ApiModelProperty(value = "")
  public ToolResultLocation getLocation() {
    return location;
  }

  public void setLocation(ToolResultLocation location) {
    this.location = location;
  }

  public ToolResult metadata(Map<String, Object> metadata) {
    this.metadata = metadata;
    return this;
  }

  public ToolResult putMetadataItem(String key, Object metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<String, Object>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

   /**
   * Get metadata
   * @return metadata
  **/
  @ApiModelProperty(value = "")
  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public ToolResult descriptions(Descriptions descriptions) {
    this.descriptions = descriptions;
    return this;
  }

   /**
   * Get descriptions
   * @return descriptions
  **/
  @ApiModelProperty(value = "")
  public Descriptions getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(Descriptions descriptions) {
    this.descriptions = descriptions;
  }

  public ToolResult variants(List<Variant> variants) {
    this.variants = variants;
    return this;
  }

  public ToolResult addVariantsItem(Variant variantsItem) {
    if (this.variants == null) {
      this.variants = new ArrayList<Variant>();
    }
    this.variants.add(variantsItem);
    return this;
  }

   /**
   * Get variants
   * @return variants
  **/
  @ApiModelProperty(value = "")
  public List<Variant> getVariants() {
    return variants;
  }

  public void setVariants(List<Variant> variants) {
    this.variants = variants;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToolResult toolResult = (ToolResult) o;
    return Objects.equals(this.id, toolResult.id) &&
        Objects.equals(this.isManual, toolResult.isManual) &&
        Objects.equals(this.detectionMethod, toolResult.detectionMethod) &&
        Objects.equals(this.tool, toolResult.tool) &&
        Objects.equals(this.severity, toolResult.severity) &&
        Objects.equals(this.contextualSeverity, toolResult.contextualSeverity) &&
        Objects.equals(this.cwe, toolResult.cwe) &&
        Objects.equals(this.contextualCwe, toolResult.contextualCwe) &&
        Objects.equals(this.toolHierarchy, toolResult.toolHierarchy) &&
        Objects.equals(this.descriptor, toolResult.descriptor) &&
        Objects.equals(this.location, toolResult.location) &&
        Objects.equals(this.metadata, toolResult.metadata) &&
        Objects.equals(this.descriptions, toolResult.descriptions) &&
        Objects.equals(this.variants, toolResult.variants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, isManual, detectionMethod, tool, severity, contextualSeverity, cwe, contextualCwe, toolHierarchy, descriptor, location, metadata, descriptions, variants);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ToolResult {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    isManual: ").append(toIndentedString(isManual)).append("\n");
    sb.append("    detectionMethod: ").append(toIndentedString(detectionMethod)).append("\n");
    sb.append("    tool: ").append(toIndentedString(tool)).append("\n");
    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
    sb.append("    contextualSeverity: ").append(toIndentedString(contextualSeverity)).append("\n");
    sb.append("    cwe: ").append(toIndentedString(cwe)).append("\n");
    sb.append("    contextualCwe: ").append(toIndentedString(contextualCwe)).append("\n");
    sb.append("    toolHierarchy: ").append(toIndentedString(toolHierarchy)).append("\n");
    sb.append("    descriptor: ").append(toIndentedString(descriptor)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    descriptions: ").append(toIndentedString(descriptions)).append("\n");
    sb.append("    variants: ").append(toIndentedString(variants)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

