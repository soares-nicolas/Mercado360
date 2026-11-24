package com.mercado360.model;

import java.util.Objects;

/** Classe abstrata: base comum de todas as entidades (guarda o id). */
public abstract class EntidadeBase {

    private Long id;

    protected EntidadeBase() { }

    protected EntidadeBase(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadeBase outra = (EntidadeBase) o;
        return id != null && Objects.equals(id, outra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }
}