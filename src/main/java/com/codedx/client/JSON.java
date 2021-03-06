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


package com.codedx.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonElement;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.codedx.client.model.*;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class JSON {
    private Gson gson;
    private boolean isLenientOnJson = false;
    private DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private SqlDateTypeAdapter sqlDateTypeAdapter = new SqlDateTypeAdapter();
    private OffsetDateTimeTypeAdapter offsetDateTimeTypeAdapter = new OffsetDateTimeTypeAdapter();
    private LocalDateTypeAdapter localDateTypeAdapter = new LocalDateTypeAdapter();

    public static GsonBuilder createGson() {
        GsonFireBuilder fireBuilder = new GsonFireBuilder()
        ;
        return fireBuilder.createGsonBuilder();
    }

    private static String getDiscriminatorValue(JsonElement readElement, String discriminatorField) {
        JsonElement element = readElement.getAsJsonObject().get(discriminatorField);
        if(null == element) {
            throw new IllegalArgumentException("missing discriminator field: <" + discriminatorField + ">");
        }
        return element.getAsString();
    }

    private static Class getClassByDiscriminator(Map classByDiscriminatorValue, String discriminatorValue) {
        Class clazz = (Class) classByDiscriminatorValue.get(discriminatorValue.toUpperCase());
        if(null == clazz) {
            throw new IllegalArgumentException("cannot determine model class of name: <" + discriminatorValue + ">");
        }
        return clazz;
    }

    public JSON() {
        gson = createGson()
            .registerTypeAdapter(Date.class, dateTypeAdapter)
            .registerTypeAdapter(java.sql.Date.class, sqlDateTypeAdapter)
            .registerTypeAdapter(OffsetDateTime.class, offsetDateTimeTypeAdapter)
            .registerTypeAdapter(LocalDate.class, localDateTypeAdapter)
            .create();
    }

    /**
     * Get Gson.
     *
     * @return Gson
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * Set Gson.
     *
     * @param gson Gson
     * @return JSON
     */
    public JSON setGson(Gson gson) {
        this.gson = gson;
        return this;
    }

    public JSON setLenientOnJson(boolean lenientOnJson) {
        isLenientOnJson = lenientOnJson;
        return this;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @SuppressWarnings("unchecked")
    public <T> T deserialize(String body, Type returnType) {
        try {
            if (isLenientOnJson) {
                JsonReader jsonReader = new JsonReader(new StringReader(body));
                // see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonReader.html#setLenient(boolean)
                jsonReader.setLenient(true);
                return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(body, returnType);
            }
        } catch (JsonParseException e) {
            // Fallback processing when failed to parse JSON form response body:
            // return the response body string directly for the String return type;
            if (returnType.equals(String.class))
                return (T) body;
            else throw (e);
        }
    }

    /**
     * Gson TypeAdapter for JSR310 OffsetDateTime type
     */
    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    if (date.endsWith("+0000")) {
                        date = date.substring(0, date.length()-5) + "Z";
                    }
                    return OffsetDateTime.parse(date, formatter);
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 LocalDate type
     */
    public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        private DateTimeFormatter formatter;

        public LocalDateTypeAdapter() {
            this(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        public LocalDateTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    return LocalDate.parse(date, formatter);
            }
        }
    }

    public JSON setOffsetDateTimeFormat(DateTimeFormatter dateFormat) {
        offsetDateTimeTypeAdapter.setFormat(dateFormat);
        return this;
    }

    public JSON setLocalDateFormat(DateTimeFormatter dateFormat) {
        localDateTypeAdapter.setFormat(dateFormat);
        return this;
    }

    /**
     * Gson TypeAdapter for java.sql.Date type
     * If the dateFormat is null, a simple "yyyy-MM-dd" format will be used
     * (more efficient than SimpleDateFormat).
     */
    public static class SqlDateTypeAdapter extends TypeAdapter<java.sql.Date> {

        private DateFormat dateFormat;

        public SqlDateTypeAdapter() {
        }

        public SqlDateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, java.sql.Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = date.toString();
                }
                out.value(value);
            }
        }

        @Override
        public java.sql.Date read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    try {
                        if (dateFormat != null) {
                            return new java.sql.Date(dateFormat.parse(date).getTime());
                        }
                        return new java.sql.Date(ISO8601Utils.parse(date, new ParsePosition(0)).getTime());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }
            }
        }
    }

    /**
     * Gson TypeAdapter for java.util.Date type
     * If the dateFormat is null, ISO8601Utils will be used.
     */
    public static class DateTypeAdapter extends TypeAdapter<Date> {

        private DateFormat dateFormat;

        public DateTypeAdapter() {
        }

        public DateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = ISO8601Utils.format(date, true);
                }
                out.value(value);
            }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            try {
                switch (in.peek()) {
                    case NULL:
                        in.nextNull();
                        return null;
                    default:
                        String date = in.nextString();
                        try {
                            if (dateFormat != null) {
                                return dateFormat.parse(date);
                            }
                            return ISO8601Utils.parse(date, new ParsePosition(0));
                        } catch (ParseException e) {
                            throw new JsonParseException(e);
                        }
                }
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public JSON setDateFormat(DateFormat dateFormat) {
        dateTypeAdapter.setFormat(dateFormat);
        return this;
    }

    public JSON setSqlDateFormat(DateFormat dateFormat) {
        sqlDateTypeAdapter.setFormat(dateFormat);
        return this;
    }

}
