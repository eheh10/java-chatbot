package com.domain.meta;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MetaTest {
    @Test
    void test(){
        //given
        Meta2 bannerMeta = Meta2.BANNER;
        Meta2 exitBanner = Meta2.EXIT;

        //when
        boolean actual =  bannerMeta.contain(exitBanner);

        //then
        assertThat(actual).isFalse();
    }

    @Test
    void test2(){
        // given
        Meta a = new Meta(Set.of("b","banner"));
        Meta b = new Meta(Set.of("b"));
        Meta c = new Meta(Set.of("banner"));
        Meta d = new Meta(Set.of("banner","b"));
        Meta e = new Meta(Set.of("b","a"));

        // when
        boolean aActual = Objects.equals(a,a); //equals 오버라이딩
        boolean bActual = Objects.equals(a,b);
        boolean cActual = Objects.equals(a,c);
        boolean dActual = Objects.equals(a,d);
        boolean eActual = Objects.equals(a,e);

        // then
        Assertions.assertThat(aActual).isTrue();
        Assertions.assertThat(bActual).isTrue();
        Assertions.assertThat(cActual).isTrue();
        Assertions.assertThat(dActual).isTrue();
        Assertions.assertThat(eActual).isFalse();
    }

    @Test
    void containTest(){
        Meta a = new Meta(Set.of("b","banner"));
        Meta b = new Meta(Set.of("b"));
        Meta c = new Meta(Set.of("banner"));
        Meta d = new Meta(Set.of("banner","b"));
        Meta e = new Meta(Set.of("b","a"));

        Assertions.assertThat(a.contain(a)).isTrue();
        Assertions.assertThat(a.contain(b)).isTrue();
        Assertions.assertThat(a.contain(c)).isTrue();
        Assertions.assertThat(a.contain(d)).isTrue();
        Assertions.assertThat(a.contain(e)).isFalse();
    }
}