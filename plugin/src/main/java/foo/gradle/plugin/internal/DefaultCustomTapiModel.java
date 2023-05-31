package foo.gradle.plugin.internal;


import foo.gradle.plugin.CustomTapiModel;

import java.io.Serializable;

public class DefaultCustomTapiModel implements CustomTapiModel, Serializable {

    private final String result;

    public DefaultCustomTapiModel(String result) {
        this.result = result;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Model(result=" + result + ")";
    }
}
