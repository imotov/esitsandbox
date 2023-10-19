package org.motovs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestDocument {
    public String foo;
    public String bar;

    @JsonCreator
    public TestDocument(@JsonProperty("foo") String foo, @JsonProperty("bar") String bar) {
        this.foo = foo;
        this.bar = bar;
    }
}
