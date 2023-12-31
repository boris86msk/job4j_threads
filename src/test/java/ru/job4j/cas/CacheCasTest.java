package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class CacheCasTest {

    @Test
    public void wenAdd() {
        CacheCas cacheCas = new CacheCas();
        List<BaseDataCas> list = List.of(new BaseDataCas(1, 0),
                new BaseDataCas(2, 0), new BaseDataCas(3, 0));
        list.forEach(cacheCas::add);
        assertThat(cacheCas.getModel(1)).isNotNull();
        assertThat(cacheCas.getModel(2)).isNotNull();
        assertThat(cacheCas.getModel(3)).isNotNull();
        assertThat(cacheCas.getModel(4)).isNull();
    }

    @Test
    public void wenDelete() {
        CacheCas cacheCas = new CacheCas();
        List<BaseDataCas> list = List.of(new BaseDataCas(1, 0),
                new BaseDataCas(2, 0), new BaseDataCas(3, 0));
        list.forEach(cacheCas::add);
        list.forEach(cacheCas::delete);
        cacheCas.add(new BaseDataCas(4, 0));
        assertThat(cacheCas.getModel(1)).isNull();
        assertThat(cacheCas.getModel(3)).isNull();
        assertThat(cacheCas.getModel(4)).isNotNull();
    }

    @Test
    public void wenUpdate() {
        CacheCas cacheCas = new CacheCas();
        BaseDataCas baseData1 = new BaseDataCas(1, 0);
        BaseDataCas baseData2 = new BaseDataCas(1, 0);
        BaseDataCas baseData3 = new BaseDataCas(1, 1);
        BaseDataCas base = new BaseDataCas(2, 0);
        baseData1.setName("base_1");
        baseData2.setName("base_2");
        baseData3.setName("base_3");
        cacheCas.add(baseData1);
        cacheCas.add(base);
        cacheCas.update(baseData2);
        assertThat(cacheCas.getModel(1).getVersion()).isEqualTo(1);
        assertThat(cacheCas.getModel(1).getName()).isEqualTo("base_2");
        cacheCas.update(baseData3);
        assertThat(cacheCas.getModel(1).getVersion()).isEqualTo(2);
        assertThat(cacheCas.getModel(1).getName()).isEqualTo("base_3");
    }

    @Test
    public void wenThrowOptimisticException() {
        CacheCas cacheCas = new CacheCas();
        BaseDataCas baseData1 = new BaseDataCas(1, 0);
        BaseDataCas baseData2 = new BaseDataCas(1, 1);
        cacheCas.add(baseData1);
        assertThatThrownBy(() -> cacheCas.update(baseData2))
                .isInstanceOf(OptimisticException.class);

    }

}