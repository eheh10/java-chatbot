package com.domain.meta;

import java.util.ArrayList;
import java.util.List;

public abstract class Meta1 {
//    private final List<String> values;
//
//    public Meta(List<String> values) {
//        this.values = values;
//    }
//
//    public static Meta of(String... values){
//        return new Meta(Arrays.asList(values));
//    }

    public boolean contain(Meta1 meta1){
        return this.getValues().stream()
                .anyMatch(meta1.getValues()::contains);
    }

    public Meta1 combine(Meta1 meta1){
        return new Meta1() {
            @Override
            protected List<String> getValues() {
                List<String> mergeValues = new ArrayList<>();
                mergeValues.addAll(meta1.getValues());
                mergeValues.addAll(Meta1.this.getValues());
//                for (String s:meta.getValues()){
//                    mergeValues.add(s);
//                }

                return mergeValues;
            }
        };
    }

    abstract protected List<String> getValues();
}
