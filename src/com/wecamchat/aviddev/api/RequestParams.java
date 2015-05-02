package com.wecamchat.aviddev.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class RequestParams {
    private static String ENCODING = "UTF-8";

    protected HashMap<String, String> urlParams;
    protected HashMap<String, FileWrapper> fileParams;
    protected HashMap<String, ArrayList<String>> urlParamsWithArray;

    /**
     * Constructs a new empty <code>RequestParams</code> instance.
     */
    public RequestParams() {
        init();
    }

    /**
     * Constructs a new RequestParams instance containing the key/value string
     * params from the specified map.
     * 
     * @param source
     *            the source key/value string map to add.
     */
    public RequestParams(Map<String, String> source) {
        init();

        for (Map.Entry<String, String> entry : source.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Constructs a new RequestParams instance and populate it with a single
     * initial key/value string param.
     * 
     * @param key
     *            the key name for the intial param.
     * @param value
     *            the value string for the initial param.
     */
    public RequestParams(String key, String value) {
        init();

        put(key, value);
    }

    /**
     * Constructs a new RequestParams instance and populate it with multiple
     * initial key/value string param.
     * 
     * @param keysAndValues
     *            a sequence of keys and values. Objects are automatically
     *            converted to Strings (including the value {@code null}).
     * @throws IllegalArgumentException
     *             if the number of arguments isn't even.
     */
    public RequestParams(Object... keysAndValues) {
        init();
        int len = keysAndValues.length;
        if (len % 2 != 0)
            throw new IllegalArgumentException(
                    "Supplied arguments must be even");
        for (int i = 0; i < len; i += 2) {
            String key = String.valueOf(keysAndValues[i]);
            String val = String.valueOf(keysAndValues[i + 1]);
            put(key, val);
        }
    }

    /**
     * Adds a key/value string pair to the request.
     * 
     * @param key
     *            the key name for the new param.
     * @param value
     *            the value string for the new param.
     */
    public void put(String key, String value) {
        if (key != null && value != null && !value.equalsIgnoreCase("")) {
            urlParams.put(key, value);
        }
    }

    public void put(String key, int value) {
        if (key != null && value != -1) {
            urlParams.put(key, "" + value);
        }
    }

    public void put(String key, boolean value) {
        if (key != null) {
            urlParams.put(key, "" + value);
        }
    }

    /**
     * Adds a file to the request.
     * 
     * @param key
     *            the key name for the new param.
     * @param file
     *            the file to add.
     */
    public void put(String key, File file) throws FileNotFoundException {
        put(key, new FileInputStream(file), file.getName());
    }

    /**
     * Adds param with more than one value.
     * 
     * @param key
     *            the key name for the new param.
     * @param values
     *            is the ArrayList with values for the param.
     */
    public void put(String key, ArrayList<String> values) {
        if (key != null && values != null) {
            urlParamsWithArray.put(key, values);
        }
    }

    /**
     * Adds value to param which can have more than one value.
     * 
     * @param key
     *            the key name for the param, either existing or new.
     * @param value
     *            the value string for the new param.
     */
    public void add(String key, String value) {
        if (key != null && value != null) {
            ArrayList<String> paramArray = urlParamsWithArray.get(key);
            if (paramArray == null) {
                paramArray = new ArrayList<String>();
                this.put(key, paramArray);
            }
            paramArray.add(value);
        }
    }

    public void add(String key, long value) {
        if (key != null && value != -1) {
            ArrayList<String> paramArray = urlParamsWithArray.get(key);
            if (paramArray == null) {
                paramArray = new ArrayList<String>();
                this.put(key, paramArray);
            }
            paramArray.add("" + value);
        }
    }

    public void add(String key, double value) {
        if (key != null && value != -1) {
            ArrayList<String> paramArray = urlParamsWithArray.get(key);
            if (paramArray == null) {
                paramArray = new ArrayList<String>();
                this.put(key, paramArray);
            }
            paramArray.add("" + value);
        }
    }

    /**
     * Adds an input stream to the request.
     * 
     * @param key
     *            the key name for the new param.
     * @param stream
     *            the input stream to add.
     */
    public void put(String key, InputStream stream) {
        put(key, stream, null);
    }

    /**
     * Adds an input stream to the request.
     * 
     * @param key
     *            the key name for the new param.
     * @param stream
     *            the input stream to add.
     * @param fileName
     *            the name of the file.
     */
    public void put(String key, InputStream stream, String fileName) {
        put(key, stream, fileName, null);
    }

    /**
     * Adds an input stream to the request.
     * 
     * @param key
     *            the key name for the new param.
     * @param stream
     *            the input stream to add.
     * @param fileName
     *            the name of the file.
     * @param contentType
     *            the content type of the file, eg. application/json
     */
    public void put(String key, InputStream stream, String fileName,
            String contentType) {
        if (key != null && stream != null) {
            fileParams.put(key, new FileWrapper(stream, fileName, contentType));
        }
    }

    /**
     * Removes a parameter from the request.
     * 
     * @param key
     *            the key name for the parameter to remove.
     */
    public void remove(String key) {
        urlParams.remove(key);
        fileParams.remove(key);
        urlParamsWithArray.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        for (HashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue().inputStream);
        }

        for (HashMap.Entry<String, ArrayList<String>> entry : urlParamsWithArray
                .entrySet()) {
            if (result.length() > 0)
                result.append("&");

            ArrayList<String> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                if (i != 0)
                    result.append("&");
                result.append(entry.getKey());
                result.append("=");
                result.append(values.get(i));
            }
        }

        return result.toString();
    }

    private void init() {
        urlParams = new HashMap<String, String>();
        fileParams = new HashMap<String, FileWrapper>();
        urlParamsWithArray = new HashMap<String, ArrayList<String>>();
    }

    protected List<BasicNameValuePair> getParamsList() {
        List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();

        for (HashMap.Entry<String, String> entry : urlParams.entrySet()) {
            lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        for (HashMap.Entry<String, ArrayList<String>> entry : urlParamsWithArray
                .entrySet()) {
            ArrayList<String> values = entry.getValue();
            for (String value : values) {
                lparams.add(new BasicNameValuePair(entry.getKey(), value));
            }
        }

        return lparams;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(getParamsList(), ENCODING);
    }

    private static class FileWrapper {
        public InputStream inputStream;
        public String fileName;
        public String contentType;

        public FileWrapper(InputStream inputStream, String fileName,
                String contentType) {
            this.inputStream = inputStream;
            this.fileName = fileName;
            this.contentType = contentType;
        }

        public String getFileName() {
            if (fileName != null) {
                return fileName;
            } else {
                return "nofilename";
            }
        }
    }
}
