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
import com.codedx.client.model.Filter;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * GroupedCountsRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-12-07T19:30:21.793Z")
public class GroupedCountsRequest {
  @SerializedName("filter")
  private Filter filter = null;

  @SerializedName("countBy")
  private String countBy = null;

  public GroupedCountsRequest filter(Filter filter) {
    this.filter = filter;
    return this;
  }

   /**
   * Get filter
   * @return filter
  **/
  @ApiModelProperty(value = "")
  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public GroupedCountsRequest countBy(String countBy) {
    this.countBy = countBy;
    return this;
  }

   /**
   * &#x60;countBy&#x60; may be one of the following&amp;#58; - path - file - descriptor - resultDescriptor - detectionMethod - severity - status - toolOverlap - traceSession - issueTrackerAssociationStatus - sourceMethodAndHybridness&amp;#58;finding-id (replace &#39;finding-id&#39; with an actual finding id) - standard&amp;#58;(cwe | standard-id) (The \&quot;Get Standards\&quot; endpoint will return usable countBy values for standard-id) - age&amp;#58;[age bucket specifiers]  The general format of an *age bucket specifier* is &#x60;&lt;bucket1&gt;&#x3D;&lt;title1&gt;;...;&lt;bucketN&gt;&#x3D;&lt;titleN&gt;&#x60; for N buckets.  Each \&quot;bucket\&quot; is a concatenation of age specifiers&amp;#58; - &#x60;today&#x60; includes findings that were first seen today - &#x60;&lt;number&gt;d&#x60; includes findings that were seen at most *number* days ago, e.g. &#x60;7d&#x60; means findings that were seen at *most* 7 days ago. &#x60;h&#x60;, &#x60;m&#x60;, or &#x60;s&#x60; can be used instead of &#x60;d&#x60; to indicate hours, minutes, or seconds, respectively. - &#x60;-&lt;specifier&gt;&#x60; negates a specifier, e.g. &#x60;-7d&#x60; means findings that were seen at *least* 7 days ago - &#x60;&lt;sepcifier1&gt;&lt;specifier2&gt;&#x60; creates an and relationship between the two (or more) specifiers, e.g. &#x60;7d-3d&#x60; mean \&quot;at most 7 days old, *and* at least 3 days old\&quot;.  The countBy used in Code Dx&#39;s UI to populate the Age Filter is&amp;#58; &#x60;&#x60;&#x60; age:today&#x3D;First seen today;7d-today&#x3D;Less than 7 days old;30d-7d&#x3D;7 - 30 days old;90d-30d&#x3D;30 - 90 days old;180d-90d&#x3D;90 - 180 days old;-180d&#x3D;More than 180 days old &#x60;&#x60;&#x60; Note that because each bucket/title specification is joined with a semicolon (;), semicolons in a title must be prefixed with a backslash (\\) to distinguish between textual semicolons and formatting semicolons. E.g. age:today&#x3D;hello\\; world. 
   * @return countBy
  **/
  @ApiModelProperty(value = "`countBy` may be one of the following&#58; - path - file - descriptor - resultDescriptor - detectionMethod - severity - status - toolOverlap - traceSession - issueTrackerAssociationStatus - sourceMethodAndHybridness&#58;finding-id (replace 'finding-id' with an actual finding id) - standard&#58;(cwe | standard-id) (The \"Get Standards\" endpoint will return usable countBy values for standard-id) - age&#58;[age bucket specifiers]  The general format of an *age bucket specifier* is `<bucket1>=<title1>;...;<bucketN>=<titleN>` for N buckets.  Each \"bucket\" is a concatenation of age specifiers&#58; - `today` includes findings that were first seen today - `<number>d` includes findings that were seen at most *number* days ago, e.g. `7d` means findings that were seen at *most* 7 days ago. `h`, `m`, or `s` can be used instead of `d` to indicate hours, minutes, or seconds, respectively. - `-<specifier>` negates a specifier, e.g. `-7d` means findings that were seen at *least* 7 days ago - `<sepcifier1><specifier2>` creates an and relationship between the two (or more) specifiers, e.g. `7d-3d` mean \"at most 7 days old, *and* at least 3 days old\".  The countBy used in Code Dx's UI to populate the Age Filter is&#58; ``` age:today=First seen today;7d-today=Less than 7 days old;30d-7d=7 - 30 days old;90d-30d=30 - 90 days old;180d-90d=90 - 180 days old;-180d=More than 180 days old ``` Note that because each bucket/title specification is joined with a semicolon (;), semicolons in a title must be prefixed with a backslash (\\) to distinguish between textual semicolons and formatting semicolons. E.g. age:today=hello\\; world. ")
  public String getCountBy() {
    return countBy;
  }

  public void setCountBy(String countBy) {
    this.countBy = countBy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupedCountsRequest groupedCountsRequest = (GroupedCountsRequest) o;
    return Objects.equals(this.filter, groupedCountsRequest.filter) &&
        Objects.equals(this.countBy, groupedCountsRequest.countBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filter, countBy);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupedCountsRequest {\n");
    
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
    sb.append("    countBy: ").append(toIndentedString(countBy)).append("\n");
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

