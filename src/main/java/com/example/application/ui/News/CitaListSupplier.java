package com.example.application.ui.News;

import com.example.application.backend.entity.Cita;
import com.vaadin.flow.function.SerializableSupplier;
import java.util.function.Supplier;
import java.io.Serializable;

import java.util.List;

public class CitaListSupplier implements SerializableSupplier<List<? extends Cita>>, Serializable {
    private final List<Cita> citaList;

    public CitaListSupplier(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public List<? extends Cita> get() {
        return citaList;
    }
}
