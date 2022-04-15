package com.domain.meta;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Meta {
    public static final Meta BANNER = new Meta(Set.of("b","banner")); //set 으로 중복 방지
    public static final Meta EXIT = new Meta(Set.of("e","exit"));
    public static final Meta FILE = new Meta(Set.of("f","file"));
    public static final Meta UPDATE = new Meta(Set.of("u","update"));
    public static final Meta ERROR = new Meta(Set.of());

    private final Set<String> supports;

    public Meta(Set<String> supports) {
        this.supports = supports;
    }

    public boolean contain(Meta meta){
//        for (String m:meta.supports){
//            if (!this.supports.contains(m)){
//                return false;
//            }
//        }
//        return true;

        return Objects.equals(this,meta);

//        return this.getValues().stream()
//                .anyMatch(meta1.getValues()::contains);
    }

    @Override
    public boolean equals(Object meta){
        if (!(meta instanceof Meta)){
            return false;
        }

        Meta other = (Meta)meta;

        return this.supports.containsAll(other.supports);
    }

    public Meta combine(Meta meta){
        Set<String> newSupports = new HashSet<>(this.supports); //깊은 복사 - String은 불변객체이므로 List만 깊은 복사
        newSupports.addAll(meta.supports);
        return new Meta(newSupports);
    }


}
